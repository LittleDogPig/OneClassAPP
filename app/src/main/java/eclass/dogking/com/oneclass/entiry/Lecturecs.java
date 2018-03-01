package eclass.dogking.com.oneclass.entiry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dog on 2018/2/6 0006.
 */

public class Lecturecs implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("lid")
    private int lid;
    @SerializedName("sid")
    private int sid;
    @SerializedName("time")
    private String time;
    @SerializedName("exercise")
    private String exercise;
    @SerializedName("exam")
    private String exam;
    @SerializedName("score")
    private int score;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getExam() {
        return exam;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
