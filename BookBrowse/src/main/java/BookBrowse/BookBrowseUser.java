package BookBrowse;

import java.util.ArrayList;
import java.util.Scanner;
import com.google.api.services.books.v1.model.Volume;

public class BookBrowseUser {
	
	private ArrayList<Volume> readingList;
	private Scanner userInput;
	
	public BookBrowseUser() {
		readingList = new ArrayList<>();
		userInput = new Scanner(System.in);
	}
	
	public void addVolumeToReadingList(Volume vol) {
		readingList.add(vol);
	}
	
	public ArrayList<Volume> getReadingList() {
		return readingList;
	}
	
	public String getInput() {
		String result = userInput.nextLine();
		return result;
	}
	
	public void endInput() {
		userInput.close();
	}
}
