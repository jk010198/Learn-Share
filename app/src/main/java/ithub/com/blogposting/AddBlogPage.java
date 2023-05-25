package ithub.com.blogposting;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ithub.com.blogposting.Models.FirebaseBlogModel;
import ithub.com.blogposting.Models.RegistrationModel;

public class AddBlogPage extends AppCompatActivity {

    TextInputLayout et_code_title;
    TextInputLayout et_code;
    DatabaseReference mypostdbref;
    DatabaseReference customerdbref;
    DatabaseReference adminpostdbref;
    String blog_id, code_title, code;
    String currentEmail;
    SharedPreferences sp;
    String currentUserId, currentUsername;
    String approve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog_page);

        et_code_title = findViewById(R.id.et_code_title);
        et_code = findViewById(R.id.et_code);

        //////////////// firebase db con
        customerdbref = FirebaseDatabase.getInstance().getReference("Customer");
        mypostdbref = FirebaseDatabase.getInstance().getReference("My_blog");
        adminpostdbref = FirebaseDatabase.getInstance().getReference("All_blog");
        ///////////////////////////////////////////

        ////////////////////////////// getting current Uid
        sp = getSharedPreferences("login", MODE_PRIVATE);
        currentEmail = sp.getString("username", "");

        customerdbref.orderByChild("email").equalTo(currentEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    RegistrationModel rm = ds.getValue(RegistrationModel.class);
                    currentUserId = rm.getId();
                    currentUsername = rm.getName();
                    //Toast.makeText(AddBlogPage.this, ""+key, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //////////////////////////////

    }

    ////////////////////////////// upload blog
    public void submitBlog(View view) {

        blog_id = mypostdbref.push().getKey();
        code_title = et_code_title.getEditText().getText().toString();
        code = et_code.getEditText().getText().toString();
        approve = "disapprove";

        if (code_title.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, "Please Fill the details", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseBlogModel fbm = new FirebaseBlogModel(currentUserId, blog_id, code_title, code, approve, currentUsername,
                    currentEmail);
            mypostdbref.child(currentUserId).child(blog_id).setValue(fbm);
            adminpostdbref.child(blog_id).setValue(fbm);
            Toast.makeText(this, "Blog send", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }
    }
    //////////////////////////////
}
