package jp.techacademy.shino.oomori.mylog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Shino on 2017/05/27.
 */

public class MLogAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private ArrayList<MLog> mLogArrayList;
    private Realm realm;
    RealmResults<Category> categoryRealmResults;

    public MLogAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        realm = Realm.getDefaultInstance();
        realm.close();
        categoryRealmResults = realm.where(Category.class).findAllSorted("id");
    }

    public void setTaskList(ArrayList<MLog> logArrayList) {
        mLogArrayList = logArrayList;
    }

    // アイテム（データ）の数を返す
    @Override
    public int getCount() {
        return mLogArrayList.size();
    }

    // アイテム（データ）を返す
    @Override
    public Object getItem(int position) {
        return mLogArrayList.get(position);
    }

    // アイテム（データ）のIDを返す ⇒TODO : なくてもよい？
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Viewを返す
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_2, null);
        }

        TextView textView1 = (TextView) convertView.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) convertView.findViewById(android.R.id.text2);

        return convertView;
    }

}
