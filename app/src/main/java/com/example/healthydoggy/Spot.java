package com.example.healthydoggy;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Spot implements Parcelable {
    private int id;
    private String address;
    private int bed;
    private String dateTour;
    private String description;
    private String distance;
    private String duration;
    private int price;
    private double score;
    private String tourCount;
    private String title;
    private List<String> pic;

    // 空构造方法（Gson解析需要）
    public Spot() {}

    // 从Parcel中读取数据的构造方法
    protected Spot(Parcel in) {
        id = in.readInt();
        address = in.readString();
        bed = in.readInt();
        dateTour = in.readString();
        description = in.readString();
        distance = in.readString();
        duration = in.readString();
        price = in.readInt();
        score = in.readDouble();
        tourCount = in.readString();
        title = in.readString();
        pic = in.createStringArrayList(); // 读取字符串列表
    }

    // Parcelable Creator（必须实现）
    public static final Creator<Spot> CREATOR = new Creator<Spot>() {
        @Override
        public Spot createFromParcel(Parcel in) {
            return new Spot(in);
        }

        @Override
        public Spot[] newArray(int size) {
            return new Spot[size];
        }
    };

    // 以下是原有的getter和setter方法（保持不变）
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public String getDateTour() {
        return dateTour;
    }

    public void setDateTour(String dateTour) {
        this.dateTour = dateTour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getTourCount() {
        return tourCount;
    }

    public void setTourCount(String tourCount) {
        this.tourCount = tourCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    // Parcelable接口必须实现的方法（返回0即可）
    @Override
    public int describeContents() {
        return 0;
    }

    // 将数据写入Parcel（与构造方法中的读取顺序对应）
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(address);
        dest.writeInt(bed);
        dest.writeString(dateTour);
        dest.writeString(description);
        dest.writeString(distance);
        dest.writeString(duration);
        dest.writeInt(price);
        dest.writeDouble(score);
        dest.writeString(tourCount);
        dest.writeString(title);
        dest.writeStringList(pic); // 写入字符串列表
    }
}