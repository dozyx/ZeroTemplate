package cn.dozyx.template.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import cn.dozyx.template.notification.NotificationTest;

public class MyService extends Service {
    private static final String TAG = "MyService";

    private IHello.Stub mBinder = new IHello.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                double aDouble,
                String aString) throws RemoteException {
        }

        @Override
        public void hello(final String msg) throws RemoteException {
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyService.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public String getReply() throws RemoteException {
            return "服务端：你好";
        }
    };

    private Handler mUiHandler = new Handler();

    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        startActivity(new Intent(this, NotificationTest.class));
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: binder == " + mBinder);
        return mBinder;
    }

}
