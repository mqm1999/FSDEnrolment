package util;

import com.google.gson.Gson;
import constant.Constants;
import entity.Role;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Utils {
    public static boolean validateEmail(String email) {
        return Pattern.matches(".*@university\\.com$", email);
    }

    public static boolean validatePassword(String password) {
        return password.length() >= 5 && Pattern.matches("^[A-Z]\\d{3,}.*", password);
    }

    public static File createFile() {
        File studentData = new File("./students.data");
        try {
            if (studentData.createNewFile()) {
                System.out.println("File created: "
                        + studentData.getName());
            } else {
                System.out.println("File already exists.");
            }
            return studentData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Role> createListRole() {
        Role student = new Role(1L, "student");
        Role admin = new Role(2L, "admin");
        List<Role> roleList = new ArrayList<>();
        roleList.add(student);
        roleList.add(admin);
        return roleList;
    }

    public static void addInitialData(File file, List<Role> roleList) {
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
         * */
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileWriter fw = new FileWriter(file.getPath());
            stringBuilder.append(new Gson().toJson(roleList));
            stringBuilder.append("\n".repeat(8));
            fw.write(stringBuilder.toString());
            fw.close();
            System.out.println("Finish creating roles");
        } catch (IOException e) {
            System.out.println("Error file writer");
        }
    }

    public static Map<String, String> handleDataFromFile(File file) {
        Map<String, String> map = new HashMap<>();
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getName()))) {
            while (bufferedReader.readLine() != null) {
                fileContent.add(bufferedReader.readLine());
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

    public static Map<String, >
}
