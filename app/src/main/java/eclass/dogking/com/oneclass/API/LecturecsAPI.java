package eclass.dogking.com.oneclass.API;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.Course;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecture;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.entiry.Lecturecs;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dog on 2018/2/6 0006.
 */

public interface LecturecsAPI {

    @FormUrlEncoded
    @POST("lecturecs/choselecture")
    Observable<HttpDefault<Lecturecs>> choselecture(
            @Field("name") String name,
            @Field("tel") String tel
    );

    @GET("lecturecs/lecturedatashow")
    Observable<HttpDefault<List<List<LectureShow>>>> getlecturedatashow(
            @Query("tel") String tel
    );

    @GET("lecturecs/lectureshow")
    Observable<HttpDefault<List<LectureShow>>> getlectureshow(
            @Query("tel") String tel
    );

    @GET("lecturecs/getuserchose")
    Observable<HttpDefault<List<Course>>> getuserchose(
            @Query("tel") String tel
    );

    @FormUrlEncoded
    @POST("lecturecs/getlecturecs")
    Observable<HttpDefault<Lecturecs>> getlecturecs(
            @Field("tel") String tel,
            @Field("name") String name

    );

    @FormUrlEncoded
    @POST("lecturecs/unchose")
    Observable<HttpDefault<String>> unchose(
            @Field("tel") String tel,
            @Field("name") String name
    );

}
