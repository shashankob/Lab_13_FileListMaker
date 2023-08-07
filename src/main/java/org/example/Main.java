import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> myList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean needsSave = false;
        String currentFile = null;

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("A – Add an item to the list");
            System.out.println("D – Delete an item from the list");
            System.out.println("V – View the list");
            System.out.println("O – Open a list file from disk");
            System.out.println("S – Save the current list file to disk");
            System.out.println("C – Clear the list");
            System.out.println("Q – Quit the program");
            char optionChoice = scanner.nextLine().toUpperCase().charAt(0);

            switch (optionChoice) {
                case 'A':
                    System.out.println("Enter the string to add");
                    String itemAdd = scanner.nextLine();
                    myList.add(itemAdd);
                    needsSave = true;
                    break;

                case 'D':
                    if (myList.isEmpty()) {
                        System.out.println("There is nothing in the list.");
                    } else {
                        System.out.println("Enter the index of item to remove:");
                        int indexToRemove = getRangedInt(0, myList.size() - 1);
                        myList.remove(indexToRemove);
                        needsSave = true;
                    }
                    break;

                case 'V':
                    System.out.println(myList);
                    break;

                case 'O':
                    if (needsSave) {
                        System.out.println("The current list can be saved. Would you like to save it? (Y/N)");
                        if (scanner.nextLine().equalsIgnoreCase("Y")) {
                            saveList(myList, currentFile);
                            needsSave = false;
                        }
                    }
                    System.out.println("Enter file name to open:");
                    currentFile = scanner.nextLine();
                    myList = loadList(currentFile);
                    break;

                case 'S':
                    if (currentFile == null) {
                        System.out.println("Enter the file name to save:");
                        currentFile = scanner.nextLine();
                    }
                    saveList(myList, currentFile);
                    needsSave = false;
                    break;

                case 'C':
                    myList.clear();
                    needsSave = false;
                    break;

                case 'Q':
                    if (needsSave) {
                        System.out.println("Current list needs to be saved. Save it? (Y/N)");
                        if (scanner.nextLine().equalsIgnoreCase("Y")) {
                            saveList(myList, currentFile);
                        }
                    }
                    System.out.println("Program Exited");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static int getRangedInt(int minNum, int maxNum) {
        Scanner scanner = new Scanner(System.in);
        int inputChoice;
        do {
            System.out.println("Enter a number between " + minNum + " and " + maxNum);
            while (!scanner.hasNextInt()) {
                System.out.println("Try again");
                scanner.next();
            }
            inputChoice = scanner.nextInt();
        } while (inputChoice < minNum || inputChoice > maxNum);
        return inputChoice;
    }

    private static ArrayList<String> loadList(String filename) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File(filename + ".txt"));
            while (fileScanner.hasNextLine()) {
                list.add(fileScanner.nextLine());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File is not found in database.");
        }
        return list;
    }

    private static void saveList(ArrayList<String> list, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new File(filename + ".txt"));
            for (String item : list) {
                writer.println(item);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }
}
