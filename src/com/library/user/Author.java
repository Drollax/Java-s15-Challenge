package com.library.user;

import com.library.books.Book;
import java.util.ArrayList;
import java.util.List;

public class Author extends Person {

    private List<Book> booksWritten;

    public Author(String name) {
        super(name);
        this.booksWritten = new ArrayList<>();
    }

    @Override
    public void whoYouAre() {
        System.out.println("I am an author. My name is " + getName());
    }

    public void newBook(Book book) {
        booksWritten.add(book);
        System.out.println("New book added to author's collection: " + book.getBookName());
    }

    public void showBook() {
        System.out.println("Books written by " + getName() + ":");
        if (booksWritten.isEmpty()) {
            System.out.println("No books recorded for this author.");
        } else {
            for (Book b : booksWritten) {
                System.out.println("- " + b.getBookName());
            }
        }
    }
}