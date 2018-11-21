package haiidea.com.tangshancheck;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haiidea.com.tangshancheck.activity.SelectActivity;
import haiidea.com.tangshancheck.callback.MyAuthCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.title_tv)
    TextView mTitleTV;
    @Bind(R.id.back_iv)
    ImageView mBackIV;
    @Bind(R.id.key_tv)
    TextView mKeyTipTV;
    @Bind(R.id.username_et)
    EditText mUsernameET;
    @Bind(R.id.pw_et)
    EditText mPWET;
    @Bind(R.id.icon)
    ImageView mIconTV;

    private CancellationSignal mCancellationSignal = new CancellationSignal();
    private MyAuthCallback myAuthCallback;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //识别成功
                case Constant.MSG_AUTH_SUCCESS:
                    if (!"1".equals(AppConfig.getAppConfig().get("is_bind_key"))) {
                        AppConfig.getAppConfig().set("is_bind_key", "1");
                        mKeyTipTV.setText("绑定成功");
                        toNextActivity();
                    } else {
                        loginTask();
                    }
                    break;
                //识别失败
                case Constant.MSG_AUTH_FAILED:
                    break;
                //识别错误
                case Constant.MSG_AUTH_ERROR:
                    break;
                //帮助
                case Constant.MSG_AUTH_HELP:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBackIV.setVisibility(View.GONE);
        mTitleTV.setText("欢迎进入测评系统");
        String username = AppConfig.getAppConfig().get("username");
        if (!TextUtils.isEmpty(username)){
            mUsernameET.setText(username);
            mUsernameET.setSelection(username.length());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if ("1".equals(AppConfig.getAppConfig().get("is_bind_key"))) {// 是否已经录入指纹
            initKey();
        }
    }

    private void initKey(){
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (mCancellationSignal.isCanceled()) {
                    mCancellationSignal = new CancellationSignal();
                }
                startListening(mCancellationSignal);
            } else {
                startListening(null);
            }
        }catch (Exception e){

        }
    }
    private void startListening(final CancellationSignal cancellationSignal){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, 1);
            return;
        }

        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(this);
        if (fingerprintManager != null) {
            if (!fingerprintManager.isHardwareDetected()) {
                //是否支持指纹识别
                mKeyTipTV.setText("该设备不支持指纹登录");
                toNextActivity();
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                //是否已注册指纹
                mKeyTipTV.setText("请到设置页面录入指纹");
            } else {
                mIconTV.setVisibility(View.VISIBLE);
                if (!"1".equals(AppConfig.getAppConfig().get("is_bind_key"))) {
                    mKeyTipTV.setText("请按压指纹绑定账号");
                }else{
                    mKeyTipTV.setText("请按压指纹登录");
                }
                try {
                    //这里去新建一个结果的回调，里面回调显示指纹验证的信息
                    myAuthCallback = new MyAuthCallback(handler);
                    fingerprintManager.authenticate(null, 0, cancellationSignal, myAuthCallback, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"已授权",Toast.LENGTH_SHORT).show();
            initKey();
        }else{
            Toast.makeText(this,"拒绝授权",Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.login_tv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv:
                loginTask();
                // TODO: 2018/11/11 首次登录成功后调用,后续更改
                break;
        }
    }
    private void loginTask(){
        boolean isFirst = false;
        //登录任务
        //登录成功后调用下面代码
        //首次登录需要修改密码 由后台判断
        if (isFirst){
            modifyPW();
        } else {
            loginSuccess();
        }
    }
    private void loginSuccess(){

        if ("1".equals(AppConfig.getAppConfig().get("is_bind_key"))) {
            toNextActivity();
        }else{
            bindKey();
        }
    }
    private void modifyPW(){
        //修改密码成功后
        loginSuccess();
    }
    private void toNextActivity(){
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
        }
        startActivity(new Intent(MainActivity.this,SelectActivity.class));
        // TODO: 2018/11/12          finish(); 是否能返回登录页面，待定
    }
    private void bindKey(){
        String pw = mPWET.getText().toString().trim();
        String username = mUsernameET.getText().toString().trim();
        AppConfig.getAppConfig().set("username",username);
        AppConfig.getAppConfig().set("pw",pw);
        Toast.makeText(this,"开始登录",Toast.LENGTH_SHORT).show();
        mKeyTipTV.setText("请绑定指纹");
        initKey();
    }

}
