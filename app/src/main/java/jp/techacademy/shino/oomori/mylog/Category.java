package jp.techacademy.shino.oomori.mylog;

import android.graphics.Color;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Shino on 2017/05/27.
 */

public class Category extends RealmObject implements Serializable {
    // Category Name
    private String strCategoryName;
    // Color
    private int categoryColor;

    // ID -primary key
    @PrimaryKey
    private int id;

    public String getCategoryName() {
        return strCategoryName;
    }

    public void setCategoryName(String strCategoryName) {
        this.strCategoryName = strCategoryName;
    }

    public int getCategoryColor(){ return  categoryColor;}

    public void setCategoryColor(int categoryColor){
        this.categoryColor = categoryColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
