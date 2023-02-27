package cn.dozyx.template

import android.content.Context
import androidx.work.*
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import io.reactivex.Single
import timber.log.Timber
import java.util.concurrent.TimeUnit

class WorkManagerTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("one time") {
            override fun run() {
                testOneTime()
            }
        })

        addAction(object : Action("cancel") {
            override fun run() {
                WorkManager.getInstance(this@WorkManagerTest).cancelAllWorkByTag(WORKER_TAG)
            }
        })
    }

    private fun testOneTime() {
//        val constraints = Constraints.Builder().

//        val request = OneTimeWorkRequestBuilder<SuccessWork>().build()
        val requestBuilder = OneTimeWorkRequestBuilder<RetryWork>()
        requestBuilder.setBackoffCriteria(
            BackoffPolicy.LINEAR,
            OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.MILLISECONDS
        )// 重试执行的是新的 worker 对象
        requestBuilder.addTag(WORKER_TAG)
        WorkManager.getInstance(this).enqueue(requestBuilder.build())
    }

    class SuccessWork(appContext: Context, workerParams: WorkerParameters) :
        RxWorker(appContext, workerParams) {
        override fun createWork(): Single<Result> {
            Timber.d("SuccessWork.createWork: $this")
            return Single.just(Result.success()).delay(3, TimeUnit.SECONDS)
        }
    }

    class RetryWork(appContext: Context, private val workerParams: WorkerParameters) :
        RxWorker(appContext, workerParams) {
        override fun createWork(): Single<Result> {
            Timber.d("RetryWork.createWork: $this ${workerParams.runAttemptCount}")
            return Single.just(Result.retry()).delay(3, TimeUnit.SECONDS)
        }
    }

    companion object {
        const val WORKER_TAG = "my_worker"
    }
}