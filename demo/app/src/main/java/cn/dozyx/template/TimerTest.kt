package cn.dozyx.template

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.PowerManager
import android.os.SystemClock
import android.text.TextUtils
import androidx.core.app.AlarmManagerCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import cn.dozyx.template.service.ForegroundService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.LinkedList
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit

/**
 * 计时一般会在进入后台一会就被停止
 * 息屏会短暂地恢复，但一会后又停止
 */
class TimerTest : BaseTestActivity() {
    private var dickTime = 0
    private var isLooping = false
    private val mainHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            printDickTime()
            if (!isLooping) {
                return
            }
            sendEmptyMessageDelayed(1, 1000)
        }
    }
    private val simpleTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val timer = Timer()
    private val countDownTimer = object : CountDownTimer(120 * 1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            appendResult("${simpleTimeFormat.format(Date())} count down：${dickTime++}")
        }

        override fun onFinish() {
            appendResult("${simpleTimeFormat.format(Date())} count down end")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver()
    }

    override fun initActions() {
        addAction(object : Action("前台服务") {
            override fun run() {
                // 开启前台服务可以确保后台任务继续运行
                startService(Intent(this@TimerTest, ForegroundService::class.java))
            }
        })

        addAction(object : Action("Wake") {
            override fun run() {
                // 小米 11（Android13）上还是不能确保后台计时持续运行
                appendResult("${simpleTimeFormat.format(Date())} wakeLock.acquire")
                val powerManager = applicationContext.getSystemService(POWER_SERVICE) as PowerManager
                val wakeLock =
                    powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, ":timing")
                wakeLock.acquire(120 * 1000)
            }
        })

        addAction(object : Action("Handler") {
            override fun run() {
                handleTimerByHandler()
            }
        })

        addAction(object : Action("Timer") {
            override fun run() {
                handlTimerByTimer()
            }
        })

        addAction(object : Action("CountDownTimer") {
            override fun run() {
                handleTimerByCountDownloadTimer()
            }
        })

        addAction(object : Action("Thread") {
            override fun run() {
                Thread {
                    appendResult("thread start...")
                    while (true) {
                        Thread.sleep(1000)
                        printDickTime()
                    }
                }.start()
            }
        })

        addAction(object : Action("协程") {
            override fun run() {
                GlobalScope.launch {
                    while (true) {
                        delay(1000)
                        printDickTime()
                    }
                }
            }
        })

        addAction(object : Action("WorkManager") {
            override fun run() {
                appendResult("${simpleTimeFormat.format(Date())} add worker")
                val request =
                    OneTimeWorkRequestBuilder<MyWorker>()
                        .setInitialDelay(60, TimeUnit.SECONDS)
                        .build()
                WorkManager.getInstance(this@TimerTest).enqueue(request)
            }
        })

        addAction(object : Action("AlarmManager") {
            override fun run() {
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                appendResult(
                    "${simpleTimeFormat.format(Date())} start alarm, " +
                            "canScheduleExactAlarms: ${if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) alarmManager.canScheduleExactAlarms() else ""}"
                )
                // setExactAndAllowWhileIdle 进入后台后没有按准确延迟时间执行，而是在收到息屏广播时执行，
                // 所以应用休眠状态，APP 是无法执行 alarm 的。另外一点是息屏广告可以唤醒应用，这时才触发了 alarm。
                AlarmManagerCompat.setExactAndAllowWhileIdle(
                    alarmManager,
                    AlarmManager.ELAPSED_REALTIME,
                    60 * 1000L + SystemClock.elapsedRealtime(),
                    PendingIntent.getBroadcast(
                        this@TimerTest,
                        111,
                        Intent(ACTION_ALARM),
                        PendingIntent.FLAG_ONE_SHOT
                    )
                )
            }
        })

        addAction(object : Action("AlarmManager") {
            override fun run() {

            }
        })
    }

    private fun handleTimerByCountDownloadTimer() {
        if (!isLooping) {
            countDownTimer.start()
        } else {
            countDownTimer.cancel()
        }
        isLooping = !isLooping
    }

    private fun handlTimerByTimer() {
        if (isLooping) {
            // 后台状态计时会停止
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    printDickTime()
                }
            }, 0, 1000)
        } else {
            timer.cancel()
        }
        isLooping = !isLooping
    }

    private fun handleTimerByHandler() {
        mainHandler.sendEmptyMessage(1)
        isLooping = !isLooping
    }

    private fun printDickTime() {
        appendResult("${simpleTimeFormat.format(Date())} timer: ${dickTime++}")
    }

    private fun registerReceiver() {
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                appendResult("${simpleTimeFormat.format(Date())} receive broadcast：${intent?.action}")
                if (TextUtils.equals(intent?.action, ACTION_ALARM)) {
                    printDickTime()
                }
            }
        }, IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(ACTION_WORKER)
            addAction(ACTION_ALARM)
        })
    }

    override fun onPause() {
        Timber.d("HandlerTest.onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.d("HandlerTest.onStop")
        super.onStop()
    }

    companion object {
        const val ACTION_WORKER = "intent.action.worker"
        const val ACTION_ALARM = "intent.action.alarm"
    }

    class MyWorker(context: Context, workerParams: WorkerParameters) :
        Worker(context, workerParams) {
        override fun doWork(): Result {
            applicationContext.sendBroadcast(Intent(ACTION_WORKER))
            return Result.success()
        }
    }
}