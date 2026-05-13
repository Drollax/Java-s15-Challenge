package com.library.user;

import com.library.books.Book;
import com.library.constants.BookStatus;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Person{
    private List<Book> borrowedBooks;
    private static final int MAX_BOOK_LIMIT = 5;
    private List<Book> boughtBooks;

    public Reader(String name){
        super(name);
        this.borrowedBooks = new ArrayList<>();
        this.boughtBooks = new ArrayList<>();
    }

    @Override
    public void whoYouAre(){
        System.out.println("I am a reader and my name is " + getName());
    }

    public void borrowBook(Book book){
      if(book.getStatus() == BookStatus.AVAILABLE ){
        if (borrowedBooks.size() < MAX_BOOK_LIMIT) {
            borrowedBooks.add(book);
            book.updateStatus(BookStatus.BORROWED);
            book.changeOwner(this);
        } else {
            System.out.println("You can not borrow more than 5 books");
        }

      } else {
          System.out.println("Book is not available.");
      }

    }

    public void purchaseBook(Book book){
        if(book.getStatus() == BookStatus.AVAILABLE){
            double price = book.getPrice();
            System.out.println("Receipt: " + price + " TL");
            boughtBooks.add(book);
            book.updateStatus(BookStatus.BOUGHT);
            book.changeOwner(this);
        } else {
            System.out.println("The book, you want to buy is not currently available to be bought");
        }
    }

    public void returnBoughtBook(Book book){
        if (boughtBooks.contains(book)){
            boughtBooks.remove(book);
            book.updateStatus(BookStatus.AVAILABLE);
            book.changeOwner(null);
            System.out.println("Book returned. Your refund : " + book.getPrice() + " TL");
        } else {
            System.out.println("There is no book in your bought list that is named: " + book.getBookName());
        }

    }

    public void returnBorrowedBook(Book book){
        if (borrowedBooks.contains(book)){
            borrowedBooks.remove(book);
            book.updateStatus(BookStatus.AVAILABLE);
            book.changeOwner(null);
            System.out.println("Book returned. Your refund : " + book.getPrice() + " TL");
        }else {
            System.out.println("There is no book in your borrowed list that is named: " + book.getBookName());
        }
    }

    public void showBook(){
        System.out.println("--------------------------Borrowed Books List --------------------------------");
        if (borrowedBooks.isEmpty()){
            System.out.println("You have no books that is borrowed");
        } else {

            for (Book b : borrowedBooks ){
                b.display();
            }
        }
    }
}
