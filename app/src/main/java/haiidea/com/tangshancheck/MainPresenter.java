package haiidea.com.tangshancheck;

import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import haiidea.com.tangshancheck.networkUtil.HttpUtil;
import haiidea.com.tangshancheck.networkUtil.OnSuccessAndFaultListener;
import haiidea.com.tangshancheck.networkUtil.OnSuccessAndFaultSub;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/11/9.
 */

public class MainPresenter {
    private Context mContext;
    public MainPresenter(Context context){
        mContext = context;
    }
    public void login(String username,String pw){
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("pw",pw);
        Observable<ResponseBody> observable =  HttpUtil.getIntance().getApi().login(map);
        HttpUtil.getIntance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(mContext,"请求成功："+result,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(mContext,"请求失败："+errorMsg,Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
