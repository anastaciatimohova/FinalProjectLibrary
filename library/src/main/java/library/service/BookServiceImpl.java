package library.service;

import library.XML.ParserDOM;
import library.XML.ValidatorXML;
import library.dao.BookDao;
import library.dao.BookDaoImpl;
import library.model.Author;
import library.model.Book;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookServiceImpl implements BookService {

    BookDao bookDao = new BookDaoImpl();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void createBook() {
        try {
            Book book = new Book();
            System.out.println("Введите название книги:");
            book.setTitle(scanner.nextLine());
            System.out.println("Введите имя автора:");
            book.setAuthor(new Author(scanner.nextLine()));
            System.out.println("Введите ISBN:");
            book.setISBN(scanner.nextLine());
            book.setGenre(new GenreServiceImpl().genreChoice());
            if (bookDao.createBook(book)) {
                System.out.println("Книга успешно добавлена.");
            } else {
                System.out.println("Книга с таким именем или ISBN уже существует.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeBook() {
        try {
            long id = 0;
            System.out.println("Введите id книги, которую вы хотите удалить:");
            while (true) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Такого id не существует, попробуйте ещё раз:");
                }
            }
            bookDao.removeBook(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editBook() {
        try {
            long id = 0;
            System.out.println("Введите id книги, которую вы хотите изменить:");
            while (true) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Такого id не существует, попробуйте ещё раз:");
                }
                while (true) {
                    System.out.println("Выберите пункт, который вы бы хотели изменить:\n1.Название книги\n2.Автор книги" +
                            "\n3.Жанр книги\n4.ISBN книги");
                    try {
                        int choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 1:
                                System.out.println("Введите новое название книги:");
                                String newTitle = scanner.nextLine();
                                if (bookDao.editBook(id, choice, newTitle)) {
                                    System.out.println("Название книги успешно изменено.");
                                    return;
                                } else {
                                    System.out.println("Книга с таким названием уже существует, попробуйте ещё раз.");
                                }
                                break;
                            case 2:
                                System.out.println("Введите нового автора:");
                                String newAuthorName = scanner.nextLine();
                                bookDao.editBook(id, choice, newAuthorName);
                                System.out.println("Имя автора успешно изменено.");
                                return;
                            case 3:
                                bookDao.editBook(id, choice, new GenreServiceImpl().genreChoice().name());
                                System.out.println("Жанр успешно изменен.");
                                return;
                            case 4:
                                System.out.println("Введите новое ISBN:");
                                String newISBN = scanner.nextLine();
                                if (bookDao.editBook(id, choice, newISBN)) {
                                    System.out.println("ISBN книги успешно изменено.");
                                    return;
                                } else {
                                    System.out.println("Книга с таким ISBN уже существует, попробуйте ещё раз.");
                                }
                                break;
                        }
                        if (choice < 1 && choice > 4) {
                            System.out.println("Вы ввели неверное число, попробуйте ещё раз:");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Вы ввели неверный формат, попробуйте ещё раз:");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showAllBooks() {
        try {
            System.out.println("Ваши книги:");
            List<Book> books = bookDao.showAllBooks();
            for (Book book : books) {
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBookXML() throws SQLException {
        ValidatorXML validatorXML = new ValidatorXML();
        ParserDOM parserDOM = new ParserDOM();
        String xml = "D:\\Java_FreeIT\\final-project\\library\\src\\main\\java\\library\\files\\Books.xml";
        String xsd = "D:\\Java_FreeIT\\final-project\\library\\src\\main\\java\\library\\files\\Validator.xsd";
        File xmlFile = new File(xml);
        if (validatorXML.validatedXML(xml, xsd)) {
            try {
                ArrayList<Book> books = parserDOM.getBooks(xmlFile);
                for (Book book : books) {
                    if (bookDao.createBook(book)) {
                        System.out.println("Книга(и) успешно добавлена.");
                    } else {
                        System.out.println("Такая книга уже существует.");
                    }
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("В предоставленном файле указан неверный формат.");
        }
    }

    @Override
    public void close() {
        System.out.println("До новых встреч!");
    }
}
