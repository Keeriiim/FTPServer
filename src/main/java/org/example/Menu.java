package org.example;

import java.util.Scanner;


public class Menu {
    private Scanner scan;



    public Menu()  {
        printMenu();
    }


    public void printMenu(){ // Main metod to run the program
        Scanner test = new Scanner(System.in);
        System.out.println("Please select an option:");
        System.out.println("1. List all files");
        System.out.println("2. Create & upload file");
        System.out.println("3. Delete file");
        System.out.println("4. Update file");
        System.out.println("5. Read file");
        System.out.println("6. Exit");

        System.out.print("Enter your choice: ");
        int MainMenuChoice = scan.nextInt();

        switch (MainMenuChoice) { // Switch case for the main menu
            case 1:
                break;

            case 2:
                break;


            case 3:
                break;

            case 4:
                break;

            case 5:
                break;

            case 6: // Exits the program
                System.exit(0);
                break;
        }

    }

}

