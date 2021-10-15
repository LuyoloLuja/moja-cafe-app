package net.moja.cafe;

public class Shift {
    private String waiterName;
    private String dayName;

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayName() {
        return dayName;
    }

    //    public String getWaiterDay() {
//        return dayName;
//    }

    @Override
    public String toString() {
        return "Shift{" +
                "waiterName='" + waiterName + '\'' +
                ", dayName='" + dayName + '\'' +
                '}';
    }
}
