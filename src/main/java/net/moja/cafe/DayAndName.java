package net.moja.cafe;

public class DayAndName {

    private String name;
    private String day;
    private int nameId;
    private int dayId;

    public DayAndName(String name, String day) {
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
}
