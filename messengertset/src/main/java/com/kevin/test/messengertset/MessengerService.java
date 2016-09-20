package com.kevin.test.messengertset;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Kevin-He on 2016/9/6.
 */
public class MessengerService extends Service {


    private static class MessengerHandler extends Handler{
        private static final String TAG = "MessengerHandler" ;

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MyContents.MSG_FROM_CLIENT:
                    Log.i(TAG, "handleMessage: "+msg.getData());
                    //Log.i(TAG, "handleMessage: "+msg.obj);
                    Messenger client = msg.replyTo;
                    Message replyMsg = Message.obtain(null,MyContents.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","收到了客户端的消息！");
                    replyMsg.setData(bundle);
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    private  final Messenger mMessenger = new Messenger(new MessengerHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
