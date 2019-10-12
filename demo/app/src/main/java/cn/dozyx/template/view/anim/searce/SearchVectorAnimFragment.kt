package cn.dozyx.template.view.anim.searce

import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import cn.dozyx.core.base.BaseFragment
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.vector_fragment_search.*

/**
 * Create by dozyx on 2019/7/19
 **/

class SearchVectorAnimFragment : BaseFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(context!!, R.drawable.anim_vector_search)
                image_anim.setImageDrawable(animatedVectorDrawableCompat)
                (animatedVectorDrawableCompat as Animatable).start()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.vector_fragment_search
    }
}