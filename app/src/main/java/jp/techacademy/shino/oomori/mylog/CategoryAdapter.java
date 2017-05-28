package jp.techacademy.shino.oomori.mylog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shino on 2017/05/27.
 */

public class CategoryAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Category> mCategoryArrayList;

    public CategoryAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = context;
    }

    public void setCategoryList(ArrayList<Category> categoryArrayList) {
        mCategoryArrayList = categoryArrayList;
    }

    @Override
    public int getCount() {
        return mCategoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCategoryArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1回もタップしてない状態のスピナーの描画
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        TextView textView1 = (TextView) convertView.findViewById(android.R.id.text1);

        textView1.setText(mCategoryArrayList.get(position).getCategoryName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // 展開した時のスピナーの状態の描画。選ばれている行を色つけたりとかできる
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        TextView textView1 = (TextView) convertView.findViewById(android.R.id.text1);

        textView1.setText(mCategoryArrayList.get(position).getCategoryName());

        return convertView;
    }

}
