package cn.dozyx.core.widget;

/**
 * Create by dozyx on 2019/8/21
 **/

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

/**
 * 参考：https://github.com/samlu/ConstraintRadioGroup
 * 源码基本来自上面的参考，但因为对原来的代码风格不太习惯所以做了修改，并对一些问题做了修改
 */
@SuppressWarnings("unused")
public final class ConstraintRadioGroup extends ConstraintHelper {

    public static final int NO_ID = -1;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(ConstraintRadioGroup rg, @IdRes int nCheckedId);
    }

    private OnCheckedChangeListener onCheckedChangeListener;
    private final ArrayList<RadioButton> radioButtons = new ArrayList<>();
    /**
     * 避免 RadioButton check 状态变化时陷入死循环
     */
    private boolean isSkipCheckingViewsRecursively = false;
    private int checkedId = NO_ID;
    private int checkedIdBeforePreLayout = NO_ID;
    /**
     * 设置给每个 RadioButton 的监听器
     */
    private final CompoundButton.OnCheckedChangeListener checkedChangeListener =
            (radioButton, checked) -> {
                if (isSkipCheckingViewsRecursively) {
                    return;
                }
                if (checkedId != NO_ID) {
                    // uncheck the checked button
                    isSkipCheckingViewsRecursively = true;
                    for (RadioButton v : radioButtons) {
                        if (v.getId() == checkedId) {
                            v.setChecked(false);
                            break;
                        }
                    }
                    isSkipCheckingViewsRecursively = false;
                }

                setCheckedId(radioButton.getId());
            };


    public ConstraintRadioGroup(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    public ConstraintRadioGroup(Context ctx, AttributeSet attrs, int defStyleAttr) {
        super(ctx, attrs, defStyleAttr);
    }

    @Override
    protected void init(AttributeSet attrs) {
        super.init(attrs);
        mUseViewMeasure = false;
    }

    @Override
    public void updatePreLayout(ConstraintLayout container) {
        // 不能直接调用 super 来实现 setIds，因为 super 方法中会依赖 mHelperWidget!=null，但因为子类没有对其赋值，会导致它为null
        if (mReferenceIds != null) {
            setIds(mReferenceIds);
        }

        for (int i = 0; i < mCount; i++) {
            int nId = mIds[i];
            View v = container.getViewById(nId);
            if (v instanceof RadioButton) {
                radioButtons.add((RadioButton) v);
                ((RadioButton) v).setOnCheckedChangeListener(checkedChangeListener);
            }
        }

        if (checkedIdBeforePreLayout != NO_ID) {
            check(checkedIdBeforePreLayout);
        }
    }

    @Override
    public void updatePostLayout(ConstraintLayout container) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = 0;
        params.height = 0;

        super.updatePostLayout(container);
    }

    public void clearCheck() {
        if (checkedId != NO_ID) {
            // uncheck the checked button
            isSkipCheckingViewsRecursively = true;
            for (RadioButton v : radioButtons) {
                if (v.getId() == checkedId) {
                    v.setChecked(false);
                    break;
                }
            }
            isSkipCheckingViewsRecursively = false;

            setCheckedId(NO_ID);
        }
    }

    /**
     * @return selected RadioButton id, -1 if no checked button
     */
    @IdRes
    public int getCheckedId() {
        return checkedId;
    }

    /**
     * @param radioButtonId set it as checked
     */
    public void check(@IdRes int radioButtonId) {
        final boolean isBeforePreLayout = radioButtons.isEmpty();
        checkedIdBeforePreLayout = (isBeforePreLayout ? radioButtonId : NO_ID);

        boolean found = false;
        isSkipCheckingViewsRecursively = true;
        for (RadioButton v : radioButtons) {
            if (v.getId() == radioButtonId) {
                v.setChecked(true);
                found = true;
            } else {
                v.setChecked(false);
            }
        }
        isSkipCheckingViewsRecursively = false;

        if (!isBeforePreLayout) {
            setCheckedId(found ? radioButtonId : NO_ID);
        }
    }

    /**
     * <p>Register a callback to be invoked when the checked radio button
     * changes in this group.</p>
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(@Nullable OnCheckedChangeListener listener) {
        onCheckedChangeListener = listener;
    }

    private void setCheckedId(int id) {
        if (checkedId != id) {
            checkedId = id;

            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, id);
            }
        }
    }
}
