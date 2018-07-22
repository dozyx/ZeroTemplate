package com.zerofate.template.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zerofate.android.data.Shakespeare;

/**
 * Created by Administrator on 2017/10/30.
 */

public class SimpleListFragment extends Fragment {
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, Shakespeare.TITLES));
        return listView;
    }
}
