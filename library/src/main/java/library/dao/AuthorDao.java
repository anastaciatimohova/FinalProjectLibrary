package library.dao;

import java.sql.SQLException;

public interface AuthorDao {

    Long editAuthor(String newName) throws SQLException;
}
