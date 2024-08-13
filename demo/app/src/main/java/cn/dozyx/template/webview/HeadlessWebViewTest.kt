package cn.dozyx.template.webview

import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.acsbendi.requestinspectorwebview.RequestInspectorWebViewClient
import com.acsbendi.requestinspectorwebview.WebViewRequest
import kotlinx.android.synthetic.main.web_view_headless.btn_load
import okio.buffer
import okio.source
import java.nio.charset.Charset

class HeadlessWebViewTest : BaseActivity() {

    private val PLAYER_HTML = "<!DOCTYPE html><html> <style type=\"text/css\"> html, body { height: 100%; width: 100%; margin: 0 0 0 0; padding: 0 0 0 0; background: #000000; overflow: hidden; position: fixed; } </style> <head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0\"> <script src=\"https://www.youtube.com/iframe_api\"></script> </head> <body> <div id=\"playerId\"></div> </body> <script type=\"text/javascript\"> var player; var isPlayActionActive = false; var currentCaptionTrack = null; var confirmLoadCaption = true; function onYouTubeIframeAPIReady() { console.log(\"[JS] initialized ytplayer\"); player = new YT.Player('playerId', { height: '100%', width: '100%', events: { 'onReady': onPlayerReady, 'onStateChange': onPlayerStateChange, 'onPlaybackQualityChange': onPlayerPlaybackQualityChange, 'onPlaybackRateChange': onPlayerPlaybackRateChange, 'onError': onPlayerError, 'onApiChange': onPlayerApiChange }, // https://developers.google.com/youtube/player_parameters playerVars: { 'autoplay': 0, // do not autoplay when player loads ? 'cc_load_policy':1, // auto open caption 'autohide': 1, // deprecated, auto hide controls when start playing 'controls': 0, // hide player controls 'enablejsapi': 1, // enable iFrame API controls 'fs': 0, // hide fullscreen button 'origin' : 'https://www.youtube.com', // should use our domain ? 'rel': 0, // do not show related videos when ends 'showinfo': 0, // do not show video info before playing 'iv_load_policy': 3 // hide video annotations } }); } function onPlayerReady(event) { console.log('[JS] ytplayer is ready'); onReady(); } // id of the task used to periodically send out the playback seconds var updateSecondTask = 0; var delayPlayVideoTask = 0; var lastEventData = null; function onPlayerStateChange(event) { clearInterval(updateSecondTask); clearInterval(delayPlayVideoTask); switch (event.data) { case YT.PlayerState.UNSTARTED: { onStateChange(\"UNSTARTED\"); console.log(\"[JS] ytplayer's state changed to UNSTARTED\") // Sometimes player tries to play a video and enters BUFFERING state, // however it returns to UNSTARTED when done buffering video data, // which is clearly something wrong within the player. // So we fix this by trying to play video manually when it goes wrong. if(YT.PlayerState.BUFFERING === lastEventData){ delayPlayVideoTask = setInterval(function() { if (!isPlayActionActive) { clearInterval(delayPlayVideoTask); return; } playVideo(); }, 500); } break; } case YT.PlayerState.ENDED: { onStateChange(\"ENDED\"); console.log(\"[JS] ytplayer's state changed to ENDED\") break; } case YT.PlayerState.PLAYING: { if (!isPlayActionActive) { pauseVideo(); break; } onStateChange(\"PLAYING\"); console.log(\"[JS] ytplayer's state changed to PLAYING\") updateSecondTask = setInterval(function() { onCurrentSeconds(); }, 100); sendDuration(); hideCards(); if(!confirmLoadCaption){ if(currentCaptionTrack != null){ doSetCaption(currentCaptionTrack); }else{ doCloseCaption(); } confirmLoadCaption=true; } break; } case YT.PlayerState.PAUSED: { onStateChange(\"PAUSED\"); console.log(\"[JS] ytplayer's state changed to PAUSED\") break; } case YT.PlayerState.BUFFERING: { onStateChange(\"BUFFERING\"); console.log(\"[JS] ytplayer's state changed to BUFFERING\") break; } case YT.PlayerState.CUED: { onStateChange(\"CUED\"); console.log(\"[JS] ytplayer's state changed to CUED\") break; } } lastEventData = event.data; } function onPlayerPlaybackQualityChange(playbackQuality) { console.log('[JS] quality changed to ' + playbackQuality.data); onPlaybackQualityChange(playbackQuality.data); } function onPlayerPlaybackRateChange(playbackRate) { console.log('[JS] playback rate changed to ' + playbackRate.data); onPlaybackRateChange(playbackRate.data); } function onPlayerError(error) { console.log('[JS] An error occurred: ' + error.data); onError(error.data); } function onPlayerApiChange() { console.log('[JS] player API changed'); onApiChange(); } // // WEB to APP functions // function onReady() { window.YouTubePlayerBridge.onReady(); } function onStateChange(state) { window.YouTubePlayerBridge.onStateChange(state); } function onPlaybackQualityChange(quality) { window.YouTubePlayerBridge.onPlaybackQualityChange(quality); } function onPlaybackRateChange(rate) { window.YouTubePlayerBridge.onPlaybackRateChange(rate); } function onError(error) { window.YouTubePlayerBridge.onError(error); } function onApiChange() { window.YouTubePlayerBridge.onApiChange(); } function onCurrentSeconds() { window.YouTubePlayerBridge.currentSeconds(player.getCurrentTime()); } function sendDuration() { window.YouTubePlayerBridge.onVideoDuration(player.getDuration()); } function sendVideoTitle(title) { window.YouTubePlayerBridge.onVideoTitle(title); } function sendVideoId(id) { window.YouTubePlayerBridge.onVideoId(id); } function setLog(msg) { window.YouTubePlayerBridge.onLog(msg); } function getSubTitleModule(){ var module = ''; try{ if(player.getOptions('cc')){ module = 'cc'; } }catch(err){ console.log('[JS] no cc module'); } try{ if(player.getOptions('captions')){ module = 'captions'; } }catch(err){ console.log('[JS] no captions module'); } return module; } function doSetCaption(track){ var module = getSubTitleModule(); if(module == ''){ console.log('[JS] no subtitle'); return; } player.setOption(module,'track', {}); player.setOption(module,'track', track); setTimeout(function() { if(player!=null){ player.setOption(module,'fontSize', 2.5); } }, 1000); } function setCaption(languageCode, name, kind, url) { console.log('[JS] load subTitle (language = ' + languageCode + ', name = ' + name + ', kind = ' + kind ); currentCaptionTrack = {'languageCode':languageCode, 'srclang':languageCode,'label':name}; doSetCaption(currentCaptionTrack); confirmLoadCaption = false; } function doCloseCaption(){ console.log('[JS] close subTitle'); var module = getSubTitleModule(); if(module == ''){ console.log('[JS] no subtitle'); return; } player.setOption(module,'track', {}); } function closeCaption() { doCloseCaption(); currentCaptionTrack=null; confirmLoadCaption = false; } // // APP to WEB functions // function seekTo(startSeconds) { console.log(\"[JS] Seek to \" +startSeconds); // startSeconds is not exactly precise because is an int if(Math.floor(player.getDuration()) == startSeconds) startSeconds = player.getDuration(); // todo(xieshaoze): // Set param allowSeekAhead to false when user dragging progress bar, // and set to true after user release it. Recommended by YouTube iframe api docs. player.seekTo(startSeconds, true); } function pauseVideo() { console.log(\"[JS] Pause video\"); player.pauseVideo(); isPlayActionActive = false; clearInterval(delayPlayVideoTask); } function playVideo() { setLog(\"try to play Video\"); console.log(\"[JS] Play video\"); isPlayActionActive = true; player.playVideo(); } function loadVideo(videoId, startSeconds) { console.log(\"[JS] Loading video: \" +videoId + \" - \" + startSeconds); setLog(\"Loading video: \" +videoId + \" - \" + startSeconds); player.loadVideoById(videoId, startSeconds); // sometimes player cannot auto-play video after loading it, // so we can manually play video. playVideo(); } function cueVideo(videoId, startSeconds) { console.log(\"[JS] Cue video: \" +videoId + \" - \" + startSeconds); setLog(\"Cue video: \" +videoId + \" - \" + startSeconds); player.cueVideoById(videoId, startSeconds); } function mute() { console.log(\"[JS] mute\"); player.mute(); } function unMute() { console.log(\"[JS] unMute\"); player.unMute(); } function setVolume(volumePercent) { console.log(\"[JS] setVolume: \" + volumePercent); player.setVolume(volumePercent); } function hideCards(){ var doc = player.getIframe().contentWindow.document; var elements = doc.getElementsByClassName(\"ytp-ce-element\"); for (var i = 0; i < elements.length; i++) { elements[i].style.display = \"none\"; } } </script> <!--<script src=\"https://www.youtube.com/iframe_api\"></script>--> <!--<script type=\"text/javascript\">--> <!--if (!window['YT']) {var YT = {loading: 0,loaded: 0};}if (!window['YTConfig']) {var YTConfig = {'host': 'http://www.youtube.com'};}if (!YT.loading) {YT.loading = 1;(function(){var l = [];YT.ready = function(f) {if (YT.loaded) {f();} else {l.push(f);}};window.onYTReady = function() {YT.loaded = 1;for (var i = 0; i < l.length; i++) {try {l[i]();} catch (e) {}}};YT.setConfig = function(c) {for (var k in c) {if (c.hasOwnProperty(k)) {YTConfig[k] = c[k];}}};var a = document.createElement('script');a.type = 'text/javascript';a.id = 'www-widgetapi-script';a.src = 'https:' + '//s.ytimg.com/yts/jsbin/www-widgetapi-vflxBao7t/www-widgetapi.js';a.async = true;var b = document.getElementsByTagName('script')[0];b.parentNode.insertBefore(a, b);})();}--> <!--</script>--></html>"
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPlayer()
        btn_load.setOnClickListener {
            webView.loadUrl("javascript:loadVideo('" + "78ASlB64rA4" + "', " + 0 + ")")
        }
    }

    private fun initPlayer() {
        webView = WebView(this)
//        fl_container.addView(webView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        WebView.setWebContentsDebuggingEnabled(true)

        webView.settings.apply {
            javaScriptEnabled = true
            mediaPlaybackRequiresUserGesture = false
            cacheMode = WebSettings.LOAD_DEFAULT
            allowUniversalAccessFromFileURLs = true
            domStorageEnabled = true
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
//                webView.performClick()
//                webView.loadUrl("https://www.youtube.com/embed/78ASlB64rA4?autoplay=1")
//                webView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()")
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }

        }
        webView.webViewClient = object :RequestInspectorWebViewClient(webView){
            override fun shouldInterceptRequest(
                view: WebView,
                webViewRequest: WebViewRequest
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, webViewRequest)
            }
        }
        webView.addJavascriptInterface(YouTubePlayerBridge(), "YouTubePlayerBridge")
