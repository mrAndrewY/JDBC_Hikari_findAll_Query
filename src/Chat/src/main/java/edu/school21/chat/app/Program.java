package edu.school21.chat.app;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Program {

    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        UsersRepositoryJdbcImpl asker = new UsersRepositoryJdbcImpl(dataSource.getDataSource());
        updateData("schema.sql" , dataSource);
        updateData("data.sql" , dataSource);
        LinkedList<User> usersList = asker.findAll(0,4);

        for (int i =0; i< usersList.size(); i++) {
            String box = usersList.get(i).toString();
            System.out.println(box+"\n|||||||||||||||||||||||||||");
        }
        System.exit(0);
        }


    private static void updateData(String file, JdbcDataSource dataSource) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            InputStream input = Program.class.getClassLoader().getResourceAsStream(file);
            Scanner scanner = new Scanner(input).useDelimiter(";");

            while (scanner.hasNext()) {
                st.executeUpdate(scanner.next().trim());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()+" "+e.getErrorCode());
        }
    }

    }







