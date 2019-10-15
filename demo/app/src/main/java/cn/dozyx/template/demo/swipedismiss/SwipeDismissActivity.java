package cn.dozyx.template.demo.swipedismiss;

import cn.dozyx.core.base.BaseActivity;
import cn.dozyx.template.R;

/**
 * 实现类似于微信大图页面滑动关闭
 * 思路1: CoordinatorLayout.Behavior
 * @author dozyx
 * @date 2019-10-15
 */
public class SwipeDismissActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_swipe_dismiss;
    }
}
