package eclass.dogking.com.oneclass.Database;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.User;
import io.realm.Realm;
import io.realm.RealmResults;



public class UserService {
    private Realm realm;

    public  UserService(Realm realm){
        this.realm=realm;
    }
/*
    public boolean Canlogin(String account,String password){
        RealmResults<User> userList = realm.where(User.class)
                .equalTo("Account", account)
                .equalTo("Password", password)
                .findAll();

        if (userList.size() == 0){//size为0则表示没有数据,没有此账号
            return false;
        }
        else return true;
    }

    public boolean CanSign(String account){
        RealmResults<User> userList = realm.where(User.class)
                .equalTo("Account", account)
                .findAll();

        if (userList.size() == 0){//size为0则表示没有数据，可以注册
            return true;
        }
        else return false;


    }*/

}
