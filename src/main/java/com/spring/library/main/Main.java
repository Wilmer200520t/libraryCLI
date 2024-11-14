package com.spring.library.main;

import com.spring.library.model.Author;
import com.spring.library.model.Book;
import com.spring.library.model.Language;
import com.spring.library.modelData.BookData;
import com.spring.library.modelData.ResponseData;
import com.spring.library.repository.BookRepository;
import com.spring.library.utils.DataConvert;
import com.spring.library.utils.HttpClientApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final String BASE_URL = "https://gutendex.com/books/";
    private BookRepository bookRepository;
    private Scanner scanner = new Scanner(System.in);
    private HttpClientApi httpClient = new HttpClientApi();
    private DataConvert dataConvert = new DataConvert();

    public Main(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public void run(){
        int choice;
        boolean isRuning = true;
        System.out.println("Welcome to Spring Boot Library!");

        while (isRuning) {
            choice = showMenu();

            switch(choice) {
                case 1:
                    searchAndSaveBooks();
                    break;
                case 2:
                    showAllBooks();
                    break;
                case 3:
                    showAllAuthors();
                    break;
                case 4:
                    showAuthorsInRangeAge();
                    break;
                case 5:
                    ShowBooksByLanguage();
                    break;
                default:
                    isRuning = false;
            }
        }

    }

    private void ShowBooksByLanguage() {
        String message = """
                Opciones disponibls('**'):
                en - Ingles
                es - Español
                pt - Portugues
                de - Aleman
                fr - Frances
                it - Italiano
                """;
        System.out.println(message);
        String language = scanner.nextLine();

        List<Book> books = bookRepository.findByLanguage(Language.getLanguage(language));

        System.out.println("Lista de libros en el idioma " + Language.getLanguage(language) + ":");
        books.forEach(System.out::println);
    }

    private void showAuthorsInRangeAge() {
        int option;
        try {
            System.out.println("Introduzca el año:");
            option = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Entrada no esperada, Error: " + e.getMessage());
            return;
        }

        List<Author> authors = bookRepository.findAuthorBydeathYearLessThan(option);

        if (authors.isEmpty()) {
            System.out.println("Ningun autor vivo en dicho año.");
            return;
        }
        System.out.println("Lista de actores vivos en el año " + option + ":");
        authors.forEach(System.out::println);

    }

    private void showAllAuthors() {
        try {
            List<Author> authors = bookRepository.findAuthors();
            authors.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void showAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            System.out.println("List of books:");
            books.forEach(
                    book -> System.out.println(book.toString())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void searchAndSaveBooks() {
        List<Book> books = new ArrayList<>();

        System.out.println("---------------------------------------------------------");
        System.out.println("Escriba el nombre del libro: ");
        String bookName = scanner.nextLine();
        bookName = processInput(bookName);

        if (bookName == null) return;

        String response = httpClient.getData(BASE_URL + "?search=" + bookName);

        ResponseData responseData = dataConvert.getDataFromJson(response, ResponseData.class);

        if (responseData.count() == 0) {
            System.out.println("Libro no encontrado.");
            return;
        }

        List<BookData> bookData = responseData.books();

        try {
            Optional<BookData> firstBook =  bookData.stream()
                    .findFirst();

            if (firstBook.isPresent()) {
                var book = firstBook.get();
                Book bookFind = new Book(book);
                Optional<Book> bookOptional = bookRepository.findByTitle(bookFind.getTitle());

                if (bookOptional.isPresent()) {
                    System.out.println("Libro ya registrado.");
                    return;
                }

                bookFind.setAuthors(book.author());
                bookRepository.save(bookFind);

            }

        } catch (Exception e) {
            System.out.println("Error al obtener o guardar el libro: " + e.getMessage());
        }

    }

    private int showMenu(){
       int option = 0;
       String message = """
               
               Opciones: 
               1.- Buscar y guardar libro por titulo
               2.- Listar libros registrados
               3.- Listar Autores registrados
               4.- Listar Autores vivos en un rango de años
               5.- Listar libro por idioma
               
               6.- Salir
               """;

       System.out.println(message);
       System.out.println("---------------------------------------------------------");
       System.out.println("---------------------------------------------------------");
       String input = scanner.nextLine();

       try {
           option = Integer.parseInt(input);
       } catch (Exception e) {
           System.out.println("Entrada no esperada, Error: " + e.getMessage());
           option = showMenu();
       }

       return option;
    }

    private String processInput(String input) {
        if(input.isEmpty()) {
            System.out.println("No ah digitado ningun nombre de serie.");
            return null;
        }

        //Procesing Input
        input = input.trim();
        input = input.toLowerCase();
        input = input.replaceAll(" {3}", "%20");
        input = input.replaceAll(" {2}", "%20");
        input = input.replaceAll(" ", "%20");

        return input;
    }
}
