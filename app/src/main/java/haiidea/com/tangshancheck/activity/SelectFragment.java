package haiidea.com.tangshancheck.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haiidea.com.tangshancheck.R;

/**
 * Created by Administrator on 2018/11/11.
 */

public class SelectFragment extends Fragment {
    @Bind(R.id.tiller)
    TextView mTitlerTV;
    @Bind(R.id.one_rb)
    RadioButton oneRB;
    @Bind(R.id.two_rb)
    RadioButton twoRB;
    @Bind(R.id.three_rb)
    RadioButton threeRB;
    @Bind(R.id.four_rb)
    RadioButton fourRB;

    private Activity mActivity;
    private Bundle mParamBundle;

    public static SelectFragment newInstance(Bundle bundle) {
        SelectFragment fragment = new SelectFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater Inflater = inflater.cloneInContext(mActivity);
        View view = Inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, view);
        String type = getArguments().getString("type", "");
        mParamBundle = getArguments().getBundle("param");
        mParamBundle.putString(type,type);
        String title="";
        oneRB.setText("优");
        twoRB.setText("良");
        twoRB.setChecked(true);
        threeRB.setText("中");
        fourRB.setText("差");
        switch (type){
            case "1":
                title= "政治意识";
                oneRB.setText("优");
                break;
            case "2":
                title= "大局意识";
                break;
            case "3":
                title= "思想品质";
                break;
            case "4":
                title= "道德作风";
                break;
            case "5":
                title= "廉政";
                break;
        }
        mTitlerTV.setText(title);
        return view;
    }
    @OnClick({R.id.tiller})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tiller:
                Log.e("",mParamBundle.toString());
                break;
        }
    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}
