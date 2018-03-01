package eclass.dogking.com.oneclass.API;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.User;
import eclass.dogking.com.oneclass.entiry.UserExercise;
import eclass.dogking.com.oneclass.entiry.UserExerciseShow;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dog on 2018/2/10 0010.
 */

public interface UserExerciseAPI {

    @FormUrlEncoded
    @POST("userexercise/getrecordshow")
    Observable<HttpDefault<List<UserExerciseShow>>> getrecordshow(
            @Field("tel") String tel,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("userexercise/answer")
    Observable<HttpDefault<UserExercise>> answer(
            @Field("tel") String tel,
            @Field("name") String name,
            @Field("lecturename") String lecturename,
            @Field("answer") String answer
    );

    @FormUrlEncoded
    @POST("userexercise/getuserrecord")
    Observable<HttpDefault<UserExercise>> getrecord(
            @Field("tel") String tel,
            @Field("name") String name,
            @Field("lecturename") String lecturename
    );

}
