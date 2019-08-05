package cn.dozyx.template.justfortest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * @author dozeboy
 * @date 2017/12/11
 */

public class DialogFragmentTest extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_fragment_test, container, false);
        return root;
    }
}
