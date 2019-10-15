package cn.dozyx.template.arch

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

/**
 * Create by timon on 2019/10/14
 */
class ViewModelTest : BaseTestActivity() {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modelProvider1 = ViewModelProviders.of(this)
        val viewModel11 = modelProvider1.get(CustomViewModel1::class.java)
        val modelProvider2 = ViewModelProviders.of(this)
        val viewModel21 = modelProvider2.get(CustomViewModel2::class.java)
        val viewModel22 = modelProvider2.get(CustomViewModel2::class.java)

        val viewModel12 = modelProvider1.get(CustomViewModel1::class.java)
        // ViewModelProviders.of() 返回的是新的 ViewModelProvider 实例
        Timber.d("ViewModelTest.onCreate provide1 $modelProvider1 provide2 $modelProvider2")
        // ViewModelProvider.get() 返回的是同一个实例
        Timber.d("ViewModelTest.onCreate model11 $viewModel11 model12 $viewModel12")
        Timber.d("ViewModelTest.onCreate model21 $viewModel21 model22 $viewModel22")
    }
}
class CustomViewModel1 : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        Timber.d("CustomViewModel1.onCleared")
    }
}

class CustomViewModel2 : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        Timber.d("CustomViewModel2.onCleared")
    }
}


