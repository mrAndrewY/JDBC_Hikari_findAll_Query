package edu.school21.chat.models;
import java.util.Objects;
import java.util.ArrayList;

public class User {
    private Long id;
    private String userName;
    private String password;
    private ArrayList<Chatroom> chatRoomsCreated;
    private ArrayList<Chatroom> chatSocial;

    public User(Long Id, String UserName, String Password, ArrayList<Chatroom> Chatroom1, ArrayList<Chatroom> chatRooms2) {
        id = --Id;
        userName = UserName;
        password = Password;
        chatRoomsCreated = Chatroom1;
        chatSocial = chatRooms2;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Chatroom> getChatRoomsCreated() {
        return chatRoomsCreated;
    }

    public ArrayList<Chatroom> getChatSocial() {
        return chatSocial;
    }

    public void setId(Long Id) {
        id = Id;
    }

    public void setUserName(String Name) {
        userName = Name;
    }

    public void setPassword(String Password) {
        password = Password;
    }

    public void setChatRoomsCreated(ArrayList<Chatroom> chatRooms1) {
        chatRoomsCreated = chatRooms1;
    }

    public void setChatSocial(ArrayList<Chatroom> chatRooms2) {
        chatSocial = chatRooms2;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id,userName,password);
    }

    public boolean equals(Object obj){
        if ( obj == null) return false;
        if ( obj == this) return true;
        if ( obj.getClass() != getClass() ) return false;
        if ((hashCode()==obj.hashCode())) {
            return (id.equals(((User)obj).getId()));
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder ChatroomCr = new StringBuilder();
        StringBuilder ChatSoc = new StringBuilder();
        String Created = "";
        String Social = "";
        if (chatRoomsCreated == null) {
            Created = null;
        } else {
            chatroomListInString(ChatroomCr, chatRoomsCreated);
            ChatroomCr.append('\n');
            }
        if (chatSocial == null) {
            Social = null;
        } else {
            chatroomListInString(ChatSoc, chatSocial);
        }
        String ChCrStr = ChatroomCr.toString();
        String ChSoc = ChatSoc.toString();
        Created = (Created == null) ? null : ChCrStr;
        Social = (Social == null) ? null : ChSoc;

        return ("user: id=" + id + ",login=" + userName + ",password=" +
                password + "\n{\nCreatedRooms={\n" + Created + " }  SocialRooms={ \n" + Social + " }}");
    }

    private void chatroomListInString(StringBuilder str, ArrayList<Chatroom> chatooms) {
        for (int i = 0; i < chatooms.size(); i++) {
            Chatroom tmp1 = chatooms.get(i);
            str.append("id: ");
            str.append(tmp1.getId());
            str.append(", name: ");
            str.append(tmp1.getName());
            str.append(", owner: { ");
            str.append(tmp1.getOwner().getId());
            str.append(", ");
            str.append(tmp1.getOwner().getUserName());
            str.append(", ");
            str.append(tmp1.getOwner().getPassword());
            str.append(" }\n");
        }
    }






    }








