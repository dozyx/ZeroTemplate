package cn.dozyx.template.view.recyclerview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.tese_item_animator.*
import timber.log.Timber
import java.util.*

class ItemAnimatorTest : BaseActivity() {
    private lateinit var adapter: CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_remove.setOnClickListener {
            val item = adapter.getItem(0)
            item.status = "finish"
            adapter.notifyItemChanged(0)
            adapter.notifyDataSetChanged()
            // notifyItemRemoved 之前调用 notifyDataSetChanged 会导致动画无效
            // notifyItemRemoved 之前调用 notifyItemChanged 通知 item 有更新，实际上也不会更新。。。

            val viewHolder = rv.findViewHolderForAdapterPosition(0)
//            (viewHolder as? BaseViewHolder)?.setText(android.R.id.text1, "error") // 会导致动画有一个突变的过程。。

//            adapter.data.removeAt(0)
//            adapter.notifyItemRemoved(0)
            Handler().post {
                // notifyItemRemoved 之前调用 notifyDataSetChanged，可以在 remove 之前正常刷新。
                // 而如果调用的是 notifyItemChanged 而不是 notifyDataSetChanged，那么整个列表的刷新会有点奇怪，
                // 会出现一些 item 重叠，应该 notifyItemChanged 是个动画过程有关系。
                adapter.data.removeAt(0)
                adapter.notifyItemRemoved(0)
            }
            Handler().postDelayed( {
//                adapter.data.removeAt(0)
//                adapter.notifyItemRemoved(0)
            }, 500)
//            adapter.notifyDataSetChanged()
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
//            adapter.notifyItemChanged(0)
//            adapter.removeAt(0)
        }
        adapter = CustomAdapter().also {
            it.data = mutableListOf(
                StatusString("text1", "start"),
                StatusString("text2", "start"),
                StatusString("text3", "start")
            )
        }
        rv.adapter = adapter
//        rv.itemAnimator = FlyAnimator()
//        rv.itemAnimator = DefaultItemAnimator()
//        rv.itemAnimator = CustomItemAnimator()
    }

    override fun getLayoutId() = R.layout.tese_item_animator

    private class CustomAdapter :
        BaseQuickAdapter<StatusString, BaseViewHolder>(R.layout.item_text) {
        override fun convert(holder: BaseViewHolder, item: StatusString) {
            Timber.d("CustomAdapter.convert ${holder.adapterPosition}")
            holder.setText(android.R.id.text1, item.toString())
        }
    }
}

class CustomItemAnimator : RecyclerView.ItemAnimator() {
    override fun animateDisappearance(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun animateAppearance(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo?,
        postLayoutInfgo: ItemHolderInfo
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun animatePersistence(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun runPendingAnimations() {
        TODO("Not yet implemented")
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        TODO("Not yet implemented")
    }

    override fun endAnimations() {
        TODO("Not yet implemented")
    }

    override fun isRunning(): Boolean {
        TODO("Not yet implemented")
    }
}

data class StatusString(val text: String, var status: String)

class FlyAnimator : SimpleItemAnimator() {
    private var removeHolders: MutableList<RecyclerView.ViewHolder> = ArrayList()
    private var removeAnimators: MutableList<RecyclerView.ViewHolder> = ArrayList()
    private var moveHolders: MutableList<RecyclerView.ViewHolder> = ArrayList()
    private var moveAnimators: MutableList<RecyclerView.ViewHolder> = ArrayList()
    private var removeDur: Long = 5000
    private var moveDur: Long = 5000
    fun setRemoveDur(removeDur: Long) {
        this.removeDur = removeDur
    }

    fun setMoveDur(moveDur: Long) {
        this.moveDur = moveDur
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        removeHolders.add(holder)
        return true
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        holder.itemView.translationY = (fromY - toY).toFloat()
        moveHolders.add(holder)
        return true
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
        return false
    }

    override fun runPendingAnimations() {
        if (!removeHolders.isEmpty()) {
            for (holder in removeHolders) {
                remove(holder)
            }
            removeHolders.clear()
        }
        if (!moveHolders.isEmpty()) {
            for (holder in moveHolders) {
                move(holder)
            }
            moveHolders.clear()
        }
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {}
    override fun endAnimations() {}
    override fun isRunning(): Boolean {
        return !(removeHolders.isEmpty() && removeAnimators.isEmpty() && moveHolders.isEmpty() && moveAnimators.isEmpty())
    }

    private fun remove(holder: RecyclerView.ViewHolder) {
        removeAnimators.add(holder)
        val animation = TranslateAnimation(
            0F,
            (-holder.itemView.width).toFloat(), 0F, 0F
        )
        animation.duration = removeDur
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                dispatchRemoveStarting(holder)
            }

            override fun onAnimationEnd(animation: Animation) {
                removeAnimators.remove(holder)
                dispatchRemoveFinished(holder)
                if (!isRunning) {
                    dispatchAnimationsFinished()
                }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        holder.itemView.startAnimation(animation)
    }

    private fun move(holder: RecyclerView.ViewHolder) {
        moveAnimators.add(holder)
        val animator = ObjectAnimator.ofFloat(
            holder.itemView,
            "translationY", holder.itemView.translationY, 0f
        )
        animator.duration = moveDur
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                dispatchMoveStarting(holder)
            }

            override fun onAnimationEnd(animation: Animator) {
                dispatchMoveFinished(holder)
                moveAnimators.remove(holder)
                if (!isRunning) {
                    dispatchAnimationsFinished()
                }
            }
        })
        animator.start()
    }
}