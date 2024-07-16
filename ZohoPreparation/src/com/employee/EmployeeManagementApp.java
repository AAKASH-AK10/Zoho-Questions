package com.employee;

import java.util.*;
import java.util.stream.Collectors;

class Employee {
    String empName;
    int age;
    String designation;
    String department;
    String reportingTo;

    public Employee(String empName, int age, String designation, String department, String reportingTo) {
        this.empName = empName;
        this.age = age;
        this.designation = designation;
        this.department = department;
        this.reportingTo = reportingTo;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-5d %-15s %-15s %-15s", empName, age, designation, department, reportingTo);
    }
}

public class EmployeeManagementApp {
    static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        initializeData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Display all Employee records");
            System.out.println("2. Search Employee records");
            System.out.println("3. Print reporting tree of an Employee");
            System.out.println("4. Print employees reporting to a given manager");
            System.out.println("5. Print summary of Department, Designation, ReportingTo");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayAllEmployees();
                    break;
                case 2:
                    searchEmployeeRecords(scanner);
                    break;
                case 3:
                    printReportingTree(scanner);
                    break;
                case 4:
                    printEmployeesReportingTo(scanner);
                    break;
                case 5:
                    printSummary();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void initializeData() {
        employees.add(new Employee("Alice", 30, "Developer", "IT", "Bob"));
        employees.add(new Employee("Charlie", 25, "Analyst", "Finance", "David"));
        employees.add(new Employee("Eve", 35, "Manager", "IT", "Frank"));
        employees.add(new Employee("Grace", 28, "Developer", "IT", "Eve"));
        employees.add(new Employee("Bob", 40, "CTO", "IT", "Frank"));
        // Add more employees as needed
    }

    static void displayAllEmployees() {
        System.out.printf("%-15s %-5s %-15s %-15s %-15s\n", "EmpName", "Age", "Designation", "Department", "ReportingTo");
        System.out.println("-------------------------------------------------------------------------------");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    static void searchEmployeeRecords(Scanner scanner) {
        System.out.println("Enter search criteria (e.g., empName=Alice AND age>30): ");
        scanner.nextLine();  // Consume newline left-over
        String criteria = scanner.nextLine();
        String[] conditions = criteria.split("AND");

        List<Employee> result = new ArrayList<>(employees);

        for (String condition : conditions) {
            condition = condition.trim();
            if (condition.contains("=")) 
            {
                String[] parts = condition.split("=");
                String key = parts[0].trim();
                String value = parts[1].trim();
                result = result.stream().filter(emp -> filterByEquals(emp, key, value)).collect(Collectors.toList());
            } 
            else if (condition.contains(">")) 
            {
                String[] parts = condition.split(">");
                String key = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                result = result.stream().filter(emp -> filterByGreaterThan(emp, key, value)).collect(Collectors.toList());
            } 
            else if (condition.contains("<")) 
            {
                String[] parts = condition.split("<");
                String key = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                result = result.stream().filter(emp -> filterByLessThan(emp, key, value)).collect(Collectors.toList());
            } // Add more conditions as needed
        }

        displaySearchResults(result);
    }

    static boolean filterByEquals(Employee emp, String key, String value) {
        switch (key.toLowerCase()) {
            case "empname":
                return emp.empName.equalsIgnoreCase(value);
            case "age":
                return emp.age == Integer.parseInt(value);
            case "designation":
                return emp.designation.equalsIgnoreCase(value);
            case "department":
                return emp.department.equalsIgnoreCase(value);
            case "reportingto":
                return emp.reportingTo.equalsIgnoreCase(value);
            default:
                return false;
        }
    }

    static boolean filterByGreaterThan(Employee emp, String key, int value) {
        if (key.equalsIgnoreCase("age")) {
            return emp.age > value;
        }
        return false;
    }

    static boolean filterByLessThan(Employee emp, String key, int value) {
        if (key.equalsIgnoreCase("age")) {
            return emp.age < value;
        }
        return false;
    }

    static void displaySearchResults(List<Employee> result) {
        if (result.isEmpty()) {
            System.out.println("No records found.");
        } else {
            System.out.printf("%-15s %-5s %-15s %-15s %-15s\n", "EmpName", "Age", "Designation", "Department", "ReportingTo");
            System.out.println("-------------------------------------------------------------------------------");
            for (Employee emp : result) {
                System.out.println(emp);
            }
        }
    }

    static void printReportingTree(Scanner scanner) {
        System.out.print("Enter Employee Name: ");
        scanner.nextLine();  // Consume newline left-over
        String empName = scanner.nextLine();
        printReportingTreeHelper(empName, 0);
    }

    static void printReportingTreeHelper(String empName, int level) {
        for (Employee emp : employees) {
            if (emp.empName.equalsIgnoreCase(empName)) {
                printIndentation(level);
                System.out.println(emp.empName + " (" + emp.designation + ")");
                for (Employee e : employees) {
                    if (e.reportingTo.equalsIgnoreCase(empName)) {
                        printReportingTreeHelper(e.empName, level + 1);
                    }
                }
            }
        }
    }

    static void printIndentation(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
    }

    static void printEmployeesReportingTo(Scanner scanner) {
        System.out.print("Enter Manager Name: ");
        scanner.nextLine();  // Consume newline left-over
        String managerName = scanner.nextLine();
        System.out.printf("%-15s %-5s %-15s %-15s %-15s\n", "EmpName", "Age", "Designation", "Department", "ReportingTo");
        System.out.println("-------------------------------------------------------------------------------");
        for (Employee emp : employees) {
            if (emp.reportingTo.equalsIgnoreCase(managerName)) {
                System.out.println(emp);
            }
        }
    }

    static void printSummary() {
        Map<String, Long> departmentSummary = employees.stream().collect(Collectors.groupingBy(emp -> emp.department, Collectors.counting()));
        Map<String, Long> designationSummary = employees.stream().collect(Collectors.groupingBy(emp -> emp.designation, Collectors.counting()));
        Map<String, Long> reportingToSummary = employees.stream().collect(Collectors.groupingBy(emp -> emp.reportingTo, Collectors.counting()));

        System.out.println("Department Summary:");
        departmentSummary.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("\nDesignation Summary:");
        designationSummary.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("\nReportingTo Summary:");
        reportingToSummary.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}

