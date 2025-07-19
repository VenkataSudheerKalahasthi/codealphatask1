import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTracker {

    // Method to return grade category
    public static String getGradeCategory(double grade) {
        if (grade >= 90) return "A";
        else if (grade >= 80) return "B";
        else if (grade >= 70) return "C";
        else if (grade >= 60) return "D";
        else return "F";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("===== Student Grade Tracker =====");

        System.out.print("Enter the number of students: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        for (int i = 0; i < n; i++) {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            System.out.print("Enter " + name + "'s grade: ");
            double grade = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            students.add(new Student(name, grade));
        }

        // Calculations
        double total = 0;
        double highest = -1;
        double lowest = 101;
        String topStudent = "", lowStudent = "";

        for (Student s : students) {
            total += s.grade;

            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = total / students.size();

        // Report
        System.out.println("\n===== Grade Summary Report =====");
        for (Student s : students) {
            System.out.println("Student: " + s.name +
                               " | Grade: " + s.grade +
                               " | Category: " + getGradeCategory(s.grade));
        }

        System.out.printf("\nAverage Grade: %.2f\n", average);
        System.out.println("Highest Grade: " + highest + " by " + topStudent);
        System.out.println("Lowest Grade: " + lowest + " by " + lowStudent);

        // Save report to file
        try {
            FileWriter writer = new FileWriter("StudentReport.txt");
            writer.write("===== Grade Summary Report =====\n");
            for (Student s : students) {
                writer.write("Student: " + s.name +
                             " | Grade: " + s.grade +
                             " | Category: " + getGradeCategory(s.grade) + "\n");
            }
            writer.write(String.format("\nAverage Grade: %.2f\n", average));
            writer.write("Highest Grade: " + highest + " by " + topStudent + "\n");
            writer.write("Lowest Grade: " + lowest + " by " + lowStudent + "\n");
            writer.close();
            System.out.println("\n📄 Report saved to StudentReport.txt");
        } catch (IOException e) {
            System.out.println("❌ Error saving report to file.");
        }

        scanner.close();
    }
}
