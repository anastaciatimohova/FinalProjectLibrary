package library.dao;

import library.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDaoImpl implements GenreDao {
    DBUtils dbUtils = new DBUtils();

    @Override
    public Long editGenre(String newName) throws SQLException {

        Connection connection = dbUtils.getConnection();
        PreparedStatement statement1 = connection.prepareStatement("select id from genre where name_genre = ?");
        statement1.setString(1, newName);
        ResultSet resultSet = statement1.executeQuery();

        if (resultSet.next()) {
            long result1 = resultSet.getLong(1);
            connection.close();
            return result1;
        } else {
            PreparedStatement statement2 = connection.prepareStatement("insert genre (name_genre) values (?)");
            statement2.setString(1, newName);
            statement2.executeUpdate();

            PreparedStatement statement3 = connection.prepareStatement("select id from genre where name_genre = ?");
            statement3.setString(1, newName);
            ResultSet resultSet3 = statement3.executeQuery();
            if (resultSet3.next()) {
                long result3 = resultSet3.getLong(1);
                connection.close();
                return result3;
            } else {
                connection.close();
                return null;
            }
        }
    }
}
