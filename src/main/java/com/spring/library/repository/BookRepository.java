package com.spring.library.repository;

import com.spring.library.model.Author;
import com.spring.library.model.Book;
import com.spring.library.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    List<Book> findByLanguage(Language language);

    @Query("SELECT a FROM Author a")
    List<Author> findAuthors();

    @Query("SELECT a FROM Author a WHERE a.birthYear < :year AND a.deathYear > :year")
    List<Author> findAuthorBydeathYearLessThan(int year);
}
