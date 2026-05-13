package com.library.service;

import com.library.books.Book;


import java.util.*;

public class Library {
    private Map<Long, Book> books = new HashMap<>();


    public Map<Long, Book> getBooks() {
        return books;
    }

    public void addNewBook(Book book){
        if(book == null) return;

        if(books.containsKey(book.getBookID())){
            System.out.println("Error : This ID is already used =" + book.getBookID()  );
        } else {
            books.put(book.getBookID(), book);
            System.out.println("Book succesfully added");
        }

    }

    public void lendBook(Book book){

    }
}
