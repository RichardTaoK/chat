package org.han.zzchat.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;


import org.han.zzchat.R;

public class AtyLoginOrRegister extends AppCompatActivity implements View.OnClickListener{
    private TabHost tabHost;

    private Button btnLogin;
    private EditText etLoginUsername;
    private EditText etLoginPassword;

    private Button btnRegister;
    private EditText etRegisterUsername;
    private EditText etRegisterPassword;
    private EditText etInsurePassword;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login_or_register);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();
    }

    private void initViews() {
        tabHost= (TabHost) findViewById(R.id.tabHost);
        btnLogin= (Button) findViewById(R.id.btn_login);
        etLoginUsername = (EditText) findViewById(R.id.et_login_username);
        etLoginPassword= (EditText) findViewById(R.id.et_login_password);

        btnRegister= (Button) findViewById(R.id.btn_register);
        etRegisterPassword= (EditText) findViewById(R.id.et_register_password);
        etRegisterUsername= (EditText) findViewById(R.id.et_register_username);
        etInsurePassword= (EditText) findViewById(R.id.et_insure_password);

        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("Login").setIndicator("Login").setContent(R.id.layout_login));
        tabHost.addTab(tabHost.newTabSpec("Register").setIndicator("Register").setContent(R.id.layout_register));

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:{
                Log.i("han","1");
                Intent intent =new Intent(this,AtyMain.class);
                startActivity(intent);
                Log.i("han","2");
                finish();
                break;
            }
            case R.id.btn_register:{
                Intent intent =new Intent(this,AtyMain.class);
                startActivity(intent);
                finish();
                break;
            }
            default:
                break;
        }
    }
}
