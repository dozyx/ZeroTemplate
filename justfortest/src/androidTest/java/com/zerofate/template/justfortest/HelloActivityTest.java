package com.zerofate.template.justfortest;

import static org.junit.Assert.*;

import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

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