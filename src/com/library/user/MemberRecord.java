package com.library.user;

import com.library.books.Book;
import com.library.constants.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberRecord extends Person {
    private Long memberId;
    private Type type; // Student veya Faculty
    private LocalDate dateOfMembership;
    private int noBooksIssued = 0;
    private int maxBookLimit = 5;
    private String address;
    private String phoneNo;
    private List<Book> borrowedBooks;

    public MemberRecord(Long personId, String name, String address, String phoneNo, Type type) {
        super(personId, name);
        this.memberId = personId;
        this.address = address;
        this.phoneNo = phoneNo;
        this.type = type;
        this.dateOfMembership = LocalDate.now();
        this.borrowedBooks = new ArrayList<>();
    }
    @Override
    public void whoYouAre(){

    }

    public void addBorrowedBook(Book book) {
        this.borrowedBooks.add(book);
        this.noBooksIssued = borrowedBooks.size();
    }

    public void removeBorrowedBook(Book book) {
        this.borrowedBooks.remove(book);
        this.noBooksIssued = borrowedBooks.size();
    }


    public Long getMemberId() { return memberId; }
    public int getNoBooksIssued() { return noBooksIssued; }
    public int getMaxBookLimit() { return maxBookLimit; }
    public List<Book> getBorrowedBooks() {return borrowedBooks;}
    public String getAddress() {return address;}
    public String getPhoneNo() {return phoneNo;}
    public LocalDate getDateOfMembership() {return dateOfMembership;}
    public Type getType() {return type;}
}