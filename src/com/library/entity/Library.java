package com.library.entity;

import com.library.books.Book;
import com.library.books.Category;
import com.library.repo.BookRepo;
import com.library.user.Librarian;
import com.library.user.MemberRecord;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private String libraryName;
    private BookRepo bookRepo; // Diyagramdaki Composition ilişkisi
    private List<MemberRecord> members;
    private List<Librarian> librarians;

    public Library(String libraryName) {
        this.libraryName = libraryName;
        this.bookRepo = new BookRepo(); // Kütüphane oluşurken depo da oluşur
        this.members = new ArrayList<>();
        this.librarians = new ArrayList<>();
    }

    // --- Kitap Yönetimi ---
    public void addBook(Book book) {
        bookRepo.addBook(book);
    }

    public void removeBook(long bookId) {
        bookRepo.removeBook(bookId);
    }

    // --- Üye ve Kütüphaneci Yönetimi ---
    public void addMember(MemberRecord member) {
        if (!members.contains(member)) {
            members.add(member);
            System.out.println(member.getName() + " kütüphaneye üye olarak eklendi.");
        }
    }

    public void addLibrarian(Librarian librarian) {
        if (!librarians.contains(librarian)) {
            librarians.add(librarian);
            System.out.println("Yeni görevli eklendi: " + librarian.getName());
        }
    }

    // --- Bilgi Gösterimi ---
    public void showBooks() {
        System.out.println("\n--- " + libraryName + " Kitap Listesi ---");
        for (Book book : bookRepo.findAll()) {
            book.display();
        }
    }

    // --- Getter Metodları ---
    public BookRepo getBookRepository() { return bookRepo; }
    public List<MemberRecord> getMembers() { return members; }
    public List<Librarian> getLibrarians() { return librarians; }
    public String getLibraryName() { return libraryName; }
}