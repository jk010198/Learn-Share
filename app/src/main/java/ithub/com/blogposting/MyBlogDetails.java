package ithub.com.blogposting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ithub.com.blogposting.Models.FirebaseBlogModel;

public class MyBlogDetails extends AppCompatActivity {

    EditText et_code_title, et_full_code;
    DatabaseReference myblog_dbref;
    DatabaseReference allblog_dbref;
    String currentUserId, myBlogId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blog_details);
        currentUserId = HomeActivity.currentUserId;

        et_code_title = findViewById(R.id.et_code_title);
        et_full_code = findViewById(R.id.et_full_code);

        et_code_title.setText(getIntent().getStringExtra("code_title"));
        et_full_code.setText(getIntent().getStringExtra("code"));

        ////////////////////////////// firebase db con
        myblog_dbref = FirebaseDatabase.getInstance().getReference("My_blog").child(currentUserId);
        allblog_dbref = FirebaseDatabase.getInstance().getReference("All_blog");
        //////////////////////////////

        ////////////////////////////// retrive blog_id
        myblog_dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                    FirebaseBlogModel fbm = getorder.getValue(FirebaseBlogModel.class);
                    myBlogId = fbm.getBlog_id();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //////////////////////////////
    }

    ////////////////////////////// menu create & nav
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
                myblog_dbref.child(myBlogId).removeValue();
                allblog_dbref.child(myBlogId).removeValue();
                Toast.makeText(this, "del", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
        }
        return true;
    }
    //////////////////////////////
}
