package cn.dozyx.template;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.FrameMetrics;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;


import com.blankj.utilcode.util.ThreadUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String CATEGORY_MY_SAMPLE = "cn.dozyx.zerofate.intent.category.sample";
    private static final String EXTRA_PREFIX_PATH = "prefix";

    private String prefixPath;
    private List<String> paths = new ArrayList<>();
    ListView browseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                Log.d(TAG, "onCreate: ");
                setContentView(R.layout.activity_main);
                browseListView = findViewById(R.id.main_list);
                prefixPath = getIntent().getStringExtra(EXTRA_PREFIX_PATH);
                if (prefixPath == null) {
                    prefixPath = "";
                }
                browseListView.setAdapter(
                        new SimpleAdapter(MainActivity.this, getData(prefixPath), android.R.layout.simple_list_item_1,
                                new String[]{"title"}, new int[]{android.R.id.text1}));
                browseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Map<String, Object> info = (Map<String, Object>) parent.getItemAtPosition(position);
                        Intent intent = (Intent) info.get("intent");
                        startActivity(intent);
                    }
                });
//            }
//        }, 2000);
    }

    private List<Map<String, Object>> getData(String prefix) {
        PackageManager pm = getPackageManager();
        List<Map<String, Object>> data = new ArrayList<>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(CATEGORY_MY_SAMPLE);

        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(mainIntent, 0);
        if (activities == null) {
            return data;
        }

        String[] prefixPaths;
        String prefixWithSlash = prefix;

        if (prefix.equals("")) {
            prefixPaths = null;
        } else {
            prefixPaths = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }

        Map<String, Boolean> entries = new HashMap<>();//过滤重复条目

        for (ResolveInfo info : activities) {
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString() : info.activityInfo.name;

            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {
                String[] labelPaths = label.split("/");
                String nextLabel =
                        prefixPaths == null ? labelPaths[0]
                                : labelPaths[prefixPaths.length];// 前缀的下一段
                if ((prefixPaths != null ? prefixPaths.length : 0) == labelPaths.length - 1) {
                    // 直接启动 Activity
                    addItem(data, nextLabel,
                            activityIntent(info.activityInfo.applicationInfo.packageName,
                                    info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(data, nextLabel, browseIntent(
                                prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }

            }
        }
        Collections.sort(data, sDisplayNameComparator);
        return data;
    }

    private static final Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {

                private final Collator collator = Collator.getInstance();

                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return collator.compare(o1.get("title"), o2.get("title"));
                }
            };

    private void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }


    private Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    private Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        result.putExtra(EXTRA_PREFIX_PATH, path);
        return result;
    }
}
