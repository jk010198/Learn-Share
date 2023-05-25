package ithub.com.blogposting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminBlogDetails extends AppCompatActivity {

    EditText et_code_title, et_full_code, et_blog_status;
    String blog_id, user_id;
    DatabaseReference allblog_dbref;
    DatabaseReference myblog_dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_blog_details);

        et_code_title = findViewById(R.id.blogdetails_et_code_title);
        et_full_code = findViewById(R.id.blogdetails_et_code);
        et_blog_status = findViewById(R.id.blogdetails_et_blogstatus);

        blog_id = getIntent().getStringExtra("blog_id");
        user_id = getIntent().getStringExtra("user_id");
        et_code_title.setText(getIntent().getStringExtra("code_title"));
        et_full_code.setText(getIntent().getStringExtra("code"));
        et_blog_status.setText(getIntent().getStringExtra("status"));

        ////////////////////////////// firebase db con
        allblog_dbref = FirebaseDatabase.getInstance().getReference("All_blog");
        myblog_dbref = FirebaseDatabase.getInstance().getReference("My_blog");
        //////////////////////////////

    }

    ////////////////////////////// menu creation & nav
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blog_del, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.blog_del:
                allblog_dbref.child(blog_id).removeValue();
                myblog_dbref.child(user_id).child(blog_id).removeValue();
                Toast.makeText(this, "del", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
        }
        return true;
    }
    //////////////////////////////
}
