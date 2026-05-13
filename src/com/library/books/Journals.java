package com.library.books;

import com.library.constants.BookStatus;

import java.time.LocalDate;

public class Journals extends Book {


    public Journals(int bookID, String author, String bookName, double price, BookStatus status, String edition, LocalDate dateOfPurchase) {
        super(bookID, author, bookName, price, status, edition, dateOfPurchase);
    }
}
