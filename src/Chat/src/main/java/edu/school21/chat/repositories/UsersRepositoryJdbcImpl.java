package edu.school21.chat.repositories;

//import apple.laf.JRSUIUtils;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class UsersRepositoryJdbcImpl {
    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String stringQuery() {
        String queryB = "With listUsers AS (select u.id  as ss," +
                "u.login as ln," +
                "u.password as pwd," +
                "c.id           as chatcreatedid," +
                "c.chatroomname as chatcreatedName," +
                "foo.mesr as   chatsocial," +
                "foo.author as aa " +
                "from chat.user u " +
                "LEFT JOIN chat.chatroom c on c.owner = u.id " +
                "LEFT JOIN  (select author, messageroom as mesr from chat.message group by author, mesr order by 1,2) as foo " +
                "on author = u.id " +
                "left join chat.message cc on cc.messageroom = c.id " +
                ")" +

                "select DISTINCT ss, ln, pwd, chatcreatedid, chatcreatedName, chatsocial,cr.chatroomname, owner, cu.login, cu.password from listUsers " +
                "left join (select id, chatroomname, owner from chat.chatroom) cr on cr.id= chatsocial " +
                "left join chat.user cu on cu.id = owner " +
                "where ss between ? and ? " +
                "ORDER BY 1";

        return queryB;

    }

    ;

    public LinkedList<User> findAll(int page, int size) {
        int rangeCountBegin = page * size ;
        int rangeCountEnd = (page * size + size) ;
        LinkedList<User> resultUserList = new LinkedList<User>();
        try {
            String queryB = stringQuery();
            Connection con = dataSource.getConnection();
            PreparedStatement pst = con.prepareStatement(queryB);
            pst.setLong(1, rangeCountBegin);
            pst.setLong(2, rangeCountEnd);
            Statement st = con.createStatement();
            ResultSet rs = pst.executeQuery();
            TreeMap<Long, Chatroom> RoomCreated = new TreeMap<>();
            TreeMap<Long, Chatroom> RoomForSocial = new TreeMap<>();
            TreeMap<Long, User> addUserList = new TreeMap<>();
            Long numberUser = null;
            User user = null;
            while (rs.next()) {
                if (numberUser == null || numberUser != rs.getLong(1)) {
                    if (user != null) {
                        addUserToList(resultUserList, user, RoomCreated, RoomForSocial);
                    }
                    user = new User(rs.getLong(1), rs.getString(2), rs.getString(3),
                            null, null);
                    numberUser = rs.getLong(1);
                }
                if (!RoomCreated.containsKey(rs.getLong(4)) && rs.getLong(4) != 0) {
                    RoomCreated.put(rs.getLong(4), new Chatroom(rs.getLong(4),
                            rs.getString(5), user, null));
                }
                Long temp = rs.getLong(6);
                if (!RoomForSocial.containsKey(rs.getLong(6)) && rs.getLong(6) != 0) {
                    User userAdd = addUserList.entrySet().contains(rs.getLong(8)) ?
                            addUserList.get(rs.getLong(8)) : new User(rs.getLong(8),
                            rs.getString(9), rs.getString(10), null, null);
                    RoomForSocial.put(rs.getLong(6), new Chatroom(rs.getLong(6),
                            rs.getString(7), userAdd, null));
                }
                if (rs.isLast()) addUserToList(resultUserList, user, RoomCreated, RoomForSocial);
            }
        } catch (SQLException se) {
            System.out.println(se.getErrorCode());
        }
        return resultUserList;
    }

    private void addUserToList(LinkedList<User> finalList, User user, TreeMap<Long, Chatroom> chCr, TreeMap<Long, Chatroom> chS) {
        user.setChatRoomsCreated(new ArrayList<Chatroom>());
        user.setChatSocial(new ArrayList<Chatroom>());
        for (Map.Entry<Long, Chatroom> entry : chCr.entrySet()) {
            user.getChatRoomsCreated().add(entry.getValue());
        }
        for (Map.Entry<Long, Chatroom> entry : chS.entrySet()) {
            user.getChatSocial().add(entry.getValue());
        }
        chCr.clear();
        chS.clear();
        finalList.add(user);
    }


}

