package cn.dozyx.template.data;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.jetbrains.annotations.Nullable;

import cn.dozyx.template.base.Action;
import cn.dozyx.template.base.BaseTestActivity;

/**
 * 结论：反序列化出来的对象与序列化的不是同一个
 */
public class ParcelableTest extends BaseTestActivity {
    private User user = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addAction(new Action("传递数据") {
            @Override
            public void run() {
                Intent intent = new Intent(ParcelableTest.this, ParcelableTest.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        addAction(new Action("修改数据") {
            @Override
            public void run() {
                user.age = 18;
            }
        });

        addAction(new Action("读取数据") {
            @Override
            public void run() {
                LogUtils.d("前 user: " + user + " ");
                user = getIntent().getParcelableExtra("user");
                if (user == null){
                    ToastUtils.showShort("请先传递再获取");
                    return;
                }
                LogUtils.d("后 user: " + user + " " + user.age);
            }
        });
    }

    private static class User implements Parcelable {
        int age;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.age);
        }

        public User() {
        }

        protected User(Parcel in) {
            this.age = in.readInt();
        }

        public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
            @Override
            public User createFromParcel(Parcel source) {
                return new User(source);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };
    }
}
