package eclass.dogking.com.oneclass.entiry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dog on 2018/2/24 0024.
 */
public class Exam implements Serializable {
    @SerializedName("id")
    int id;
    @SerializedName("lid")
    int lid;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("answer")
    String answer;
    @SerializedName("url")
    String url;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
