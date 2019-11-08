import com.onica.books.helpers.CommandPromptHelper;
import com.onica.books.services.BookServices;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        CommandPromptHelper commandPromptHelper = new CommandPromptHelper();
        commandPromptHelper.handle_readBooks();
        boolean exit = false;
        do {
            int choice = commandPromptHelper.fireInitialPrompt();

            switch (choice) {
                // list books
                case 1:
                    commandPromptHelper.handle_listBooks();
                    break;
                // add book
                case 2:
                    int bookId = commandPromptHelper.handle_addBook();
                    System.out.println(String.format("Book %d Saved", bookId));
                    break;
                // search for books
                case 3:
                    commandPromptHelper.handle_editBook();
                    break;
                // edit books
                case 4:
                    commandPromptHelper.handle_booksSearch();
                    break;
                // save and exit
                case 5:
                    commandPromptHelper.handle_save();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid Input, please enter a digit between 1 and 5");
            }
        } while (!exit);
    }
}
