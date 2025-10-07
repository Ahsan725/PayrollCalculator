package com.pluralsight;

import java.io.*;

public class PayrollCalculator {

    public static void main(String[] args) {

        String filename = "EmployeeData.txt";

        // Use try-with-resources.
        // FileNotFoundException might be thrown here by the FileReader constructor.
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
        } catch (IOException e) {
            System.out.println("Problem reading the file: " + e.getMessage());
             e.printStackTrace();
        }
    }
}