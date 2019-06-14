package cn.dozyx.template.practice;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 将 ListView 设置为不滚动，解决 ScrollView 嵌套 ListView 导致ListView只显示一项的问题
 */

public class InScrollListView extends ListView {
    public InScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 也可以修改为固定高度
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
