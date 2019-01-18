package com.zerofate.template.justfortest;

import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

/**
 * @author dozeboy
 * @date 2018/3/22
 */

public class MyViewModel extends ViewModel {
    private MediatorLiveData<String> mediatorLiveData;
    private MutableLiveData<String> source1 = new MutableLiveData<>();
    private MutableLiveData<Integer> source2 = new MutableLiveData<>();

    public MediatorLiveData<String> getMediator() {
        if (mediatorLiveData == null) {
            mediatorLiveData = new MediatorLiveData<>();
            mediatorLiveData.addSource(source1, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    mediatorLiveData.setValue("这是字符串：" + s);
                }
            });
            mediatorLiveData.addSource(source2, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {
                    mediatorLiveData.setValue("这是数字：" + integer);
                }
            });
        }
        return mediatorLiveData;
    }

    public void setInt(int value) {
        source2.setValue(value);
    }

    public void setString(String value) {
        source1.setValue(value);
    }

}
