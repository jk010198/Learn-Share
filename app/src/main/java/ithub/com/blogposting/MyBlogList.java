package ithub.com.blogposting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ithub.com.blogposting.Adaptor.MyBlogListAdaptor;
import ithub.com.blogposting.Models.FirebaseBlogModel;
import ithub.com.blogposting.Models.RegistrationModel;

public class MyBlogList extends AppCompatActivity {

    ListView listView;
    List<FirebaseBlogModel> bloglist;
    DatabaseReference dbref;
    TextView emptyblog;
    String currentUserId ;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blog_list);

        listView = findViewById(R.id.myblog_listview);
        emptyblog = findViewById(R.id.tv_no_blogdata);

        bloglist = new ArrayList<>();

        ////////////////////////////// db con & get current user id
        currentUserId = HomeActivity.currentUserId;
        dbref = FirebaseDatabase.getInstance().getReference("My_blog").child(currentUserId);
        //////////////////////////////

        ////////////////////////////// our blog list
        try {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    bloglist.clear();
                    for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                        FirebaseBlogModel fbm = getorder.getValue(FirebaseBlogModel.class);
                        bloglist.add(fbm);
                    }

                    if (!(bloglist.size() > 0)) {
                        emptyblog.setVisibility(View.VISIBLE);
                    } else {
                        emptyblog.setVisibility(View.INVISIBLE);
                    }
                    Collections.reverse(bloglist);
                    MyBlogListAdaptor myblogadaptor = new MyBlogListAdaptor(MyBlogList.this, bloglist);
                    listView.setAdapter(myblogadaptor);

                    ////////////////////////////// when click 1 blog
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getApplicationContext(), MyBlogDetails.class);
                            FirebaseBlogModel fbm = (FirebaseBlogModel) parent.getItemAtPosition(position);
                            i.putExtra("id", fbm.getBlog_id());
                            i.putExtra("code_title", fbm.getCode_title());
                            i.putExtra("code", fbm.getCode());
                            startActivity(i);
                        }
                    });
                    //////////////////////////////
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }
        //////////////////////////////

    }
}
