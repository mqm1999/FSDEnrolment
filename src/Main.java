import authen.StudentRegistration;
import entity.Role;
import util.Utils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        File studentData = Utils.createFile();
        List<Role> roleList = Utils.createListRole();
        Utils.addInitialData(studentData, roleList);
        while (true) {
            LinkedHashMap<String, List> handledData = Utils.transformData(Objects.requireNonNull(Utils.handleDataFromFile(studentData)));
            StudentRegistration.register(handledData, studentData);
        }
    }
}