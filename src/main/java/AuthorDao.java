import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AuthorDao {
    private SessionFactory sessionFactory;

    public AuthorDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Return author if exists in database, else return null
    public Author getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Author author = null;
        try {
            session.beginTransaction();
            author = session.get(Author.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return author;
    }


    public List<Author> getAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Author> authors = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Author.class);
            authors = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return authors;
    }


    // Return the id of saved author
    // Return null if operation fails
    public Integer add(Author author) {
        Session session = sessionFactory.getCurrentSession();
        Integer id = null;
        try {
            session.beginTransaction();
            // save/update referenced objects
            if (author.getAuthorDetail() != null) {
                author.getAuthorDetail().setAuthor(author);
                session.saveOrUpdate(author.getAuthorDetail());
            }

            for (Book b : author.getBooks()) {
                b.getAuthors().add(author);
                session.saveOrUpdate(b);
            }

            // save author
            id = (Integer) session.save(author);  // must create a new entry; so save (not saveOrUpdate)
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }


    // If such author (determined by author.id) exists in database, try to update it. If successful, return true else return false
    // If such author does not exist in database, return null
    public Boolean update(Author author) {
        Author authorDb = getById(author.getId());
        if (authorDb == null) {
            return null;
        }
        // update fields
        authorDb.setAuthorDetail(author.getAuthorDetail());
        authorDb.setFirstName(author.getFirstName());
        authorDb.setLastName(author.getLastName());
        authorDb.setBooks(author.getBooks());

        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            // save/update referenced objects
            authorDb.getAuthorDetail().setAuthor(authorDb);
            session.saveOrUpdate(authorDb.getAuthorDetail());

            for (Book b : authorDb.getBooks()) {
                b.getAuthors().add(authorDb);
                session.saveOrUpdate(b);
            }

            // update author
            session.saveOrUpdate(authorDb);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }


    // If an author with given id exists in database, try to delete it. If successful, return true else return false
    // If such author does not exist, return null
    public Boolean delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Author authorDb = session.get(Author.class, id);
            if (authorDb == null) {
                return null;
            }
            // first delete all objects which reference this author

            // delete author
            session.delete(authorDb);
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
