package eclass.dogking.com.oneclass.entiry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dog on 2018/2/10 0010.
 */

public class UserExercise implements Serializable {
    @SerializedName("id")
    int id;
    @SerializedName("sid")
    int sid;
    @SerializedName("eid")
    int eid;
    @SerializedName("score")
    String score;
    @SerializedName("finish")
    String finish;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getSid() {
        return sid;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }
}

