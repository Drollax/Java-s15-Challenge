package com.library.user;

import com.library.books.Book;
import com.library.constants.BookStatus;
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


    public boolean canBorrowBook() {
        return noBooksIssued < maxBookLimit;
    }

    public void borrowBook(Book book) {
        // Kitap müsait mi ve kullanıcının limiti var mı?
        if (book.getStatus() == BookStatus.AVAILABLE) {
            if (canBorrowBook()) {
                borrowedBooks.add(book);
                noBooksIssued++; // Alınan kitap sayısını artır
                book.updateStatus(BookStatus.BORROWED);
                book.changeOwner(this);
            } else {
                System.out.println("Hata: " + getName() + " için maksimum kitap limitine (" + maxBookLimit + ") ulaşıldı.");
            }
        } else {
            System.out.println("Hata: '" + book.getBookName() + "' şu an mevcut değil.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            noBooksIssued--; // Sayacı azalt
            book.updateStatus(BookStatus.AVAILABLE);
            book.changeOwner(null);
            System.out.println(book.getBookName() + " iade edildi. Kalan kota: " + (maxBookLimit - noBooksIssued));
        } else {
            System.out.println("Hata: Bu kitap ödünç listesinde bulunamadı.");
        }
    }

    public void showBook() {
        System.out.println("--- " + getName() + " Tarafından Ödünç Alınan Kitaplar ---");
        if (borrowedBooks.isEmpty()) {
            System.out.println("Ödünç alınmış kitap yok.");
        } else {
            for (Book b : borrowedBooks) {
                b.display();
            }
        }
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