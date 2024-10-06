package controller;

import dto.StudentLoginInfo;
import service.AuthenticationService;
import service.StudentService;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class StudentController {
    public static void sessionStudentDetail(StudentLoginInfo student, LinkedHashMap<String, List> handledData, File studentData) {
        Scanner sc = new Scanner(System.in);
        if (Objects.equals(student, null)) {
            return;
        } else {
            System.out.println("-----------------The subject enrolment system-----------------");
            System.out.println("Please select an option");
            System.out.println("(e) Enrol");
            System.out.println("(r) Remove a subject from enrolment list");
            System.out.println("(s) Show current enrolled subjects");
            System.out.println("(c) Change password");
            System.out.println("(x) Quit");
            String option = sc.nextLine();
            switch (option.toLowerCase()) {
                case "e":
                    StudentService.enroll(handledData, studentData, student);
                    break;
                case "r":
                    StudentService.remove(handledData, studentData, student);
                    break;
                case "s":
                    StudentService.view(handledData, studentData, student);
                    break;
                case "c":
                    AuthenticationService.changePassword(handledData, studentData, student);
                    break;
                case "x":
                    System.out.println("Quit");
                    break;
                default:
                    System.out.println("Not an option");
                    sessionStudentDetail(student, handledData, studentData);
                    break;
            }
        }
    }
}
