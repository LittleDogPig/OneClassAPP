package eclass.dogking.com.oneclass.API;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.Exam;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.PptShow;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dog on 2018/2/25 0025.
 */

public interface PptAPI {

    @FormUrlEncoded
    @POST("ppt/pptshow")
    Observable<HttpDefault<List<PptShow>>> pptshow(
            @Field("tel") String tel
    );

    @FormUrlEncoded
    @POST("ppt/deletePPT")
    Observable<HttpDefault<String>> deletePPT(
            @Field("id") int id
    );

    @POST("ppt/pptlistShow")
    Observable<HttpDefault<List<PptShow>>> pptlistShow();

    @FormUrlEncoded
    @POST("ppt/getPPT")
    Observable<HttpDefault<List<PptShow>>> getPPT(
            @Field("name") String name
    );

}
