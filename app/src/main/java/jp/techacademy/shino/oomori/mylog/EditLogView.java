package jp.techacademy.shino.oomori.mylog;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * TODO: document your custom view class.
 */
public class EditLogView extends FrameLayout {


    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button mDateButton, mTimeButton;
    private EditText mTitleEdit, mContentEdit;
    private Spinner mCategorySpinner;
    private MLog mLog;
    private Realm realm;
    private CategoryAdapter categoryAdapter;
    private int selectedCategoryID;

    public EditLogView(Context context) {
        super(context);
    }

    public EditLogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditLogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.edit_log_view,this,true);

        // UI部品の設定
        mTitleEdit = (EditText)findViewById(R.id.detail_edit_text);
        mCategorySpinner = (Spinner)findViewById(R.id.category_spinner);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void addTask(){
        // タスクの追加・更新。アラームの設定
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        if (mLog == null) {
            // 新規作成の場合
            mLog = new MLog();

            RealmResults<MLog> mlogRealmResults = realm.where(MLog.class).findAll();

            int identifier;
            if (mlogRealmResults.max("id") != null) {
                identifier = mlogRealmResults.max("id").intValue() + 1;
            } else {
                identifier = 0;
            }
            mLog.setId(identifier);
        }

        String title = mTitleEdit.getText().toString();
        String content = mContentEdit.getText().toString();

        mLog.setCategoryId(selectedCategoryID);
        mLog.setmLogDetail(title);
        GregorianCalendar calendar = new GregorianCalendar(mYear,mMonth,mDay,mHour,mMinute);
        Date date = calendar.getTime();
        mLog.setmLogDateStr(date.toString()); // TODO : YYYYMMDD

        // TODO : 時間枠を数字に変える
        //mLog.setmLogTimeNum(7);
        //mLog.setLogStartTime();

        realm.copyToRealmOrUpdate(mLog);
        realm.commitTransaction();

        realm.close();
    }
}
