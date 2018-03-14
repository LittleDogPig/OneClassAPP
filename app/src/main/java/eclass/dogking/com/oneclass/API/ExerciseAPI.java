package eclass.dogking.com.oneclass.API;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.Exercise;
import eclass.dogking.com.oneclass.entiry.ExerciseShow;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecture;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dog on 2018/2/10 0010.
 */

public interface ExerciseAPI {
    @FormUrlEncoded
    @POST("exercise/getexercise")
    Observable<HttpDefault<Exercise>> getexercise(
            @Field("lecturename") String lecturename,
            @Field("name") String name

    );

    @POST("exercise/exerciseshow")
    Observable<HttpDefault<List<ExerciseShow>>> exerciseshow();

    @FormUrlEncoded
    @POST("exercise/getExercise")
    Observable<HttpDefault<List<ExerciseShow>>> getExercise(
            @Field("name") String name

    );

    @FormUrlEncoded
    @POST("exercise/getexson")
    Observable<HttpDefault<List<Exercise>>> getexson(
            @Field("name") String name

    );

    @FormUrlEncoded
    @POST("exercise/deleteExercise")
    Observable<HttpDefault<String>> deleteExercise(
            @Field("id") int id

    );

    @FormUrlEncoded
    @POST("exercise/getoneexercise")
    Observable<HttpDefault<Exercise>> getoneexercise(
            @Field("id") int id
    );

}
