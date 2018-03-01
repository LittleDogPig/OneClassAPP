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

}
