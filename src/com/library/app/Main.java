package com.library.app;

import com.library.books.Book;
import com.library.constants.BookStatus;


import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // 1. Bir kitap nesnesi oluşturalım
        // Parametreler: ID, Yazar, İsim, Fiyat, Statü, Edisyon, Satın Alma Tarihi
        Book testKitabi = new Book(
                101,
                "Jose Saramago",
                "Körlük",
                150.50,
                BookStatus.AVAILABLE,
                "5. Baskı",
                LocalDate.of(2023, 10, 25)
        );

        // 2. Bilgileri ekrana yazdırarak test edelim
        System.out.println("--- Kitap Testi Başarılı ---");
        System.out.println("Kitap Adı: " + testKitabi.getBookName()); // Yazdığın metoda göre
        System.out.println("Yazar: " + testKitabi.getAuthor());
        System.out.println("Fiyat: " + testKitabi.getPrice() + " TL");
        System.out.println("Durum: " + testKitabi.getStatus());
        System.out.println("Satın Alma Tarihi: " + testKitabi.getDateOfPurchase());

        // 3. Status güncellemeyi test edelim
        testKitabi.updateStatus(BookStatus.BORROWED);
        System.out.println("Yeni Durum: " + testKitabi.getStatus());
    }

}
