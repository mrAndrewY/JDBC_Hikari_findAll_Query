package edu.school21.chat.models;

import java.util.ArrayList;
import java.lang.Long;
import java.util.Objects;

public class Chatroom {
    private Long id;
    private String name ;
    private User owner;
    ArrayList<Message> messages;
    public Chatroom(Long Id, String Name, User Owner, ArrayList<Message> Messages){
        id=Id;
        name=Name;
        owner=Owner;
        messages=Messages;
    }
    public Long  getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public User getOwner(){
        return owner;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }


    public void setId(Long Id){
        id=Id;
    }
    public void setName(String Name){
        name=Name;
    }
    public void setOwner(User Owner){
         owner=Owner;
    }

    public void setMessages (ArrayList<Message> Messages){
        messages=Messages;
    }






      @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        if ((hashCode() == obj.hashCode())) {
            return (id.equals(((Chatroom) obj).getId()));
        } else {
            return false;
        }
    }

    public String toString(){
        return ("Chatroom id="+id +"," +
                "name="+name  +"," +
                "creator={"+
                owner +
                "},messages="+
                null);
    }

}
