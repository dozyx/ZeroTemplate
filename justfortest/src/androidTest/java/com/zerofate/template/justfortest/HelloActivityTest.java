package com.zerofate.template.justfortest;

import androidx.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase;
import android.test.ActivityInstrumentationTestCase2;

/**
 * @author Timon
 * @date 2018/4/18
 */
public class HelloActivityTest extends ActivityTestRule<HelloActivity> {


    public HelloActivityTest(
            Class<HelloActivity> activityClass) {
        super(activityClass);
    }
}