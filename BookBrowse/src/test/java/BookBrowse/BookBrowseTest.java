package BookBrowse;

import static org.junit.Assert.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.Test;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.BooksRequestInitializer;
import com.google.api.services.books.v1.Books.Volumes.List;
import com.google.api.services.books.v1.model.Volumes;

public class BookBrowseTest {
	Books books;
	JsonFactory jF = GsonFactory.getDefaultInstance();
	
	public BookBrowseTest() {
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
	
	@Test public void testBooksBuilderConnection() {
		try {
			books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jF, null)
					.setApplicationName("BookBuilder")
					.setGoogleClientRequestInitializer(new BooksRequestInitializer(null))
					.build();
		} catch (GeneralSecurityException e) {
			assert(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(true);	
	}
	
	@Test public void testQueryGBooks() {
		String[] queries = {"Moby Dick","The Lorax","Das Kapital"};
		String[] expected = {"Herman Melville","Dr. Seuss","Karl Marx"};
		Volumes[] vols = new Volumes[queries.length];
		try {
			for(int i = 0; i < 3; i++) {
				List volumesList = books.volumes().list(queries[i]);
				Volumes volumes = volumesList.execute();
				vols[i] = volumes;
			}
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		} 
		
		for(int i = 0; i < 3; i++) {
			assertEquals(vols[i].getItems().get(0).getVolumeInfo().getAuthors().get(0),expected[i]);
		}
	}
}
