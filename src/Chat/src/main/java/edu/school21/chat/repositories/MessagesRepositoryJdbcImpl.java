package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;


public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findUser(Long id) throws SQLException {
        String uQuery = "SELECT * FROM chat.user WHERE id = " + id;

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(uQuery);
            if (!rs.next()) {
                return null;
            }
            return new User(id, rs.getString(2), rs.getString(3), null, null);
        }
    }

    @Override
    public Optional<Message> findById(Long id) {
        String mQuery = "SELECT * FROM chat.message WHERE id = " + id;

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(mQuery);
            if (!rs.next()) {
                return null;
            }
            return Optional.of(new Message(rs.getLong(1), findUser(rs.getLong(2)),
                    findChat(rs.getLong(3)), rs.getString(4), rs.getTimestamp(5).toLocalDateTime()));
        } catch (SQLException se) {
            return Optional.empty();
        }


    }


    private Chatroom findChat(Long id) throws SQLException {
        String cQuery = "SELECT * FROM chat.chatroom WHERE id = " + id;

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(cQuery);

            if (!rs.next()) {
                return null;
            }
            return new Chatroom(id, rs.getString(2), null, null);
        }
    }


    public void updateMessage(Message message) {

        boolean isAuthorInMessage = message.getAuthor() != null;
        boolean isRoomInMessage = message.getRoom() != null;
        boolean isUserExist = isAuthorInMessage && isUserByNameInDataBase(message.getAuthor().getUserName());
        boolean isChatRoomExist = isRoomInMessage && isRoomByNameInDataBase(message.getRoom().getName());
        if (isChatRoomExist && isUserExist && findById(message.getId()).isPresent()) {
            String sqlQuery = "UPDATE chat.message SET author=?, messageroom=?, messagetext=?, messagedatetime =? WHERE id=?";
            Long idToInsert = message.getId();
            Long authorId = message.getAuthor().getId();
            Long chatroomId = message.getRoom().getId();
            String messageText = message.getMessageText() == null ? null : message.getMessageText();
            LocalDateTime timeT = message.getTime() == null ? null : message.getTime();
            try (Connection con = dataSource.getConnection();
                 PreparedStatement update = con.prepareStatement(sqlQuery)
            ) {
                con.setAutoCommit(true);
                update.setLong(1, authorId);
                update.setLong(2, chatroomId);
                update.setString(3, messageText);
                update.setTimestamp(4, Timestamp.valueOf(timeT));
                update.setLong(5, idToInsert);
                update.executeUpdate();
            } catch (SQLException se) {

                System.out.println("Sql does not work: " + se.getSQLState());
            }
        } else {
            System.out.println("Error: \nis author in message: " + isAuthorInMessage +
                    "\nis room in message: " + isRoomInMessage + "\nAuthor exist: " + isUserExist + "\nChatroom exist: " + isChatRoomExist);

        }

    }


    public boolean isUserByNameInDataBase(String name) {
        boolean res = false;
        String uQuery = "SELECT * FROM chat.user WHERE login like" + "'" + name + "'";
        try {
            try (Connection con = dataSource.getConnection();
                 Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(uQuery);
                if (rs.next()) {
                    res = true;
                } else {
                    throw new NotSavedSubEntityException();
                }
            }

        } catch (SQLException se) {
            System.out.println(se.getErrorCode());
        } catch (NotSavedSubEntityException ne) {
            System.err.println("User not found in database");
        } catch (NullPointerException ne) {
            System.err.println("Null parameter cant be proceed");
        }

        return res;
    }


    public boolean isRoomByNameInDataBase(String name) {
        String uQuery = "SELECT * FROM chat.chatroom WHERE chatroomname like" + "'" + name + "'";
        boolean res = false;
        try {
            try (Connection con = dataSource.getConnection();
                 Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(uQuery);
                if (rs.next()) {
                    res = true;
                } else {
                    throw new NotSavedSubEntityException();
                }
            }
        } catch (SQLException se) {
            se.getErrorCode();
        } catch (NotSavedSubEntityException nf) {
            System.err.println("Room not found in database");
        }
        return res;
    }


    public void saveMessage(Message message) {
        boolean userExists = isUserByNameInDataBase(message.getAuthor().getUserName());
        boolean roomExists = isRoomByNameInDataBase(message.getRoom().getName());
        if (userExists && roomExists) {
            String eQuery = "INSERT INTO chat.message(author, MessageRoom, MessageText, MessageDateTime) values" +
                    "(" + message.getAuthor().getId() + "," + message.getRoom().getId() + ",'" +
                    message.getMessageText() + "','" + message.getTime().toString() + "') RETURNING id";
            try (Connection con = dataSource.getConnection();
                 Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(eQuery);
            } catch (SQLException se) {
                se.getErrorCode();
            }
        }

    }


}