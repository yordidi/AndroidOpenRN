package com.nativeopenrn;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "HomeActivity";
    ActivityResultLauncher<Intent> homeActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button1 = (Button) findViewById(R.id.button_open_main);
        Button button2 = (Button) findViewById(R.id.button_open_my_react);
        Button button3 = (Button) findViewById(R.id.button_open_official_react);
        Button button4 = (Button) findViewById(R.id.button_open_resulted_react);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
        homeActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            String backData = data.getStringExtra("resultData");
                            Log.d(TAG, "onActivityResult: " + backData);
                            Toast.makeText(HomeActivity.this, backData, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 打开rn，并且传递一个路由数据，指定路由
            case R.id.button_open_main:
                Intent intent = new Intent(HomeActivity.this, MainReactActivity.class);
                intent.putExtra("routeName", "settings");
                startActivity(intent);
                break;
            // 仅打开rn，不传递任何数据
            case R.id.button_open_my_react:
                Intent intent2 = new Intent(HomeActivity.this, MyReactActivity.class);
                startActivity(intent2);
                break;
            // 打开官方教程创建的rn，不能兼容react-navigation
            case R.id.button_open_official_react:
                Intent intent3 = new Intent(HomeActivity.this, OfficialReactActivity.class);
                startActivity(intent3);
                break;
            // 打开rn，并且监听返回值
            case R.id.button_open_resulted_react:

                Intent intent4 = new Intent(this, ReactWithResult.class);
                if (homeActivityResultLauncher != null) {
                    homeActivityResultLauncher.launch(intent4);
                }

                break;
        }
    }


}