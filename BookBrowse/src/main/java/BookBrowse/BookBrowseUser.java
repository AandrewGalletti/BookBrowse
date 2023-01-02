package BookBrowse;

import java.util.ArrayList;
import java.util.Scanner;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;

public class BookBrowseUser {
	
	private ArrayList<Volume> readingList;
	private Scanner userInput;
	
	public BookBrowseUser() {
		readingList = new ArrayList<>();
		userInput = new Scanner(System.in);
	}
	
	public void addToReadingList(Volumes vols) {
		System.out.println("Enter indices 1-5 to add multiple search results to reading list");
		String reply = getInput();
		int prevListLength = readingList.size();
		for(int i = 0; i < reply.length();i++) {
			for(int j = 1; j < 6; j++) {
				if(reply.substring(i, i+1).equals("" + j)) { 
					readingList.add(vols.getItems().get(j-1));
				}
			}
		}
		if(readingList.size() > prevListLength)
			displayReadingList();
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
	
	public void displayReadingList() {
		if(!readingList.isEmpty()) {
			int i = 1;
			System.out.println("READING LIST");
			for(Volume vol: readingList) {
				System.out.println(i + " ====================================================+");
				BookBrowseDriver.displayVolumeInfo(vol);
				i++;
			}	
		}
	}
}
