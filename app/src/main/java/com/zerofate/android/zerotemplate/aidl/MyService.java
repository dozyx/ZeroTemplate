package com.zerofate.android.zerotemplate.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    private MyAidlImpl mService;

    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mService = new MyAidlImpl();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mService;
    }

    private static class MyAidlImpl extends IMyAidlInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                double aDouble,
                String aString) throws RemoteException {

        }

        @Override
        public String getResult() throws RemoteException {
            return "成功获得数据";
        }
    }
}
