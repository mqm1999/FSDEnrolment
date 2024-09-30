package service;

import com.google.gson.Gson;
import dto.StudentLoginInfo;
import entity.Role;
import entity.Student;
import entity.User;
import util.Utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Authentication {

    public static void register(LinkedHashMap<String, List> data, File studentData) {
        List<User> userList = data.get("user");
        Scanner sc = new Scanner(System.in);
        boolean usernameFlag = false;
        boolean passwordFlag = false;
        String username = null;
        String password = null;
        System.out.println("Email: ");
        String firstUsername = sc.nextLine();
        if (!Utils.validateEmail(firstUsername) || !Authentication.validateEmailDuplication(firstUsername, userList)) {
            while (!usernameFlag) {
                System.out.println("Invalid email. Enter new email: ");
                username = sc.nextLine();
                usernameFlag = Utils.validateEmail(username) && Authentication.validateEmailDuplication(username, userList);

            }
        } else {
            username = firstUsername;
        }
        // TODO: find way to mask the echo password and hash
        System.out.println("Password: ");
        String firstPassword = sc.nextLine();
        if (!Utils.validatePassword(firstPassword)) {
            while (!passwordFlag) {
                System.out.println("Invalid password. Enter new password: ");
                password = sc.nextLine();
                passwordFlag = Utils.validatePassword(password);
            }
        } else {
            password = firstPassword;
        }

        // get student role id
        Long roleId = 0L;
        List<Role> roleList = data.get("role");
        for (Role role : roleList) {
            if (Objects.equals("student", role.getRoleName().toLowerCase())) {
                roleId = role.getId();
            }
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(password);
        user.setRoleId(roleId);

        // get user list to add
        userList.add(user);

        // create student linked with user
        List<Student> studentList = data.get("student");
        List<String> studentIdList = studentList.stream().map(Student::getStudentId).collect(Collectors.toList());
        boolean studentIdFlag = true;
        DecimalFormat df = new DecimalFormat("000000");
        long genId = (long) ((Math.random() * 999999) + 1);
        if (Authentication.checkStudentId(studentIdList, genId, df)) {
            while (studentIdFlag) {
                genId = (long) ((Math.random() * 999999) + 1);
                studentIdFlag = Authentication.checkStudentId(studentIdList, genId, df);
            }
        }
        Student student = new Student();
        student.setUserId(user.getId());
        student.setStudentId(df.format(genId));
        student.setEmail(user.getUsername());
        studentList.add(student);

        // add updated info to map
        data.put("user", userList);
        data.put("student", studentList);

        try {
            List<String> lines = Files.readAllLines(Path.of(studentData.getPath()));
            lines.set(1, new Gson().toJson(userList));
            lines.set(3, new Gson().toJson(studentList));
            Files.write(Paths.get(studentData.getPath()), lines);
        } catch (Exception e) {
            System.out.println("Cannot save file after registering new student");
        }
        System.out.println("Registered new student");
    }

    public static StudentLoginInfo studentLogin(LinkedHashMap<String, List> data) {
        List<User> userList = data.get("user");
        Map<String, String> userMap = userList.stream().collect(Collectors.toMap(User::getUsername, User::getPassword));
        List<Student> studentList = data.get("student");
        Map<String, Student> studentMap = studentList.stream().collect(Collectors.toMap(Student::getEmail, student -> student));
        return handleLogin(userMap, studentMap);
    }

    private static StudentLoginInfo handleLogin(Map<String, String> userMap, Map<String, Student> studentMap) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        String firstUsername = sc.nextLine();
        System.out.println("Password: ");
        String firstPassword = sc.nextLine();
        return checkLoginInfo(userMap, studentMap, firstPassword, firstUsername);

    }

    private static StudentLoginInfo checkLoginInfo(Map<String, String> userMap, Map<String, Student> studentMap, String firstPassword, String firstUsername) {
        if (!userMap.containsKey(firstUsername)) {
            System.out.println("User is not found");
            return null;
        } else if (userMap.containsKey(firstUsername) && !Objects.equals(userMap.get(firstUsername), firstPassword)) {
            System.out.println("Login information is incorrect");
            return null;
        } else {
            System.out.println("Login successfully");
            Student student = studentMap.get(firstUsername);
            return new StudentLoginInfo(student.getEmail(), student.getStudentId());
        }
    }

    private static boolean validateEmailDuplication(String username, List<User> userList) {
        return !userList.stream().map(User::getUsername).collect(Collectors.toList()).contains(username);
    }

    public static boolean checkStudentId(List<String> studentIdList, Long genId, DecimalFormat df) {
        String genIdString = df.format(genId);
        return studentIdList.contains(genIdString);
    }

    public static void adminLogin(LinkedHashMap<String, List> handledData, File studentData) {
    }
}
