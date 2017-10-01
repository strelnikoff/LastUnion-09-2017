package lastunion.application.DAO;

import lastunion.application.Models.UserModel;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDAO {
    public enum FieldToUpdate{
        ID,
        NAME,
        EMAIL,
        PASSWORD,
        SCORE
    }

    JdbcTemplate jdbcTemplate;

    public UserDAO(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public UserModel getUserById(final Integer id)  {
        final String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new UserModel(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("score")
                )
        );
    }

    public UserModel getUserByName(final String name)  {
        final String sql = "SELECT * FROM users WHERE login = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{name}, (rs, rowNum) ->
                new UserModel(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("score")
                )
        );
    }

    public void modifyUser(UserModel user, UserModel changedUser) {
        StringBuilder builder = new StringBuilder ("UPDATE users set login = ");
        builder.append(changedUser.getUserName());
        builder.append(" email = ");
        builder.append(changedUser.getUserEmail());
        builder.append(',');
        builder.append("password = ");
        builder.append(changedUser.getUserPasswordHash());
        builder.append(',');
        builder.append(" score = ");
        builder.append(changedUser.getUserHighScore());
        builder.append(')');

        executeQuery(builder.toString());
    }

    public void saveUser(UserModel user)  {
        final String sql = "INSERT INTO users (login, email, password) VALUES(?, ?, ?)";
        StringBuilder builder = new StringBuilder("INSERT INTO users (login, email, password) VALUES(");
        builder.append('\'');
        builder.append(user.getUserName());
        builder.append('\'');
        builder.append(',');
        builder.append('\'');
        builder.append(user.getUserEmail());
        builder.append('\'');
        builder.append(',');
        builder.append('\'');
        builder.append(user.getUserPasswordHash());
        builder.append('\'');
        builder.append(')');

        executeQuery(builder.toString());
    }

    public void deleteUserByName(final String userName){
        final StringBuilder builder = new StringBuilder("DELETE FROM users WHERE login = ");
        builder.append(userName);
        builder.append(')');

        executeQuery(builder.toString());
    }

    private void executeQuery(String query)  {
        jdbcTemplate.update(query);
    }

}
