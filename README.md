- This is a simple project that demonstrates the use of Hibernate ORM to perform CRUD operations on a relational database.
- Running this project will create/update all the tables in the database named 'crud_hibernate'. The database, however, must be present in localhost. 
- The created schema is very simple (contains only 5 tables in total with minimal attributes). However, it is so designed that all possible relationships are present in it. 
    - Tables created: 
      - author 
      - author_detail 
      - book 
      - publisher 
      - author_book (mapping table)
    - Relationships: 
      - one-to-one : between author and author_detail 
      - one-to-many: from publisher to book 
      - many-to-one: from book to publisher 
      - many-to-many: from author to book
<pre>

author_detail < 1 ------ 1 > author < * -------- * > book < * ------ 1 > publisher
-------------                ------                  ----                ---------
id                           id                      id                  id 
genre                        first_name              name                name 
nationality                  last_name               price               estd 

</pre>

- Hibernate configuration is written in src/main/resources/hibernate.cfg.xml. 
  - Make sure you have updated mysql credentials to yours.  
- Dependencies are managed with maven (see pom.xml) 



 