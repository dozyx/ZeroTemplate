package com.zerofate.template.activity;

import android.os.Bundle;

import com.zerofate.template.base.BaseShowResultActivity;

public class LifeCycleTest extends BaseShowResultActivity {

    @Override
    protected String[] getButtonText() {
        return new String[0];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendText("onCreate ->");
    }

    @Override
    protected void onStart() {
        super.onStart();
        appendText("onStart ->");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        appendText("onRestart ->");
    }

    @Override
    protected void onResume() {
        super.onResume();
        appendText("onResume ->");
    }

    @Override
    protected void onPause() {
        appendText("onPause ->");
        super.onPause();
    }

    @Override
    protected void onStop() {
        appendText("onStop ->");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        appendText("onDestroy ->");
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        appendText("onRestoreInstanceState -> ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        appendText("onSaveInstanceState ->");
    }
}
