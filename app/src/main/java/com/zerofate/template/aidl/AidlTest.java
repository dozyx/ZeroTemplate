package com.zerofate.template.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.zerofate.template.R;
import com.zerofate.template.util.Constants;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AidlTest extends AppCompatActivity {

    private static final String TAG = "AidlTest";
    @BindView(R.id.bind)
    Button mTestBtn;
    private IHello mService;
    private boolean mIsBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: binder == " + service);
            mService = IHello.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(AidlTest.this, "disconnected", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bind)
    void bind() {
        if (!mIsBound) {
            bindService(new Intent(this, MyService.class), mConnection,
                    BIND_AUTO_CREATE);
            mIsBound = true;
            Toast.makeText(this, "绑定服务", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.unbind)
    void unbind() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
            Toast.makeText(this, "解绑服务", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.hello)
    void hello() {
        if (mService != null) {
            Random random = new Random();
            try {
                mService.hello(Constants.sMessages[random.nextInt(Constants.sMessages.length)]);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.reply)
    void reply() {
        if (mService != null) {
            try {
                Toast.makeText(this, mService.getReply(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
