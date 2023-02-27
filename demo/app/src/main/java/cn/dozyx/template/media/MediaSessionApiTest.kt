package cn.dozyx.template.media

import android.app.PendingIntent
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseTestActivity
import cn.dozyx.template.notification.ZNotification
import androidx.media.app.NotificationCompat.MediaStyle
import cn.dozyx.template.BuildConfig
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MediaSessionApiTest : BaseTestActivity() {
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var sessionToken: MediaSessionCompat.Token
    private val receiver = MediaReceiver()// 调用多次 registerReceiver，也只能接受一次广播

    companion object {
        private const val MEDIA_NOTIFICATION_ID = 0xb339
        private const val PENDING_REQUEST_CODE_ACTION = 100
        private const val PENDING_REQUEST_CODE_CANCEL = 1000
        private const val ACTION_BASE = BuildConfig.APPLICATION_ID + ".action."
        private const val ACTION_CANCEL = ACTION_BASE + "CANCEL"
        private const val ACTION_PLAY = ACTION_BASE + "PLAY"
        private const val ACTION_PAUSE = ACTION_BASE + "PAUSE"
        private const val ACTION_STOP = ACTION_BASE + "STOP"
        private const val ACTION_NEXT = ACTION_BASE + "NEXT"
        private const val ACTION_PREVIOUS = ACTION_BASE + "PREVIOUS"
        private const val ACTION_FAST_FORWARD = ACTION_BASE + "FAST_FORWARD"
        private const val ACTION_FAST_REPEAT = ACTION_BASE + "REPEAT"
    }

    override fun initActions() {
        addAction("controller") {

        }

        addAction("通知") {
            showMediaNotification()
            // 先发送通知，再设置 playback state，进度条不会显示，可能是每一次状态变更都要求更新通知？
        }
        addAction("关闭通知") {
            NotificationManagerCompat.from(this).cancel(MEDIA_NOTIFICATION_ID)
        }

        addAction("播放中") {
            updateMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING)
            // 设为播放中只会，系统会自己定时更新进度
        }
        addAction("暂停") {
            updateMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED)
        }

        addAction("更新封面1") {
            updateMetaData(1, createBitmap(R.drawable.bg_3), 120)
            // 只设置 meta 不会导致通知封面发生变化，需要发送通知更新
//            updateMediaCover(null)
            updateMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING)
