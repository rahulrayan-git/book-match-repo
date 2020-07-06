package bookclient;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.MockitoJUnitRunner;

import match.client.BookMatchClient;
import match.dao.BookDao;
import match.exception.MatchBookException;
import match.model.Book;

@RunWith(MockitoJUnitRunner.class)
public class BookMatchClientTest {

	@InjectMocks
	BookMatchClient bookMatchClient;

	@Mock
	BookDao bookdao;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void matchBooksTest() throws MatchBookException {

		List<Book> booklist = new ArrayList<Book>();
		Book b = new Book();
		b.setBookAuthor("Charles J. Yeo");
		b.setBookTitle("Andrews' Diseases of the Skin: Clinical Dermatology");
		b.setId(new Long(1));

		booklist.add(b);
		when(bookdao.fetchBookList()).thenReturn(booklist);
		bookMatchClient.matchBooks();
		Mockito.verify(bookdao, times(1)).fetchBookList();

	}

	@Test(expected = MatchBookException.class)
	public void matchBooksTestWithException() throws MatchBookException {

		List<Book> booklist = new ArrayList<Book>();
		Book b = new Book();
		b.setBookAuthor("Charles J. Yeo");
		b.setBookTitle("Andrews' Diseases of the Skin: Clinical Dermatology");
		b.setId(new Long(1));

		booklist.add(b);
		when(bookdao.fetchBookList()).thenThrow(new MatchBookException("failed to fetch book Details"));
		bookMatchClient.matchBooks();

	}
}
