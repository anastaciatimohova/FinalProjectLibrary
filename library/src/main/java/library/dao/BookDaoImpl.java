package library.dao;

import library.model.Author;
import library.model.Book;
import library.model.Genre;
import library.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private DBUtils dbUtils = new DBUtils();

    @Override
    public boolean createBook(Book book) throws SQLException {

        Connection connection = dbUtils.getConnection();

        if (chekOutTitle(book.getTitle())) {
            return false;
        }
        if (chekOutISBN(book.getISBN())) {
            return false;
        }
        AuthorDao authorDao = new AuthorDaoImpl();
        Long authorID = authorDao.editAuthor(book.getAuthor().getName());

        GenreDao genreDao = new GenreDaoImpl();
        Long genreID = genreDao.editGenre(book.getGenre().name());

        PreparedStatement statement = connection.prepareStatement("insert books (title, author_id, isbn, genre_id) " +
                "values ((?), (?), (?), (?))");
        statement.setString(1, book.getTitle());
        statement.setString(2, String.valueOf(authorID));
        statement.setString(3, book.getISBN());
        statement.setString(4, String.valueOf(genreID));
        int i = statement.executeUpdate();

        dbUtils.closeConnection(connection);

        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeBook(long id) throws SQLException {
        Connection connection = dbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement("delete from books where id = ?");
        statement.setString(1, String.valueOf(id));
        int i1 = statement.executeUpdate();

        if (i1 > 0) {
            System.out.println("Указанная книга удалена.");
            return true;
        } else {
            System.out.println("Книги с таким id не существует.");
            return false;
        }
    }

    @Override
    public boolean editBook(Long id, int choice, String newName) throws SQLException {
        AuthorDao authorDao = new AuthorDaoImpl();
        GenreDao genreDao = new GenreDaoImpl();
        switch (choice) {
            case 1:
                if (chekOutTitle(newName)) {
                    return false;
                }
                Connection connection1 = dbUtils.getConnection();
                PreparedStatement statement1 = connection1.prepareStatement("update books set title = ? where id = ?");
                statement1.setString(1, newName);
                statement1.setString(2, String.valueOf(id));
                statement1.executeUpdate();
                connection1.close();
                return true;
            case 2:
                Long idAuthor = authorDao.editAuthor(newName);
                if (idAuthor != null) {
                    Connection connection2 = dbUtils.getConnection();
                    PreparedStatement statement2 = connection2.prepareStatement("update books set author_id = ? where id = ?");
                    statement2.setString(1, String.valueOf(idAuthor));
                    statement2.setString(2, String.valueOf(id));
                    statement2.executeUpdate();
                    connection2.close();
                    return true;
                }
            case 3:
                Long idGenre = genreDao.editGenre(newName);
                if (idGenre != null) {
                    Connection connection3 = dbUtils.getConnection();
                    PreparedStatement statement3 = connection3.prepareStatement("update books set genre_id = ? where id = ?");
                    statement3.setString(1, String.valueOf(idGenre));
                    statement3.setString(2, String.valueOf(id));
                    statement3.executeUpdate();
                    connection3.close();
                    return true;
                }
            case 4:
                if (chekOutISBN(newName)) {
                    return false;
                }
                Connection connection4 = dbUtils.getConnection();
                PreparedStatement statement4 = connection4.prepareStatement("update books set isbn = ? where id = ?");
                statement4.setString(1, newName);
                statement4.setString(2, String.valueOf(id));
                statement4.executeUpdate();
                connection4.close();
                return true;
        }
        return false;
    }

    @Override
    public List<Book> showAllBooks() throws SQLException {
        Connection connection = dbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement("select books.id, books.title, author.name_author,genre.name_genre, books.isbn " +
                "from books, genre, author where books.genre_id = genre.id and books.author_id = author.id");
        ResultSet resultSet = statement.executeQuery();
        return setBook(resultSet, connection);
    }

    private boolean chekOutTitle(String nameTitle) throws SQLException {
        Connection connection = dbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement("select title from books where title = ?");
        statement.setString(1, nameTitle);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    private boolean chekOutISBN(String isbn) throws SQLException {
        Connection connection = dbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement("select isbn from books where isbn = ?");
        statement.setString(1, isbn);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    private List<Book> setBook(ResultSet resultSet, Connection connection) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong(1));
            book.setTitle(resultSet.getString(2));
            book.setAuthor(new Author(resultSet.getString(3)));
            book.setGenre(Genre.valueOf(resultSet.getString(4)));
            book.setISBN(resultSet.getString(5));
            books.add(book);
        }
        connection.close();
        return books;
    }
}
