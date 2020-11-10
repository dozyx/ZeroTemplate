package cn.dozyx.template.media

import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.SurfaceView
import android.view.TextureView
import android.webkit.WebSettings
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.source.LoadEventInfo
import com.google.android.exoplayer2.source.MediaLoadData
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.EventLogger
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.test_exo_player.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.IOException

/**
 * 基本使用步骤：
 * 1. 添加依赖
 * 2. 构建 ExoPlayer (一般使用 SimpleExoPlayer)
 * 3. 添加 PlayerView (默认提供了 SurfaceView 和 PlayerControlView)
 * 4. 为 PlayerView 设置播放器 ExoPlayer
 * 5. 为播放资源创建 MediaSource (需要提供一个 DataSource.Factory)
 * 6. 将 MediaSource 设置到 SimpleExoPlayer 中，调用 prepare() 方法
 * 7. 调用 [ExoPlayer.setPlayWhenReady] 可以直接开始播放
 * @author dozyx
 * @date 10/29/20
 */
class ExoPlayerTest : BaseActivity() {
    /**
     * SimpleExoPlayer 实现了[ExoPlayer] 接口
     * ExoPlayer 只能从单个线程访问
     */
    private lateinit var player: SimpleExoPlayer

    /**
     * PlayerView 继承于 FrameLayout，默认封装了一个 [SurfaceView] (可以通过在 surface_type 属性指定其他的 surface，比如 [TextureView] 或者不指定 Surface)
     * 和一个 [PlayerControlView] (需要在 PlayerView 中添加一个 id 为 exo_controller_placeholder 的 view 来放置 PlayerControlView；
     * 如果要使用自己的 PlayerControlView，则需要将其 id 设为 exo_controller。
     * 不使用 PlayerControlView 的话，则需要自己实现控制)
     * PlayerControlView 默认布局 [R.layout.exo_player_control_view]，可通过属性更换布局或者使用同名布局文件覆盖
     *
     */
    private lateinit var playerView: PlayerView
    private val okHttpClient = OkHttpClient.Builder().apply { }.build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentType = Util.inferContentType(MEDIA_URI)// inferContentType 主要是根据路径判断的。。。
        Timber.d("ExoPlayerTest.onCreate inferContentType: $contentType")
        initUi()
        attachPlayer()
        prepareMedia()
        listenEvent()
    }

    private fun listenEvent() {
        // 第一种方式
        player.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                Timber.d("ExoPlayerTest.onPlaybackStateChanged: ${stateString(state)}")
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                Timber.e(error)
            }
        })
        player.addAnalyticsListener(object : AnalyticsListener {
            override fun onLoadError(
                eventTime: AnalyticsListener.EventTime,
                loadEventInfo: LoadEventInfo,
                mediaLoadData: MediaLoadData,
                error: IOException,
                wasCanceled: Boolean
            ) {
                Timber.e(error)
            }

            override fun onPlayerError(
                eventTime: AnalyticsListener.EventTime,
                error: ExoPlaybackException
            ) {
                Timber.e(error)
            }
        })
        /**
         * [EventLogger] 将日志输出到 logcat，过滤 EventLogger|ExoPlayerImpl
         * [TrackSelector] 轨道选择器
         */
        player.addAnalyticsListener(EventLogger(DefaultTrackSelector(this)))
    }

    private fun stateString(@Player.State state: Int): String {
        return when (state) {
            Player.STATE_IDLE -> "STATE_IDLE"
            Player.STATE_BUFFERING -> "STATE_BUFFERING"
            Player.STATE_READY -> "STATE_READY" // 播放、暂停不会触发 state 变化
            Player.STATE_ENDED -> "STATE_ENDED"
            else -> "UNKNOWN"
        }
    }

    private fun prepareMedia() {
        /**
         * [Util.getUserAgent] 返回的格式如 「传入的应用名称/应用版本号 (Linux;Android 10) ExoPlayerLib/exo版本号」
         */
        var userAgent = Util.getUserAgent(this, "appName")
//        userAgent = WebSettings.getDefaultUserAgent(this)
        var dataSourceFactory: DataSource.Factory? = null
        dataSourceFactory = DefaultDataSourceFactory(this, userAgent)// DataSource: 从 uri 指定资源中读取数据
//        dataSourceFactory = OkHttpDataSourceFactory(Call.Factory { request -> okHttpClient.newCall(request) }, userAgent)

        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(MEDIA_URI).buildUpon().apply {
                /**
                 * 为播放媒体设置 tag，可以通过 [ExoPlayer.getCurrentMediaItem] 读取
                 */
                setTag("id")
            }.build())// 每个媒体都由一个 MediaSource 表示
        player.setMediaSource(mediaSource)
        player.prepare()
    }

    private fun attachPlayer() {
        player = SimpleExoPlayer.Builder(this).apply {
            setLooper(Looper.getMainLooper()) // 如果不设置 looper 的话，那么将使用 thread 所关联的 looper；如果 thread 没有 looper，那么将使用主线程的 looper
        }.build().apply {
            //        setVideoSurface(null)

        }
        playerView.player = player // 将播放器关联到视图上
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }

    private fun initUi() {
        playerView = PlayerView(this).apply {

        }
        fl_player_container.addView(playerView)
    }

    override fun getLayoutId() = R.layout.test_exo_player

    companion object {
        private const val MEDIA_URI = "http://ddeta2nicr6gf.cloudfront.net/ckctdume903lf18t15q6egfxs/nowm-720p.mp4"
        private const val MEDIA_URI2 = "https://video.like.video/na_live/4ay/297g8w_4.mp4"
    }
}