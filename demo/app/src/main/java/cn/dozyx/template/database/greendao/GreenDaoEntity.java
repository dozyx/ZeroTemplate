package cn.dozyx.template.database.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

/**
 * https://greenrobot.org/greendao/documentation/how-to-get-started/
 * Entity 不支持 kotlin 类
 * {@link Entity} 声明一个实体类，可以用来表示一行数据，每一个 field 表示某一列的属性
 * 编译后将自动生成一个 {@link GreenDaoEntityDao} 类，并生成一些模板代码（构造函数、setter、getter 等）
 * {@link Generated} 表示这些代码是由 greendao 生成的
 * 如果不想在这个类中自动生成代码或者在下一次生成过程中修改此类，可以使用 {@link Keep} 注解。不过需要注意，如果不使用自动生成，需要自行编写 greenDao 需要的代码
 *
 * 生成的 Dao 类可以视为一张表。自动生成的表名为 GREEN_DAO_ENTITY
 */
@Entity
@Keep
class GreenDaoEntity {
    /**
     * {@link Id} 主键，如果插入的 entity 没有指定 id，那么数据库会自己做处理。
     */
    @Id
    private Long id;
    private int intValue;
    private String stringValue;
    private boolean boolValue;

    @Generated(hash = 2040594983)
    public GreenDaoEntity(Long id, int intValue, String stringValue, boolean boolValue) {
        this.id = id;
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.boolValue = boolValue;
    }

    @Generated(hash = 1963997359)
    public GreenDaoEntity() {
    }


    public int getIntValue() {
        return this.intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public boolean getBoolValue() {
        return this.boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GreenDaoEntity{" +
                "id=" + id +
                ", intValue=" + intValue +
                ", stringValue='" + stringValue + '\'' +
                ", boolValue=" + boolValue +
                '}';
    }
}
