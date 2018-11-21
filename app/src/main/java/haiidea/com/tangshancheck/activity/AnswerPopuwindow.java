package haiidea.com.tangshancheck.activity;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import haiidea.com.tangshancheck.R;

/**
 * Created by Administrator on 2018/11/20.
 */

public class AnswerPopuwindow extends PopupWindow {
    private Context mContext;

    public AnswerPopuwindow(Context context){
        mContext = context;
        setWidth((int) mContext.getResources().getDimension(R.dimen.dp_150));
        setHeight((int) mContext.getResources().getDimension(R.dimen.dp_150));
        final View contentView = View.inflate(mContext, R.layout.popuwindow_answer, null);
        TextView tv = contentView.findViewById(R.id.next_tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        super.setContentView(contentView);
    }
    private Callback mCallback;
    public void setCallback(Callback callback){
        mCallback = callback;
    }
    public interface Callback{
        void call(String name);
    }
}
