import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AuthorDetailDao {
    private SessionFactory sessionFactory;

    public AuthorDetailDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    // Return authorDetail if exists in database, else return null
    public AuthorDetail getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        AuthorDetail authorDetail = null;
        try {
            session.beginTransaction();
            authorDetail = session.get(AuthorDetail.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return authorDetail;
    }


    public List<AuthorDetail> getAll() {
        Session session = sessionFactory.getCurrentSession();
        List<AuthorDetail> authorDetail = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(AuthorDetail.class);
            authorDetail = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return authorDetail;
    }


    // Return the id of saved authorDetail
    // Return null if operation fails
    public Integer add(AuthorDetail authorDetail) {
        Session session = sessionFactory.getCurrentSession();
        Integer id = null;
        try {
            session.beginTransaction();
            // save/update referenced objects
            if (authorDetail.getAuthor() != null) {
                authorDetail.getAuthor().setAuthorDetail(authorDetail);
                session.saveOrUpdate(authorDetail.getAuthor());
            }
            // save authorDetail
            id = (Integer) session.save(authorDetail);  // must create a new entry; so save (not saveOrUpdate)
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }


    // If such authorDetail (determined by authorDetail.id) exists in database, try to update it. If successful, return true else return false
    // If such authorDetail does not exist in database, return null
    public Boolean update(AuthorDetail authorDetail) {
        AuthorDetail authorDetailDb = getById(authorDetail.getId());
        if (authorDetailDb == null) {
            return null;
        }
        // update fields
        authorDetailDb.setAuthor(authorDetail.getAuthor());
        authorDetailDb.setGenre(authorDetail.getGenre());
        authorDetailDb.setNationality(authorDetail.getNationality());

        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            // save/update referenced objects
            authorDetailDb.getAuthor().setAuthorDetail(authorDetailDb);
            session.saveOrUpdate(authorDetailDb.getAuthor());
            // update authorDetail
            session.saveOrUpdate(authorDetailDb);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }


    // If an authorDetail with given id exists in database, try to delete it. If successful, return true else return false
    // If such authorDetail does not exist, return null
    public Boolean delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            AuthorDetail authorDetailDb = session.get(AuthorDetail.class, id);
            if (authorDetailDb == null) {
                return null;
            }

            // first delete all objects which reference this author (Otherwise the deleted author_detail will be re-saved by cascade)
            // EXPLANATION:
            // To map the one-to-one relationship between tables 'author' and 'author_detail',
            // we have column 'author_detail_id' in table 'author' i.e. 'author' references 'author_detail'
            // Thus, in AuthorDao.delete(): when we do session.delete(authorDb), the corresponding entry for author_detail gets deleted as well (because of Cascade.ALL)
            // However, here in AuthorDetailDao.delete(): when we do session.delete(authorDetailDb) without first deleting the corresponding author,
            // then the author_detail just deleted would be re-saved by cascade (since the corresponding author (which contains the author_detail_id in database) has not been deleted first)
            // Therefore we need to delete the referencing 'author' first.
            session.delete(authorDetailDb.getAuthor());

            // delete authorDetail
            session.delete(authorDetailDb);
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
