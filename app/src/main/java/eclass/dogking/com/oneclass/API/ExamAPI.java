package eclass.dogking.com.oneclass.API;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.Exam;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecturecs;
import eclass.dogking.com.oneclass.entiry.Teacher;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dog on 2018/2/24 0024.
 */

public interface ExamAPI {

    @FormUrlEncoded
    @POST("exam/findexam")
    Observable<HttpDefault<Exam>> findExam(
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("exam/answer")
    Observable<HttpDefault<Lecturecs>> answer(
            @Field("tel") String tel,
            @Field("name") String name,
            @Field("answer") String answer
    );

    @POST("exam/list")
    Observable<HttpDefault<List<Exam>>> list();

    @POST("exam/deleteExam")
    Observable<HttpDefault<String>> deleteExam(
            @Query("id" ) int id
    );

    @FormUrlEncoded
    @POST("exam/getExam")
    Observable<HttpDefault<List<Exam>>> getExam(
            @Field("name") String name
    );

}
