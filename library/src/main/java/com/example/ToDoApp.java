package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoApp {

    private static final String FILE_NAME = "tasks.txt";

    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        ArrayList<String> tasks = loadTasks();
        Scanner sc = new Scanner(System.in);
        int choice;

        printHeader("📚 Welcome to Library To-Do App 📚");

        do {
            printMenu();
            System.out.print(BLUE + "Enter choice: " + RESET);
            while (!sc.hasNextInt()) {
                System.out.print(RED + "Please enter a valid number: " + RESET);
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch(choice) {
                case 1:
                    System.out.print(GREEN + "Enter task: " + RESET);
                    String task = sc.nextLine();
                    tasks.add(task);
                    saveTasks(tasks);
                    System.out.println(GREEN + "✅ Task added successfully!" + RESET);
                    break;
                case 2:
                    printHeader("📋 Current Tasks");
                    if(tasks.isEmpty()) {
                        System.out.println(YELLOW + "No tasks yet." + RESET);
                    } else {
                        for(int i=0; i<tasks.size(); i++) {
                            System.out.println(GREEN + (i+1) + ". " + tasks.get(i) + RESET);
                        }
                    }
                    break;
                case 3:
                    if(tasks.isEmpty()) {
                        System.out.println(YELLOW + "No tasks to delete." + RESET);
                        break;
                    }
                    System.out.print(RED + "Enter task number to delete: " + RESET);
                    while (!sc.hasNextInt()) {
                        System.out.print(RED + "Please enter a valid number: " + RESET);
                        sc.next();
                    }
                    int index = sc.nextInt() - 1;
                    sc.nextLine();
                    if(index >= 0 && index < tasks.size()) {
                        String removed = tasks.remove(index);
                        saveTasks(tasks);
                        System.out.println(GREEN + "✅ Removed: " + removed + RESET);
                    } else {
                        System.out.println(RED + "❌ Invalid task number." + RESET);
                    }
                    break;
                case 4:
                    System.out.println(BLUE + "👋 Exiting... Goodbye!" + RESET);
                    break;
                default:
                    System.out.println(RED + "❌ Invalid choice, try again." + RESET);
            }

        } while(choice != 4);

        sc.close();
    }

    // Method to print menu
    private static void printMenu() {
        System.out.println("\n" + BLUE + "===============================" + RESET);
        System.out.println(BLUE + "1. Add task" + RESET);
        System.out.println(BLUE + "2. View tasks" + RESET);
        System.out.println(BLUE + "3. Delete task" + RESET);
        System.out.println(BLUE + "4. Exit" + RESET);
        System.out.println(BLUE + "===============================" + RESET);
    }

    // Method to print header
    private static void printHeader(String title) {
        System.out.println("\n" + BLUE + "===============================" + RESET);
        System.out.println(BLUE + title + RESET);
        System.out.println(BLUE + "===============================" + RESET);
    }

    // Load tasks from file
    private static ArrayList<String> loadTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while((line = br.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            // File might not exist yet; ignore
        }
        return tasks;
    }

    // Save tasks to file
    private static void saveTasks(ArrayList<String> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for(String task : tasks) {
                bw.write(task);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(RED + "Error saving tasks: " + e.getMessage() + RESET);
        }
    }
}