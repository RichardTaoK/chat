package org.han.zzchat.aty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import org.han.zzchat.R;

public class AtyWelcome extends AppCompatActivity{

    private static final int DELAY = 2000;
    private static final int GO_GUIDE=0;
    private static final int GO_HOME=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_welcome);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initLoad();
    }

    /**
     * 引导页只在首次开启时显示
     */
    private void initLoad() {
        //SharedPreferences只能保存简单类型的数据,表示创建一个文件夹，一般会将复杂类型的数据转换成Base64编码，然后将转换后的数据以字符串的形式保存在 XML文件中，再用SharedPreferences保存。
        SharedPreferences sharedPreferences=getSharedPreferences("zzchat",MODE_PRIVATE);
        boolean welcome=sharedPreferences.getBoolean("welcome",true);
        Log.i("han", String.valueOf(welcome));
        if(!welcome){
            handler.sendEmptyMessageDelayed(GO_HOME,DELAY);
            Log.i("han","home");//false
        }else{
            Log.i("han","guide");
            handler.sendEmptyMessageDelayed(GO_GUIDE,DELAY);

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("welcome",false);
            editor.apply();
        }
    }
    public void goHome(){
        Intent intent =new Intent(AtyWelcome.this,AtyLoginOrRegister.class);
        startActivity(intent);
        Log.i("aa","12");
        finish();
    }

    public void goGuide(){
        Intent intent =new Intent(AtyWelcome.this,AtyGuide.class);
        startActivity(intent);
        finish();

    }

    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_GUIDE:{
                    goGuide();
                    break;
                }
                case GO_HOME:{
                    goHome();
                    break;
                }
                default:
                    break;
            }
        }
    };

}
