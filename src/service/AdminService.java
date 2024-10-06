package service;

import com.google.gson.Gson;
import controller.AdminController;
import dto.EnrolmentInfoForAdminView;
import dto.StudentGroupByGradeInfo;
import entity.Student;
import entity.Subject;
import entity.SubjectEnrolment;
import entity.User;
import enums.Grade;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AdminService {
    public static void clearDatabase(LinkedHashMap<String, List> handledData, File studentData) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Are you sure to delete database? Type \"yes\" to confirm");
        String confirmation = sc.nextLine();
        if (Objects.equals(confirmation, "yes")) {
            handledData.clear();
            try {
                List<String> lines = Files.readAllLines(Paths.get(studentData.getPath()));
                List<String> blankLines = lines.stream()
                        .map(line -> "")
                        .collect(Collectors.toList());
                Files.write(Paths.get(studentData.getPath()), blankLines);
                System.out.println("All lines in the file replaced with blank lines successfully.");
            } catch (IOException e) {
                System.out.println("There is a problem with the file");
            }
        }
        AdminController.sessionAdminDetail(handledData, studentData);
    }

    public static void groupStudentsByGrade(LinkedHashMap<String, List> handledData, File studentData) {
        List<SubjectEnrolment> subjectEnrolmentList = handledData.get("subjectenrolment");
        List<Subject> subjectList = handledData.get("subject");
        List<Student> studentList = handledData.get("student");
        if (subjectEnrolmentList.isEmpty()) {
            System.out.println("No enrolments have been recorded!");
        } else if (subjectList.isEmpty()) {
            System.out.println("No subjects have been recorded!");
        } else if (studentList.isEmpty()) {
            System.out.println("No students have been recorded!");
        } else {
            Map<String, Student> studentMap = studentList.stream().collect(Collectors.toMap(Student::getStudentId, student -> student));
            Map<String, List<StudentGroupByGradeInfo>> subjectStudentMap = new HashMap<>();
            for (SubjectEnrolment se : subjectEnrolmentList) {
                for (Grade grade : Grade.values()) {
                    for (Subject subject : subjectList) {
                        if (Objects.equals(se.getSubjectId(), subject.getSubjectId()) && Objects.equals(se.getSubjectGrade(), grade.toString())) {
                            String s = subject.getSubjectName() + " Grade " + se.getSubjectGrade();
                            if (subjectStudentMap.containsKey(s)) {
                                subjectStudentMap.get(s).add(new StudentGroupByGradeInfo(se.getStudentId(), studentMap.get(se.getStudentId()).getStudentName(), subject.getSubjectId(), subject.getSubjectName(), se.getSubjectMark(), se.getSubjectGrade()));
                            } else {
                                List<StudentGroupByGradeInfo> sList = new ArrayList<>();
                                sList.add(new StudentGroupByGradeInfo(se.getStudentId(), studentMap.get(se.getStudentId()).getStudentName(), subject.getSubjectId(), subject.getSubjectName(), se.getSubjectMark(), se.getSubjectGrade()));
                                subjectStudentMap.put(s, sList);
                            }
                            break;
                        }
                    }
                }
            }
            for (Map.Entry<String, List<StudentGroupByGradeInfo>> entry : subjectStudentMap.entrySet()) {
                System.out.println(entry.getKey());
                if (!entry.getValue().isEmpty()) {
                    for (StudentGroupByGradeInfo student : entry.getValue()) {
                        System.out.println(student);
                    }
                }
                System.out.println("--------------------------------");
            }
        }
        AdminController.sessionAdminDetail(handledData, studentData);
    }

    public static void partitionStudents(LinkedHashMap<String, List> handledData, File studentData) {
        List<SubjectEnrolment> subjectEnrolmentList = handledData.get("subjectenrolment");
        List<Subject> subjectList = handledData.get("subject");
        List<Student> studentList = handledData.get("student");
        if (subjectEnrolmentList.isEmpty()) {
            System.out.println("No enrolments have been recorded!");
        } else if (subjectList.isEmpty()) {
            System.out.println("No subjects have been recorded!");
        } else if (studentList.isEmpty()) {
            System.out.println("No students have been recorded!");
        } else {
            Map<String, Student> studentMap = studentList.stream().collect(Collectors.toMap(Student::getStudentId, student -> student));
            Map<String, String> subjectMap = subjectList.stream().collect(Collectors.toMap(Subject::getSubjectId, Subject::getSubjectName));
            Map<String, List<StudentGroupByGradeInfo>> subjectStudentMap = new HashMap<>();
            for (SubjectEnrolment se : subjectEnrolmentList) {
                if (Objects.equals(se.getSubjectGrade(), Grade.Z.toString())) {
                    String string = subjectMap.get(se.getSubjectId()) + " FAIL:";
                    if (subjectStudentMap.containsKey(string)) {
                        subjectStudentMap.get(string).add(new StudentGroupByGradeInfo(se.getStudentId(), studentMap.get(se.getStudentId()).getStudentName(), se.getSubjectId(), subjectMap.get(se.getSubjectId()), se.getSubjectMark(), se.getSubjectGrade()));
                    } else {
                        List<StudentGroupByGradeInfo> sList = new ArrayList<>();
                        sList.add(new StudentGroupByGradeInfo(se.getStudentId(), studentMap.get(se.getStudentId()).getStudentName(), se.getSubjectId(), subjectMap.get(se.getSubjectId()), se.getSubjectMark(), se.getSubjectGrade()));
                        subjectStudentMap.put(string, sList);
                    }
                } else {
                    String string = subjectMap.get(se.getSubjectId()) + " PASS:";
                    if (subjectStudentMap.containsKey(string)) {
                        subjectStudentMap.get(string).add(new StudentGroupByGradeInfo(se.getStudentId(), studentMap.get(se.getStudentId()).getStudentName(), se.getSubjectId(), subjectMap.get(se.getSubjectId()), se.getSubjectMark(), se.getSubjectGrade()));
                    } else {
                        List<StudentGroupByGradeInfo> sList = new ArrayList<>();
                        sList.add(new StudentGroupByGradeInfo(se.getStudentId(), studentMap.get(se.getStudentId()).getStudentName(), se.getSubjectId(), subjectMap.get(se.getSubjectId()), se.getSubjectMark(), se.getSubjectGrade()));
                        subjectStudentMap.put(string, sList);
                    }
                }
            }
            for (Map.Entry<String, List<StudentGroupByGradeInfo>> entry : subjectStudentMap.entrySet()) {
                System.out.println(entry.getKey());
                if (!entry.getValue().isEmpty()) {
                    for (StudentGroupByGradeInfo student : entry.getValue()) {
                        System.out.println(student);
                    }
                } else {
                    System.out.println("\n");
                }
                System.out.println("--------------------------------");
            }
        }
        AdminController.sessionAdminDetail(handledData, studentData);
    }

    public static void removeStudentById(LinkedHashMap<String, List> handledData, File studentData) {
        Scanner sc = new Scanner(System.in);
        List<User> userList = handledData.get("user");
        List<Student> studentList = handledData.get("student");
        List<SubjectEnrolment> subjectEnrolmentList = handledData.get("subjectenrolment");
        Map<String, String> studentIdMap = studentList.stream().collect(Collectors.toMap(Student::getStudentId, Student::getEmail));
        System.out.println("ID of student that needs to be deleted: ");
        String id = sc.nextLine();
        if (!studentIdMap.containsKey(id)) {
            System.out.println("Student ID not found!");
            AdminController.sessionAdminDetail(handledData, studentData);
        } else {
            String email = studentIdMap.get(id);
            subjectEnrolmentList.removeIf(subjectEnrolment -> (Objects.equals(subjectEnrolment.getStudentId(), id)));
            studentList.removeIf(student -> (Objects.equals(student.getStudentId(), id)));
            userList.removeIf(user -> (Objects.equals(user.getUsername(), email)));
            try {
                List<String> lines = Files.readAllLines(Path.of(studentData.getPath()));
                lines.set(1, new Gson().toJson(userList));
                lines.set(3, new Gson().toJson(studentList));
                lines.set(7, new Gson().toJson(subjectEnrolmentList));
                Files.write(Paths.get(studentData.getPath()), lines);
                System.out.println("Delete successfully");
                AdminController.sessionAdminDetail(handledData, studentData);
            } catch (IOException e) {
                System.out.println("Error deleting student with ID " + id);
            }
        }
    }

    public static void show(LinkedHashMap<String, List> handledData, File studentData) {
        List<Student> studentList = handledData.get("student");
        List<Subject> subjectList = handledData.get("subject");
        List<SubjectEnrolment> subjectEnrolmentList = handledData.get("subjectenrolment");
        Map<String, String> studentInfoMap = studentList.stream().collect(Collectors.toMap(Student::getStudentId, Student::getStudentName));
        Map<String, String> subjectInfoMap = subjectList.stream().collect(Collectors.toMap(Subject::getSubjectId, Subject::getSubjectName));
        Map<String, List<EnrolmentInfoForAdminView>> subjectEnrolmentMap = new HashMap<>();
        for (SubjectEnrolment se : subjectEnrolmentList) {
            if (!subjectEnrolmentMap.containsKey(se.getStudentId())) {
                List<EnrolmentInfoForAdminView> infoForView = new ArrayList<>();
                infoForView.add(new EnrolmentInfoForAdminView(studentInfoMap.get(se.getStudentId()), subjectInfoMap.get(se.getSubjectId()), se.getEnrollTime(), se.getSubjectMark(), se.getSubjectGrade()));
                subjectEnrolmentMap.put(se.getStudentId(), infoForView);
            } else {
                subjectEnrolmentMap.get(se.getStudentId()).add(new EnrolmentInfoForAdminView(studentInfoMap.get(se.getStudentId()), subjectInfoMap.get(se.getSubjectId()), se.getEnrollTime(), se.getSubjectMark(), se.getSubjectGrade()));
            }
        }
        for (Map.Entry<String, List<EnrolmentInfoForAdminView>> entry : subjectEnrolmentMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + studentInfoMap.get(entry.getKey()) + ":");
            for (EnrolmentInfoForAdminView e : entry.getValue()) {
                System.out.println(e);
            }
            System.out.println("----------------------------");
        }
        AdminController.sessionAdminDetail(handledData, studentData);
    }
}