package com.gatis.leksika.topscores;

import java.math.BigDecimal;

/**
 * Created by roberts on 16.7.7.
 */
public class TopScore implements Comparable<TopScore> {
    private String name;
    private Integer score_got;
    private Integer score_possible;
    private Long timestamp;
    private Integer place;
    private Double ratio_got;

    public TopScore(String name, Integer score_got, Integer score_possible, Long timestamp) {
        this.name = name;
        this.score_got = score_got;
        this.score_possible = score_possible;
        this.timestamp = timestamp;
        this.ratio_got = getPercentage(score_got,score_possible);
    }

    // Overriding the compareTo method
    public int compareTo(TopScore d){
        return (this.ratio_got).compareTo(d.ratio_got);
    }

    // Overriding the compare method to sort the percentage got
    public int compare(TopScore d, TopScore d1){
        Double x = d1.ratio_got*100 - d.ratio_got*100;
        return x.intValue();
    }

    public String getName() {
        return name;
    }

    public Integer getScoreGot() {
        return score_got;
    }

    public Integer getScorePossible() {
        return score_possible;
    }

    public Long getDateTs() {
        return timestamp;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer p) {
        this.place = p;
    }

    public static Double getPercentage(int n, int total) {
        if (total == 0 ){
            return 0d;
        }

        double proportion = ((double) n) / ((double) total);
        return round(proportion * 100,1);
    }

    public static Double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}