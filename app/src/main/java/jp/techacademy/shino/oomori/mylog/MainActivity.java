package jp.techacademy.shino.oomori.mylog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    private MLog mLog;
    private Realm mRealm;

    LogGridView customView;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button mDateButton;

    private View.OnClickListener mOnDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            String dateString = mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay);
                            mDateButton.setText(dateString);

                            ChangeLogView();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    };


    private View.OnClickListener mOnLogEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // ログを入力・編集する画面に遷移させる
            // TODO : 位置からログを編集

            Intent intent = new Intent(MainActivity.this, EditLogActivity.class);
            startActivity(intent);
//            //コンテキストからインフレータを取得
//            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            //レイアウトXMLからビュー(レイアウト)をインフレート
//            final View popupView = inflater.inflate(R.layout.edit_log_view, null);
//
//            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//            dialog.setTitle("入力してください")
//                    .setCancelable(true)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    })
//                    .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            // キャンセルボタンをタップした時の処理をここに記述
//                        }
//                    })
//                    .setView(popupView)
//                    .show(); //ダイアログ表示
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.input_button).setOnClickListener(mOnLogEditClickListener);

        mDateButton = (Button)findViewById(R.id.date_button);
        mDateButton.setOnClickListener(mOnDateClickListener);
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String dateString = mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay);
        mDateButton.setText(dateString);

        ChangeLogView();
    }

    private void ChangeLogView() {
        // TODO : 画面リセット
        LogGridView view = new LogGridView(MainActivity.this);
        view.invalidate();

        // Realmの設定
        mRealm = Realm.getDefaultInstance();
        RealmResults<MLog> mLogRealmResults;
        mLogRealmResults = mRealm.where(MLog.class).equalTo("mLogDateStr", mYear + String.format("%02d",(mMonth + 1)) + String.format("%02d", mDay)).findAll();

        for (int i = 0; i < mLogRealmResults.size(); i++)
        {
            final MLog mLog = mLogRealmResults.get(i);
            customView = (LogGridView) findViewById(R.id.log_grid);
            Date start = mLog.getLogStartTime();
            Date end = mLog.getLogEndTime();

            // Realmデータベースから、「全てのデータを取得して新しい日時順に並べた結果」を取得
            RealmResults<Category> categoryRealmResults = mRealm.where(Category.class).equalTo("id",mLog.getCategoryId()).findAll();
            customView.setColorAtPosition(start, end, categoryRealmResults.get(0).getCategoryColor());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                // メニュー１選択時の処理
                Intent intent = new Intent(MainActivity.this, EditCategoryActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
