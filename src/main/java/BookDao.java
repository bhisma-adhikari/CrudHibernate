import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDao {
    private SessionFactory sessionFactory;

    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Return book if exists in database, else return null
    public Book getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = null;
        try {
            session.beginTransaction();
            book = session.get(Book.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return book;
    }


    public List<Book> getAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Book.class);
            books = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return books;
    }


    // Return the id of saved book
    // Return null if operation fails
    public Integer add(Book book) {
        Session session = sessionFactory.getCurrentSession();
        Integer id = null;
        try {
            session.beginTransaction();
            // save/update referenced objects

            for (Author a : book.getAuthors()) {
                a.getBooks().add(book);
                session.saveOrUpdate(a);
            }

            book.getPublisher().getBooks().add(book);
            session.saveOrUpdate(book.getPublisher());

            // save book
            id = (Integer) session.save(book);  // must create a new entry; so save (not saveOrUpdate)
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }


    // If such book (determined by book.id) exists in database, try to update it. If successful, return true else return false
    // If such book does not exist in database, return null
    public Boolean update(Book book) {
        Book bookDb = getById(book.getId());
        if (bookDb == null) {
            return null;
        }
        // update fields
        bookDb.setAuthors(book.getAuthors());
        bookDb.setName(book.getName());
        bookDb.setPrice(book.getPrice());

        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            // save/update referenced objects
            for (Author a : bookDb.getAuthors()) {
                a.getBooks().add(bookDb);
                session.saveOrUpdate(a);
            }

            bookDb.getPublisher().getBooks().add(bookDb);
            session.saveOrUpdate(bookDb.getPublisher());

            // update book
            session.saveOrUpdate(bookDb);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }


    // If an book with given id exists in database, try to delete it. If successful, return true else return false
    // If such book does not exist, return null
    public Boolean delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Book bookDb = session.get(Book.class, id);
            if (bookDb == null) {
                return null;
            }
            // first delete all objects which reference this book

            // delete book
            session.delete(bookDb);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }
}
