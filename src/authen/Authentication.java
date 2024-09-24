package authen;

import entity.Role;
import entity.Student;
import entity.User;
import util.Utils;

import java.io.File;
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
        long genId = (long) (Math.random() * 1000000 - 1);
        if (Authentication.checkStudentId(studentIdList, genId, df)) {
            while (studentIdFlag) {
                genId = (long) (Math.random() * 1000000 - 1);
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

        Utils.writeFile(data, studentData);
        System.out.println("Registered new student");
    }

    private static boolean validateEmailDuplication(String username, List<User> userList) {
        return !userList.stream().map(User::getUsername).collect(Collectors.toList()).contains(username);
    }

    public static boolean checkStudentId(List<String> studentIdList, Long genId, DecimalFormat df) {
        String genIdString = df.format(genId);
        return studentIdList.contains(genIdString);
    }
}
