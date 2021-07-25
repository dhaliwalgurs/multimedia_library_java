# Media Library Project

## Project Proposal

**What will the application do?**

The application is a library system designed to be used as a personal organizational tool. After consuming a Book, Movie
 or Album the user will be able to record relevant information including but not limited to the Title, Artist, Genre, 
 and user’s Rating out of 10. The application will allow the users to graphically interact with entries by editing and 
 viewing subsets of each library by genre, rating or artist. This application will provide the foundation for other more
  complicated functions like filtered suggestions or matching preferences between 2 users 
  (*not in the scope of this project*).

**Who will use it?**

The application is to be used by individuals as an interactive personal library and record of impressions of media 
consumed.

**Why is this project of interest to you?**

This project excites me because it is easy to imagine this application being used regularly 
as a substitute for excel or some other static database. My hope is that the ease of use, 
purpose built functions, and the ability to continue to add advanced functionality on demand 
will make it a no-brainer for those of us feeling inundated in our new information age.

## User Stories

-	**As a user, I want to be able to add a Book, Movie, Album item to a specific type of Media library**
-	**As a user, I want to be able to remove a Book, Movie, Album item from a specific type of Media library**
-	**As a user, I want to be able to view all items in a specified Media library**
-	*As a user, I want to be able to view all items of a certain rating in a specified Media library*
-	*As a user, I want to be able to view all items of a certain genre in a specified Media library*
-	**As a user, I want to be able to quickly check if a Book, Movie, Album item has already been entered into a specified Media library**
-	*As a user, I want to be able change the rating of a Media item already in a specific Media library*
-	*As a user, I want to be able change the genre of a Media item already in a specific Media library*

-   **As a user, I want to be able to save all my Media libraries to a file**
-   **As a user, I want to be able load my various Media libraries to the application**

**Phase 4: Task 2**  
*Option: Include a type hierarchy in your code*
- The type hierarchy has Book, Movie and Album each extend a generic Media class
- The subclasses have different functionality when accessing the corresponding online database for an entry
- The subclasses have different functionality for their information prompts

**Phase 4: Task 3**  
*Possible Changes and Refactoring:*
- More clearly distinguish the common functionality implemented in MediaLibraryUI for the console and graphical applications
- Consider storing the 3 MediaLibrary objects in MediaLibraryUI in a container, rather than as separate fields
- Reevaluate whether the Writable interface is required for the project or if another design could avoid its addition
- Refactor the GraphicalLibraryApp into additional helper classes to break the functionality up into smaller logical units