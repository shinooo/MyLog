package jp.techacademy.shino.oomori.mylog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class EditLogView extends FrameLayout {

    private Context context;
    private int mStartYear, mStartMonth, mStartDay, mStartHour, mStartMinute;
    private int mEndYear, mEndMonth, mEndDay, mEndHour, mEndMinute;
    private Button mDateButton, mStartTimeButton, mEndTimeButton;
    private EditText mDetailEdit;
    private Spinner mCategorySpinner;
    private MLog mLog;
    private Realm mRealm;
    private CategoryAdapter categoryAdapter;
    private int selectedCategoryID;

    private View.OnClickListener mOnDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mStartYear = year;
                            mStartMonth = monthOfYear;
                            mStartDay = dayOfMonth;
                            String dateString = mStartYear + "/" + String.format("%02d",(mStartMonth + 1)) + "/" + String.format("%02d", mStartDay);
                            mDateButton.setText(dateString);
                        }
                    }, mStartYear, mStartMonth, mStartDay);
            datePickerDialog.show();
        }
    };

    private View.OnClickListener mOnStartTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            mStartHour = hourOfDay;
                            mStartMinute = minute;
                            String timeString = String.format("%02d", mStartHour) + ":" + String.format("%02d", mStartMinute);
                            mStartTimeButton.setText(timeString);
                        }
                    }, mStartHour, mStartMinute, false);
            timePickerDialog.show();
        }
    };

    private View.OnClickListener mOnEndTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            mEndHour = hourOfDay;
                            mEndMinute = minute;
                            String timeString = String.format("%02d", mEndHour) + ":" + String.format("%02d", mEndMinute);
                            mEndTimeButton.setText(timeString);
                        }
                    }, mEndHour, mEndMinute, false);
            timePickerDialog.show();
        }
    };

    private View.OnClickListener mOnDoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addTask();
        }
    };

    public EditLogView(Context context) {
        this(context,null);
    }

    public EditLogView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditLogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        // Realmの設定
        mRealm = Realm.getDefaultInstance();

        LayoutInflater.from(context).inflate(R.layout.edit_log_view,this,true);

        // UI部品の設定
        findViewById(R.id.done_button).setOnClickListener(mOnDoneClickListener);
        mDetailEdit = (EditText)findViewById(R.id.detail_edit_text);
        mDateButton = (Button)findViewById(R.id.date_button);

        mDateButton.setOnClickListener(mOnDateClickListener);
        mStartTimeButton = (Button)findViewById(R.id.time_from_button);
        mStartTimeButton.setOnClickListener(mOnStartTimeClickListener);
        mEndTimeButton = (Button)findViewById(R.id.time_to_button);
        mEndTimeButton.setOnClickListener(mOnEndTimeClickListener);

        mCategorySpinner = (Spinner) findViewById(R.id.category_spinner);
        setCategorySpinner();

        if (mLog == null) {
            // 新規作成の場合
            mCategorySpinner.setSelection(mRealm.where(Category.class).equalTo("id", 0).findFirst().getId());

            Calendar calendar = Calendar.getInstance();
            mStartYear = calendar.get(Calendar.YEAR);
            mStartMonth = calendar.get(Calendar.MONTH);
            mStartDay = calendar.get(Calendar.DAY_OF_MONTH);
            mStartHour = calendar.get(Calendar.HOUR_OF_DAY);
            mStartMinute = calendar.get(Calendar.MINUTE);
            mEndHour = calendar.get(Calendar.HOUR_OF_DAY);
            mEndMinute = calendar.get(Calendar.MINUTE);

        } else {
            // 更新の場合
            mDetailEdit.setText(mLog.getmLogDetail());
            // Category名取得
            mCategorySpinner.setSelection(mRealm.where(Category.class).equalTo("id", mLog.getCategoryId()).findFirst().getId());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mLog.getLogStartTime());
            mStartYear = calendar.get(Calendar.YEAR);
            mStartMonth = calendar.get(Calendar.MONTH);
            mStartDay = calendar.get(Calendar.DAY_OF_MONTH);
            mStartHour = calendar.get(Calendar.HOUR_OF_DAY);
            mStartMinute = calendar.get(Calendar.MINUTE);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(mLog.getLogEndTime());
            mEndHour = calendar.get(Calendar.HOUR_OF_DAY);
            mEndMinute = calendar.get(Calendar.MINUTE);
        }
        String dateString = mStartYear + "/" + String.format("%02d",(mStartMonth + 1)) + "/" + String.format("%02d", mStartDay);
        String startTimeString = String.format("%02d", mStartHour) + ":" + String.format("%02d", mStartMinute);
        String endTimeString = String.format("%02d", mEndHour) + ":" + String.format("%02d", mEndMinute);
        mDateButton.setText(dateString);
        mStartTimeButton.setText(startTimeString);
        mEndTimeButton.setText(endTimeString);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void addTask(){
        // タスクの追加・更新
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        if (mLog == null) {
            // 新規作成の場合
            mLog = new MLog();

            RealmResults<MLog> mLogRealmResults = realm.where(MLog.class).findAll();

            int identifier;
            if (mLogRealmResults.max("id") != null) {
                identifier = mLogRealmResults.max("id").intValue() + 1;
            } else {
                identifier = 0;
            }
            mLog.setId(identifier);
        }

        String detail = mDetailEdit.getText().toString();

        mLog.setCategoryId(selectedCategoryID);
        mLog.setmLogDetail(detail);

        GregorianCalendar calendar = new GregorianCalendar(mStartYear,mStartMonth,mStartDay,mStartHour,mStartMinute);
        Date date = calendar.getTime();
        mLog.setmLogDateStr(mStartYear + String.format("%02d",(mStartMonth + 1))  + String.format("%02d", mStartDay));
        mLog.setLogStartTime(date);

        GregorianCalendar calendar2 = new GregorianCalendar(mStartYear,mStartMonth,mStartDay,mEndHour,mEndMinute);
        Date date2 = calendar2.getTime();
        mLog.setLogEndTime(date2);

        // TODO : 時間枠を数字に変える?
        //mLog.setmLogTimeNum(7);

        realm.copyToRealmOrUpdate(mLog);
        realm.commitTransaction();

        realm.close();
    }

    private void setCategorySpinner() {
        RealmResults<Category> categoryRealmResults;
        // Realmデータベースから、「全てのデータを取得して新しい日時順に並べた結果」を取得
        categoryRealmResults = mRealm.where(Category.class).findAllSorted("id", Sort.ASCENDING);
        categoryAdapter = new CategoryAdapter(context);
        categoryAdapter.setCategoryList((ArrayList<Category>) mRealm.copyFromRealm(categoryRealmResults));
        mCategorySpinner.setAdapter(categoryAdapter);

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Category selectedCategory = (Category) categoryAdapter.getItem(position);
                // Here you can do the action you want to...
                selectedCategoryID = selectedCategory.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                selectedCategoryID = 0;
            }
        });


    }

}
