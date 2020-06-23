package cn.dozyx.template.database.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author dozyx
 * @date 2020/6/23
 */
@Entity
public class GreenDaoEntity2 {
    private String str;
    private String str2;

    @Generated(hash = 336081118)
    public GreenDaoEntity2(String str, String str2) {
        this.str = str;
        this.str2 = str2;
    }

    @Generated(hash = 385291970)
    public GreenDaoEntity2() {
    }

    public String getStr() {
        return this.str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getStr2() {
        return this.str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }
}
