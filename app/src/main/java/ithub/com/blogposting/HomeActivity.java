package ithub.com.blogposting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ithub.com.blogposting.Adaptor.AdminBlogListAdaptor;
import ithub.com.blogposting.Adaptor.RecyclerViewAdaptor;
import ithub.com.blogposting.Adaptor.RecyclerViewAdaptorSecond;
import ithub.com.blogposting.Models.BlogModel;
import ithub.com.blogposting.Models.FirebaseBlogModel;
import ithub.com.blogposting.Models.RegistrationModel;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<FirebaseBlogModel> list;
    SharedPreferences sp;
    DatabaseReference customerdbref;
    DatabaseReference allblog_dbref;
    static String currentEmail, currentUserId;
    TextView emptyblog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.blog_recyclerview);

        emptyblog = findViewById(R.id.tv_no_blogdata);

        ////////////////////// for checking user session //////////////////////////////////
        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.contains("admin") && sp.contains("a_pass")) {
            startActivity(new Intent(HomeActivity.this, AdminHome.class));
            finish();
        }
        //////////////////////////////////////////////////////////////////

        allblog_dbref = FirebaseDatabase.getInstance().getReference("All_blog");

        list = new ArrayList<FirebaseBlogModel>();

        //////// fetch blog that approve by admin /////////////////
        try {
            allblog_dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                        FirebaseBlogModel fbm = getorder.getValue(FirebaseBlogModel.class);
                        if ((fbm.getApprove().equals("approve"))) {
                            list.add(fbm);
                        }
                    }

                    if (!(list.size() > 0)) {
                        emptyblog.setVisibility(View.VISIBLE);
                    } else {
                        emptyblog.setVisibility(View.INVISIBLE);
                    }

                    Collections.reverse(list);

                    RecyclerViewAdaptorSecond recyclerViewad = new RecyclerViewAdaptorSecond(HomeActivity.this, (ArrayList<FirebaseBlogModel>) list);
                    //RecyclerViewAdapter recyclerViewad = new RecyclerViewAdapter(this, list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false));
                    //recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                    recyclerView.setAdapter(recyclerViewad);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }
        ////////////////////////////////////////////////////////

        //////////// for get login user UID
        sp = getSharedPreferences("login", MODE_PRIVATE);
        currentEmail = sp.getString("username", "");
        customerdbref = FirebaseDatabase.getInstance().getReference("Customer");

        customerdbref.orderByChild("email").equalTo(currentEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    RegistrationModel rm = ds.getValue(RegistrationModel.class);
                    currentUserId = rm.getId();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ////////////////////////////////

    }

    ////////////// for exiting app
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
    ///////////////////////////

    ////////////////////  menu creation & clicking navigation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.contains("username") && sp.contains("password")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.after_login, menu);
        } else if (sp.contains("admin") && sp.contains("a_pass")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.admin_menu, menu);
            startActivity(new Intent(getApplicationContext(), AdminHome.class));
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;

            case R.id.register:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;


            case R.id.my_blog:
                startActivity(new Intent(getApplicationContext(), MyBlogList.class));
                break;

            case R.id.add:
                startActivity(new Intent(getApplicationContext(), AddBlogPage.class));
                break;

            case R.id.logout:
                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.signOut();
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear();
                edit.commit();
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                break;
        }

        return true;
    }
    ///////////////////////////////////////////
}
