package cn.dozyx.template.demo.swipedismiss;

import cn.dozyx.core.base.BaseActivity;
import cn.dozyx.template.R;

/**
 * 实现类似于微信大图页面滑动关闭
 * 思路1: CoordinatorLayout.Behavior，为单个子 View 指定拖动关闭隐藏行为，并在隐藏后回调关闭 Activity。好像没必要用这种，因为隐藏的是整个 Activity，所以可以直接操作父布局
 * 思路2: 自定义一个 FrameLayout，实现拖动收缩隐藏。
 * @author dozyx
 * @date 2019-10-15
 */
public class DragDismissActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_swipe_dismiss;
    }
}
