package cn.dozyx.template.arch

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.*
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class LiveDataActivity : BaseTestActivity(), LifecycleObserver {
    override fun initActions() {

    }

    private lateinit var userModel: UserModel

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateEvent() {
        Timber.d("LiveDataActivity.onCreateEvent")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("LiveDataActivity.onCreate")
        Handler().postDelayed({
            lifecycle.addObserver(this)
        }, 2000)
        val observer = Observer<User> {
            appendResult(it.toString())
        }
        userModel = ViewModelProviders.of(this).get(UserModel::class.java)
        addButton("observe", Runnable {
            userModel.userData.observe(this, observer)
        })

        addButton("removeObserver", Runnable {
            userModel.userData.removeObserver(observer)
        })

        addButton("setValue", Runnable {
            userModel.userData.value = User("章安", 11)
        })

        addButton("getValue", Runnable {

        })

        addButton("setValue null", Runnable {
            userModel.userData.value = null
        })

        addButton("single observe", Runnable {
            userModel.singleLiveUserData.observe(this, Observer {
                //                ToastX.showShort(this, "single: $it")
            })
        })
        addButton("single observe2", Runnable {
            userModel.singleLiveUserData.observe(this, Observer {
                appendResult("single2: $it")
            })
        })

        addButton("single setValue", Runnable {
            userModel.singleLiveUserData.value = User("是的", 17)
        })

        val data1 = MutableLiveData<Boolean>()
        val data2 = MutableLiveData<Boolean>()
        val mediator = MediatorLiveData<Int>()
        addButton("MediatorLiveData Add Source", Runnable {
            mediator.addSource(data1) {
                appendResult("data1")
                mediator.value = 1
            }
            mediator.addSource(data2) {
                appendResult("data2")
                mediator.value = 2
            }
        })
        addButton("MediatorLiveData observe", Runnable {
            mediator.observe(this, Observer<Int> {
                appendResult("mediator")
            })
        })
        addButton("MediatorLiveData data1 change", Runnable {
            data1.value = true
        })
        addButton("MediatorLiveData data2 change", Runnable {
            data2.value = true
        })


    }

    class User(val name: String, val age: Int) {
        override fun toString(): String {
            return "User(name='$name', age=$age)"
        }
    }

    class UserModel : ViewModel() {
        var userData = MutableLiveData<User>()
        val singleLiveUserData = SingleLiveEvent<User>()

    }
}
