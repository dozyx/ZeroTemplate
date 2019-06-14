package cn.dozyx.template.design;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.behavior.SwipeDismissBehavior;
import cn.dozyx.template.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeDismissBehaviorDemo extends AppCompatActivity {

    @BindView(R.id.swipe_dismiss_text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_dismiss_behavior_demo);
        ButterKnife.bind(this);
        SwipeDismissBehavior behavior = new SwipeDismissBehavior();
        behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);
        ((CoordinatorLayout.LayoutParams) textView.getLayoutParams()).setBehavior(new
                SwipeDismissBehavior<>());
    }
}