//            window.decorView.postDelayed(
//                {
//                    updateMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING)
//                    showMediaNotification(createBitmap(R.drawable.bg_3)) },
//                500
//            )
//            showMediaNotification()

            /*val requestOptions = RequestOptions()
                .override(128, 128)
            Glide.with(this)
                .asBitmap()
                .load(ContextCompat.getDrawable(this, R.drawable.bg_3))
                .apply(requestOptions)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        updateMediaCover(resource)
                        updateMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING)
                        showMediaNotification()
//            showMediaNotification()
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        Timber.d("MediaSessionApiTest.onLoadFailed")
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        TODO("Not yet implemented")
                    }
                })*/
        }

        addAction("更新封面2") {
            updateMetaData(2, createBitmap(R.drawable.bg_1), 160)
            window.decorView.postDelayed({
                updateMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING, 100)
                window.decorView.postDelayed({showMediaNotification(createBitmap(R.drawable.bg_1))}, 100)
            }, 500)
        }

        addAction("receiver2") {
            registerReceiver()
        }

        addAction("active") {
            // 小米手机上只有设置为 true，通知右侧按钮入口展开才会显示当前应用的播放内容
            mediaSession.isActive = !mediaSession.isActive
        }

        addAction("metadata") {
            val metadata = mediaSession.controller.metadata
            repeat(10) {
                // 连续调用中间数据会有丢失：onMetadataChanged 回调次数是正常的，但是最后几次数据是一样的
                // 在 onMetadataChanged 中 使用 mediaSession.controller.metadata 拿到的是最后一次的数据，
                // 因为 setMetadata 之后并不会马上触发 onMetadataChanged，稍微的延迟可以让数据正常
                window.decorView.postDelayed({
                    Timber.d("MediaSessionApiTest setMetadata $it")
                    val newMeta = MediaMetadataCompat.Builder(metadata)
                        .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "标题 $it")
                        .putString(
                            MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,
                            "content://com.dozyx.player/automotive-media%3Aalbum_art_2.jpg"
                        ).build()
                    mediaSession.setMetadata(newMeta)
                }, 50 /*(50 * it).toLong()*/)
            }
        }
    }

    private fun createBitmap(drawable: Int): Bitmap {
        return BitmapFactory.decodeResource(resources, drawable)
    }

    private var i = 0
    private fun updateMetaData(id: Int, displayIcon: Bitmap?, duration: Long = 120) {
        val builder = createMeta(id)
        displayIcon?.let {
//            builder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, it)
//            builder.putString(
//                MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,
//                metadata.description.iconUri?.toString()
//            )
//            builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ART, it)
//            builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, it)
        }
//        builder.putString(
//            MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,
//            "content://com.dozyx.player/automotive-media%3Aalbum_art_2.jpg"
//        )
        builder.putLong(
            MediaMetadataCompat.METADATA_KEY_DURATION,
            TimeUnit.SECONDS.toMillis(120) /*+ (i++)*/
        )// 小米 11（Android） 必须变更 duration 封面才会有变化？
        builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, TimeUnit.SECONDS.toMillis(duration))
        mediaSession.setMetadata(builder.build())
    }

    private fun updateCover(displayIcon: Bitmap?) {
        mediaSession.controller.metadata
    }

    private fun updateDuration() {
        val currentMetadata = mediaSession.controller.metadata ?: return
        MediaMetadataCompat.Builder(currentMetadata).let {
            mediaSession.setMetadata(it.build())
        }
    }

    private fun updateMediaPlaybackState(state: Int, position: Long = 90) {
        val playbackState = PlaybackStateCompat.Builder().also {
            it.setState(state, TimeUnit.SECONDS.toMillis(position), 1F)
            // setActions 并不是产生什么实际效果，只是用来表示这个 session 支持哪些 action。只设置这个，并不会使通知显示按钮，也就是系统不会帮我们添加按钮
            it.setActions(
                PlaybackStateCompat.ACTION_PLAY_PAUSE
                        or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                        or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                        or PlaybackStateCompat.ACTION_STOP
                        or PlaybackStateCompat.ACTION_PLAY
                        or PlaybackStateCompat.ACTION_SEEK_TO// 允许通知栏进度条拖动
            )
            /*it.addCustomAction("custom", "doit", R.drawable.exo_icon_play)
            // CustomAction 只是多了 extras 信息
            // addCustomAction 跟 setActions 一样，看不出有什么效果。应该只是提供给 controller 的一些信息
            it.addCustomAction(
                PlaybackStateCompat.CustomAction.Builder(
                    "custom2",
                    "doit2",
                    R.drawable.exo_icon_next
                ).also { action ->
                    action.setExtras(Bundle().also { extras ->
                        extras.putString("extra_nothing", "哈哈哈")
                    })
                }.build()
            )*/
        }.build()
        mediaSession.setPlaybackState(playbackState)
    }


    private fun showMediaNotification(largeIcon: Bitmap? = null) {
        Timber.d("MediaSessionApiTest.showMediaNotification")
        var _largeIcon = largeIcon
        if (_largeIcon == null) {
            _largeIcon = mediaSession.controller.metadata.description.iconBitmap
        }
        _largeIcon?.let { showImage(it) }

        val mediaStyle = MediaStyle().also {
            it.setMediaSession(sessionToken)
            it.setShowCancelButton(true)
            it.setCancelButtonIntent(buildCancelIntent())// 小米 11p 没看到有按钮，估计跟机型有关
            it.setShowActionsInCompactView(0, 1)// 华为手机（鸿蒙，Android10）上有生效：表现上看参数表示的是收起状态展示哪几个 action
        }
        val notification =
            NotificationCompat.Builder(this, ZNotification.DEFAULT_CHANNEL_ID).also {
                it.setSmallIcon(R.drawable.ic_notification_test)// 不规范的图标会导致进度条显示不正常（thumb 不显示）
                // 设置封面，只设置 description 属性是不会让通知封面改变的
                it.setLargeIcon(_largeIcon)
                it.setUsesChronometer(true)
                it.setStyle(mediaStyle)
                it.addAction(
                    R.drawable.exo_controls_previous, "上一首",
                    createActionIntent(ACTION_PREVIOUS)
                )
                it.addAction(R.drawable.exo_controls_play, "播放", createActionIntent(ACTION_PLAY))
                it.addAction(R.drawable.exo_controls_pause, "暂停", createActionIntent(ACTION_PAUSE))
                it.addAction(R.drawable.exo_controls_next, "下一首", createActionIntent(ACTION_NEXT))
                it.addAction(
                    R.drawable.exo_controls_fastforward,
                    "快进",
                    createActionIntent(ACTION_FAST_FORWARD)
                )
                // 小米 11p 最多显示了 5 个 action
                it.addAction(
                    R.drawable.exo_controls_repeat_one, "循环", createActionIntent(
                        ACTION_FAST_REPEAT
                    )
                )
//                it.setProgress(90, 120, false) // 没作用
                it.setOnlyAlertOnce(true)
            }.build()
        NotificationManagerCompat.from(this).notify(MEDIA_NOTIFICATION_ID, notification)
    }

    private fun buildCancelIntent() = createActionIntent(ACTION_CANCEL)

    private fun createActionIntent(action: String): PendingIntent {
        return PendingIntent.getBroadcast(
            this,
            PENDING_REQUEST_CODE_ACTION,
            Intent(action),
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMediaSession()
        registerReceiver()
    }

    private fun registerReceiver() {
        registerReceiver(MediaReceiver(), IntentFilter().also {
            it.addAction(ACTION_PREVIOUS)
            it.addAction(ACTION_PLAY)
            it.addAction(ACTION_PAUSE)
            it.addAction(ACTION_NEXT)
            it.addAction(ACTION_FAST_FORWARD)
            it.addAction(ACTION_FAST_REPEAT)
        })
    }

    private fun initMediaSession() {
        mediaSession = MediaSessionCompat(
            this,
            "Api session",
            ComponentName(this, MusicIntentReceiver::class.java),
            null
        )
//        mediaSession.setQueue()
        sessionToken = mediaSession.sessionToken
        mediaSession.setCallback(SessionCallback())// 包含媒体按钮和音量事件，方法回调执行在调用线程
        mediaSession.setSessionActivity(null)
        mediaSession.setExtras(Bundle().also { it.putString("extra_from_session", "test1111") })
        mediaSession.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                    or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
                    or MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS
        )
        mediaSession.setMetadata(
            createMeta()
//                .putBitmap(
//                    MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON,
//                    BitmapFactory.decodeResource(resources, R.drawable.bg_4)
//                )// METADATA_KEY_DISPLAY_ICON 在 小米 11p 上，会显示在媒体控制中心（播放通知右侧按钮展开）
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, TimeUnit.SECONDS.toMillis(120))
                .build()
        )
        mediaSession.isActive = true // 设置为 true 才会被系统感知

        mediaSession.controller.registerCallback(MediaControllerCallback(), null)
    }

    private fun createMeta(id: Int = 0): MediaMetadataCompat.Builder {
        return MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "1111($id)")
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "歌曲名($id)")
            .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, "Subtitle($id)")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "歌手($id)")
