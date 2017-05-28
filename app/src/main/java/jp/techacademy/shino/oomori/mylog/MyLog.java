package jp.techacademy.shino.oomori.mylog;

import android.app.Application;
import android.graphics.Color;

import java.sql.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Shino on 2017/05/27.
 */

public class MyLog extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Category category = realm.createObject(Category.class, 0);
                        category.setCategoryName("設定なし");
                        category.setCategoryColor((Color.WHITE));

                        MLog mlog = realm.createObject(MLog.class);
                        mlog.setmLogDateStr("20170527");
                        mlog.setmLogTimeNum(0);
                        mlog.setCategoryId(0);
                        mlog.setmLogDetail("");
                        mlog.setLogStartTime(Date.valueOf("2017/05/27 00:00:00"));
                    }
                })
                // アプリのアンインストールの手間が省けるけど、公開するときにそのままだと危険！！！
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
