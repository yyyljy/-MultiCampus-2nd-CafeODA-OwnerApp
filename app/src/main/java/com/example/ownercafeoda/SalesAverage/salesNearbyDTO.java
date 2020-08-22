package com.example.ownercafeoda.SalesAverage;

public class salesNearbyDTO {
    String year;
    int quarter;
    String road;
    String totalamt; // 최대 100억까지 값이 존재함
    int totalcnt;
    int weekdayrate;
    int weekendrate;
    int mondayrate;
    int tuesdayrate;
    int wednesdayrate;
    int thursdayrate;
    int fridayrate;
    int saturdayrate;
    int sundayrate;
    int t0006rate;
    int t0611rate;
    int t1114rate;
    int t1417rate;
    int t1721rate;
    int t2124rate;
    int manrate;
    int womanrate;
    int a10rate;
    int a20rate;
    int a30rate;
    int a40rate;
    int a50rate;
    int a60rate;
    int cafecnt;

    public salesNearbyDTO(String year, int quarter, String road, String totalamt, int totalcnt, int weekdayrate, int weekendrate, int mondayrate, int tuesdayrate, int wednesdayrate, int thursdayrate, int fridayrate, int saturdayrate, int sundayrate, int t0006rate, int t0611rate, int t1114rate, int t1417rate, int t1721rate, int t2124rate, int manrate, int womanrate, int a10rate, int a20rate, int a30rate, int a40rate, int a50rate, int a60rate, int cafecnt) {
        this.year = year;
        this.quarter = quarter;
        this.road = road;
        this.totalamt = totalamt;
        this.totalcnt = totalcnt;
        this.weekdayrate = weekdayrate;
        this.weekendrate = weekendrate;
        this.mondayrate = mondayrate;
        this.tuesdayrate = tuesdayrate;
        this.wednesdayrate = wednesdayrate;
        this.thursdayrate = thursdayrate;
        this.fridayrate = fridayrate;
        this.saturdayrate = saturdayrate;
        this.sundayrate = sundayrate;
        this.t0006rate = t0006rate;
        this.t0611rate = t0611rate;
        this.t1114rate = t1114rate;
        this.t1417rate = t1417rate;
        this.t1721rate = t1721rate;
        this.t2124rate = t2124rate;
        this.manrate = manrate;
        this.womanrate = womanrate;
        this.a10rate = a10rate;
        this.a20rate = a20rate;
        this.a30rate = a30rate;
        this.a40rate = a40rate;
        this.a50rate = a50rate;
        this.a60rate = a60rate;
        this.cafecnt = cafecnt;
    }

    public salesNearbyDTO() {
    }

