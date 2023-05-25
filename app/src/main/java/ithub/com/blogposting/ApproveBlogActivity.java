package ithub.com.blogposting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Random;

public class ApproveBlogActivity extends AppCompatActivity {

    EditText et_code_title, et_full_code;
    String  userName, blog_id;
    DatabaseReference allblog_dbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_blog);

        et_code_title = findViewById(R.id.et_code_title);
        et_full_code = findViewById(R.id.et_code);

        ////////////////////////////// firebase db con
        allblog_dbref = FirebaseDatabase.getInstance().getReference("All_blog");
        //////////////////////////////

        ////////////////////////////// get data from prev activity
        blog_id = getIntent().getStringExtra("id");
        et_code_title.setText(getIntent().getStringExtra("code_title"));
        et_full_code.setText(getIntent().getStringExtra("code"));
        userName = getIntent().getStringExtra("user_name");
        //////////////////////////////
    }

    ////////////////////////////// update approve field
    public void methodUpdate(View view) {

        allblog_dbref.child(blog_id).child("approve").setValue("approve");
        FirebaseDatabase notify_dbref = FirebaseDatabase.getInstance();
        notify_dbref.getReference("Blog_notification").child("notifyBy").setValue(userName);
        notify_dbref.getReference("Blog_notification").child("key").setValue((new Random().nextInt())+"");

        onBackPressed();
        Toast.makeText(this, "updated successfully", Toast.LENGTH_SHORT).show();
    }
    //////////////////////////////
}
