package library;

import library.service.BookService;
import library.service.BookServiceImpl;

import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) throws SQLException {
        Application application = new Application();
        application.start();
    }
}