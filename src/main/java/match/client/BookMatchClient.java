package match.client;

import java.io.IOException;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import match.dao.BookDao;
import match.exception.MatchBookException;
import match.model.Book;

/**
 * @author RahulR3
 *
 */
@Component
public class BookMatchClient {

	private static final Logger logger = LogManager.getLogger(BookMatchClient.class);
	private static final String EXISTS = ": Exists ->";
	
	@Autowired
	BookDao bookdao;

	public static void main(String[] args) throws MatchBookException {

		BasicConfigurator.configure();
		BookMatchClient bookMatchClient = startFramework();
		bookMatchClient.matchBooks();

	}

	static BookMatchClient startFramework() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("/applicationContext.xml");
		return ac.getBean(BookMatchClient.class);
	}

	/**
	 * Method which compares Title of BooksToMatch from enum class 
	 * and Database Records
	 * and Prints output if it matches and 
	 * @throws MatchBookException if any Exception occurs
	 */ 
	public void matchBooks() throws MatchBookException {

	
			logger.info("inside BookMatchClient==> matchBooks()");
			List<Book> booklist = bookdao.fetchBookList();
			for (BooksToMatch bookToMatch : BooksToMatch.values()) {

				boolean bookMatches = false;

				for (Book book : booklist) {

					if (compareTitles(book.getBookTitle(), bookToMatch.getTitle())) {
						bookMatches = true;
						logger.info(bookToMatch.getTitle() + EXISTS + bookMatches);

					}

				}
				if (!bookMatches) {
					logger.info(bookToMatch.getTitle() + EXISTS + bookMatches);

				}

			}
	}

	/**
	 * method which compare Title from Database and BooksToMatch from enum class
	 * 
	 * @param bookDataBase      Title from Database
	 * @param bookfromConstants Title from BooksToMatch enum class
	 * @return true if matched else false
	 */
	private boolean compareTitles(String bookDataBase, String bookfromConstants) {
				
		String content1 = bookDataBase.replaceAll("\\Wthe\\W|^the\\W|\\Wthe$", " ")
				.replaceAll("\\Wof\\W|^of\\W|\\Wof$", " ").replaceAll("\\Win\\W|^in\\W|\\Win$", " ")
				.replaceAll("\\Wand\\W|^and\\W|\\Wand$", " ").replaceAll("[\\W]", "").toUpperCase();

		String content2 = bookfromConstants.replaceAll("\\Wthe\\W|^the\\W|\\Wthe$", " ")
				.replaceAll("\\Wof\\W|^of\\W|\\Wof$", " ").replaceAll("\\Win\\W|^in\\W|\\Win$", " ")
				.replaceAll("\\Wand\\W|^and\\W|\\Wand$", " ").replaceAll("[\\W]", "").toUpperCase();

		return  content1.equalsIgnoreCase(content2) ? true : false;

	}

}
