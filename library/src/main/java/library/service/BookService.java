package library.service;

import java.sql.SQLException;

public interface BookService {

    void createBook() throws SQLException;

    void removeBook() throws SQLException;

    void editBook() throws SQLException;

    void showAllBooks() throws SQLException;

    void addBookXML() throws SQLException;

    void close() throws SQLException;
}