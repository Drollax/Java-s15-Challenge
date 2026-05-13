package com.library.app;

import com.library.books.Book;
import com.library.books.Category;
import com.library.constants.BookStatus;
import com.library.constants.Type;
import com.library.entity.Library;
import com.library.service.Bill;
import com.library.service.LoanService;
import com.library.user.Author;
import com.library.user.Librarian;
import com.library.user.MemberRecord;
import com.library.user.Student;


import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // 1. Kütüphane ve Servis Kurulumu
        Library library = new Library("Merkez Kütüphanesi");
        LoanService loanService = new LoanService();

        // 2. Personel ve Üye Oluşturma
        Librarian admin = new Librarian(1L, "Ayşe Yılmaz", "secret123");
        Student student = new Student(10L, "Can Demir", "İstanbul/Beşiktaş", "555-1234", "S-2024-001");
        MemberRecord guest = new MemberRecord(20L, "Mehmet Bey", "Ankara/Çankaya", "555-9876", Type.MEMBER);

        library.addLibrarian(admin);
        library.addMember(student);
        library.addMember(guest);

        // 3. Kitap ve Kategori Hazırlığı
        Category bilim = new Category(101L, "Bilim");
        Author hawking = new Author(50L, "Stephen Hawking");
        Book book1 = new Book(500L, hawking, "Zamanın Kısa Tarihi", 45.0, BookStatus.AVAILABLE, "10. Baskı", LocalDate.now(), bilim);
        Book book2 = new Book(501L, hawking, "Büyük Sorulara Kısa Yanıtlar", 35.0, BookStatus.AVAILABLE, "3. Baskı", LocalDate.now(), bilim);

        library.addBook(book1);
        library.addBook(book2);

        System.out.println("\n--- TEST BAŞLIYOR ---");

        // 4. Kütüphaneci Üyeyi Doğruluyor (Diyagramdaki verifyMember)
        if (admin.verifyMember(student)) {
            System.out.println("Doğrulama Başarılı: " + student.getName() + " işlem yapabilir.");
        }

        // 5. Kitap Ödünç Verme (LoanService Denetimi)
        System.out.println("\n>>> Kitap Ödünç Veriliyor...");
        Bill bill = loanService.borrowBook(student, book1);

        if (bill != null) {
            System.out.println("Fatura Oluşturuldu: ID " + bill.getBillNo() + " | Tutar: " + bill.getAmount() + " TL");
        }

        // 6. Durum Kontrolü
        System.out.println("\n--- GÜNCEL DURUMLAR ---");
        System.out.println("Kitap Durumu: " + book1.getStatus()); // NOT_AVAILABLE olmalı
        System.out.println("Öğrencinin Aldığı Kitap Sayısı: " + student.getNoBooksIssued());
        System.out.println("Kitabın Yeni Sahibi: " + book1.getOwner().getName());

        // 7. İade İşlemi
        System.out.println("\n>>> Kitap İade Ediliyor...");
        loanService.returnBook(student, book1);

        // 8. Sonuç Kontrolü
        System.out.println("İade Sonrası Kitap Durumu: " + book1.getStatus()); // AVAILABLE olmalı
        System.out.println("Öğrencinin Kalan Kitap Sayısı: " + student.getNoBooksIssued());

        System.out.println("\n--- TEST TAMAMLANDI ---");
    }
}

