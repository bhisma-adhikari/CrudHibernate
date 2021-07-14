import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Set;
//import org.hibernate.dialect.MySQL

public class Main {
    private static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Author.class)
            .addAnnotatedClass(AuthorDetail.class)
            .addAnnotatedClass(Book.class)
            .addAnnotatedClass(Publisher.class)
            .buildSessionFactory();

    private static AuthorDao authorDao = new AuthorDao(sessionFactory);
    private static AuthorDetailDao authorDetailDao = new AuthorDetailDao(sessionFactory);
    private static BookDao bookDao = new BookDao(sessionFactory);
    private static PublisherDao publisherDao = new PublisherDao(sessionFactory);

    public static void main(String[] args) {
        test();

        //        populateDb();

    }

    public static void test() {
        Publisher publisher1 = new Publisher("Vidharthi", 1950, null);
        Book book1 = new Book("Muna Madan", 12.22, null, publisher1);
        Set<Book> books1 = new HashSet<>();
        books1.add(book1);
        Author author1 = new Author("Laxmi", "Devkota", null, books1);
        AuthorDetail authorDetail1 = new AuthorDetail("poem", "Nepalese", author1);
        author1.setAuthorDetail(authorDetail1);
        book1.getAuthors().add(author1);

        bookDao.add(book1);
    }

    public static void populateDb() {
        Author author1 = new Author("John", "Doe", null, null);
        Author author2 = new Author("Jacob", "Lee", null, null);

        AuthorDetail authorDetail1 = new AuthorDetail("fiction", "American", author1);
        AuthorDetail authorDetail2 = new AuthorDetail("comedy", "Chinese", author2);

        authorDetailDao.add(authorDetail1);
        authorDetailDao.add(authorDetail2);

        Set<Author> authors1 = new HashSet<>();
        authors1.add(author1);
        authors1.add(author2);

        Set<Author> authors2 = new HashSet<>();
        authors2.add(author1);

        Publisher publisher1 = new Publisher("Horizon Publishers", 1992, null);
        publisherDao.add(publisher1);


        Book book1 = new Book("The Amazing Universe", 34.45, authors1, publisher1);
        Book book2 = new Book("Funny Fagin", 21.23, authors2, publisher1);

        bookDao.add(book1);
        bookDao.add(book2);

        Set<Book> books1 = new HashSet<>();
        books1.add(book1);
        books1.add(book2);


    }
}
