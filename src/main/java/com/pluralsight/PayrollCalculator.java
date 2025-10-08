package com.pluralsight;

import java.io.*;
import java.util.Scanner;

public class PayrollCalculator {
    //declared some static variables
    static Scanner sc = new Scanner(System.in);
    static String outputFilename;

    public static void main(String[] args) {
        String inputFilename; //input file
        int outputChoice; //output file

        System.out.println("What is the name of the file you want to read from?");
        inputFilename = sc.nextLine(); //get input file name from the user

        System.out.println("Where would you like the results?");
        System.out.println("""
                (1) Print to Screen
                (2) Save to File
                """);
        outputChoice = sc.nextInt(); //Either print to screen or save to file
        sc.nextLine(); // consume the newline after nextInt()

        boolean jsonFormat = false; //initialize it as a false by default
        if (outputChoice == 2) {
            jsonFormat = fileOptions(); //set it to true if user wants JSON format
        }

        StringBuilder result = new StringBuilder(); //creates one result that can be either printed to
        // screen or saved to file. This way there is no result accumulation twice.

        // Using try-with-resources.
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilename))) {
            String line;

            while ((line = br.readLine()) != null) {
                var tokens = line.split("\\|");
                int empId = Integer.parseInt(tokens[0]); //parse variables that need to be parsed
                double hours = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);
                //no need to parse to name (tokens[1]) as it is already a string

                Employee emp = new Employee(empId, tokens[1], hours, payRate);

                if (jsonFormat) {
                    result.append("{")
                            .append("\"employeeId\":").append(emp.getEmployeeId()).append(",")
                            .append("\"name\":\"").append(emp.getName()).append("\",")
                            .append("\"grossPay\":").append(emp.getGrossPay())
                            .append("}")
                            .append(System.lineSeparator());
                } else {
                    result.append("Employee Name: ").append(emp.getName())
                            .append(", Employee ID: ").append(emp.getEmployeeId())
                            .append(", Gross Pay: $").append(emp.getGrossPay())
                            .append(System.lineSeparator());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: The file was not found at the specified path. Check your file name and location.");
            return;
        } catch (IOException e) {
            System.err.println("A general I/O error occurred while reading the file: " + e.getMessage());
            return;
        }

        if (outputChoice == 1) {
            System.out.println("Printing to screen...");
            System.out.print(result);
        } else if (outputChoice == 2) {
            writeToFile(result.toString());
        } else {
            System.err.println("Invalid choice. Use 1 or 2.");
        }
    }

    private static boolean fileOptions() {
        System.out.println("What is the file name where you want the results saved?");
        outputFilename = sc.nextLine().trim();

        if (outputFilename.isEmpty()) {
            System.err.println("No output file name provided. Exiting.");
            System.exit(1);
        }

        System.out.println("Would you like the results in JSON format? Type Y for yes, anything else for default format");
        char wantJSON = sc.nextLine().toLowerCase().charAt(0);
        return wantJSON == 'y';
    }

    public static void writeToFile(String result) {
        System.out.println("Writing to file...");
        if (outputFilename == null || outputFilename.isEmpty()) {
            System.err.println("Output file name is not set. Cannot write.");
            return;
        }
        try (FileWriter wr = new FileWriter(outputFilename)) {
            wr.write(result);
            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println("Could not write to file: " + e.getMessage());
        }
    }
}
