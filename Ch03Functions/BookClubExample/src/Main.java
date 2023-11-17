/*
Bootstrap learning 'Clean Code'
STEPS
1. Read the chapter
2. Capture the principles
3a. Write a sample dirty code related to online BookClub project, highlight the principles
3b. Write a sample clean code related to online BookClub project, highlight the principles
4. Share principles with folks
5. Push code to GitHub
** Use ChatGPT help where needed
*/

/*
Chapter 3 of "Clean Code," which focuses on Functions, is a critical section that provides valuable insights into writing
effective functions in programming. Here's a brief overview of the key takeaways from this chapter:

1. Small Size: Functions should be small. Robert C. Martin even suggests that functions should hardly ever be 20 lines long.

2. Do One Thing: Functions should do only one thing and do it well.
If a function is doing more than one task, it's a sign that it could be broken down further. - Covered

3. One Level of Abstraction per Function: The statements within a function should all be at the same level of abstraction
to make them easier to read and understand. - Covered

3a. Stepdown Rule for reading code from top to bottom - a set of TO paragraphs

4. Descriptive Names: Functions should have names that clearly describe their purpose.
Longer descriptive names are better than short cryptic ones.

5. Function Arguments: The fewer arguments a function has, the better.
Zero arguments (niladic) is ideal, followed by one (monadic), two (dyadic), and so on. - Covered

6. No Side Effects: Functions should not have hidden effects. They should not modify any states outside their own scope,
which can lead to unexpected behavior. - Covered

7. Command-Query Separation: Functions should either change the state of an object (command) or
return information about an object (query), but not both. eg Query(Get) and Command(update)

8. Prefer Exceptions to Returning Error Codes: Returning error codes from command functions leads to cluttered code.
Using exceptions instead helps in separating error processing from the main logic. - Covered
Go through John Ousterhout's related lectures

9. DRY (Don’t Repeat Yourself): Avoid duplication in code. Duplication can lead to inconsistencies and
makes the code more difficult to maintain.

10. Structured Programming: Following the rules of structured programming, such as having one entry and
one exit point (return statement), can make functions more transparent and less error-prone.




If there is one takeaway that I should choose which is that one?

If you were to choose just one takeaway from the "Clean Code" principles regarding functions,
I would recommend focusing on Do One Thing. This principle is foundational
and influences many other aspects of good coding practices.

Why "Do One Thing" is Key:

Clarity and Focus: When a function does exactly one thing, its purpose and behavior are clear.
This makes it easier to understand, debug, and modify.

Reusability and Modularity: Functions that do one thing can often be reused in different contexts.
This modularity is a cornerstone of good software design.

Simplified Testing: It's easier to write tests for functions that have a single responsibility.
Each test can be more focused and less likely to break for unrelated reasons.

Maintenance: In the long run, code with well-defined, single-purpose functions is easier to maintain.
Changes in one part of the system are less likely to unexpectedly affect other parts.

Impact on Other Principles: Adhering to this principle naturally leads to better alignment with other best practices.
For example, functions that do one thing are often smaller (Small Size), and it's easier to give them Descriptive Names.

By focusing on ensuring that each function in your codebase does one thing and does it well,
you'll be adopting a practice that positively influences many other aspects of your code's quality.


 */

/*
Code summary:
This Java code defines a simple book club management system.

The Main class contains the main method where the program execution starts.
It creates an instance of BookClub, adds books to it, lists all books,
checks if a specific book is in the club,
schedules a session with a book, and then removes a book to demonstrate the updated list.

The Book class represents a book with a title attribute and methods to set and get the title.

The Session class is designed to manage a session for a book.

The BookClub class manages a collection of books, with methods to
add and remove books, schedule sessions for books, find a book by its title, list all books in the club,
and check if a specific book is in the club.

The code demonstrates principles of clean code, such as small and single-purpose functions, descriptive names,
proper handling of function arguments, command-query separation, and structured programming.
Each method in the BookClub class is focused on a specific action and is designed to be clear and easy to understand.
 */

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        System.out.println("Hello world!");
//        Creating an instance of BookClub
        BookClub bookClub = new BookClub();

//        Creating books
        Book book1 = new Book("Clean Code");
        Book book2 = new Book("Clean Architecture");

//        Adding books to the club
        bookClub.addBook(book1);
        bookClub.addBook(book2);

//        Listing all books in the club
        System.out.println("Books in the club:");
        for (Book b : bookClub.listAllBooks() ) {
            System.out.println(b.getTitle());
        }

//        Checking if a book is in the club
        String searchTitle="Clean Code";
        System.out.println("Is '" + searchTitle + "' in the club? " + bookClub.isBookInClub(searchTitle));

//        Creating a session and scheduling it with a book
        Session session = new Session();
        bookClub.scheduleSession(book1, session);

//        Removing a book
        bookClub.removeBook(book1);
        System.out.println("After removing 'Clean Code':");
        for(Book book : bookClub.listAllBooks()) {
            System.out.println(book.getTitle());
        }
    }
}

class Book {
    private String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

class Session {
    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }
}

class BookClub{
    private List<Book> books = new ArrayList<>();

    //        1. Small Size & 2. Do One Thing: Add a single book
    public void addBook(Book book) {
        if (book == null) {
//                8. Prefer Exceptions to Returning Error Codes
            throw new IllegalArgumentException("Book cannot be null");
        }
//            6. No Side Effects: Directly affects only the books list
        books.add(book);
    }

    //        1. Small Size & 2. Do One Thing: Remove a single book
    public void removeBook(Book book) {
        books.remove(book);
    }

    //        1. Small Size & 2. Do One Thing: Schedule a session for a book
    public void scheduleSession(Book book, Session session) {
        if (book == null || session == null) {
            throw new IllegalArgumentException("Session and Book must not be null");
        }
//            7. Command-Query Separation: This is a 'command' function
        session.setBook(book);
        System.out.println("Session scheduled for: " + book.getTitle());
    }

    //        3. One Level of Abstraction per Function & 9. DRY (Don't Repeat Yourself): Finds a book by title
    public Book findBookByTitle(String title) {
        for (Book b : books) {
            if(b.getTitle().equalsIgnoreCase(title)) {
//                    10. Structured Programming: Single exit point
                return b;
            }
        }
        return null; // if no book is found, return null
    }

    //        4. Descriptive Names: Lists all books in the club
    public List<Book> listAllBooks() {
//            6. No Side Effects: Returning a copy to avoid side effects
        return new ArrayList<>(books);
    }

    //        5. Function Arguments & 7. Command-Query Separation: Query function to check if a book is in the club
    public boolean isBookInClub(String title) {
        for (Book b : books) {
            if(b.getTitle().equalsIgnoreCase(title)) {
                return true; // Returns information without changing the state
            }
        }
        return false;
    }

}