//                .putString(MediaMetadataCompat.METADATA_KEY_AUTHOR, "作者") // 不会显示在标题下面
            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "专辑($id)")
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, "http://test.com")
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, TimeUnit.SECONDS.toMillis(120))
    }

    private inner class MediaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Timber.d("MediaReceiver.onReceive: " + intent?.action)
        }
    }

    private inner class SessionCallback : MediaSessionCompat.Callback() {
        override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {
            super.onCommand(command, extras, cb)
            Timber.d("SessionCallback.onCommand")
        }

        override fun onMediaButtonEvent(mediaButtonEvent: Intent?): Boolean {
            Timber.d("SessionCallback.onMediaButtonEvent")
            return super.onMediaButtonEvent(mediaButtonEvent)
        }

        override fun onPrepare() {
            super.onPrepare()
            Timber.d("SessionCallback.onPrepare")
        }

        override fun onPrepareFromMediaId(mediaId: String?, extras: Bundle?) {
            super.onPrepareFromMediaId(mediaId, extras)
            Timber.d("SessionCallback.onPrepareFromMediaId")
        }

        override fun onPrepareFromSearch(query: String?, extras: Bundle?) {
            super.onPrepareFromSearch(query, extras)
            Timber.d("SessionCallback.onPrepareFromSearch")
        }

        override fun onPrepareFromUri(uri: Uri?, extras: Bundle?) {
            super.onPrepareFromUri(uri, extras)
            Timber.d("SessionCallback.onPrepareFromUri")
        }

        override fun onPlay() {
            super.onPlay()
            Timber.d("SessionCallback.onPlay")
        }

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            super.onPlayFromMediaId(mediaId, extras)
            Timber.d("SessionCallback.onPlayFromMediaId")
        }

        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            super.onPlayFromSearch(query, extras)
            Timber.d("SessionCallback.onPlayFromSearch")
        }

        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            super.onPlayFromUri(uri, extras)
            Timber.d("SessionCallback.onPlayFromUri")
        }

        override fun onSkipToQueueItem(id: Long) {
            super.onSkipToQueueItem(id)
            Timber.d("SessionCallback.onSkipToQueueItem")
        }

        override fun onPause() {
            super.onPause()
            Timber.d("SessionCallback.onPause")
        }

        override fun onSkipToNext() {
            super.onSkipToNext()
            Timber.d("SessionCallback.onSkipToNext")
        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            Timber.d("SessionCallback.onSkipToPrevious")
        }

        override fun onFastForward() {
            super.onFastForward()
            Timber.d("SessionCallback.onFastForward")
        }

        override fun onRewind() {
            super.onRewind()
            Timber.d("SessionCallback.onRewind")
        }

        override fun onStop() {
            super.onStop()
            Timber.d("SessionCallback.onStop")
        }

        override fun onSeekTo(pos: Long) {
            super.onSeekTo(pos)
            Timber.d("SessionCallback.onSeekTo")
//            updateMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING, pos)
//            updateDuration()
//            showMediaNotification(createBitmap(R.drawable.bg_1))
            updateMediaPlaybackState(PlaybackStateCompat.STATE_BUFFERING)
        }

        override fun onSetRating(rating: RatingCompat?) {
            super.onSetRating(rating)
            Timber.d("SessionCallback.onSetRating")
        }

        override fun onSetRating(rating: RatingCompat?, extras: Bundle?) {
            super.onSetRating(rating, extras)
            Timber.d("SessionCallback.onSetRating")
        }

        override fun onSetCaptioningEnabled(enabled: Boolean) {
            super.onSetCaptioningEnabled(enabled)
            Timber.d("SessionCallback.onSetCaptioningEnabled")
        }

        override fun onSetRepeatMode(repeatMode: Int) {
            super.onSetRepeatMode(repeatMode)
            Timber.d("SessionCallback.onSetRepeatMode")
        }

        override fun onSetShuffleMode(shuffleMode: Int) {
            super.onSetShuffleMode(shuffleMode)
            Timber.d("SessionCallback.onSetShuffleMode")
        }

        override fun onCustomAction(action: String?, extras: Bundle?) {
            super.onCustomAction(action, extras)
            Timber.d("SessionCallback.onCustomAction")
        }

        override fun onAddQueueItem(description: MediaDescriptionCompat?) {
            super.onAddQueueItem(description)
            Timber.d("SessionCallback.onAddQueueItem")
        }

        override fun onAddQueueItem(description: MediaDescriptionCompat?, index: Int) {
            super.onAddQueueItem(description, index)
            Timber.d("SessionCallback.onAddQueueItem")
        }

        override fun onRemoveQueueItem(description: MediaDescriptionCompat?) {
            super.onRemoveQueueItem(description)
            Timber.d("SessionCallback.onRemoveQueueItem")
        }

        override fun onRemoveQueueItemAt(index: Int) {
            super.onRemoveQueueItemAt(index)
            Timber.d("SessionCallback.onRemoveQueueItemAt")
        }
    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
        override fun onSessionReady() {
            super.onSessionReady()
            Timber.d("MediaControllerCallback.onSessionReady")
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()
            Timber.d("MediaControllerCallback.onSessionDestroyed")
        }

        override fun onSessionEvent(event: String?, extras: Bundle?) {
            super.onSessionEvent(event, extras)
            Timber.d("MediaControllerCallback.onSessionEvent")
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            Timber.d("MediaControllerCallback.onPlaybackStateChanged ${mediaSession.controller.metadata.description.title}")
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            Timber.d(
                """MediaControllerCallback.onMetadataChanged 
                    ${metadata?.getString(MediaMetadataCompat.METADATA_KEY_TITLE)}
                    last: ${mediaSession.controller.metadata?.getString(MediaMetadataCompat.METADATA_KEY_TITLE)}"""
            )
            val largeIcon = metadata?.description?.iconBitmap
//            largeIcon?.let { showImage(it) }
        }

        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
            super.onQueueChanged(queue)
            Timber.d("MediaControllerCallback.onQueueChanged")
        }

        override fun onQueueTitleChanged(title: CharSequence?) {
            super.onQueueTitleChanged(title)
            Timber.d("MediaControllerCallback.onQueueTitleChanged")
        }

        override fun onExtrasChanged(extras: Bundle?) {
            super.onExtrasChanged(extras)
            Timber.d("MediaControllerCallback.onExtrasChanged")
        }

        override fun onAudioInfoChanged(info: MediaControllerCompat.PlaybackInfo?) {
            super.onAudioInfoChanged(info)
            Timber.d("MediaControllerCallback.onAudioInfoChanged")
        }

        override fun onCaptioningEnabledChanged(enabled: Boolean) {
            super.onCaptioningEnabledChanged(enabled)
            Timber.d("MediaControllerCallback.onCaptioningEnabledChanged")
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
            Timber.d("MediaControllerCallback.onRepeatModeChanged")
        }

        override fun onShuffleModeChanged(shuffleMode: Int) {
            super.onShuffleModeChanged(shuffleMode)
            Timber.d("MediaControllerCallback.onShuffleModeChanged")
        }
    }
}

private class MusicIntentReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("MusicIntentReceiver.onReceive action: ${intent?.action}")
    }
}