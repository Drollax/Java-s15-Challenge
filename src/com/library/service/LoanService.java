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

    // Diyagramdaki borrowBook metodu
    public Bill borrowBook(MemberRecord member, Book book) {
        // Genel Kurallar 1 & 2: Limit ve Durum Kontrolü
        if (member.getNoBooksIssued() >= member.getMaxBookLimit()) {
            System.out.println("Hata: " + member.getName() + " maksimum kitap limitine ulaştı!");
            return null;
        }

        if (book.getStatus() != BookStatus.AVAILABLE) {
            System.out.println("Hata: " + book.getBookName() + " şu an mevcut değil.");
            return null;
        }

        // Genel Kurallar 3 & 4: Ödünç Alma İşlemi
        book.updateStatus(BookStatus.BORROWED); // Durum NOT_AVAILABLE olur
        book.changeOwner(member);               // Owner set edilir
        member.getBorrowedBooks().add(book);
        // MemberRecord içindeki noBooksIssued artırımı burada veya member.borrowBook içinde yapılabilir.

        // Fatura Oluşturma
        Bill newBill = new Bill(++billCounter, book.getPrice());
        bills.add(newBill);

        System.out.println("İşlem Başarılı! Fatura No: " + newBill.getBillNo());
        return newBill;
    }

    // Diyagramdaki returnBook metodu
    public void returnBook(MemberRecord member, Book book) {
        if (member.getBorrowedBooks().contains(book)) {
            // Genel Kurallar 5 & 6: İade İşlemi
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