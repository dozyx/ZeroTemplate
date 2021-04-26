package cn.dozyx.template.account

import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.google.android.gms.common.AccountPicker
import timber.log.Timber

class AccountActivity : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("AccountPicker") {
            override fun run() {
                getAccountByAccountPicker()
            }
        })
        addAction(object : Action("AccountManager") {
            override fun run() {
                getAccountByManager()
            }
        })
    }

    /**
     * 需要权限 android.permission.GET_ACCOUNTS
     */
    private fun getAccountByManager() {
        val accountManager = getSystemService(ACCOUNT_SERVICE) as AccountManager
        accountManager.accounts.forEach {
            Timber.d("AccountActivity.getAccountByManager ${it.name} ${it.type}")
        }
    }

    /**
     * 需要引入依赖 com.google.android.gms:play-services-auth
     *
     * https://developers.google.com/android/reference/com/google/android/gms/common/AccountPicker
     */
    private fun getAccountByAccountPicker() {
        val options = AccountPicker.AccountChooserOptions.Builder()
                .setAllowableAccountsTypes(listOf("com.google"))
                .setTitleOverrideText("测试标题")// 小米 11 没发现有什么用，标题没变化
                .setAlwaysShowAccountPicker(true)// doc 解释：即使只有一个 account 也弹出选择器。测试: 为 true 每次都弹出，为 false 只有第一次调用弹窗，后续调用直接回调 onActivityResult，即使重启 app 也一样。
                .build()
        startActivityForResult(AccountPicker.newChooseAccountIntent(options), 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("AccountActivity.onActivityResult requestCode: $requestCode resultCode: $resultCode accountName: ${data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)} accountType: ${data?.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE)}")

    }
}