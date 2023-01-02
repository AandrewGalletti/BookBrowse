package BookBrowse;

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
			queryGBooks(jF,getQuery());	
		}
		System.out.println("Thank you for using BookBrowse!");
	}
	
	private static void queryGBooks(JsonFactory Jf, String query) throws Exception {
		final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), Jf, null)
				.setApplicationName("BookBuilder")
				.setGoogleClientRequestInitializer(new BooksRequestInitializer(null))
				.build();
		
		System.out.println("Query: [" + query + "]");
		List volumesList = books.volumes().list(query);
		Volumes volumes = volumesList.execute();
		System.out.println("SEARCH RESULTS");
		if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
		    System.out.println("No matches found.");
		    return;
		}
		
		for (int i = 0; i < 5; i++) {
			System.out.println((i+1) + " ===================================================+");
			Volume vol = volumes.getItems().get(i);
			displayVolumeInfo(vol);
		}
		addToReadingList(volumes);
	}
	

	private static String getQuery() {
		System.out.println("Please enter search query");
		return user.getInput();
	}
	
	private static void addToReadingList(Volumes vols) {
		System.out.println("Enter indices 1-5 to add multiple search results to reading list");
		String reply = user.getInput();
		int prevListLength = user.getReadingList().size();
		for(int i = 0; i < reply.length();i++) {
			for(int j = 1; j < 6; j++) {
				if(reply.substring(i, i+1).equals("" + j)) { 
					user.addVolumeToReadingList(vols.getItems().get(j-1));
				}
			}
		}
		if(user.getReadingList().size() > prevListLength)
			displayReadingList();
		whatNext();
	}
	
	private static void displayReadingList() {
		if(!user.getReadingList().isEmpty()) {
			int i = 1;
			System.out.println("READING LIST");
			for(Volume vol: user.getReadingList()) {
				System.out.println(i + " ====================================================+");
				displayVolumeInfo(vol);
				i++;
			}	
		}
	}
	
	private static void displayVolumeInfo(Volume vol) {
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
			displayReadingList();
			whatNext();
		}
		else {
			quit = true;
			user.endInput();
		}
	} 
}
