package haiidea.com.tangshancheck.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haiidea.com.tangshancheck.R;

/**
 * Created by Administrator on 2018/11/11.
 */

public class PersonActivity extends Activity {
    @Bind(R.id.title_tv)
    TextView mTitleTV;
    @Bind(R.id.back_iv)
    ImageView mBackIV;
    @Bind(R.id.start_tv)
    TextView mStartTV;
    @Bind(R.id.person_answer_tv)
    TextView mSelectPersonTV;
//    @Bind(R.id.person_answer_tv)
//    TextView
    private SeleftPopuwindow mPopuwindow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        mTitleTV.setText("选择被测评人员");
        mStartTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonActivity.this, QuestionActivity.class);
                intent.putExtra("name",mSelectPersonTV.getText().toString());
                startActivity(intent);
            }
        });
        ArrayList<String> list = new ArrayList<>();
        list.add("汪涵");
        list.add("李大成");
        list.add("郭子仪");
        mPopuwindow = new SeleftPopuwindow(this,list);
        mPopuwindow.setCallback(new SeleftPopuwindow.Callback() {
            @Override
            public void call(String name) {
                mSelectPersonTV.setText(name);
            }
        });

    }
    @OnClick({R.id.back_iv,R.id.person_answer_tv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.person_answer_tv:
                mPopuwindow.showAsDropDown(mSelectPersonTV);
                break;
        }
    }
    private void showPopuwindow(){

    }
}
