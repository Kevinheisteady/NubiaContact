package com.kevin.test.messengertset;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private Messenger mService;
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());
    public static final String TAG = "MainActivity";
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyContents.MSG_FROM_SERVICE:
                    Log.i(TAG, "receive msg from service"+msg.getData());
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            Message msg = Message.obtain(null,MyContents.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg","hello,this is client");
            msg.setData(data);

            msg.replyTo = mGetReplyMessenger;
            try{
                mService.send(msg);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,MessengerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String name = tm.getNetworkOperatorName();
        Log.d(TAG, "networkName"+name);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
