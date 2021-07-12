package cn.dozyx.template.view.recyclerview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.ex.dp
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
        btn_remove_update.setOnClickListener {
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
            Handler().postDelayed({
//                adapter.data.removeAt(0)
//                adapter.notifyItemRemoved(0)
            }, 500)
//            adapter.notifyDataSetChanged()
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
//            adapter.notifyItemChanged(0)
//            adapter.removeAt(0)
        }
        btn_remove.setOnClickListener {
//            adapter.removeAt(0)
            adapter.data.removeAt(0)
            adapter.notifyItemRemoved(0)
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
        rv.itemAnimator = TopFadeOutItemAnimator()
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

class TopFadeOutItemAnimator : SimpleItemAnimator() {

    private val mPendingRemovals = ArrayList<RecyclerView.ViewHolder>()// 记录将要动画的 item 信息
    private var mRemoveAnimations = ArrayList<RecyclerView.ViewHolder>()// 记录开始动画的 item 信息
    private val mPendingMoves = ArrayList<RecyclerView.ViewHolder>()
    private var mMoveAnimations = ArrayList<RecyclerView.ViewHolder>()
    private val duration = 2000L

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        Timber.d("CustomItemAnimator.animateAdd")
        return false
    }

    override fun runPendingAnimations() {
        Timber.d("CustomItemAnimator.runPendingAnimations")
        if (mPendingRemovals.isEmpty() && mPendingMoves.isEmpty()) {
            return
        }
        for (holder in mPendingRemovals) {
            animRemoveImpl(holder)
        }
        mPendingRemovals.clear()

        for (holder in mPendingMoves) {
            animMoveImpl(holder)
        }
        mPendingMoves.clear()
    }

    private fun animMoveImpl(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        view.animate().translationY(0f)
        val animation = view.animate()
        mMoveAnimations.add(holder)
        animation.setDuration(duration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animator: Animator) {
                dispatchMoveStarting(holder)
            }

            override fun onAnimationCancel(animator: Animator) {
                view.translationY = 0f
            }

            override fun onAnimationEnd(animator: Animator) {
                animation.setListener(null)
                dispatchMoveFinished(holder)
                mMoveAnimations.remove(holder)
                dispatchFinishedWhenDone()
            }
        }).start()
    }

    private fun dispatchFinishedWhenDone() {
        if (!isRunning) {
            dispatchAnimationsFinished()
        }
    }

    private fun animRemoveImpl(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        val animation = view.animate()
        mRemoveAnimations.add(holder)
        animation.setDuration(duration).alpha(0f).scaleX(0.6F).scaleY(0.6F)
            .translationYBy((-96).dp).setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animator: Animator) {
                        dispatchRemoveStarting(holder)
                    }

                    override fun onAnimationEnd(animator: Animator) {
                        animation.setListener(null)
                        view.alpha = 1f
                        dispatchRemoveFinished(holder)
                        mRemoveAnimations.remove(holder)
                        dispatchFinishedWhenDone()
                    }
                }).start()
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        Timber.d("CustomItemAnimator.animateMove ${holder.absoluteAdapterPosition}")
        // 虽然只是实现移除动画，但还是需要实现 move，要不然移除之后，其他 item 会直接没有动画地变换位置
        val view = holder.itemView
        val deltaY = toY - fromY
        if (deltaY == 0) {
            dispatchMoveFinished(holder)
            return false
        }
        if (deltaY != 0) {
            view.translationY = -deltaY.toFloat()// 维持在原来的位置，通过动画移入新位置
        }
        mPendingMoves.add(holder)
        return false
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
        Timber.d("CustomItemAnimator.animateChange")
        return false
    }

    override fun isRunning(): Boolean {
        // 当前是否有动画正在运行
        return !(mPendingRemovals.isEmpty() && mPendingMoves.isEmpty() && mRemoveAnimations.isEmpty() && mMoveAnimations.isEmpty())
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        Timber.d("CustomItemAnimator.endAnimation")
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        Timber.d("CustomItemAnimator.animateRemove ${holder.absoluteAdapterPosition}")
        mPendingRemovals.add(holder)
        return true
    }

    override fun endAnimations() {
        Timber.d("CustomItemAnimator.endAnimations")

    }
}

data class StatusString(val text: String, var status: String)

/**
 * 移除时向左移出
 */
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