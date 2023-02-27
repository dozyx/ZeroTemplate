package cn.dozyx.template.media

import android.app.PendingIntent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.SurfaceView
import android.view.TextureView
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import cn.dozyx.template.notification.manager.NotificationManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.source.LoadEventInfo
import com.google.android.exoplayer2.source.MediaLoadData
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.EventLogger
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.test_exo_player.*
import okhttp3.OkHttpClient
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
    private var currentIndex: Int = 0

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
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var playerNotificationManager: PlayerNotificationManager
    private val songs = listOf(
        Song(
            "https://storage.googleapis.com/automotive-media/Keys_To_The_Kingdom.mp3",
            "Keys To The Kingdom",
            "https://storage.googleapis.com/automotive-media/album_art_2.jpg",
            221
        ),
        Song(
            "https://storage.googleapis.com/automotive-media/The_Coldest_Shoulder.mp3",
            "The Coldest Shoulder",
            "https://storage.googleapis.com/automotive-media/album_art_3.jpg",
//            160
        220
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentType = Util.inferContentType(MEDIA_URI)// inferContentType 主要是根据路径判断的。。。
        Timber.d("ExoPlayerTest.onCreate inferContentType: $contentType")
        initUi()
        attachPlayer()
        listenEvent()
        mediaSession = MediaSessionCompat(this, "ExoPlayer")
        mediaSession.setCallback(MyCallback())
//        prepareMedia(0)
        prepareLocalMedia()
        playerNotificationManager =
            PlayerNotificationManager(
                this,
                NotificationManager.CHANNEL_ID_DEFAULT,
                10086,
                MyMediaDescriptionAdapter(mediaSession.controller)
            )
        playerNotificationManager.setMediaSessionToken(mediaSession.sessionToken)
        playerNotificationManager.setPlayer(player)
    }

    inner class MyCallback : MediaSessionCompat.Callback() {
        override fun onSkipToNext() {
            super.onSkipToNext()

        }
    }

    private fun listenEvent() {
        // 第一种方式
        player.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                Timber.d("ExoPlayerTest.onPlaybackStateChanged: ${stateString(state)}")
                if (state == ExoPlayer.STATE_READY) {
                    mediaSession.setPlaybackState(
                        PlaybackStateCompat.Builder()
                            .setState(PlaybackStateCompat.STATE_PLAYING, player.currentPosition, 1F)
                            .build()
                    )
                }
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

    private fun prepareMedia(index: Int) {

        currentIndex = index
        val song = songs[index]
        updateMetadata(song)
        if (!mediaSession.isActive) {
            mediaSession.isActive = true
        }

        /**
         * [Util.getUserAgent] 返回的格式如 「传入的应用名称/应用版本号 (Linux;Android 10) ExoPlayerLib/exo版本号」
         */
        var userAgent = Util.getUserAgent(this, "appName")
//        userAgent = WebSettings.getDefaultUserAgent(this)
        var dataSourceFactory: DataSource.Factory? = null
        dataSourceFactory = DefaultDataSourceFactory(this, userAgent)// DataSource: 从 uri 指定资源中读取数据
//        dataSourceFactory = OkHttpDataSourceFactory(Call.Factory { request -> okHttpClient.newCall(request) }, userAgent)

        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(song.url).buildUpon().apply {
                /**
                 * 为播放媒体设置 tag，可以通过 [ExoPlayer.getCurrentMediaItem] 读取
                 */
                setTag("id")
            }.build())// 每个媒体都由一个 MediaSource 表示
        player.setMediaSource(mediaSource)
        player.prepare()
    }

    private fun prepareLocalMedia() {
        player.setMediaSource(buildMediaSource(Environment.getExternalStorageDirectory().absolutePath + "/video.mp4"))
        player.prepare()
        updateMetadata(songs[0])
    }

    private fun buildMediaSource(filePath: String): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(this, "test")
        )
        val mediaUri = Uri.parse(filePath)
        //这是一个代表将要被播放的媒体的MediaSource
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaUri)
    }

    private fun updateMetadata(song: Song) {
        MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.url)
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, song.title)
            .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, song.image)
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, song.duration) // duration 相同或者没有的话，通知封面没有变化，很奇怪
            .build().apply {
                mediaSession.setMetadata(this)
            }
    }

    private fun attachPlayer() {
        player =
            SimpleExoPlayer.Builder(this, DefaultRenderersFactory(this, EXTENSION_RENDERER_MODE_ON))
                .apply {
                    setLooper(Looper.getMainLooper()) // 如果不设置 looper 的话，那么将使用 thread 所关联的 looper；如果 thread 没有 looper，那么将使用主线程的 looper
                }.build().apply {
                    //        setVideoSurface(null)

                }
        playerView.player = player // 将播放器关联到视图上
        player_view.player = player
        player_view.setShutterBackgroundColor(Color.TRANSPARENT)
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }

    private fun initUi() {
        playerView = PlayerView(this).apply {

        }
//        fl_player_container.addView(playerView)
        btn_next.setOnClickListener {
            prepareMedia(if (currentIndex + 1 >= songs.size) 0 else currentIndex + 1)
        }
        btn_only_audio.setOnClickListener {
            (player.trackSelector as DefaultTrackSelector).apply {

                setParameters(buildUponParameters().apply {
                    setRendererDisabled(0, !parameters.getRendererDisabled(0))
                })
            }
        }
    }

    override fun getLayoutId() = R.layout.test_exo_player


    private inner class MyMediaDescriptionAdapter(private val controller: MediaControllerCompat) :
        PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
            return controller.metadata.description.title ?: "默认标题"
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return controller.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return controller.metadata.description.subtitle
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            val icon =
                controller.metadata.getBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON)
            if (icon != null) {
                return icon
            }
            val iconUri = controller.metadata.description.iconUri
            Glide.with(this@ExoPlayerTest)
                .asBitmap()
                .load(iconUri)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        callback.onBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
//            return BitmapFactory.decodeResource(resources, R.drawable.bg_1)
            return null

            /*// Glide 内部禁止 UI 线程调用，会 Crash
            return Glide.with(this@ExoPlayerTest)
                .asBitmap()
                .load(iconUri)
                .submit()
                .get()*/
        }
    }

    companion object {
        //        private const val MEDIA_URI = "http://ddeta2nicr6gf.cloudfront.net/ckctdume903lf18t15q6egfxs/nowm-720p.mp4"
//        private const val MEDIA_URI2 = "https://video.like.video/na_live/4ay/297g8w_4.mp4"
        private const val MEDIA_URI =
            "https://storage.googleapis.com/automotive-media/The_Coldest_Shoulder.mp3"
    }

    data class Song(val url: String, val title: String, val image: String, val duration: Long)


    fun Song.toMediaItem(): MediaItem {
        return MediaItem.Builder().setUri(url)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(title)
                    .build()
            )
            .build()
    }
}