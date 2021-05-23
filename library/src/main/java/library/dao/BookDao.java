package library.dao;

import library.model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {

    boolean createBook(Book book) throws SQLException;

    boolean removeBook(long id) throws SQLException;

    boolean editBook(Long id, int choice, String newName) throws SQLException;

    List<Book> showAllBooks() throws SQLException;

}
