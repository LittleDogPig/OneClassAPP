package eclass.dogking.com.oneclass.API;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Teacher;
import eclass.dogking.com.oneclass.entiry.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
/**
 * Created by dog on 2018/2/4 0004.
 */

public interface TeacherAPI {

    @FormUrlEncoded
    @POST("teacher/findTeacherByName")
    Observable<HttpDefault<Teacher>> findNameByName(
            @Field("name") String name
    );

    @GET("teacher/teacherlist")
    Observable<HttpDefault<List<Teacher>>> teacherlist();

    @FormUrlEncoded
    @POST("teacher/getTeacher")
    Observable<HttpDefault<List<Teacher>>> getTeacher(
            @Field("name") String name
    );

    //删除用户
    @POST("teacher/deleteteacher")
    Observable<HttpDefault<String>> deleteteacher(
            @Query("id") int id
    );

    @FormUrlEncoded
    @POST("teacher/newteacher")
    Observable<HttpDefault<Teacher>> newteacher(
            @Field("name") String name,
            @Field("email") String email,
            @Field("school") String school,
            @Field("sex") String sex,
            @Field("description") String description
    );

    @FormUrlEncoded
    @POST("teacher/udteacher")
    Observable<HttpDefault<Teacher>> udteacher(
            @Field("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("school") String school,
            @Field("sex") String sex,
            @Field("description") String description
    );

    @POST("teacher/findTeacher")
    Observable<HttpDefault<Teacher>> findTeacher(
            @Query("id") int id
    );



}
