package jp.techacademy.shino.oomori.mylog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {


    private View.OnClickListener mOnTestClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //コンテキストからインフレータを取得
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //レイアウトXMLからビュー(レイアウト)をインフレート
            final View popupView = inflater.inflate(R.layout.edit_log_view, null);

            // テキスト入力用Viewの作成
            final Spinner spinner1 = (Spinner)popupView.findViewById(R.id.category_spinner);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("入力してください")
                    .setCancelable(true)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // キャンセルボタンをタップした時の処理をここに記述
                        }
                    })
                    .setView(popupView)
                    .show(); //ダイアログ表示
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.input_button).setOnClickListener(mOnTestClickListener);

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

}
