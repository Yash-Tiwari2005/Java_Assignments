import java.util.*;

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class Student {
    private int rollNumber;
    private String studentName;
    private int[] marks;

    public Student(int rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }

    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i + 1) + ": " + marks[i]);
            }
        }
    }

    public double calculateAverage() {
        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        return (double) sum / marks.length;
    }

    public String getResultStatus() {
        for (int m : marks) {
            if (m < 35) {
                return "Fail";
            }
        }
        return "Pass";
    }

    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);
        System.out.print("Marks: ");
        for (int m : marks) {
            System.out.print(m + " ");
        }
        System.out.println();
        System.out.println("Average: " + calculateAverage());
        System.out.println("Result: " + getResultStatus());
    }

    public int getRollNumber() {
        return rollNumber;
    }
}

public class ResultManager {
    private Student[] students;
    private int count;
    private Scanner scanner;

    public ResultManager(int capacity) {
        students = new Student[capacity];
        count = 0;
        scanner = new Scanner(System.in);
    }

    public void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();

            int[] marks = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = scanner.nextInt();
            }
            scanner.nextLine();

            Student s = new Student(roll, name, marks);
            s.validateMarks();

            if (count < students.length) {
                students[count++] = s;
                System.out.println("Student added successfully. Returning to main menu...");
            } else {
                System.out.println("Student list full.");
            }
        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage() + " Returning to main menu...");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Enter numbers only.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            int roll = scanner.nextInt();
            scanner.nextLine();

            Student found = null;
            for (int i = 0; i < count; i++) {
                if (students[i].getRollNumber() == roll) {
                    found = students[i];
                    break;
                }
            }

            if (found != null) {
                found.displayResult();
                System.out.println("Search completed.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid roll number.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void mainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("===== Student Result Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Show Student Details");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        showStudentDetails();
                        break;
                    case 3:
                        running = false;
                        System.out.println("Exiting program. Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input error. Enter numeric values only.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        ResultManager manager = new ResultManager(100);
        manager.mainMenu();
    }
}
