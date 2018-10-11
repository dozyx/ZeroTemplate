package com.zerofate.template.fragment;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerofate.template.R;
import com.zerofate.template.base.IBaseView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentLifeCycleFragment extends Fragment {

    private IBaseView baseView;

    public FragmentLifeCycleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        baseView.appendResult("Fragment: onCreateView() -> ");
        return inflater.inflate(R.layout.fragment_life_cycle, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseView = (IBaseView) context;
        baseView.appendResult("Fragment: onAttach() -> ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseView.appendResult("Fragment: onCreate() -> ");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseView.appendResult("Fragment: onViewCreated() -> ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        baseView.appendResult("Fragment: onActivityCreated() -> ");
    }

    @Override
    public void onStart() {
        super.onStart();
        baseView.appendResult("Fragment: onStart() -> ");
    }

    @Override
    public void onResume() {
        super.onResume();
        baseView.appendResult("Fragment: onResume() -> ");
    }

    @Override
    public void onPause() {
        super.onPause();
        baseView.appendResult("Fragment: onPause() -> ");
    }

    @Override
    public void onStop() {
        super.onStop();
        baseView.appendResult("Fragment: onStop() -> ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        baseView.appendResult("Fragment: onDestroyView() -> ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        baseView.appendResult("Fragment: onDestroy() -> ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseView.appendResult("Fragment: onDetach() -> ");
    }
}
