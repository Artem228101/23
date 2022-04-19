package sample.database;

public class User {
    private static boolean role;
    private static String username;
    private static int editedRecordId;

    public static boolean getRole() {return role;}
    public static void setRole(boolean hasLogged) {User.role = hasLogged;}
    public static String getUsername() {return username;}
    public static void setUsername(String editedRecordName) {User.username = editedRecordName;}
    public static int getEditedRecordId() {return editedRecordId;}
    public static void setEditedRecordId(int editedRecordId) {User.editedRecordId = editedRecordId;}
}
