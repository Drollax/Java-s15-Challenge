package com.library.app;

import com.library.books.Book;
import com.library.books.Category;
import com.library.constants.BookStatus;
import com.library.user.Author;



import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
            // 1. Kategorileri Oluştur
            Category roman = new Category(1L, "Roman");
            Category bilim = new Category(2L, "Bilim");

            // 2. Bir Yazar ve Kitap Oluştur (Başlangıçta Roman kategorisinde)
            Author author = new Author(10L, "Albert Einstein");
            Book book = new Book(101L, author, "İzafiyet Teorisi", 100.0,
                    BookStatus.AVAILABLE, "1. Baskı",
                    LocalDate.now(), roman);

            System.out.println("--- BAŞLANGIÇ DURUMU ---");
            printCategoryStatus(roman);
            printCategoryStatus(bilim);

            // 3. Kitabın kategorisini değiştirelim (Roman -> Bilim)
            System.out.println("\n>>> Kitabın kategorisi değiştiriliyor: Roman -> Bilim...");
            book.setCategory(bilim);

            System.out.println("\n--- GÜNCEL DURUM (Set Kontrolü) ---");
            printCategoryStatus(roman); // Burası boşalmış olmalı
            printCategoryStatus(bilim); // Kitap buraya geçmiş olmalı

            // 4. Kitabı tamamen kategorisiz bırakalım (Sistemden çıkarma provası)
            System.out.println("\n>>> Kitap kategoriden çıkarılıyor (null yapılıyor)...");
            book.setCategory(null);

            System.out.println("\n--- SON DURUM ---");
            printCategoryStatus(bilim); // Burası da boşalmış olmalı
        }

        // Kategorinin içindeki Set'i kontrol eden yardımcı metot
        private static void printCategoryStatus(Category cat) {
            System.out.println(cat.getCategoryName() + " Kategorisindeki Kitap Sayısı: " + cat.getBooks().size());
            for (Book b : cat.getBooks()) {
                System.out.println("   -> Sepetteki Kitap: " + b.getBookName());
            }
        }
}

