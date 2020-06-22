package cn.dozyx.demo.dagger.test

import cn.dozyx.demo.dagger.DaggerDemoApp
import cn.dozyx.demo.dagger.java.Machine
import javax.inject.Inject

object SingletonTest {
    @set:Inject
    lateinit var machine: Machine

    init {
        DaggerDemoApp.getComponent().inject(this)
    }
}
