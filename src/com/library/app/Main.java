package com.library.app;

import com.library.books.Book;
import com.library.books.Category;
import com.library.constants.BookStatus;
import com.library.constants.Type;
import com.library.entity.Library;
import com.library.service.Bill;
import com.library.service.LoanService;
import com.library.user.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Library library = new Library("Gelecek Kütüphanesi");
    private static LoanService loanService = new LoanService();
    private static Scanner scanner = new Scanner(System.in);
    private static long authorIdCounter = 106L;
    private static long bookIdCounter = 5011L;
    private static long categoryIdCounter = 4L;

    public static void main(String[] args) {
        starterData();
        Librarian admin = new Librarian(1L, "Admin", "1234");
        library.addLibrarian(admin);

        System.out.println("--- " + library.getLibraryName() + " Sistemine Hoşgeldiniz ---");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Boşluk temizleme

            switch (choice) {
                case 1: kitapEkle(); break;
                case 2: kitapAra(); break;
                case 3: kitapSil(); break;
                case 4: kitaplariListele(); break;
                case 5: oduncAl(); break;
                case 6: iadeEt(); break;
                case 7: kitapGuncelle(); break;
                case 0:
                    running = false;
                    System.out.println("Sistemden çıkılıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1- Kitap Ekle");
        System.out.println("2- Kitap Ara (ID/İsim/Yazar)");
        System.out.println("3- Kitap Sil");
        System.out.println("4- Tüm Kitapları Listele");
        System.out.println("5- Kitap Ödünç Al");
        System.out.println("6- Kitap İade Et");
        System.out.println("7- Kitap Güncelle");
        System.out.println("0- Çıkış");
        System.out.print("Seçiminiz: ");
    }

    private static void kitapEkle() {
        System.out.print("Kitap İsmi: "); String title = scanner.nextLine();
        System.out.print("Yazar İsmi: "); String authorName = scanner.nextLine();
        System.out.print("Kategori İsmi: "); String categoryName = scanner.nextLine();
        System.out.print("Fiyat: "); double price = scanner.nextDouble();


        // yazarı aradım
        Author targetAuthor = null;
        for (Book b : library.getBookRepository().findAll()) {
            if (b.getAuthor().getName().equalsIgnoreCase(authorName)) {
                targetAuthor = b.getAuthor();
                break;
            }
        }

        if (targetAuthor == null) {
            targetAuthor = new Author(authorIdCounter++, authorName);
            System.out.println("Yeni yazar oluşturuldu (ID: " + targetAuthor.getPersonId() + ")");
        } else {
            System.out.println("Kitap mevcut yazara bağlandı (ID: " + targetAuthor.getPersonId() + ")");
        }

        //kategoride aynı şekilde
        Category targetCategory = null;
        for (Book b : library.getBookRepository().findAll()) {
            if (b.getCategory() != null && b.getCategory().getCategoryName().equalsIgnoreCase(categoryName)) {
                targetCategory = b.getCategory();
                break;
            }
        }

        if (targetCategory == null) {
            targetCategory = new Category(categoryIdCounter++, categoryName);
            System.out.println("-> Yeni kategori oluşturuldu (ID: " + targetCategory.getCategoryId() + ")");
        } else {
            System.out.println("-> Kitap mevcut kategoriye (" + targetCategory.getCategoryName()+ ") eklendi.");
        }


        Book newBook = new Book(bookIdCounter++, targetAuthor, title, price, BookStatus.AVAILABLE, "1.Baskı", LocalDate.now(), targetCategory);
        library.addBook(newBook);
        System.out.println("Kitap başarıyla eklendi.");
    }


    private static void kitapAra() {
        System.out.println("1- ID ile, 2- İsim ile, 3- Yazar ile, 4- Kategori ile");
        int subChoice = scanner.nextInt(); scanner.nextLine();

        if (subChoice == 1) {
            System.out.print("ID: "); long id = scanner.nextLong();
            Book b = library.getBookRepository().findById(id);
            if (b != null) b.display(); else System.out.println("Bulunamadı.");
        } else if (subChoice == 2) {
            System.out.print("İsim: "); String title = scanner.nextLine();
            library.getBookRepository().findByTitle(title).forEach(Book::display);
        } else if (subChoice == 3) {
            System.out.print("Yazar: "); String author = scanner.nextLine();
            library.getBookRepository().findByAuthor(author).forEach(Book::display);
        } else if (subChoice == 4) {
            System.out.print("Kategori İsmi: ");
            String categoryName = scanner.nextLine();

            //
            Category targetCategory = library.getBookRepository().findAll().stream()
                    .map(Book::getCategory)
                    .filter(c -> c != null && c.getCategoryName().equalsIgnoreCase(categoryName))
                    .findFirst()
                    .orElse(null);

            if (targetCategory != null) {
                System.out.println("\n--- " + targetCategory.getCategoryName() + " Kategorisindeki Kitaplar ---");
                library.getBookRepository().findByCategory(targetCategory).forEach(Book::display);
            } else {
                System.out.println("Sistemde bu isimle kayıtlı bir kategori bulunamadı.");
            }

        }
    }

    private static void kitapSil() {
        System.out.print("Silinecek Kitap ID: ");
        long id = scanner.nextLong();
        library.removeBook(id);
    }

    private static void kitaplariListele() {
        library.showBooks();
    }

    private static void oduncAl() {

        System.out.print("İşlem yapacak Üye ID: ");
        long memberId = scanner.nextLong();


        MemberRecord member = library.getMembers().stream()
                .filter(m -> m.getMemberId().equals(memberId))
                .findFirst()
                .orElse(null);

        if (member == null) {
            System.out.println("Hata: Bu ID'ye sahip bir üye bulunamadı!");
            return;
        }

        Librarian admin = library.getLibrarians().get(0);
        if (!admin.verifyMember(member)) {
            System.out.println("Hata: Üye doğrulaması başarısız!");
            return;
        }

        if (member.getNoBooksIssued() >= member.getMaxBookLimit()) {
            System.out.println("\n--- DİKKAT: LİMİT AŞIMI ---");
            System.out.println("Hata: " + member.getName() + " zaten " + member.getNoBooksIssued() +
                    " kitap almış. Limit: " + member.getMaxBookLimit());
            System.out.println("Yeni kitap alabilmek için önce elindeki kitaplardan birini iade etmelidir.");
            return;
        }


        System.out.print("Ödünç alınacak Kitap ID: ");
        long bookId = scanner.nextLong();
        Book book = library.getBookRepository().findById(bookId);

        if (book == null) {
            System.out.println("Hata: Kitap bulunamadı.");
            return;
        }


        Bill bill = loanService.borrowBook(member, book);
        if (bill != null) {
            System.out.println("\nİşlem Başarılı!");
            System.out.println("Üye: " + member.getName());
            System.out.println("Kitap: " + book.getBookName());
            System.out.println("Fatura Tutarı: " + bill.getAmount() + " TL");
            System.out.println("İşlem Tarihi: " + LocalDate.now());
        }
    }

    private static void iadeEt() {

        System.out.print("İade işlemini yapan Üye ID: ");
        long memberId = scanner.nextLong();


        MemberRecord member = library.getMembers().stream()
                .filter(m -> m.getMemberId().equals(memberId))
                .findFirst()
                .orElse(null);

        if (member == null) {
            System.out.println("Hata: Üye bulunamadı!");
            return;
        }
        // kişinin kitapları
        List<Book> borrowedBooks = member.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println(member.getName() + " isimli üyenin üzerinde ödünç kitap görünmüyor.");
            return;
        }

        System.out.println("\n--- " + member.getName() + " Üzerindeki Kitaplar ---");
        for (Book b : borrowedBooks) {
            System.out.println("ID: " + b.getBookID() + " | İsim: " + b.getBookName() + " | Fiyat: " + b.getPrice() + " TL");
        }


        System.out.print("İade edilecek kitapID: ");
        long bookId = scanner.nextLong();
        Book book = library.getBookRepository().findById(bookId);

        if (book == null) {
            System.out.println("Hata: Kitap bulunamadı.");
            return;
        }

        loanService.returnBook(member, book);

        System.out.println("\nİade İşlemi Başarılı!");
        System.out.println("Üye: " + member.getName());
        System.out.println("Kitap: " + book.getBookName());
        System.out.println("Geri Ödenen Tutar: " + book.getPrice() + " TL");
        System.out.println("Üyenin Kalan Kitap Sayısı: " + member.getNoBooksIssued());
    }
    private static void kitapGuncelle() {
        System.out.print("Güncellenecek Kitap ID: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Book book = library.getBookRepository().findById(id);

        if (book == null) {
            System.out.println("Hata: Bu ID ile bir kitap bulunamadı.");
            return;
        }

        System.out.println("\n--- Mevcut Bilgiler ---");
        book.display();
        System.out.println("-----------------------");

        System.out.println("Yeni bilgileri giriniz: ");

        System.out.print("Yeni İsim (Mevcut: " + book.getBookName() + "): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.isEmpty()) book.setBookName(newTitle);

        System.out.print("Yeni Yazar (Mevcut: " + book.getAuthor().getName() + "): ");
        String newAuthorName = scanner.nextLine();
        if (!newAuthorName.isEmpty()) {

            Author targetAuthor = null;
            for (Book b : library.getBookRepository().findAll()) {
                if (b.getAuthor().getName().equalsIgnoreCase(newAuthorName)) {
                    targetAuthor = b.getAuthor();
                    break;
                }
            }
            if (targetAuthor == null) {
                targetAuthor = new Author(authorIdCounter++, newAuthorName);
                System.out.println("-> Yeni yazar oluşturuldu.");
            }
            book.setAuthor(targetAuthor);
        }

        System.out.print("Yeni Kategori (Mevcut: " + (book.getCategory() != null ? book.getCategory().getCategoryName() : "Yok") + "): ");
        String newCatName = scanner.nextLine();
        if (!newCatName.isEmpty()) {
            Category targetCategory = null;
            for (Book b : library.getBookRepository().findAll()) {
                if (b.getCategory() != null && b.getCategory().getCategoryName().equalsIgnoreCase(newCatName)) {
                    targetCategory = b.getCategory();
                    break;
                }
            }
            if (targetCategory == null) {
                targetCategory = new Category(categoryIdCounter++, newCatName);
                System.out.println("Yeni kategori oluşturuldu.");
            }
            book.setCategory(targetCategory);
        }

        System.out.print("Yeni Fiyat (Mevcut: " + book.getPrice() + "): ");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) {
            book.setPrice(Double.parseDouble(priceInput));
        }

        System.out.println("\nKitap bilgileri başarıyla güncellendi.");
    }


    private static void starterData(){

        Category yazilim = new Category(1L, "Yazılım");
        Category edebiyat = new Category(2L, "Edebiyat");
        Category bilim = new Category(3L, "Bilim");

        Author author1 = new Author(101L, "Robert C. Martin");
        Author author2 = new Author(102L, "Sabahattin Ali");
        Author author3 = new Author(103L, "Stephen Hawking");
        Author author4 = new Author(104L, "Joshua Bloch");
        Author author5 = new Author(105L, "George Orwell");

        library.addBook(new Book(5001L, author1, "Clean Code", 150.0, BookStatus.AVAILABLE, "1.Baskı", LocalDate.now(), yazilim));
        library.addBook(new Book(5002L, author1, "Clean Architecture", 180.0, BookStatus.AVAILABLE, "2.Baskı", LocalDate.now(), yazilim));
        library.addBook(new Book(5003L, author4, "Effective Java", 210.0, BookStatus.AVAILABLE, "3.Baskı", LocalDate.now(), yazilim));
        library.addBook(new Book(5004L, author2, "Kürk Mantolu Madonna", 65.0, BookStatus.AVAILABLE, "50.Baskı", LocalDate.now(), edebiyat));
        library.addBook(new Book(5005L, author2, "İçimizdeki Şeytan", 70.0, BookStatus.AVAILABLE, "12.Baskı", LocalDate.now(), edebiyat));
        library.addBook(new Book(5006L, author5, "1984", 90.0, BookStatus.AVAILABLE, "20.Baskı", LocalDate.now(), edebiyat));
        library.addBook(new Book(5007L, author5, "Hayvan Çiftliği", 55.0, BookStatus.AVAILABLE, "15.Baskı", LocalDate.now(), edebiyat));
        library.addBook(new Book(5008L, author3, "Zamanın Kısa Tarihi", 110.0, BookStatus.AVAILABLE, "10.Baskı", LocalDate.now(), bilim));
        library.addBook(new Book(5009L, author3, "Büyük Sorulara Kısa Yanıtlar", 95.0, BookStatus.AVAILABLE, "1.Baskı", LocalDate.now(), bilim));
        library.addBook(new Book(5010L, author3, "Kara Delikler", 85.0, BookStatus.AVAILABLE, "5.Baskı", LocalDate.now(), bilim));


        library.addMember(new Student(2001L, "Ahmet Yılmaz", "İstanbul", "555-0101", "STU-001"));
        library.addMember(new Student(2002L, "Ayşe Demir", "Ankara", "555-0202", "STU-002"));
        library.addMember(new Faculty(2003L, "Yazılım", "Ankara", "555-0202", "FC-101"));
        library.addMember(new MemberRecord(2004L, "Canberk Bey", "İzmir", "555-0303", Type.MEMBER));

        System.out.println("yüklendi");
    }

}

