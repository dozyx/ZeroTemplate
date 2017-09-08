package com.zerofate.android.zerotemplate.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.zerofate.android.zerotemplate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AidlTest extends AppCompatActivity {

    @BindView(R.id.bind)
    Button mTestBtn;
    private IMyAidlInterface mService;
    private boolean mIsBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IMyAidlInterface.Stub.asInterface(service);
            if (mService != null) {
                try {
                    mTestBtn.setText(mService.getResult());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Toast.makeText(AidlTest.this, "connected", Toast.LENGTH_SHORT).show();
            }
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
        mIsBound = bindService(new Intent(this, MyService.class), mConnection, BIND_AUTO_CREATE);
    }

    @OnClick(R.id.unbind)
    void unbind() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
            mService = null;
            mTestBtn.setText("已unbind");
        }
    }
}
