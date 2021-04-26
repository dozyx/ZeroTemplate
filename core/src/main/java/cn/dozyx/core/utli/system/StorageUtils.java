package cn.dozyx.core.utli.system;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StorageUtils {

    /**
     * 反射获取挂载的存储路径
     */
    public static List<String> getMountedVolumeList(Context context) {
        List<String> pathList = new ArrayList<>();
        try {
            StorageManager storageManager = ((StorageManager) context.getSystemService(Activity.STORAGE_SERVICE));
            if (storageManager == null) {
                return pathList;
            }
            List<StorageVolume> volumes = getStorageVolumes(storageManager);
            Method getVolumeState = storageManager.getClass().getMethod("getVolumeState", String.class);
            if (volumes != null && volumes.size() > 0) {
                Object sdVolume = volumes.get(0);
                Method getPath = sdVolume.getClass().getMethod("getPath", (Class[]) null);
                for (Object volume : volumes) {
                    sdVolume = volume;
                    String path = (String) getPath.invoke(sdVolume, (Object[]) null);
                    if (Environment.MEDIA_MOUNTED.equals(getVolumeState.invoke(storageManager,
                            path))) {
                        pathList.add(path);
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return pathList;
    }

    /**
     * SD 卡被卸载之后不会保存在 List 中
     */
    @Nullable
    private static List<StorageVolume> getStorageVolumes(StorageManager storageManager) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<StorageVolume> volumes = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            volumes = storageManager.getStorageVolumes();
        } else {
            Method methodGetVolumeList = storageManager.getClass().getMethod("getVolumeList", (Class[]) null);
            if (methodGetVolumeList != null) {
                StorageVolume[] sdVolumes = (StorageVolume[]) methodGetVolumeList.invoke(storageManager, (Object[]) null);
                if (sdVolumes != null) {
                    volumes = new ArrayList<>();
                    Collections.addAll(volumes, sdVolumes);
                }
            }
        }
        return volumes;
    }

    private StorageUtils() {
    }
}
