package library;

import library.service.BookService;
import library.service.BookServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void start() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        BookService bookService = new BookServiceImpl();
        boolean exit = true;
        while (exit) {
            System.out.println("Выберите действие (введите цифру из нижеперечисленных):");
            System.out.println("1. Добавить книгу.");
            System.out.println("2. Редактировать книгу.");
            System.out.println("3. Удалить книгу.");
            System.out.println("4. Вывести все книги.");
            System.out.println("5. Добавить книгу (книги) через XML.");
            System.out.println("6. Выйти из приложения.");
            int num = Integer.parseInt(scanner.nextLine());
            switch (num) {
                case 1:
                    bookService.createBook();
                    break;
                case 2:
                    bookService.editBook();
                    break;
                case 3:
                    bookService.removeBook();
                    break;
                case 4:
                    bookService.showAllBooks();
                    break;
                case 5:
                    bookService.addBookXML();
                    break;
                case 6:
                    bookService.close();
                    exit = false;
                    break;
            }
        }
    }
}
