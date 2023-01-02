package BookBrowse;

import java.util.ArrayList;
import com.google.api.services.books.v1.model.Volume;

public class BookBrowseUser {
	
	private ArrayList<Volume> readingList = new ArrayList<>();
	
	public BookBrowseUser() {
		
	}
	
	public void addVolumeToReadingList(Volume vol) {
		readingList.add(vol);
	}
	
	public ArrayList<Volume> getReadingList() {
		return readingList;
	}
}
