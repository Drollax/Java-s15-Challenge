package com.library.books;

import com.library.constants.BookStatus;
import com.library.user.Author;
import com.library.user.MemberRecord;

import java.time.LocalDate;


public class Book {
    private long bookID;
    private Author author;
    private String bookName;
    private double price;
    private BookStatus status;
    private String edition;
    private LocalDate dateOfPurchase;
    private MemberRecord owner;
    private Category category;


    public Book(long bookID, Author author, String bookName, double price, BookStatus status, String edition, LocalDate dateOfPurchase, Category category) {
        this.bookID = bookID;
        this.author = author;
        this.bookName = bookName;
        this.price = price;
        this.status = status;
        this.edition = edition;
        this.dateOfPurchase = dateOfPurchase;
        this.owner = null;
        setCategory(category);
    }

    public long getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category newCategory) {

        if (this.category != null) {
            this.category.removeBook(this);
        }

        this.category = newCategory;

        if (this.category != null) {
            this.category.addBook(this);
        }
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

        if (this.status == BookStatus.BORROWED && newStatus == BookStatus.BORROWED){

            System.out.println("Error: You can not borrow the book that is already borrowed");

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
        // Sahibi varsa ismini, yoksa kütüphanede olduğunu belirtir
        String ownerName = (owner != null) ? owner.getName() : "None (In Library)";

        // Kategorinin değerini kontrol eder
        String catName = (category != null) ? category.getCategoryName() : "No Category";

        System.out.println("---------------------------------------------------------");
        System.out.println("ID: " + bookID +
                " | Kitap: " + bookName +
                " | Yazar: " + author.getName());
        System.out.println("Tür: " + catName +
                " | Fiyat: " + price + " TL" +
                " | Durum: " + status +
                " | Sahibi: " + ownerName);
        System.out.println("---------------------------------------------------------");
    }

    public MemberRecord getOwner() {
        return owner;
    }

    public void changeOwner(MemberRecord newOwner){
        if (newOwner == null) {
            this.owner = null;
            System.out.println("The book has been returned to the library. Current owner: None");
        } else {
            this.owner = newOwner;
            System.out.println("The book is now assigned to: " + newOwner.getName());
        }


    }
}
