package com.library.books;

import java.util.HashSet;
import java.util.Set;

public class Category {
    private Long categoryId;
    private String categoryName;
    private Set<Book> books;

    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.books = new HashSet<>();
    }

    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }
    public void removeBook(Book book) {
        if (book != null) {
            books.remove(book);
        }
    }

    public Long getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public Set<Book> getBooks() { return books; }


}