package com.btzh.jsbridgetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.Toast;

import com.btzh.jsbridge.BridgeHandler;
import com.btzh.jsbridge.BridgeWebView;
import com.btzh.jsbridge.CallBackFunction;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private BridgeWebView elevatorWb;

    String defaultUrl = "http://192.168.0.168:8100/#/special";
    //String defaultUrl = "http://192.168.0.147:80/jsbridgetest.html";
    private JSONObject sendData;
    private Button sendDataBtn;

    private static final String TAG = "MainActivity";
    private MyHandlerCallBack.OnSendDataListener mOnSendDataListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        elevatorWb = (BridgeWebView)findViewById(R.id.elevatorWb);
        sendDataBtn = findViewById(R.id.sendData);
        initBgWebview();


        sendDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //accessTokenHandler;  SendJsData
                elevatorWb.callHandler("SendJsData", "测试发送消息", new CallBackFunction() {
                    @Override
                    public void onCallBack(String s) {
                        if (TextUtils.isEmpty(s)) {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "handler: ----error:" + s);
                        } else {
                            Log.d(TAG, "handler: ----success:" + s);
                        }
                    }
                });
            }
        });

        mOnSendDataListener = new MyHandlerCallBack.OnSendDataListener() {
            @Override
            public void sendData(String data) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "sendData:--- "+data);
               // mEditText.setText("通过webview发消息接收到数据：\n" + data);
            }
        };
    }

    void initBgWebview() {
        sendData = new JSONObject();
        try {
            //网格员所在的网格编号
            sendData.put("WGBH", "11111111111111111");
            //网格员登录编号
            sendData.put("USERID", "1009");
            //网格员的工作网格
            sendData.put("WGMC", "测试网格名称");
            //网格员名称
            sendData.put("USERNAME", "测试用户名");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        elevatorWb.setDefaultHandler(new MyHandlerCallBack(mOnSendDataListener));
        WebSettings webSettings = elevatorWb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        elevatorWb.loadUrl(defaultUrl);
        //elevatorWb.
        //authorizdInfoHandler; ReceiveJsData
        elevatorWb.registerHandler("ReceiveJsData", new BridgeHandler() {
            @Override
            public void handler(String s, CallBackFunction callBackFunction) {
                if (TextUtils.isEmpty(s)) {
                    Log.d(TAG, "handler: ----error:" + s);
                } else {
                    Log.d(TAG, "handler: ----success:" + s);
                    callBackFunction.onCallBack("Android 接收成功");
                }
            }
        });
    }



}
