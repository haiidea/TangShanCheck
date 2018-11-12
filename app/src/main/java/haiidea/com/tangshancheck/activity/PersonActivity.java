package haiidea.com.tangshancheck.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import haiidea.com.tangshancheck.R;

/**
 * Created by Administrator on 2018/11/11.
 */

public class PersonActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
    }
}
