package service;

import com.google.gson.Gson;
import constant.Constants;
import dto.StudentLoginInfo;
import entity.Role;
import entity.Student;
import entity.User;
import util.SecurityUtils;
import util.Utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class Authentication {

    public static void register(LinkedHashMap<String, List> data, File studentData) {
        List<User> userList = data.get("user");
        Scanner sc = new Scanner(System.in);
        boolean usernameFlag = false;
        int count = Constants.RETRY;
        String username = null;
        String password = null;
        System.out.println("Email: ");
        String firstUsername = sc.nextLine();
        if (!Utils.validateEmail(firstUsername) || !Authentication.validateEmailDuplication(firstUsername, userList)) {
            while (!usernameFlag) {
                System.out.println("Invalid email. Enter new email: ");
                username = sc.nextLine().trim();
                usernameFlag = Utils.validateEmail(username) && Authentication.validateEmailDuplication(username, userList);

            }
        } else {
            username = firstUsername;
        }
        // TODO: find way to mask the echo password
        System.out.println("Password: ");
        String firstPassword = sc.nextLine();
        if (!Utils.validatePassword(firstPassword)) {
            while (count > 0) {
                System.out.println("Invalid password. Enter new password, type -1 to quit creating new user: ");
                String retryPassword = sc.nextLine();
                if (Objects.equals("-1", password)) {
                    count = 0;
                } else {
                    if (!Utils.validatePassword(retryPassword)) {
                        if (count == 1) {
                            System.out.println("Please retry later");
                            return;
                        } else {
                            count--;
                        }
                    } else {
                        password = retryPassword;
                        count = 0;
                    }
                }
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
        user.setPassword(SecurityUtils.hashPassword(password));
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

    public static void changePassword(LinkedHashMap<String, List> handledData, File studentData, StudentLoginInfo student) {
        Scanner sc = new Scanner(System.in);
        List<User> userList = handledData.get("user");
        int count = Constants.RETRY;
        Map<String, String> userMap = userList.stream().collect(Collectors.toMap(User::getUsername, User::getPassword));
        Map<String, User> userInfoMap = userList.stream().collect(Collectors.toMap(User::getUsername, user -> user));
        System.out.println("Enter the old password: ");
        String oldPassword = sc.nextLine();
        if (!SecurityUtils.checkPassword(oldPassword, userMap.get(student.getEmail()))) {
            if (enterOldPassword(userMap.get(student.getEmail()), count)) {
                System.out.println("Enter new password: ");
                String newPassword = sc.nextLine();
                if (!Utils.validatePassword(newPassword)) {
                    handleUpdatePassword(count, student, userInfoMap, studentData);
                    Utils.sessionStudentDetail(student, handledData, studentData);
                } else {
                    updateNewPassword(userInfoMap, studentData, student, newPassword);
                }
            } else {
                Utils.sessionStudentDetail(student, handledData, studentData);
            }
        } else {
            System.out.println("Enter new password: ");
            String newPassword = sc.nextLine();
            if (!Utils.validatePassword(newPassword)) {
                handleUpdatePassword(count, student, userInfoMap, studentData);
                Utils.sessionStudentDetail(student, handledData, studentData);
            } else {
                updateNewPassword(userInfoMap, studentData, student, newPassword);
            }
        }
    }

    public static void adminLogin(LinkedHashMap<String, List> handledData, File studentData) {
    }
    // -------------------------- PRIVATE METHOD --------------------------

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
        } else if (userMap.containsKey(firstUsername) && !SecurityUtils.checkPassword(firstPassword, userMap.get(firstUsername))) {
            System.out.println("Login information is incorrect");
            return null;
        } else if (userMap.containsKey(firstUsername) && SecurityUtils.checkPassword(firstPassword, userMap.get(firstUsername))) {
            System.out.println("Login successfully");
            Student student = studentMap.get(firstUsername);
            return new StudentLoginInfo(student.getEmail(), student.getStudentId());
        }
        return null;
    }

    private static boolean validateEmailDuplication(String username, List<User> userList) {
        return !userList.stream().map(User::getUsername).collect(Collectors.toList()).contains(username);
    }

    private static boolean checkStudentId(List<String> studentIdList, Long genId, DecimalFormat df) {
        String genIdString = df.format(genId);
        return studentIdList.contains(genIdString);
    }

    private static boolean enterOldPassword(String hashPassword, int count) {
        Scanner sc = new Scanner(System.in);
        while (count > 0) {
            System.out.println("Wrong password, enter old password again: ");
            String secondPassword = sc.nextLine();
            if (SecurityUtils.checkPassword(secondPassword, hashPassword)) {
                return true;
            } else {
                count--;
            }
        }
        System.out.println("Please retry later");
        return false;
    }

    private static void handleUpdatePassword(int count, StudentLoginInfo student, Map<String, User> userInfoMap, File studentData) {
        Scanner sc = new Scanner(System.in);
        String password;
        // change password process
        while (count > 0) {
            System.out.println("Invalid password. Enter new password, type -1 to stop changing password: ");
            password = sc.nextLine();
            if (Objects.equals(password, "-1")) {
                count = 0;
            } else {
                if (!Utils.validatePassword(password)) {
                    if (count == 1) {
                        System.out.println("Please retry later");
                        count = 0;
                    } else {
                        count--;
                    }
                } else {
                    updateNewPassword(userInfoMap, studentData, student, password);
                    count = 0;
                }
            }
        }
    }

    private static void updateNewPassword(Map<String, User> userInfoMap, File studentData, StudentLoginInfo student, String password) {
        userInfoMap.get(student.getEmail()).setPassword(SecurityUtils.hashPassword(password));
        List<User> updatedInfo = new ArrayList<>(userInfoMap.values());
        try {
            List<String> lines = Files.readAllLines(Path.of(studentData.getPath()));
            lines.set(1, new Gson().toJson(updatedInfo));
            Files.write(Paths.get(studentData.getPath()), lines);
            System.out.println("New password updated");
        } catch (Exception e) {
            System.out.println("Error updating user info after changing password");
        }
    }
}
