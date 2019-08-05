package cn.dozyx.template.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

import cn.dozyx.template.R
import timber.log.Timber

class ViewLifeActivity : LifeCycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_life)
    }

    class ViewLifeView : View {
        constructor(context: Context) : super(context) {
            Timber.d("ViewLifeView.constructor1")
        }

        constructor(context: Context,
                    attrs: AttributeSet?) : super(context, attrs) {
            Timber.d("ViewLifeView: constructor2")
        }

        constructor(context: Context,
                    attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
            Timber.d("ViewLifeView: constructor3")
        }

        override fun onSaveInstanceState(): Parcelable? {
            Timber.d("onSaveInstanceState: ")
            return super.onSaveInstanceState()
        }

        override fun onFinishInflate() {
            super.onFinishInflate()
            Timber.d("onFinishInflate: ")
        }

        override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            Timber.d("onAttachedToWindow: ")
        }

        override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
            super.onLayout(changed, left, top, right, bottom)
            Timber.d("onLayout: ")
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            Timber.d("onMeasure: ")
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            Timber.d("onDraw: ")
        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            Timber.d("onDetachedFromWindow: ")
        }

        override fun onFocusChanged(gainFocus: Boolean, direction: Int,
                                    previouslyFocusedRect: Rect?) {
            super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
            Timber.d("onFocusChanged: $gainFocus")
        }

        override fun onRestoreInstanceState(state: Parcelable) {
            super.onRestoreInstanceState(state)
            Timber.d("onRestoreInstanceState: ")
        }
    }

    class LifeViewLinearLayout : LinearLayout {
        constructor(context: Context) : super(context) {
            Timber.d("LifeViewGroup: 1")
        }

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
            Timber.d("LifeViewGroup: 2")
        }

        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
            Timber.d("LifeViewGroup: 3")
        }

        override fun onSaveInstanceState(): Parcelable? {
            Timber.d("onSaveInstanceState: view group")
            return super.onSaveInstanceState()
        }

        override fun onRestoreInstanceState(state: Parcelable) {
            super.onRestoreInstanceState(state)
            Timber.d("onRestoreInstanceState: view group")
        }
    }
}
