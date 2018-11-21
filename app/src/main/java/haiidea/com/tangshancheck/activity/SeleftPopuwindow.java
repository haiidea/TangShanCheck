package haiidea.com.tangshancheck.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import haiidea.com.tangshancheck.R;

/**
 * Created by Administrator on 2018/11/20.
 */

public class SeleftPopuwindow   extends PopupWindow {
    private Context mContext;

    public SeleftPopuwindow(Context context, final ArrayList<String> list){
        mContext = context;
        setWidth((int) mContext.getResources().getDimension(R.dimen.dp_150));
        setHeight((int) mContext.getResources().getDimension(R.dimen.dp_30)*list.size());
        final View contentView = View.inflate(mContext, R.layout.popuwindow, null);
        ListView listView = contentView.findViewById(R.id.listview);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public String getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView = View.inflate(mContext, R.layout.item, null);
                }
                TextView tv = convertView.findViewById(R.id.name_tv);
                tv.setText(list.get(position));
                return convertView;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.call(list.get(position));
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
