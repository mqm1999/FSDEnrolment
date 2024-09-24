package authen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.Role;
import entity.Student;
import entity.User;
import util.Utils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Authentication {

    public static void register(File studentData) {
        StringBuilder stringBuilder = new StringBuilder();
        Student student = new Student();
        Scanner sc = new Scanner(System.in);
        boolean usernameFlag = false;
        boolean passwordFlag = false;
        String username = null;
        String password = null;
        System.out.println("Email: ");
        String firstUsername = sc.nextLine();
        if (!Utils.validateEmail(firstUsername)) {
            while (!usernameFlag) {
                System.out.println("Invalid email. Enter new email: ");
                username = sc.nextLine();
                usernameFlag = Utils.validateEmail(username);
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
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(password);
        Long roleId = 0L;
        try {
            Scanner scanner = new Scanner(studentData);
            if (scanner.hasNextLine()) {
                String roleData = scanner.nextLine();
                List<User> userList = new ArrayList<>();
                if (scanner.hasNextLine()) {
                    String userData = scanner.nextLine();
                    Type userListType = new TypeToken<List<User>>() {
                    }.getType();
                    userList = new Gson().fromJson(userData, userListType);
                }
                stringBuilder.append(roleData).append("\n");
                Type roleListType = new TypeToken<List<Role>>() {
                }.getType();
                List<Role> roleList = new Gson().fromJson(roleData, roleListType);
                for (Role role : roleList) {
                    if (Objects.equals(role.getRoleName().toLowerCase(), "student")) {
                        roleId = role.getId();
                        break;
                    }
                }
                user.setRoleId(roleId);
                userList.add(user);
                stringBuilder.append(new Gson().toJson(userList));
                FileWriter fw = new FileWriter(studentData.getPath());
                fw.append(stringBuilder);
                fw.close();
                System.out.println("Finish creating user");
            }
        } catch (Exception e) {
            System.out.println("Error when register");
        }
    }
}
