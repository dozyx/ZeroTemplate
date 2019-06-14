package com.zerofate.template.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.UriUtils;
import com.zerofate.template.base.BaseShowResultActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Create by timon on 2019/5/9
 **/
public class ResultActivity extends BaseShowResultActivity {
    @NotNull
    @Override
    protected String[] getButtonText() {
        return new String[]{"pick photo", "清理"};
    }

    @Override
    public void onButton1() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1, 1);
    }

    @Override
    public void onButton2() {
        setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("test", 1);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        appendResult(savedInstanceState.getInt("test") + "");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LogUtils.Config config = LogUtils.getConfig();
        config.setStackDeep(20);
        LogUtils.d("stack");
        appendResult(requestCode + " & " + resultCode + " & " + UriUtils.uri2File(data.getData(),
                MediaStore.Images.ImageColumns.DATA).getAbsolutePath());
    }
}
