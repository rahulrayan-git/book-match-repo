package match.dao;

import java.util.List;

import match.client.BookMatchClient;
import match.exception.MatchBookException;
import match.model.Book;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RahulR3
 *
 */
@Repository
public class BookDaoImpl implements BookDao {

	private static final Logger logger = LogManager.getLogger(BookDaoImpl.class);
	
	@Autowired
	public SessionFactory sessionFactory;
	/**
	 *  method which fetches all Books from database
	 * @return gives list of books
	 */
	
	@Transactional
	@Override
	public List<Book> fetchBookList() throws MatchBookException {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Book");
		return query.list();
	}
	

	SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
