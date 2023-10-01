drop schema if exists chat cascade;
drop sequence if exists seq_user;
drop sequence if exists seq_chatroom;
drop sequence if exists seq_message;
create schema if not exists CHAT;



-- Table: User
CREATE TABLE IF NOT EXISTS chat.User (
    ID numeric PRIMARY KEY,
    Login VARCHAR(80) UNIQUE,
    Password VARCHAR(40)
);

-- Table: Chatroom
CREATE TABLE IF NOT EXISTS chat.Chatroom (
    ID numeric PRIMARY KEY,
    ChatroomName VARCHAR(80) UNIQUE,
    owner numeric not null  references chat.User(id)
);

-- Table: Message
CREATE TABLE IF NOT EXISTS chat.Message (
    ID numeric PRIMARY KEY,
    author numeric not null references chat.User(id),
    MessageRoom numeric not null references chat.Chatroom(id),
    MessageText TEXT not null,
    MessageDateTime timestamp default current_timestamp
);



CREATE SEQUENCE IF NOT EXISTS chat.seq_user AS bigint START WITH 1 INCREMENT BY 1;
ALTER TABLE chat.user ALTER COLUMN ID SET DEFAULT nextval('chat.seq_user');

CREATE SEQUENCE IF NOT EXISTS chat.seq_chatroom AS bigint START WITH 1 INCREMENT BY 1;
ALTER TABLE chat.Chatroom ALTER COLUMN ID SET DEFAULT nextval('chat.seq_chatroom');

CREATE SEQUENCE IF NOT EXISTS chat.seq_message AS bigint START WITH 1 INCREMENT BY 1;
ALTER TABLE chat.message ALTER COLUMN ID SET DEFAULT nextval('chat.seq_message');






-- this query execute in program Do not delete it!
--
-- With listUsers AS (select u.id  as ss,
--                           u.login as ln,
--                           u.password as pwd,
--                           c.id           as chatcreatedid,
--                           c.chatroomname as chatcreatedName,
--                           foo.mesr as   chatsocial,
--                           foo.author as aa
--                    from chat.user u
--                             LEFT JOIN chat.chatroom c on c.owner = u.id
--                             LEFT JOIN  (select author, messageroom as mesr from chat.message group by author, mesr order by 1,2) as foo
--                                        on author = u.id
--                             left join chat.message cc on cc.messageroom = c.id
-- )
--
-- select ss, ln, pwd, chatcreatedid, chatcreatedName, chatsocial,cr.chatroomname, owner, cu.login, cu.password from listUsers
--                                                                                                                    left join (select id, chatroomname, owner from chat.chatroom) cr on cr.id= chatsocial
--                                                                                                                    left join chat.user cu on cu.id = owner
-- where ss between 0 and 6
-- ORDER BY 1 ;
--
