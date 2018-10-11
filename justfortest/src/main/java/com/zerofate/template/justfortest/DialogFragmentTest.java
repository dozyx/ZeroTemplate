package com.zerofate.template.justfortest;

import android.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Timon
 * @date 2017/12/11
 */

public class DialogFragmentTest extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_fragment_test,container,false);
        return root;
    }
}
