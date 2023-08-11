package cn.dozyx.template.view

import android.content.Context
import android.graphics.drawable.ColorStateListDrawable
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R

class StyleTest : BaseActivity() {
    override fun getLayoutId() = R.layout.test_style

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // application 的 context 并没有应用到 manifest 标签下面的 theme
//        LayoutInflater.from(this.applicationContext).inflate(R.layout.test_style, null)
    }
}

class CustomStyleView : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, R.attr.CustomStyleViewStyle)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        // attrs 包含了构建时声明的属性
        // 如果 defStyleAttr 存在，则 defStyleRes 不会生效
        val a = context?.obtainStyledAttributes(attrs, R.styleable.CustomStyleView, defStyleAttr, R.style.CustomStyleView)
        // a.indexCount 是 R.styleable.CustomStyleView 中有使用的属性的数量
        // 也就是说，obtainStyledAttributes 是获取 R.styleable.CustomStyleView 属性的值
        val colorStateList = a?.getColorStateList(R.styleable.CustomStyleView_custom_style_view_bg)
        // getColorStateList 如果属性指向的是一个 ?attr/xxx，而这个 attr 没有在 theme 中存在，则会触发 UnsupportedOperationException: Failed to resolve attribute at index 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                background = colorStateList?.let { ColorStateListDrawable(it) }
            }
        }
        a?.recycle()

    }


}