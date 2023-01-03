package BookBrowse;

import java.net.UnknownHostException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.BooksRequestInitializer;
import com.google.api.services.books.v1.Books.Volumes.List;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;

public class BookBrowseDriver {	
	public static BookBrowseUser user;
	public static boolean quit = false;
	
	public static void main(String[] args) throws Exception {
		JsonFactory jF = GsonFactory.getDefaultInstance();
		user = new BookBrowseUser();
		while(!quit) {
			System.out.println("Please enter search query");
			String query = user.getInput();
			if(query != "" && query != null) {
				try {
					Volumes searchResults = queryGBooks(jF,query);
					displaySearchResults(searchResults);
					System.out.println("\nEnter indices 1-5 in any order to add multiple search results to reading list");
					String savedSearchIndices = user.getInput();
					user.addToReadingList(searchResults, savedSearchIndices);
				} catch (Exception e) {
					if(e instanceof UnknownHostException)
						System.out.println("Error could not connect to server!");
				}
				whatNext();
			}		
		}
		System.out.println("Thank you for using BookBrowse!");
	}
	
	protected static Volumes queryGBooks(JsonFactory Jf, String query) throws Exception {
		final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), Jf, null)
				.setApplicationName("BookBuilder")
				.setGoogleClientRequestInitializer(new BooksRequestInitializer(null))
				.build();
		
		System.out.println("Query: [" + query + "]");
		List volumesList = books.volumes().list(query);
		Volumes volumes = volumesList.execute();
		return volumes;
	}
	
	private static void displaySearchResults(Volumes vols) {
		System.out.println("\nSEARCH RESULTS");
		if (vols.getTotalItems() == 0 || vols.getItems() == null) {
		    System.out.println("No matches found.");
		    return;
		}
		
		for (int i = 0; i < 5; i++) {
			System.out.println((i+1) + " ===================================================+");
			Volume vol = vols.getItems().get(i);
			displayVolumeInfo(vol);
		}
	}
	
	protected static void displayVolumeInfo(Volume vol) {
		Volume.VolumeInfo info = vol.getVolumeInfo();
		System.out.print("Author(s): ");
		if(info.getAuthors() != null) {
			for (String author : info.getAuthors()) {
				System.out.print(author + ", ");
			}
		}
		System.out.println();
		System.out.println("Title: " + info.getTitle());
		System.out.println("Publisher: " + info.getPublisher());
		
	} 
	
	private static void whatNext() {
		System.out.println("\nEnter 1 to perform another query, 2 to view reading list, or anything else to quit");
		String answer = user.getInput();
		if(answer.equals("1"))
			return;
		if(answer.equals("2")) {
			user.displayReadingList();
			whatNext();
		}
		else {
			quit = true;
			user.endInput();
		}
	} 
}
