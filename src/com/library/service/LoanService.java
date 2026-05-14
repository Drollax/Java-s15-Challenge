package com.library.service;

import com.library.books.Book;
import com.library.constants.BookStatus;
import com.library.user.MemberRecord;
import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private List<Bill> bills;
    private long billCounter = 0;

    public LoanService() {
        this.bills = new ArrayList<>();
    }

    public Bill borrowBook(MemberRecord member, Book book) {
        // limit ve durumun kontrolü
        if (member.getNoBooksIssued() >= member.getMaxBookLimit()) {
            System.out.println("Hata: " + member.getName() + " maksimum kitap limitine ulaştı!");
            return null;
        }

        if (book.getStatus() != BookStatus.AVAILABLE) {
            System.out.println("Hata: " + book.getBookName() + " şu an mevcut değil.");
            return null;
        }

        // ödünç alma
        book.updateStatus(BookStatus.BORROWED); // Durum NOT_AVAILABLE olur
        book.changeOwner(member);               // Owner set edilir
        member.getBorrowedBooks().add(book);


        Bill newBill = new Bill(++billCounter, book.getPrice());
        bills.add(newBill);

        System.out.println("İşlem Başarılı! Fatura No: " + newBill.getBillNo());
        return newBill;

    }

    // geri iade
    public void returnBook(MemberRecord member, Book book) {
        if (member.getBorrowedBooks().contains(book)) {
            book.updateStatus(BookStatus.AVAILABLE);
            book.changeOwner(null);
            member.getBorrowedBooks().remove(book);

            System.out.println(book.getBookName() + " iade edildi. Kayıtlar güncellendi.");
        } else {
            System.out.println("Hata: Bu kitap bu üyede görünmüyor.");
        }
    }

    public List<Bill> getBillHistory() {
        return bills;
    }
}