//        webView.loadUrl("https://www.youtube.com/embed/78ASlB64rA4")
//        webView.loadUrl("https://www.youtube.com/embed/78ASlB64rA4?autoplay=1") // 能触发 v1/player，接口里也有 poToken，但还是受到 LOGIN_REQUIRED 限制

        webView.loadDataWithBaseURL("https://www.youtube.com", getPlayHtml(), "text/html", "utf-8", null)
    }

    private fun getPlayHtml(): String {
//        return PLAYER_HTML
        return getResources()
            .openRawResource(R.raw.youtube_player)
            .source()
            .buffer()
            .readString(Charset.forName("UTF-8"));
    }

    override fun getLayoutId() = R.layout.web_view_headless

    class YouTubePlayerBridge {

        @JavascriptInterface
        fun onReady() {
        }

        @JavascriptInterface
        fun onStateChange(state: String?) {
        }

        @JavascriptInterface
        fun onPlaybackQualityChange(quality: String?) {
        }

        /**
         * The default playback rate is 1, which indicates that the video is playing at normal speed. Playback rates may include values like 0.25, 0.5, 1, 1.5, and 2.
         * <br></br><br></br>TODO add constants
         * @param rate 0.25, 0.5, 1, 1.5, 2
         */
        @JavascriptInterface
        fun onPlaybackRateChange(rate: String) {
        }

        @JavascriptInterface
        fun onError(error: String?) {
        }

        @JavascriptInterface
        fun onApiChange() {
        }

        @JavascriptInterface
        fun currentSeconds(seconds: String) {
        }

        @JavascriptInterface
        fun onVideoTitle(videoTitle: String?) {
        }

        @JavascriptInterface
        fun onVideoId(videoId: String?) {
        }

        @JavascriptInterface
        fun onVideoDuration(seconds: String?) {
        }

        @JavascriptInterface
        fun onLog(message: String?) {
        }

        @JavascriptInterface
        fun customAjax(ID: String, body: String?) {
            Log.d("111", "customAjax: $ID")
        }
    }
}