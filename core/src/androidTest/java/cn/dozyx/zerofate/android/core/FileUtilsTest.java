package cn.dozyx.zerofate.android.core;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import cn.dozyx.core.utli.FileUtils;

@RunWith(AndroidJUnit4.class)
public class FileUtilsTest {

    @Test
    public void testGetNewUniqueFile() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        File cacheDir = context.getExternalCacheDir();

        File file = new File(cacheDir, "1.txt");
        if (file.exists()) {
            file.delete();
        }
        Assert.assertEquals("1.txt", FileUtils.getUniqueNewFile(file).getName());
        file.createNewFile();
        Assert.assertEquals("1_1.txt", FileUtils.getUniqueNewFile(file).getName());

        File file2 = new File(cacheDir, ".txt");
        if (file2.exists()) {
            file2.delete();
        }
        Assert.assertEquals(".txt", FileUtils.getUniqueNewFile(file2).getName());
        file2.createNewFile();
        Assert.assertEquals("_1.txt", FileUtils.getUniqueNewFile(file2).getName());
    }
}
