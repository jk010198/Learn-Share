package ithub.com.blogposting;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent in= getIntent();

        boolean exitcode = in.getBooleanExtra("exit_code",false);
        if(exitcode)
        {
            finish();
            System.exit(0);
        }
        //////////////////////////////////////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                //Intent intent= new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        System.exit(0);
    }
}