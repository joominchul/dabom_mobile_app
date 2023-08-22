package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName ="kangwon_apt")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")// id
    @NonNull
    private int id;

    @ColumnInfo(name = "latitude")
    @NonNull
    private double latitude;

    @ColumnInfo(name = "longitude")
    @NonNull
    private double longitude;

    @ColumnInfo(name = "type")
    @NonNull
    private String type;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "si")
    @NonNull
    private String si;

    @ColumnInfo(name = "gu")
    @NonNull
    private String gu;

    @ColumnInfo(name = "dong")
    @NonNull
    private String dong;

    @ColumnInfo(name = "maxdealprice")
    @NonNull
    private int maxdealprice;

    @ColumnInfo(name = "mindealprice")
    @NonNull
    private int mindealprice;

    @ColumnInfo(name = "maxlprice")
    @NonNull
    private int maxlprice;

    @ColumnInfo(name = "minlprice")
    @NonNull
    private int minlprice;

    @ColumnInfo(name = "lrate")
    @NonNull
    private double lrate;

    @ColumnInfo(name = "real1year")      // 실거래년1
    @NonNull
    private int real1year;

    @ColumnInfo(name = "real1month")     // 실거래월1
    @NonNull
    private int real1month;

    @ColumnInfo(name = "real1dealprice") // 실거래기1
    @NonNull
    private int real1dealprice;

    @ColumnInfo(name = "real2year")      // 실거래년2
    @NonNull
    private int real2year;

    @ColumnInfo(name = "real2month")     // 실거래월2
    @NonNull
    private int real2month;

    @ColumnInfo(name = "real2dealprice") // 실거래가2
    @NonNull
    private int real2dealprice;

    @ColumnInfo(name = "real3year")      // 실거래년3
    @NonNull
    private int real3year;

    @ColumnInfo(name = "real3month")     // 실거래월3
    @NonNull
    private int real3month;

    @ColumnInfo(name = "real3dealprice") // 실거래가3
    @NonNull
    private int real3dealprice;

    @ColumnInfo(name = "colnum") // 아파트 매물 번호
    @NonNull
    private int colnum;





    public int getId() {
        return id;
    }


    public void setId(int colnum) {
        this.id = id;
    }

    public int getColnum() {
        return colnum;
    }


    public void setColnum(int colnum) {
        this.colnum = colnum;
    }



    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSi() {
        return si;
    }

    public String getGu() {
        return gu;
    }

    public String getDong() {
        return dong;
    }

    public int getMaxdealprice() {
        return maxdealprice;
    }

    public int getMindealprice() {
        return mindealprice;
    }

    public int getMaxlprice() {
        return maxlprice;
    }

    public int getMinlprice() {
        return minlprice;
    }

    public double getLrate() {
        return lrate;
    }

    public int getReal1year() {
        return real1year;
    }

    public int getReal1month() {
        return real1month;
    }

    public int getReal1dealprice() {
        return real1dealprice;
    }

    public int getReal2year() {
        return real2year;
    }

    public int getReal2month() {
        return real2month;
    }

    public int getReal2dealprice() {
        return real2dealprice;
    }

    public int getReal3year() {
        return real3year;
    }

    public int getReal3month() {
        return real3month;
    }

    public int getReal3dealprice() {
        return real3dealprice;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public void setGu(String gu) {
        this.gu = gu;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public void setMaxdealprice(int maxdealprice) {
        this.maxdealprice = maxdealprice;
    }

    public void setMindealprice(int mindealprice) {
        this.mindealprice = mindealprice;
    }

    public void setMaxlprice(int maxlprice) {
        this.maxlprice = maxlprice;
    }

    public void setMinlprice(int minlprice) {
        this.minlprice = minlprice;
    }

    public void setLrate(double lrate) {
        this.lrate = lrate;
    }

    public void setReal1year(int real1year) {
        this.real1year = real1year;
    }

    public void setReal1month(int real1month) {
        this.real1month = real1month;
    }

    public void setReal1dealprice(int real1dealprice) {
        this.real1dealprice = real1dealprice;
    }

    public void setReal2year(int real2year) {
        this.real2year = real2year;
    }

    public void setReal2month(int real2month) {
        this.real2month = real2month;
    }

    public void setReal2dealprice(int real2dealprice) {
        this.real2dealprice = real2dealprice;
    }

    public void setReal3year(int real3year) {
        this.real3year = real3year;
    }

    public void setReal3month(int real3month) {
        this.real3month = real3month;
    }

    public void setReal3dealprice(int real3dealprice) {
        this.real3dealprice = real3dealprice;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
