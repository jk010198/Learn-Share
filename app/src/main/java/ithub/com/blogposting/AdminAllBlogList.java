package ithub.com.blogposting;

import android.content.Intent;
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

import ithub.com.blogposting.Adaptor.AdminBlogListAdaptor;
import ithub.com.blogposting.Adaptor.MyBlogListAdaptor;
import ithub.com.blogposting.Models.FirebaseBlogModel;

public class AdminAllBlogList extends AppCompatActivity {

    ListView listView;
    String blog_id;
    List<FirebaseBlogModel> bloglist;
    TextView emptyblog;

    DatabaseReference allblog_dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_blog_list);

        listView = findViewById(R.id.admin_aproveblog_listview);
        emptyblog = findViewById(R.id.tv_no_blogdata);

        bloglist = new ArrayList<>();

        ////////////////////////////// firebase db con
        allblog_dbref = FirebaseDatabase.getInstance().getReference("All_blog");
        //////////////////////////////

        ////////////////////////////// all blog list
        try {
            allblog_dbref.addValueEventListener(new ValueEventListener() {
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
                    AdminBlogListAdaptor myblogadaptor = new AdminBlogListAdaptor(AdminAllBlogList.this, bloglist);
                    listView.setAdapter(myblogadaptor);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getApplicationContext(), AdminBlogDetails.class);
                            FirebaseBlogModel fbm = (FirebaseBlogModel) parent.getItemAtPosition(position);
                            i.putExtra("blog_id", fbm.getBlog_id());
                            i.putExtra("user_id", fbm.getId());
                            i.putExtra("code_title", fbm.getCode_title());
                            i.putExtra("code", fbm.getCode());
                            i.putExtra("status", fbm.getApprove());
                            startActivity(i);
                        }
                    });
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
