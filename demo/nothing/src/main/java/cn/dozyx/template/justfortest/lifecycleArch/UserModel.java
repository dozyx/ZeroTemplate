package cn.dozyx.template.justfortest.lifecycleArch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author dozeboy
 * @date 2018/3/15
 */

public class UserModel extends ViewModel {
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        return userLiveData;
    }

    public void setName(String name) {
        User user = new User();
        user.setName(name);
        userLiveData.setValue(user);
    }
}
