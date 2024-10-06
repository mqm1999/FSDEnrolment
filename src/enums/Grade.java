package enums;

public enum Grade {
    Z("Z"),
    P("P"),
    C("C"),
    D("D"),
    HD("HD");

    Grade(String grade) {
    }

    public static String calculateGrade(Long mark) {
        if (mark < 50) {
            return Grade.Z.toString();
        } else if (mark < 65 && mark >= 50) {
            return Grade.P.toString();
        } else if (mark < 75 && mark >= 65) {
            return Grade.C.toString();
        } else if (mark < 85 && mark >= 75) {
            return Grade.D.toString();
        } else if (mark <= 100 && mark >= 85) {
            return Grade.HD.toString();
        } else {
            return "Mark must be between 0 and 100";
        }
    }
}
