package com.zerofate.template.justfortest;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

/**
 * @author Timon
 * @date 2018/3/22
 */

public class MyViewModel extends ViewModel {
    private MediatorLiveData<String> mediatorLiveData;
    private MutableLiveData<String> source1 = new MutableLiveData<>();
    private MutableLiveData<Integer> source2 = new MutableLiveData<>();

    public MediatorLiveData<String> getMediator(){
        if (mediatorLiveData == null){
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

    public void setInt(int value){
        source2.setValue(value);
    }

    public void setString(String value){
        source1.setValue(value);
    }

}
