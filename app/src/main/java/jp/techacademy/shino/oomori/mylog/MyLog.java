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
                        Category category0 = realm.createObject(Category.class, 0);
                        category0.setCategoryName("設定なし");
                        category0.setCategoryColor((Color.WHITE));

                        Category category1 = realm.createObject(Category.class, 1);
                        category1.setCategoryName("睡眠");
                        category1.setCategoryColor((Color.BLUE));

                        Category category2 = realm.createObject(Category.class, 2);
                        category2.setCategoryName("食事");
                        category2.setCategoryColor((Color.LTGRAY));

                        Category category3 = realm.createObject(Category.class, 3);
                        category3.setCategoryName("仕事");
                        category3.setCategoryColor((Color.CYAN));

                        Category category4 = realm.createObject(Category.class, 4);
                        category4.setCategoryName("遊び");
                        category4.setCategoryColor((Color.YELLOW));

//                        MLog mlog = realm.createObject(MLog.class);
//                        mlog.setId(0);
//                        mlog.setmLogDateStr("20170604");
//                        mlog.setmLogTimeNum(0);
//                        mlog.setCategoryId(1);
//                        mlog.setmLogDetail("zzz...");
//                        mlog.setLogStartTime(Date.valueOf("2017/06/04 01:00:00"));
                    }
                })
                // アプリのアンインストールの手間が省けるけど、公開するときにそのままだと危険！！！
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
