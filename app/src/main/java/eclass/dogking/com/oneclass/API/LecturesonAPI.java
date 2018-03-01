package eclass.dogking.com.oneclass.API;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecturecs;
import eclass.dogking.com.oneclass.entiry.Lectureson;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dog on 2018/2/15 0015.
 */

public interface LecturesonAPI {


    @FormUrlEncoded
    @POST("lectureson/getlectureson")
    Observable<HttpDefault<List<Lectureson>>> getlectureson(
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("lectureson/getoneson")
    Observable<HttpDefault<Lectureson>> getoneson(
            @Field("id") int id
    );


}
