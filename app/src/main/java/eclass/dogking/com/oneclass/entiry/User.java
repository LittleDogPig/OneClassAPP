package eclass.dogking.com.oneclass.entiry;

/**
 * Created by dog on 2018/1/17 0017.
 */import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("tel")
    private String tel;
    @SerializedName("headimg")
    private String headimg;
    @SerializedName("create_time")
    private String create_time;
    @SerializedName("updaie_time")
    private String update_time;
    @SerializedName("sex")
    private String sex;
    @SerializedName("description")
    private String description;

    public int getId() {
        return id;
    }
    public void setId(int Id) {
        this.id = Id;
    }

    public String getPassword() {return password;}
    public  void setPassword(String Password) {
        this.password = Password;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String Tel) {
        this.tel = Tel;
    }

    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }

    public String getHeadimg() {
        return headimg;
    }
    public void setHeadimg(String Headimg) {
        this.headimg = Headimg;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
