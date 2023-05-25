package ithub.com.blogposting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
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

public class AdminHome extends AppCompatActivity {

    ListView listView;
    TextView emptyblog;
    List<FirebaseBlogModel> bloglist;
    SharedPreferences sp;
    DatabaseReference allblog_dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        listView = findViewById(R.id.admin_disaproveblog_listview);
        emptyblog = findViewById(R.id.tv_no_blogdata);

        ////////////////////////////// firebase db con
        allblog_dbref = FirebaseDatabase.getInstance().getReference("All_blog");
        //////////////////////////////

        bloglist = new ArrayList<>();

        //////////////////////////////
        try {
            allblog_dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    bloglist.clear();
                    for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                        FirebaseBlogModel fbm = getorder.getValue(FirebaseBlogModel.class);
                        if ((fbm.getApprove().equals("disapprove"))) {
                            bloglist.add(fbm);
                        }
                    }

                    if (!(bloglist.size() > 0)) {
                        emptyblog.setVisibility(View.VISIBLE);
                    } else {
                        emptyblog.setVisibility(View.INVISIBLE);
                    }

                    Collections.reverse(bloglist);
                    AdminBlogListAdaptor adminBlogListAdaptor = new AdminBlogListAdaptor(AdminHome.this, bloglist);
                    listView.setAdapter(adminBlogListAdaptor);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getApplicationContext(), ApproveBlogActivity.class);
                            FirebaseBlogModel fbm = (FirebaseBlogModel) parent.getItemAtPosition(position);
                            i.putExtra("id", fbm.getBlog_id());
                            i.putExtra("code_title", fbm.getCode_title());
                            i.putExtra("code", fbm.getCode());
                            i.putExtra("remark", fbm.getApprove());
                            i.putExtra("user_name", fbm.getAuthor());
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

    ////////////////////////////// when back btn click
    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setMessage("Do you want to exit?");
        adb.setTitle("Information");
        adb.setNegativeButton("NO", null);
        adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                intent.putExtra("exit_code", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        adb.show();
    }
    //////////////////////////////

    ////////////////////////////// menu creation & nav
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.contains("admin") && sp.contains("a_pass")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.admin_menu, menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear();
                edit.commit();
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                break;

            case R.id.blog_delete:
                Toast.makeText(this, "approve / disapprove blog list", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AdminAllBlogList.class));
                break;

        }
        return true;
    }
    //////////////////////////////
}
