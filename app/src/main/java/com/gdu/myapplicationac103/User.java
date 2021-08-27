package com.gdu.myapplicationac103;

/**
 * Description:
 * Created by Czm on 2021/8/26 17:15.
 */
public class User {
    private int id;
    private String name;
    private int time;
    private int calorie;
    private int mileage;
    private int avgVelocity;
    private long createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getAvgVelocity() {
        return avgVelocity;
    }

    public void setAvgVelocity(int avgVelocity) {
        this.avgVelocity = avgVelocity;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public User(int id, String name, int time, int calorie, int mileage, int avgVelocity, long createDate) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.calorie = calorie;
        this.mileage = mileage;
        this.avgVelocity = avgVelocity;
        this.createDate = createDate;
    }

    public User(String name, int time, int calorie, int mileage, int avgVelocity, long createDate) {
        this.name = name;
        this.time = time;
        this.calorie = calorie;
        this.mileage = mileage;
        this.avgVelocity = avgVelocity;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", calorie=" + calorie +
                ", mileage=" + mileage +
                ", avgVelocity=" + avgVelocity +
                ", createDate=" + createDate +
                '}';
    }
}
