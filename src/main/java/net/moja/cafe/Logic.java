package net.moja.cafe;

public class Logic {

    private String name;
    private String day;
    private int nameId;
    private int dayId;

    public Logic(String name, String day) {
        this.name = name;
        this.day = day;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDay(String day) {
        this.name = day;
    }
    public void setNameId(int nameId) {
        this.nameId = nameId;
    }
    private void setName(int dayId) {
        this.dayId = dayId;
    }

    public String getName() {
        return name;
    }
    public String getDay() {
        return day;
    }
    public int getNameId() {
        return nameId;
    }
    public int getDayId() {
        return dayId;
    }
//    public void addWaiterDetails(String name, String day) {
//        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
//
//        int nameId = getNameId(name);
//        int dayId = getDayId(day);
//
//        handle.execute("INSERT INTO Waiter_Shift (waiter_id, day_id) VALUES (?, ?)", nameId, dayId);
//    }
//
//    public int getNameId(String name) {
//        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
//
//        int checkDuplicates = handle.select("SELECT COUNT(*) FROM Waiter WHERE name = ?", name).mapTo(int.class).findOnly();
//
//        if (checkDuplicates == 0) {
//            handle.execute("INSERT INTO Waiter (name) VALUES (?)", name);
//        }
//
//        return handle.execute("SELECT id FROM Waiter WHERE name = ?", name);
//    }
//
//    public int getDayId(String day) {
//        return handle.execute("SELECT id FROM Day WHERE day = ?" , day);
//    }
//    public String getWaiterDetails() {
//        handle.execute(
//                "SELECT DISTINCT Waiter.id, name, " +
//                        "Day.id FROM Waiter_Shift " +
//                        "JOIN Waiter ON" +
//                        " Waiter_Shift.id = Waiter.id " +
//                        "JOIN Day ON Waiter_Shift.day_id = Day.id;");
//        return null;
//    }
//
//    public int getSingleWaiterDetails(String name) {
//        int waiterId = getNameId(name);
//        return handle.execute("SELECT day_id FROM Waiter_Shift WHERE waiter_id = ?", waiterId);
//    }
//
//    public void getDays() {
//        handle.execute("SELECT day FROM Day");
//    }
}
