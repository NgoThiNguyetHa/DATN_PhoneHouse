package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

public class HelloScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_screen);
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HelloScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
        fullScreen();
    }
    private void fullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null){
                controller.hide(WindowInsets.Type.statusBars());
            }
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.xanhduong));
        }else{
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}