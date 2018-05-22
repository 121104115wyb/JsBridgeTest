package com.btzh.jsbridgetest;

import android.text.TextUtils;
import android.util.Log;

import com.btzh.jsbridge.BridgeHandler;
import com.btzh.jsbridge.CallBackFunction;


/**
 * Created by liuw on 2018/3/6.
 * 自定义Handler回调
 */

class MyHandlerCallBack implements BridgeHandler {
    private OnSendDataListener mSendDataListener;

    public MyHandlerCallBack(OnSendDataListener mSendDataListener){
        this.mSendDataListener = mSendDataListener;
    }

    @Override
    public void handler(String data, CallBackFunction function) {
        Log.e("liuw","接收数据为：" + data);
        if (!TextUtils.isEmpty(data) && mSendDataListener != null) {
            mSendDataListener.sendData(data);
        }
        function.onCallBack("Native已收到消息！");
    }

    public interface OnSendDataListener {
        void sendData(String data);
    }
}
