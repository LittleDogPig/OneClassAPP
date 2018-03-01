package eclass.dogking.com.oneclass.entiry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dog on 2018/2/4 0004.
 */


public class Teacher implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("sex")
    private String sex;
    @SerializedName("email")
    private String email;
    @SerializedName("school")
    private String school;
    @SerializedName("pictureurl")
    private String pictureurl;
    @SerializedName("description")
    private String description;
    @SerializedName("detail")
    private String detail;

    public Teacher(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public  String getPictureurl(){
        return pictureurl;
    }

    public void setPictureurl(String pictureurl){
        this.pictureurl=pictureurl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public  String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public  String getDetail(){
        return detail;
    }

    public void setDetail(String detail){
        this.detail=detail;
    }



}
