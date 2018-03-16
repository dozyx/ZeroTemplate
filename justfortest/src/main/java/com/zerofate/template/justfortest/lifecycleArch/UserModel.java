package com.zerofate.template.justfortest.lifecycleArch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * @author Timon
 * @date 2018/3/15
 */

public class UserModel extends ViewModel {
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        return userLiveData;
    }

    public void setName(String name){
        User user = new User();
        user.setName(name);
        userLiveData.setValue(user);
    }
}
