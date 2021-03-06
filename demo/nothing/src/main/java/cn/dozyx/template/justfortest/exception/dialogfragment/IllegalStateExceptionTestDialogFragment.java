package cn.dozyx.template.justfortest.exception.dialogfragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author dozeboy
 * @date 2018/3/12
 */

public class IllegalStateExceptionTestDialogFragment extends DialogFragment {
    public static IllegalStateExceptionTestDialogFragment newInstance() {
        Bundle args = new Bundle();

        IllegalStateExceptionTestDialogFragment fragment =
                new IllegalStateExceptionTestDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("测试");
        return textView;
    }

}
