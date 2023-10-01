# JDBC_Hikari_findAll_Query
The project demonstrates findAll() method - getting complicated data over only one query. 
It provides reducing calls to database

1) findAll() method returns list of Users, including data and lists of chatrooms, that have data about Users


Here classes structure:

User:
Long id | String login | String password | Chatroom Created(list) | Chatroom Social(list) | Messages(null)

Chatroom:
Long id | String name | User owner | Messages (null)


For getting data we need to provide several steps:
1) creating JdbcDataSource class via Hikari library, we setting Hikary config wich provides acces to the database (localhost in our case)
2) provide using statements for getting data in repositories

Program logic is:
1) Create DataSource
2) Execute queries that creates schema (Chat) and fill it with data
3) Execute findAll() method
4) Print list of data.



To compile and start the project:
************************************************************
cd src/Chat
mvn compile
mvn exec:java -Dexec.mainClass=edu.school21.chat.app.Program
************************************************************
