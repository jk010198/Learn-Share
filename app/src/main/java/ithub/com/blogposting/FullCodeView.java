package ithub.com.blogposting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FullCodeView extends AppCompatActivity {

    String code_title, code_author_name, full_code;
    TextView tv_language_of_code, tv_author_of_code, tv_full_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_code_view);

        tv_language_of_code = findViewById(R.id.tv_set_language_of_code);
        tv_author_of_code = findViewById(R.id.tv_set_author_of_code);
        tv_full_code = findViewById(R.id.tv_set_code);

        ////////////////////////////// get data from prev activity
        code_title = getIntent().getStringExtra("code_title");
        code_author_name = getIntent().getStringExtra("author");
        full_code = getIntent().getStringExtra("code");
        //////////////////////////////

        ////////////////////////////// set data into edittxt
        tv_language_of_code.setText(code_title);
        tv_author_of_code.setText(code_author_name);
        tv_full_code.setText(full_code);
        //////////////////////////////

    }

    ////////////////////////////// menu create & nav
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.complier_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.complier:
                Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), WebActivityComplier.class);
                i.putExtra("ol_complier","https://www.onlinegdb.com/online_java_compiler");
                startActivity(i);
                break;
        }
        return true;
    }
    //////////////////////////////
}
