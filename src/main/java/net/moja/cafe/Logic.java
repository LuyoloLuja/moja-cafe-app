package net.moja.cafe;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class Logic {

    String dbDiskURL = "jdbc:sqlite:file:./mojaCafeDB.db";
    Jdbi jdbi = Jdbi.create(dbDiskURL);
    Handle handle = jdbi.open();

    public Logic() {
            handle.execute("CREATE TABLE IF NOT EXISTS Waiter (id INTEGER NOT NULL PRIMARY KEY, name TEXT)");
            handle.execute("CREATE TABLE IF NOT EXISTS Day (id INTEGER NOT NULL PRIMARY KEY, day TEXT)");

            handle.execute("CREATE TABLE IF NOT EXISTS Waiter_Shift " +
                    "(id INTEGER NOT NULL PRIMARY KEY, " +
                    "waiter_id INTEGER, day_id INTEGER, " +
                    "FOREIGN KEY (waiter_id) REFERENCES Waiter(id), " +
                    "FOREIGN KEY (day_id) REFERENCES Day(id))");
    }

    public void addWaiterDetails(String name, String day) {
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

        int nameId = getNameId(name);
        int dayId = getDayId(day);

        handle.execute("INSERT INTO Waiter_Shift (waiter_id, day_id) VALUES (?, ?)", nameId, dayId);
    }

    public int getNameId(String name) {
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

        int checkDuplicates = handle.select("SELECT COUNT(*) FROM Waiter WHERE name = ?", name).mapTo(int.class).findOnly();

        if (checkDuplicates == 0) {
            handle.execute("INSERT INTO Waiter (name) VALUES (?)", name);
        }

        return handle.execute("SELECT id FROM Waiter WHERE name = ?", name);
    }

    public int getDayId(String day) {
        return handle.execute("SELECT id FROM Day WHERE day = ?" , day);
    }

    public String getWaiterDetails() {
        handle.execute(
                "SELECT DISTINCT Waiter.id, name, " +
                        "Day.id FROM Waiter_Shift " +
                        "JOIN Waiter ON" +
                        " Waiter_Shift.id = Waiter.id " +
                        "JOIN Day ON Waiter_Shift.day_id = Day.id;");
        return null;
    }

//    public int getSingleWaiterDetails(String name) {
//        int waiterId = getNameId(name);
//        return handle.execute("SELECT day_id FROM Waiter_Shift WHERE waiter_id = ?", waiterId);
//    }
}
