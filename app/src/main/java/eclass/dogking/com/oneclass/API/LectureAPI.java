package eclass.dogking.com.oneclass.API;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecture;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dog on 2018/1/30 0030.
 */

public interface LectureAPI {

    @GET("lecture/lecturelist")
    Observable<HttpDefault<List<Lecture>>> getlecturelist();

    @FormUrlEncoded
    @POST("lecture/findlecturebyname")
    Observable<HttpDefault<Lecture>> findlecturebyname(
            @Field("name") String name
    );


    @GET("lecture/lectureshow")
    Observable<HttpDefault<List<LectureShow>>> getlectureshow();

    @FormUrlEncoded
    @POST("lecture/findnamebyname")
    Observable<HttpDefault<String>> findnamenyname(
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("lecture/findlecturelikename")
    Observable<HttpDefault<List<LectureShow>>> findlecturelikename(
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("lecture/getlecturedata")
    Observable<HttpDefault<List<String>>> getlecturedata(
            @Field("name") String name
    );
}
