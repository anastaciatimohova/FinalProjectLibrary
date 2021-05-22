package library.dao;

import java.sql.SQLException;

public interface GenreDao {
    Long editGenre(String newName) throws SQLException;
}
