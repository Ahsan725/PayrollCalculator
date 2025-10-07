package com.pluralsight;

import java.io.*;

public class PayrollCalculator {

    public static void main(String[] args) {

        String filename = "EmployeeData.txt"; //name of the file being read

        // Using try-with-resources.
        try(BufferedReader br =  new BufferedReader(new FileReader(filename))){

            String line;
            while ((line = br.readLine()) != null) {
                var tokens = line.split("\\|");
                int empId = Integer.parseInt(tokens[0]);
                double hours = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);
                Employee emp = new Employee(empId,tokens[1],hours,payRate);
                System.out.println("Employee Name: " + emp.getName() + ", Employee ID: " + emp.getEmployeeId() + ", Gross Pay: $" + emp.getGrossPay());

            }
        } catch (FileNotFoundException e) {
            // Specific action for when the file isn't where we looked
            System.err.println("ERROR: The file was not found at the specified path. Check your file name and location.");
        } catch (IOException e) {
            // Catches all other I/O errors
            System.err.println("A general I/O error occurred while reading the file: " + e.getMessage());
        }
    }
}