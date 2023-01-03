# BookBrowse
This is a basic command line java application built using the Google Books API to search Google's database of books, ebooks, magazines, and newspapers. The program takes a user provided query and returns the 5 most relevant search results, and then offers the user the oppurtunity to save any combination of the 5 results (or none) to a local reading list. The program will loop by then prompting the user to view their reading list, perform another search, or to exit the program. 

Dependencies

This project has been most effectively run within an IDE (developed using Eclipse) and requires use of the Google Books API which was imported using Gradle. The build.gradle includes:

repositories {

    mavenCentral()
  
    google()
  
}

dependencies {

    compile 'com.google.apis:google-api-services-books:v1-rev20201021-1.32.1'
  
}

Imports

The main file: BookBrowserDriver.java makes use of java util (ArrayList and Scanner), Google API client classes (GoogleHttpTransport, JsonFactory, and GsonFactory), and Google Books API classes (Books, BooksRequestInitializer, Volumes.List, Volume, and Volumes).
