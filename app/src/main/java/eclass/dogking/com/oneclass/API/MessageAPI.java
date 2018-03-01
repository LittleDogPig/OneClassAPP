package eclass.dogking.com.oneclass.API;

import java.util.List;

import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.entiry.MessageShow;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by dog on 2018/2/21 0021.
 */

public interface MessageAPI {

    @GET("message/messageshow")
    Observable<HttpDefault<List<MessageShow>>> getmessageshow();

    @FormUrlEncoded
    @POST("message/save")
    Observable<HttpDefault<List<MessageShow>>> save(
            @Field("tel") String tel,
            @Field("description") String description
    );

}
