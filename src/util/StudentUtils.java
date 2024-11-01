package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constant.Constants;
import entity.Admin;
import entity.Course;
import entity.CourseEnrolment;
import entity.Role;
import entity.Semester;
import entity.Student;
import entity.Subject;
import entity.SubjectEnrolment;
import entity.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StudentUtils {
    public static boolean validateEmail(String email) {
        return !Pattern.matches(".*@university\\.com$", email);
    }

    public static boolean validatePassword(String password) {
        return password.length() < 5 || !Pattern.matches("^[A-Z].*\\d{3,}.*", password);
    }

    public static File createFile() {
        File studentData = new File("./students.data");
        try {
            if (studentData.createNewFile()) {
                System.out.println("File created: " + studentData.getName());
            } else {
                System.out.println("File already exists.");
            }
            return studentData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addInitialData(File file, List<Role> roleList, List<Subject> subjectList) {
        /*
         * file format:
         * - role
         * - user
         * - admin
         * - student
         * - semester
         * - subject
         * - course
         * - subject enrolment
         * - course enrolment
         */
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileWriter fw = new FileWriter(file.getPath());
            stringBuilder.append(new Gson().toJson(roleList)).append("\n");
            stringBuilder.append("\n".repeat(4));
            stringBuilder.append(new Gson().toJson(subjectList));
            stringBuilder.append("\n".repeat(3));
            fw.write(stringBuilder.toString());
            fw.close();
            System.out.println("Finish creating roles");
        } catch (IOException e) {
            System.out.println("Error file writer");
        }
    }

    public static LinkedHashMap<String, String> handleDataFromFile(File file) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getName()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.add(line);
            }
            for (int i = 0; i < fileContent.size(); i++) {
                map.put(Constants.FILE_CONTENT[i], fileContent.get(i));
            }
            return map;
        } catch (FileNotFoundException e) {
            System.out.println("File not found when handle data");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LinkedHashMap<String, List> transformData(LinkedHashMap<String, String> map) {
        LinkedHashMap<String, List> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> value : map.entrySet()) {
            switch (value.getKey().toLowerCase()) {
                case "role":
                    Type roleListType = new TypeToken<List<Role>>() {
                    }.getType();
                    List<Role> roleList = new Gson().fromJson(value.getValue(), roleListType);
                    result.put(value.getKey(), roleList);
                    break;
                case "user":
                    Type userListType = new TypeToken<List<User>>() {
                    }.getType();
                    List<User> userList = new Gson().fromJson(value.getValue(), userListType);
                    if (Objects.equals(userList, null)) {
                        userList = new ArrayList<>();
                    }
                    result.put(value.getKey(), userList);
                    break;
                case "admin":
                    Type adminListType = new TypeToken<List<Admin>>() {
                    }.getType();
                    List<Admin> adminList = new Gson().fromJson(value.getValue(), adminListType);
                    if (Objects.equals(adminList, null)) {
                        adminList = new ArrayList<>();
                    }
                    result.put(value.getKey(), adminList);
                    break;
                case "student":
                    Type studentListType = new TypeToken<List<Student>>() {
                    }.getType();
                    List<Student> studentList = new Gson().fromJson(value.getValue(), studentListType);
                    if (Objects.equals(studentList, null)) {
                        studentList = new ArrayList<>();
                    }
                    result.put(value.getKey(), studentList);
                    break;
                case "semester":
                    Type semesterListType = new TypeToken<List<Semester>>() {
                    }.getType();
                    List<Semester> semesterList = new Gson().fromJson(value.getValue(), semesterListType);
                    if (Objects.equals(semesterList, null)) {
                        semesterList = new ArrayList<>();
                    }
                    result.put(value.getKey(), semesterList);
                    break;
                case "subject":
                    Type subjectListType = new TypeToken<List<Subject>>() {
                    }.getType();
                    List<Subject> subjectList = new Gson().fromJson(value.getValue(), subjectListType);
                    if (Objects.equals(subjectList, null)) {
                        subjectList = new ArrayList<>();
                    }
                    result.put(value.getKey(), subjectList);
                    break;
                case "course":
                    Type courseListType = new TypeToken<List<Course>>() {
                    }.getType();
                    List<Course> courseList = new Gson().fromJson(value.getValue(), courseListType);
                    if (Objects.equals(courseList, null)) {
                        courseList = new ArrayList<>();
                    }
                    result.put(value.getKey(), courseList);
                    break;
                case "subjectenrolment":
                    Type subjectEnrolmentListType = new TypeToken<List<SubjectEnrolment>>() {
                    }.getType();
                    List<SubjectEnrolment> subjectEnrolmentList = new Gson().fromJson(value.getValue(), subjectEnrolmentListType);
                    if (Objects.equals(subjectEnrolmentList, null)) {
                        subjectEnrolmentList = new ArrayList<>();
                    }
                    result.put(value.getKey(), subjectEnrolmentList);
                    break;
                case "courseenrolment":
                    Type courseEnrolmentListType = new TypeToken<List<CourseEnrolment>>() {
                    }.getType();
                    List<CourseEnrolment> courseEnrolmentList = new Gson().fromJson(value.getValue(), courseEnrolmentListType);
                    if (Objects.equals(courseEnrolmentList, null)) {
                        courseEnrolmentList = new ArrayList<>();
                    }
                    result.put(value.getKey(), courseEnrolmentList);
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public static List<Role> createListRole() {
        Role student = new Role(1L, "student");
        Role admin = new Role(2L, "admin");
        List<Role> roleList = new ArrayList<>();
        roleList.add(student);
        roleList.add(admin);
        return roleList;
    }

    public static List<Subject> createListSubject() {
        List<Subject> subjectList = new ArrayList<>();
        Subject fsd = new Subject(createSubjectId(subjectList), "Fundamentals of Software Development");
        subjectList.add(fsd);
        Subject database = new Subject(createSubjectId(subjectList), "Database");
        subjectList.add(database);
        Subject eeis = new Subject(createSubjectId(subjectList), "Enabling Enterprise Information Systems");
        subjectList.add(eeis);
        Subject unix = new Subject(createSubjectId(subjectList), "Unix Systems Programming");
        subjectList.add(unix);
        Subject lans = new Subject(createSubjectId(subjectList), "LANS and Routing");
        subjectList.add(lans);
        Subject projectManagement = new Subject(createSubjectId(subjectList), "Project Management");
        subjectList.add(projectManagement);
        return subjectList;
    }

    public static String createSubjectId(List<Subject> subjectList) {
        String id = null;
        boolean flag = true;
        List<String> subjectIdList = subjectList.stream().map(Subject::getSubjectId).collect(Collectors.toList());
        while (flag) {
            String genId = generateSubjectId();
            if (!subjectIdList.contains(genId)) {
                id = genId;
                flag = false;
            }
        }
        return id;
    }

    public static String generateSubjectId() {
        return String.valueOf((int) ((Math.random() * 999) + 1));
    }
}
