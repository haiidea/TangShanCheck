package haiidea.com.tangshancheck.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haiidea.com.tangshancheck.R;

/**
 * Created by Administrator on 2018/11/11.
 */

public class SelectActivity extends Activity {
    @Bind(R.id.title_tv)
    TextView mTitleTV;
    @Bind(R.id.back_iv)
    ImageView mBackIV;
    @Bind(R.id.start_tv)
    TextView mStartTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
        mTitleTV.setText("请输入方案类别编号");
        mStartTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, PersonActivity.class);
                startActivity(intent);
            }
        });

    }
    private void getPersonListData(){
//        HttpUtil.getIntance().
    }
    @OnClick({R.id.back_iv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }

}
