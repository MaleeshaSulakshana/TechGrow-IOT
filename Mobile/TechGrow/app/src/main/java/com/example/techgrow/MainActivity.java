package com.example.techgrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private Button btnNext;
    private boolean doubleBackToExitPressedOnce = false;
//    private Dialog exitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = this.findViewById(R.id.btnNext);

        btnNext.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_1));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);

            }
        });

//        requestWindowFeature(1);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        setContentView(R.layout.activity_main);

//        Get layouts
//        exitDialog = new Dialog(MainActivity.this);
//        exitDialog.setContentView(R.layout.exit_dialog_box);


//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                    Check is connect network
//                if (isNetworkConnected() == false) {
//                    Toast.makeText(MainActivity.this, "Please turn on wifi or mobile data!", Toast.LENGTH_SHORT).show();
//
////                    Check internet connection
//                }
////                else if (isInternetAvailable() == false) {
////                    Toast.makeText(MainActivity.this, "Please  check your internet connection!", Toast.LENGTH_SHORT).show();
////
////                }
//                else
//                {
//
////                    Navigate to detection activity
//                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }
//        });

    }

//    //    Tap to close app
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        Button btnExitYes, btnExitNo;
//        exitDialog.show();
//
//        btnExitYes = (Button) exitDialog.findViewById(R.id.btnYes);
//        btnExitYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finishAffinity();
//                System.exit(0);
//            }
//        });
//
//        btnExitNo = (Button) exitDialog.findViewById(R.id.btnNo);
//        btnExitNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                exitDialog.dismiss();
//            }
//        });
//
//    }
//
//    //    Check network connection
//    private boolean isNetworkConnected() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
//    }
//
//    //    Check internet connection
//    private boolean isInternetAvailable() {
//        try {
//            if (android.os.Build.VERSION.SDK_INT > 9) {
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//
//                InetAddress ipAddr = InetAddress.getByName("www.google.com");
//                return !ipAddr.equals("");
//            }
//            else {
//                return false;
//            }
//
//        } catch (Exception e) {
//            return false;
//        }
//    }

}