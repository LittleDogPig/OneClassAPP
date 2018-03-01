package eclass.dogking.com.oneclass.entiry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dog on 2018/2/10 0010.
 */

public class Exercise implements Serializable {
    @SerializedName("id")
    int id;
    @SerializedName("lid")
    int lid;
    @SerializedName("description")
    String description;
    @SerializedName("name")
    String name;
    @SerializedName("type")
    String type;
    @SerializedName("answer")
    String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getLid() {
        return lid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
