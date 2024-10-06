package service;

import com.google.gson.Gson;
import controller.StudentController;
import dto.EnrolmentInfoForStudentView;
import dto.StudentLoginInfo;
import entity.Subject;
import entity.SubjectEnrolment;
import enums.Grade;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class StudentService {
    public static void enroll(LinkedHashMap<String, List> data, File studentData, StudentLoginInfo student) {
        Scanner sc = new Scanner(System.in);
        List<Subject> subjectList = data.get("subject");
        if (subjectList.isEmpty()) {
            System.out.println("No subjects found!");
        } else {
            Map<String, String> subjectMap = subjectList.stream().collect(Collectors.toMap(Subject::getSubjectId, Subject::getSubjectName));
            List<SubjectEnrolment> subjectEnrolmentList = data.get("subjectenrolment");
            List<String> subjectIdList = subjectList.stream().map(Subject::getSubjectId).collect(Collectors.toList());
            Set<String> enrolledSubjectId = new HashSet<>();
            for (SubjectEnrolment subjectEnrolment : subjectEnrolmentList) {
                if (Objects.equals(subjectEnrolment.getStudentId(), student.getStudentId())) {
                    enrolledSubjectId.add(subjectEnrolment.getSubjectId());
                }
            }
            boolean flag = true;
            while (flag && enrolledSubjectId.size() < 5) {
                System.out.println("1. Enroll");
                System.out.println("2. Quit");
                int option = sc.nextInt();
                if (Objects.equals(option, 1)) {
                    if (enrolledSubjectId.size() == 4) {
                        System.out.println("Already enrolled in 4 subjects");
                        flag = false;
                    } else {
                        int setSize = enrolledSubjectId.size();
                        String selected = subjectIdList.get((int) (Math.random() * subjectIdList.size()));
                        enrolledSubjectId.add(selected);
                        while (enrolledSubjectId.size() == setSize) {
                            selected = subjectIdList.get((int) (Math.random() * subjectIdList.size()));
                            enrolledSubjectId.add(selected);
                        }
                        Long mark = ((long) (Math.random() * 76)) + 25L;
                        String grade = Grade.calculateGrade(mark);
                        SubjectEnrolment subjectEnrolment = new SubjectEnrolment(UUID.randomUUID().toString(), selected, student.getStudentId(), LocalDateTime.now().toString(), mark, grade);
                        subjectEnrolmentList.add(subjectEnrolment);
                    }
                    System.out.println("Enrolled subjects: ");
                    for (String string : enrolledSubjectId) {
                        System.out.println(string + ": " + subjectMap.get(string));
                    }
                    updateLatestSubjectEnrolment(studentData, subjectEnrolmentList);
                } else {
                    flag = false;
                }
            }
        }
        StudentController.sessionStudentDetail(student, data, studentData);
    }

    public static void remove(LinkedHashMap<String, List> data, File studentData, StudentLoginInfo student) {
        List<Subject> subjectList = data.get("subject");
        Map<String, String> subjectMap = subjectList.stream().collect(Collectors.toMap(Subject::getSubjectId, Subject::getSubjectName));
        List<SubjectEnrolment> subjectEnrolmentList = data.get("subjectenrolment");
        if (subjectEnrolmentList.isEmpty()) {
            System.out.println("Enrolment list is empty!");
        } else {
            Set<SubjectEnrolment> subjectEnrolmentSet = new HashSet<>();
            // get student's enrolment
            for (SubjectEnrolment subjectEnrolment : subjectEnrolmentList) {
                if (Objects.equals(subjectEnrolment.getStudentId(), student.getStudentId())) {
                    subjectEnrolmentSet.add(subjectEnrolment);
                }
            }
            Set<String> enrolledIdSet = subjectEnrolmentSet.stream().map(SubjectEnrolment::getSubjectId).collect(Collectors.toSet());
            // show enrolment
            List<SubjectEnrolment> updatedList = removeSubject(subjectEnrolmentSet, subjectMap, enrolledIdSet, subjectEnrolmentList, student, studentData);
            updateLatestSubjectEnrolment(studentData, updatedList);
        }
        StudentController.sessionStudentDetail(student, data, studentData);
    }

    public static void view(LinkedHashMap<String, List> handledData, File studentData, StudentLoginInfo student) {
        List<Subject> subjectList = handledData.get("subject");
        Map<String, String> subjectMap = subjectList.stream().collect(Collectors.toMap(Subject::getSubjectId, Subject::getSubjectName));
        List<SubjectEnrolment> subjectEnrolmentList = handledData.get("subjectenrolment");
        Set<SubjectEnrolment> subjectEnrolmentSet = new HashSet<>();
        List<EnrolmentInfoForStudentView> enrolmentInfoForViewList = new ArrayList<>();
        for (SubjectEnrolment subjectEnrolment : subjectEnrolmentList) {
            if (Objects.equals(subjectEnrolment.getStudentId(), student.getStudentId())) {
                subjectEnrolmentSet.add(subjectEnrolment);
            }
        }
        for (SubjectEnrolment subjectEnrolment : subjectEnrolmentSet) {
            EnrolmentInfoForStudentView enrolment = new EnrolmentInfoForStudentView(subjectMap.get(subjectEnrolment.getSubjectId()), subjectEnrolment.getEnrollTime(), subjectEnrolment.getSubjectMark(), subjectEnrolment.getSubjectGrade());
            enrolmentInfoForViewList.add(enrolment);
        }
        System.out.println("List of enrolled subjects: ");
        if (!enrolmentInfoForViewList.isEmpty()) {
            for (EnrolmentInfoForStudentView info : enrolmentInfoForViewList) {
                System.out.println(info);
            }
        } else {
            System.out.println("No subjects have been enrolled");
        }
        StudentController.sessionStudentDetail(student, handledData, studentData);
    }

    // -------------------------- PRIVATE METHOD --------------------------

    private static void updateLatestSubjectEnrolment(File studentData, List<SubjectEnrolment> subjectEnrolmentList) {
        try {
            List<String> lines = Files.readAllLines(Path.of(studentData.getPath()));
            lines.set(7, new Gson().toJson(subjectEnrolmentList));
            Files.write(Paths.get(studentData.getPath()), lines);
        } catch (IOException e) {
            System.out.println("Error updating student enrolment");
        }
    }

    private static List<SubjectEnrolment> removeSubject(Set<SubjectEnrolment> subjectEnrolmentSet, Map<String, String> subjectMap, Set<String> enrolledIdSet, List<SubjectEnrolment> subjectEnrolmentList, StudentLoginInfo student, File studentData) {
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("Enrolled subjects: ");
            for (SubjectEnrolment subjectEnrolment : subjectEnrolmentSet) {
                System.out.println(subjectEnrolment.getSubjectId() + ": " + subjectMap.get(subjectEnrolment.getSubjectId()));
            }
            System.out.println("Select a subject id to remove, type -1 to quit: ");
            String id = sc.nextLine();
            if (Objects.equals(id, "-1")) {
                System.out.println("Quit removing");
                flag = false;
            }
            // case: no subject with that id / no id in enrolled list
            else if (!subjectMap.containsKey(id) || !enrolledIdSet.contains(id)) {
                System.out.println("Subject is unavailable to delete");
            }
            // remove the selected
            else if (subjectMap.containsKey(id) && enrolledIdSet.contains(id)) {
                subjectEnrolmentSet.removeIf(enrolment -> Objects.equals(enrolment.getSubjectId(), id));
                subjectEnrolmentList.removeIf(enrolment -> Objects.equals(id, enrolment.getSubjectId()) && Objects.equals(student.getStudentId(), enrolment.getStudentId()));
                updateLatestSubjectEnrolment(studentData, subjectEnrolmentList);
            }
        }
        return subjectEnrolmentList;
    }
}
