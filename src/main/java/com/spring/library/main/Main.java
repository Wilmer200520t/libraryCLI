package com.spring.library.main;

import com.spring.library.repository.BookRepository;

public class Main {
    private BookRepository bookRepository;

    public Main(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void run(){
        System.out.println("Running Application");
    }
}
