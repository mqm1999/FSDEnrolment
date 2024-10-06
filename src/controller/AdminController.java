package controller;

import service.AdminService;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class AdminController {
    public static void sessionAdminDetail(LinkedHashMap<String, List> handledData, File studentData) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------------The admin system-----------------");
        System.out.println("Select an option: ");
        System.out.println("(c) clear database");
        System.out.println("(g) group students");
        System.out.println("(p) partition students");
        System.out.println("(r) remove student");
        System.out.println("(s) show");
        System.out.println("(x) exit");
        String option = sc.nextLine();
        switch (option.toLowerCase()) {
            case "c":
                AdminService.clearDatabase(handledData, studentData);
                break;
            case "g":
                AdminService.groupStudentsByGrade(handledData, studentData);
                break;
            case "p":
                AdminService.partitionStudents(handledData, studentData);
                break;
            case "r":
                AdminService.removeStudentById(handledData, studentData);
                break;
            case "s":
                AdminService.show(handledData, studentData);
                break;
            case "x":
                System.out.println("Quit admin session");
                break;
            default:
                System.out.println("Not an option");
                sessionAdminDetail(handledData, studentData);
                break;
        }
    }
}
