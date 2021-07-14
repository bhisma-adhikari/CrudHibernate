import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class PublisherDao {
    private SessionFactory sessionFactory;

    public PublisherDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Return publisher if exists in database, else return null
    public Publisher getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Publisher publisher = null;
        try {
            session.beginTransaction();
            publisher = session.get(Publisher.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return publisher;
    }


    public List<Publisher> getAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Publisher> publishers = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Publisher.class);
            publishers = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return publishers;
    }


    // Return the id of saved publisher
    // Return null if operation fails
    public Integer add(Publisher publisher) {
        Session session = sessionFactory.getCurrentSession();
        Integer id = null;
        try {
            session.beginTransaction();
            // save/update referenced objects

            for (Book b : publisher.getBooks()) {
                b.setPublisher(publisher);
                session.saveOrUpdate(b);
            }

            // save publisher
            id = (Integer) session.save(publisher);  // must create a new entry; so save (not saveOrUpdate)
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }


    // If such publisher (determined by publisher.id) exists in database, try to update it. If successful, return true else return false
    // If such publisher does not exist in database, return null
    public Boolean update(Publisher publisher) {
        Publisher publisherDb = getById(publisher.getId());
        if (publisherDb == null) {
            return null;
        }
        // update fields
        publisherDb.setBooks(publisher.getBooks());
        publisherDb.setEstd(publisher.getEstd());
        publisherDb.setName(publisher.getName());

        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            // save/update referenced objects

            for (Book b : publisherDb.getBooks()) {
                b.setPublisher(publisherDb);
                session.saveOrUpdate(b);
            }

            // update publisher
            session.saveOrUpdate(publisherDb);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }


    // If an publisher with given id exists in database, try to delete it. If successful, return true else return false
    // If such publisher does not exist, return null
    public Boolean delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Publisher publisherDb = session.get(Publisher.class, id);
            if (publisherDb == null) {
                return null;
            }
            // first delete all objects which reference this publisher
            for (Book b : publisherDb.getBooks()) {
                session.delete(b);
            }

            // delete publisher
            session.delete(publisherDb);
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