    @Override
    public String toString() {
        return "salesNearbyDTO{" +
                "year='" + year + '\'' +
                ", quarter=" + quarter +
                ", road='" + road + '\'' +
                ", totalamt='" + totalamt + '\'' +
                ", totalcnt=" + totalcnt +
                ", weekdayrate=" + weekdayrate +
                ", weekendrate=" + weekendrate +
                ", mondayrate=" + mondayrate +
                ", tuesdayrate=" + tuesdayrate +
                ", wednesdayrate=" + wednesdayrate +
                ", thursdayrate=" + thursdayrate +
                ", fridayrate=" + fridayrate +
                ", saturdayrate=" + saturdayrate +
                ", sundayrate=" + sundayrate +
                ", t0006rate=" + t0006rate +
                ", t0611rate=" + t0611rate +
                ", t1114rate=" + t1114rate +
                ", t1417rate=" + t1417rate +
                ", t1721rate=" + t1721rate +
                ", t2124rate=" + t2124rate +
                ", manrate=" + manrate +
                ", womanrate=" + womanrate +
                ", a10rate=" + a10rate +
                ", a20rate=" + a20rate +
                ", a30rate=" + a30rate +
                ", a40rate=" + a40rate +
                ", a50rate=" + a50rate +
                ", a60rate=" + a60rate +
                ", cafecnt=" + cafecnt +
                '}';
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(String totalamt) {
        this.totalamt = totalamt;
    }

    public int getTotalcnt() {
        return totalcnt;
    }

    public void setTotalcnt(int totalcnt) {
        this.totalcnt = totalcnt;
    }

    public int getWeekdayrate() {
        return weekdayrate;
    }

    public void setWeekdayrate(int weekdayrate) {
        this.weekdayrate = weekdayrate;
    }

    public int getWeekendrate() {
        return weekendrate;
    }

    public void setWeekendrate(int weekendrate) {
        this.weekendrate = weekendrate;
    }

    public int getMondayrate() {
        return mondayrate;
    }

    public void setMondayrate(int mondayrate) {
        this.mondayrate = mondayrate;
    }

    public int getTuesdayrate() {
        return tuesdayrate;
    }

    public void setTuesdayrate(int tuesdayrate) {
        this.tuesdayrate = tuesdayrate;
    }

    public int getWednesdayrate() {
        return wednesdayrate;
    }

    public void setWednesdayrate(int wednesdayrate) {
        this.wednesdayrate = wednesdayrate;
    }

    public int getThursdayrate() {
        return thursdayrate;
    }

    public void setThursdayrate(int thursdayrate) {
        this.thursdayrate = thursdayrate;
    }

    public int getFridayrate() {
        return fridayrate;
    }

    public void setFridayrate(int fridayrate) {
        this.fridayrate = fridayrate;
    }

    public int getSaturdayrate() {
        return saturdayrate;
    }

    public void setSaturdayrate(int saturdayrate) {
        this.saturdayrate = saturdayrate;
    }

    public int getSundayrate() {
        return sundayrate;
    }

    public void setSundayrate(int sundayrate) {
        this.sundayrate = sundayrate;
    }

    public int getT0006rate() {
        return t0006rate;
    }

    public void setT0006rate(int t0006rate) {
        this.t0006rate = t0006rate;
    }

    public int getT0611rate() {
        return t0611rate;
    }

    public void setT0611rate(int t0611rate) {
        this.t0611rate = t0611rate;
    }

    public int getT1114rate() {
        return t1114rate;
    }

    public void setT1114rate(int t1114rate) {
        this.t1114rate = t1114rate;
    }

    public int getT1417rate() {
        return t1417rate;
    }

    public void setT1417rate(int t1417rate) {
        this.t1417rate = t1417rate;
    }

    public int getT1721rate() {
        return t1721rate;
    }

    public void setT1721rate(int t1721rate) {
        this.t1721rate = t1721rate;
    }

    public int getT2124rate() {
        return t2124rate;
    }

    public void setT2124rate(int t2124rate) {
        this.t2124rate = t2124rate;
    }

    public int getManrate() {
        return manrate;
    }

    public void setManrate(int manrate) {
        this.manrate = manrate;
    }

    public int getWomanrate() {
        return womanrate;
    }

    public void setWomanrate(int womanrate) {
        this.womanrate = womanrate;
    }

    public int getA10rate() {
        return a10rate;
    }

    public void setA10rate(int a10rate) {
        this.a10rate = a10rate;
    }

    public int getA20rate() {
        return a20rate;
    }

    public void setA20rate(int a20rate) {
        this.a20rate = a20rate;
    }

    public int getA30rate() {
        return a30rate;
    }

    public void setA30rate(int a30rate) {
        this.a30rate = a30rate;
    }

    public int getA40rate() {
        return a40rate;
    }

    public void setA40rate(int a40rate) {
        this.a40rate = a40rate;
    }

    public int getA50rate() {
        return a50rate;
    }

    public void setA50rate(int a50rate) {
        this.a50rate = a50rate;
    }

    public int getA60rate() {
        return a60rate;
    }

    public void setA60rate(int a60rate) {
        this.a60rate = a60rate;
    }

    public int getCafecnt() {
        return cafecnt;
    }

    public void setCafecnt(int cafecnt) {
        this.cafecnt = cafecnt;
    }
}
