package jp.techacademy.shino.oomori.mylog;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Shino on 2017/05/27.
 */

public class MLog extends RealmObject implements Serializable {

    private String mLogDateStr;
    private int mLogTimeNum;
    private int categoryId;
    private String mLogDetail;
    private Date logStartTime;


    // id をプライマリーキーとして設定
    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmLogDateStr() {
        return mLogDateStr;
    }

    public void setmLogDateStr(String mLogDateStr) {
        this.mLogDateStr = mLogDateStr;
    }

    public int getmLogTimeNum() {
        return mLogTimeNum;
    }

    public void setmLogTimeNum(int mLogTimeNum) {
        this.mLogTimeNum = mLogTimeNum;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getmLogDetail() {
        return mLogDetail;
    }

    public void setmLogDetail(String mLogDetail) {
        this.mLogDetail = mLogDetail;
    }

    public Date getLogStartTime() {
        return logStartTime;
    }

    public void setLogStartTime(Date logStartTime) {
        this.logStartTime = logStartTime;
    }

}
