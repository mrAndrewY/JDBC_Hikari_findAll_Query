package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.lang.Long;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private final Chatroom room;
    private String messageText;
    private LocalDateTime time;


    public Message(Long Id, User Author, Chatroom Room, String MessageText, LocalDateTime Time) {
        id = Id;
        author = Author;
        room = Room;
        messageText = MessageText;
        time = Time;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getMessageText() {
        return messageText;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setId(Long Id) {
        id = Id;
    }

    public void setAuthor(User Author) {
        author = Author;
    }

    public void setMessageText(String Text) {
        messageText = Text;
    }

    public void setTime(LocalDateTime Time) {
        time = Time;
    }


     @Override
    public int hashCode() {
         return Objects.hash(id, author, room, messageText, time);
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        if ((hashCode() == obj.hashCode())) {
            return id.equals( ((Message) obj).getId());
        } else {
            return false;
        }
    }

    public String toString() {
        return ("\nMessage : {\nId=" + id + ",\nauthor={" + author + "},\n" + "room={" + room + "}\ntext={" + messageText + "},\ntime={" +
                time.toLocalDate() + "}");
    }

}
