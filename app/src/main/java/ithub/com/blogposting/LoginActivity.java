package ithub.com.blogposting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout et_username;
    TextInputLayout et_password;
    SharedPreferences sp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);

        sp = getSharedPreferences("login", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
    }

    ////////////////////////////// login LOGIC
    public void login(View view) {

        String username = et_username.getEditText().getText().toString();
        String password = et_password.getEditText().getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fil", Toast.LENGTH_SHORT).show();
        }
        if (username.equals("Admin@admin.com") && password.equals("123456")) {

            SharedPreferences.Editor edit = sp.edit();
            edit.putString("admin", username);
            edit.putString("a_pass", password);
            edit.commit();
            startActivity(new Intent(getApplicationContext(), AdminHome.class));
            finish();
        } else {
            if (!(username.isEmpty() || password.isEmpty())) {
                mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseMessaging.getInstance().subscribeToTopic("myBlogNotify");
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("username", et_username.getEditText().getText().toString());
                            edit.putString("password", et_password.getEditText().getText().toString());
                            edit.commit();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
    }
}
