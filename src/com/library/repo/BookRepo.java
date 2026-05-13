package com.library.repo;

import com.library.books.Book;
import com.library.books.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookRepo {
    // Diyagramdaki - books : Map<Long, Book> alanı
    private Map<Long, Book> books;

    public BookRepo() {
        this.books = new HashMap<>();
    }

    // --- Temel Kayıt İşlemleri ---
    public void addBook(Book book) {
        if (book != null) {
            books.put(book.getBookID(), book);
            System.out.println("Depoya eklendi: " + book.getBookName());
        }
    }

    public void removeBook(long bookId) {
        if (books.containsKey(bookId)) {
            Book removedBook = books.remove(bookId);
            // Kitabı kategorisinden de temizleyelim
            if (removedBook.getCategory() != null) {
                removedBook.getCategory().removeBook(removedBook);
            }
            System.out.println("ID " + bookId + " olan kitap depodan silindi.");
        }
    }

    // --- Diyagramdaki Arama Metodları ---

    public Book findById(long bookId) {
        return books.get(bookId);
    }

    public List<Book> findByTitle(String title) {
        return books.values().stream()
                .filter(b -> b.getBookName().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String authorName) {
        return books.values().stream()
                .filter(b -> b.getAuthor().getName().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
    }

    // Daha önce konuştuğumuz Set mantığını burada verimli kullanıyoruz
    public List<Book> findByCategory(Category category) {
        if (category != null) {
            return new ArrayList<>(category.getBooks());
        }
        return new ArrayList<>();
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
}
