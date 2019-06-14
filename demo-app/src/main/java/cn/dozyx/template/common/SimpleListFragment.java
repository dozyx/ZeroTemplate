package cn.dozyx.template.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.dozyx.constant.Shakespeare;

/**
 * Created by Administrator on 2017/10/30.
 */

public class SimpleListFragment extends Fragment {
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Shakespeare.TITLES));
        return listView;
    }
}
