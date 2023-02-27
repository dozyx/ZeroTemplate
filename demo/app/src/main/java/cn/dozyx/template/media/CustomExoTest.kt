package cn.dozyx.template.media

import android.os.Bundle
import android.os.Environment
import android.view.TextureView
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.template.databinding.CustomExoTestBinding
import cn.dozyx.template.databinding.VideoPlayerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.EventLogger
import com.google.android.exoplayer2.video.VideoListener
import timber.log.Timber

class CustomExoTest : AppCompatActivity() {

    private lateinit var binding: CustomExoTestBinding
    private lateinit var player: SimpleExoPlayer
    private val texture by lazy { TextureView(this) }
    private lateinit var playerViewBinding: VideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomExoTestBinding.inflate(layoutInflater)
        playerViewBinding = VideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPlayer()
        prepareMedia()
        initClick()
    }

    private fun initClick() {
        binding.play.setOnClickListener {
            player.playWhenReady = player.playWhenReady.not()
        }
        binding.seek.setOnClickListener {
            player.seekTo(player.duration - 5 * 1000)
        }
        binding.btn3.setOnClickListener {
//            player.setVideoSurface(null)
//            binding.flTextureContainer.removeAllViews()
            setVideoRenderDisable(true)
        }
        binding.btn4.setOnClickListener {
//            setTexture()
//            player.setVideoTextureView(texture)
//            binding.flTextureContainer.addView(playerViewBinding.root,
//                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
            setVideoRenderDisable(false)
            player.prepare()
        }
        binding.btn5.setOnClickListener {
            player.seekTo(player.duration - 10)
        }
    }

    private fun setTexture() {
//        binding.flTextureContainer.addView(texture)
        binding.flTextureContainer.addView(playerViewBinding.root)
        player.setVideoTextureView(playerViewBinding.texture)
    }

    private fun setVideoRenderDisable(disabled: Boolean) {
        val trackSelector = player.trackSelector
        (trackSelector as DefaultTrackSelector).let {
            it.parameters =
                it.buildUponParameters().apply { setRendererDisabled(0, disabled) }.build()
        }
    }

    private fun prepareMedia() {
        val mediaFilePath = Environment.getExternalStorageDirectory().absolutePath + "/video.mp4"
        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSourceFactory(this, "test")
        ).createMediaSource(MediaItem.fromUri(mediaFilePath))
        player.setMediaSource(mediaSource)
        player.prepare()
    }

    private fun initPlayer() {
        val builder = SimpleExoPlayer.Builder(this)
        player = builder.build()
        setTexture()
        player.addAnalyticsListener(EventLogger(player.trackSelector as MappingTrackSelector?))
        player.addVideoListener(object : VideoListener {
            override fun onRenderedFirstFrame() {
                super.onRenderedFirstFrame()
                // 并不是一个视频的第一帧，而是「此次渲染」的第一帧。包括开始播放、重置播放、seek 之后等
                Timber.d("CustomExoTest.onRenderedFirstFrame")
            }
        })
    }

}