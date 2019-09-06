package cn.dozyx.template;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Create by timon on 2019/9/6
 **/
public class DelayTextWatcher implements TextWatcher {
    private TextWatcher textWatcher;
    private long lastNotifyTimeInMillis = -1;
    private static final int TRIGGER_INTERVAL_IN_MILL = 1000;

    public DelayTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean shouldTrigger =
                currentTimeMillis - lastNotifyTimeInMillis > TRIGGER_INTERVAL_IN_MILL
                        || lastNotifyTimeInMillis == -1;
        lastNotifyTimeInMillis = currentTimeMillis;
        if (!shouldTrigger) {
            return;
        }
        if (textWatcher != null) {
            textWatcher.afterTextChanged(s);
        }
    }
}
