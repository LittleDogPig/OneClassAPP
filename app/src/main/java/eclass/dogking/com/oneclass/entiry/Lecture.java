package eclass.dogking.com.oneclass.entiry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dog on 2018/1/30 0030.
 */

public class Lecture implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("tid")
    private int tid;
    @SerializedName("pictureurl")
    private String pictureurl;
    @SerializedName("time")
    private String time;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("detail")
    private String detail;
    @SerializedName("environment")
    private String environment;

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

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public  String getPictureurl(){
        return pictureurl;
    }

    public void setPictureurl(String pictureurl){
        this.pictureurl=pictureurl;
    }

    public  String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
    }

    public  String getType(){
        return type;
    }

    public void setType(String type){
        this.type=type;
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
