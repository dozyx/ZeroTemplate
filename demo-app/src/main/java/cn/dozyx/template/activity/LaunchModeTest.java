package cn.dozyx.template.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.dozyx.template.base.BaseShowResultActivity;

/**
 * 通过 adb shell dumpsys activity activities 命令查看 Activity 栈信息
 */
public class LaunchModeTest extends BaseShowResultActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setText(this.getClass().getSimpleName());
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    }

    @Override
    protected String[] getButtonText() {
        return new String[]{
                "standard", "singleTop", "singleTask", "singleInstance", "singleTask with taskAffinity"
        };
    }

    @Override
    public void onButton1() {
        callActivity(StandardLaunchModeTest.class);
    }

    @Override
    public void onButton2() {
        callActivity(SingleTopLaunchModeTest.class);
    }

    @Override
    public void onButton3() {
        callActivity(SingleTaskLaunchModeTest.class);
    }

    @Override
    public void onButton4() {
        callActivity(SingleInstanceLaunchModeTest.class);
    }

    @Override
    public void onButton5() {
        Intent intent = new Intent(this, StandardLaunchModeTest.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    private void callActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    public static final class StandardLaunchModeTest extends LaunchModeTest {

    }

    public static final class SingleTopLaunchModeTest extends LaunchModeTest {

    }

    public static final class SingleTaskLaunchModeTest extends LaunchModeTest {

    }

    public static final class SingleInstanceLaunchModeTest extends LaunchModeTest {

    }

    public static final class SingleTaskLaunchModeTestWithTaskAffinity extends LaunchModeTest {

    }


}
