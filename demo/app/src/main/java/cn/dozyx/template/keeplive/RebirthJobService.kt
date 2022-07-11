package cn.dozyx.template.keeplive

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.N)
class RebirthJobService : JobService() {
    private var thread: Thread? = null
    override fun onStartJob(params: JobParameters): Boolean {
        keppProceessAlive(params, MILLIS_LOCK_DURATION)
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        val thread = thread
        if (thread != null) {
            thread.interrupt()
            this.thread = null
        }
        return true
    }

    private fun keppProceessAlive(params: JobParameters, duration: Int) {
        thread = Thread(Runnable {
            val id = params.jobId
            Log.d(
                TAG,
                "keppProceessAlive() jobId = [$id], duration = [$duration]"
            )
            try {
                Thread.sleep(duration.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
                return@Runnable
            } finally {
                jobFinished(params, true)
            }
        })
        thread!!.start()
    }

    companion object {
        private const val TAG = "RebirthJobService"

        // TODO: 2018/4/21 unify job id
        private const val JOB_ID_OBSERVER = 100
        private const val MILLIS_CONTENT_MAX_DELAY = 10
        private const val MILLIS_CONTENT_UPDATE_DELAY = 5 * 60 * 1000
        private const val MILLIS_OVERRIDE_DEADLINE = 60 * 60 * 1000
        private const val MILLIS_BACKOFF_CRITERIA = 5 * 60 * 1000
        private const val MILLIS_MINIMUM_LATENCY = 30 * 1000
        private const val MILLIS_LOCK_DURATION = 30 * 1000
        private const val TRIGGER_CONTENT_URL = "content://downloads/my_downloads"
        fun init(context: Context) {
            val jobInfo = buildContentUriTriggerJob(
                context.applicationContext, Uri.parse(
                    TRIGGER_CONTENT_URL
                )
            )
            val jobScheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
            Log.d(TAG, "onAppCreate()")
        }

        private fun buildContentUriTriggerJob(context: Context, uri: Uri): JobInfo {
            return JobInfo.Builder(
                JOB_ID_OBSERVER, ComponentName(
                    context, RebirthJobService::class.java
                )
            )
                .addTriggerContentUri(
                    JobInfo.TriggerContentUri(
                        uri,
                        JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS
                    )
                )
                .setTriggerContentMaxDelay(MILLIS_CONTENT_MAX_DELAY.toLong())
                .setTriggerContentUpdateDelay(MILLIS_CONTENT_UPDATE_DELAY.toLong())
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setBackoffCriteria(MILLIS_BACKOFF_CRITERIA.toLong(), JobInfo.BACKOFF_POLICY_LINEAR)
                .setMinimumLatency(MILLIS_MINIMUM_LATENCY.toLong())
                .setOverrideDeadline(MILLIS_OVERRIDE_DEADLINE.toLong())
                .build()
        }
    }
}