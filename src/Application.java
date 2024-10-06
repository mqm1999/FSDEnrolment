import controller.AdminController;
import controller.StudentController;
import dto.StudentLoginInfo;
import entity.Role;
import entity.Subject;
import service.AuthenticationService;
import util.StudentUtils;

import java.io.File;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        boolean state = true;
        File studentData = StudentUtils.createFile();
        List<Role> roleList = StudentUtils.createListRole();
        List<Subject> subjectList = StudentUtils.createListSubject();
        StudentUtils.addInitialData(studentData, roleList, subjectList);
        LinkedHashMap<String, List> handledData = StudentUtils.transformData(Objects.requireNonNull(StudentUtils.handleDataFromFile(studentData)));
        while (state) {
            state = startSystem(handledData, studentData);
        }
    }

    private static boolean startSystem(LinkedHashMap<String, List> handledData, File studentData) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Welcome to self-enroll system");
            System.out.println("Select an option: ");
            System.out.println("(A) Admin");
            System.out.println("(S) Student");
            System.out.println("(X) Exit");
            String option = sc.nextLine();
            switch (option.toLowerCase()) {
                case "s":
                    sessionStudent(handledData, studentData);
                    break;
                case "a":
                    sessionAdmin(handledData, studentData);
                    break;
                case "x":
                    System.out.println("Quit");
                    return false;
                default:
                    break;
            }
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("Invalid input");
        }
        return true;
    }

    private static void sessionAdmin(LinkedHashMap<String, List> handledData, File studentData) {
        AdminController.sessionAdminDetail(handledData, studentData);
    }

    public static void sessionStudent(LinkedHashMap<String, List> handledData, File studentData) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------------The student system-----------------");
        System.out.println("Select an option: ");
        System.out.println("(l) login");
        System.out.println("(r) register");
        System.out.println("(x) exit");
        String option = sc.nextLine();
        switch (option.toLowerCase()) {
            case "l":
                StudentLoginInfo student = AuthenticationService.studentLogin(handledData);
                StudentController.sessionStudentDetail(student, handledData, studentData);
                break;
            case "r":
                AuthenticationService.register(handledData, studentData);
                break;
            case "x":
                System.out.println("Quit student session");
                break;
            default:
                System.out.println("Not an option");
                break;
        }
    }

}