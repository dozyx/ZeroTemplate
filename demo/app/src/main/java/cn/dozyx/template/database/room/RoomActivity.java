package cn.dozyx.template.database.room;

import android.os.Bundle;

import androidx.room.Room;

import com.blankj.utilcode.util.LogUtils;
import cn.dozyx.core.utli.executor.SingleThreadExecutorKt;
import cn.dozyx.template.base.BaseTestActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.concurrent.ExecutorService;

/**
 * Create by dozyx on 2019/5/17
 **/
public class RoomActivity extends BaseTestActivity {
    private AppDatabase appDatabase;
    private ExecutorService executorService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executorService = SingleThreadExecutorKt.get();
        addButton("创建", () -> appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "database-name").build());

        addButton("添加数据", () -> executorService.execute(() -> {
            if (appDatabase != null) {
                User user = generateFakeUser();
                appDatabase.userDao().insertAll(user);
            }
        }));
    }

    @NotNull
    private User generateFakeUser() {
        User user = new User();
        user.uid = new Random().nextInt(1000);
        user.firstName = "三";
        user.lastName = "张";
        LogUtils.d(user);
        return user;
    }
}
