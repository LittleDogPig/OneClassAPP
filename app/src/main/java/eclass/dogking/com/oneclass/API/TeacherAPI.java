package eclass.dogking.com.oneclass.API;

import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Teacher;
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


}
