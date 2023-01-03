package BookBrowse;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.BooksRequestInitializer;
import com.google.api.services.books.v1.Books.Volumes.List;
import com.google.api.services.books.v1.model.Volumes;

public class BookBrowseUserTest {
	BookBrowseUser user = new BookBrowseUser();
	JsonFactory jF = GsonFactory.getDefaultInstance();
	Books books;
	
	
	public BookBrowseUserTest() {
		try {
			books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jF, null)
					.setApplicationName("BookBuilder")
					.setGoogleClientRequestInitializer(new BooksRequestInitializer(null))
					.build();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test public void testAddToReadingList() {
		Volumes volumes = null;
		try {
			List volumesList = books.volumes().list("Das Kapital");
			volumes = volumesList.execute();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
		user.addToReadingList(volumes, "1");
		assertTrue(!user.getReadingList().isEmpty());
		assertEquals(user.getReadingList().get(0).getVolumeInfo().getTitle(), "Das Kapital");
		assertEquals(user.getReadingList().get(0).getVolumeInfo().getAuthors().get(0), "Karl Marx");
	}
}
