package eclass.dogking.com.oneclass.API;

import eclass.dogking.com.oneclass.entiry.Exercise;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecture;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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



}
