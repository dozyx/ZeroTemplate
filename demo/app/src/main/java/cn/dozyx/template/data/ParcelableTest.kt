package cn.dozyx.template.data

import android.content.Intent
import android.os.Bundle
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class ParcelableTest : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logParcelObj()
    }

    private fun logParcelObj() {
        Timber.d(
            "ParcelableTest.onCreate ${
                intent.getParcelableExtra<MyParcelableObject>(
                    EXTRA_PARCEL
                )
            }"
        )
    }

    override fun initActions() {
        addAction("parcel") {
            val obj = MyParcelableObject().apply {
                overlayStatus = 1
                endDuration = 2
                endCircleAngle = 90F
            }
            Timber.d("parcel $obj")
            intent.putExtra(EXTRA_PARCEL, obj)
        }

        addAction("activity") {
            val obj = MyParcelableObject().apply {
                overlayStatus = 1
                endDuration = 2
                endCircleAngle = 90F
            }
            Timber.d("activity $obj")
            startActivity(intent.apply {
                setClass(this@ParcelableTest, ParcelableTest::class.java)
                action = Intent.ACTION_VIEW
                putExtra(EXTRA_PARCEL, obj)
            })
        }
    }

    companion object {
        private const val EXTRA_PARCEL = "extra_parcel"
    }
}