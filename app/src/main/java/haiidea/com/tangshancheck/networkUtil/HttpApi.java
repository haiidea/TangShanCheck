package haiidea.com.tangshancheck.networkUtil;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/11/9.
 */

public interface HttpApi {
    @POST("Thirdpartylogin/loginty")
    Observable<ResponseBody> login(@QueryMap Map<String, String> map) ;
}
