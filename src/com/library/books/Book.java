package com.library.books;//getOwner, display, changeOwner yazılmalı

import com.library.constants.BookStatus;
import com.library.user.Reader;

import java.time.LocalDate;

public class Book {
    private int bookID;
    private String author;
    private String bookName;
    private double price;
    private BookStatus status;
    private String edition;
    private LocalDate dateOfPurchase;
    private Reader owner;


    public Book(int bookID, String author, String bookName, double price, BookStatus status, String edition, LocalDate dateOfPurchase) {
        this.bookID = bookID;
        this.author = author;
        this.bookName = bookName;
        this.price = price;
        this.status = status;
        this.edition = edition;
        this.dateOfPurchase = dateOfPurchase;
        this.owner = null;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void updateStatus(BookStatus newStatus) {

        if (this.status == BookStatus.BOUGHT && newStatus == BookStatus.BORROWED){

            System.out.println("Error: You can not borrow or reserve a book, that is bought");

        } else
            {
                this.status = newStatus;
                System.out.println("The book status has been updated to: " + newStatus);
            }
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void display() {
        String ownerName = (owner != null) ? owner.getName() : "None (In Library)";
        System.out.println("ID: " + bookID + " | Ad: " + bookName + " | Owner: " + ownerName + " | Status: " + status);
    }

    public Reader getOwner() {
        return owner;
    }

    public void changeOwner(Reader newOwner){
        if (newOwner == null) {
            this.owner = null;
            System.out.println("The book has been returned to the library. Current owner: None");
        } else {
            this.owner = newOwner;
            System.out.println("The book is now assigned to: " + newOwner.getName());
        }

    }
}
