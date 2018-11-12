package haiidea.com.tangshancheck;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2018/11/9.
 */

public class TangShanApplication extends Application {
    private static TangShanApplication instance;

    public static Application getApplication(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        if (shouldInit()) {
            instance = this;
        }
    }
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
