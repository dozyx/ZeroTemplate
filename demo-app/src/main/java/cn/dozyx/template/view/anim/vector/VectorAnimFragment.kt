package cn.dozyx.template.view.anim.vector

import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import cn.dozyx.template.R
import cn.dozyx.template.view.anim.BaseAnimDemoFragment

/**
 * Create by timon on 2019/7/18
 */
class VectorAnimFragment : BaseAnimDemoFragment<ImageView>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(context!!, R.drawable.anim_vector)
        animView1.setImageDrawable(animatedVectorDrawableCompat)
        (animView1.drawable as Animatable).start()
    }

    override fun getAnimView(): ImageView = ImageView(context)
}
