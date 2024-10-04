import dto.StudentLoginInfo;
import entity.Role;
import entity.Subject;
import service.Authentication;
import util.Utils;

import java.io.File;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        boolean state = true;
        File studentData = Utils.createFile();
        List<Role> roleList = Utils.createListRole();
//        List<Semester> semesterList = Utils.createListSemester();
        List<Subject> subjectList = Utils.createListSubject();
        Utils.addInitialData(studentData, roleList, subjectList);
        LinkedHashMap<String, List> handledData = Utils.transformData(Objects.requireNonNull(Utils.handleDataFromFile(studentData)));
        while (state) {
            startSystem(handledData, studentData);
        }
    }

    private static void startSystem(LinkedHashMap<String, List> handledData, File studentData) {
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
                    break;
                default:
                    break;
            }
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("Invalid input");
        }
    }

    private static void sessionAdmin(LinkedHashMap<String, List> handledData, File studentData) {
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
                StudentLoginInfo student = Authentication.studentLogin(handledData);
                Utils.sessionStudentDetail(student, handledData, studentData);
                break;
            case "r":
                Authentication.register(handledData, studentData);
                break;
            case "x":
                break;
            default:
                break;
        }
    }

}