package com.spring.library.model;

import com.spring.library.modelData.AuthorData;
import com.spring.library.modelData.BookData;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authors = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Language language;

    private int downloads;
    private boolean copyright;

    public Book(BookData bookData) {
        this.title = bookData.title();
        this.language = bookData.language().getFirst();
        this.downloads = bookData.downloads();
        this.copyright = bookData.copyright();
    }

    protected Book() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorData> authors) {
        authors.forEach(book -> {
            Author author = new Author(book);
            author.setBook(this);
            this.authors.add(author);
        });
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", language=" + language +
                ", downloads=" + downloads +
                ", copyright=" + copyright +
                '}';
    }
}
