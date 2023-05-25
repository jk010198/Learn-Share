package ithub.com.blogposting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout et_name, et_emailid, et_mobileno, et_password, et_cpassword;
    String Validemail;
    FirebaseAuth mAuth;
    String name, email, mobile_no, password, conf_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = findViewById(R.id.et_register_name);
        et_emailid = findViewById(R.id.et_register_email);
        et_mobileno = findViewById(R.id.et_register_mobileno);
        et_password = findViewById(R.id.et_register_password);
        et_cpassword = (TextInputLayout) findViewById(R.id.et_register_cpassword);
        mAuth = FirebaseAuth.getInstance();
    }

    ////////////////////////////// otp verfiy & register user
    public void otpVerification(View view) {

        name = et_name.getEditText().getText().toString();
        email = et_emailid.getEditText().getText().toString();
        mobile_no = et_mobileno.getEditText().getText().toString();
        password = et_password.getEditText().getText().toString();
        conf_password = et_cpassword.getEditText().getText().toString();

        Validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

        Matcher matcher = Pattern.compile(Validemail).matcher(email);

        if (name.isEmpty() || email.isEmpty() || mobile_no.isEmpty() || password.isEmpty() || conf_password.isEmpty()){
            Toast.makeText(this, "fill properly", Toast.LENGTH_SHORT).show();
        } else if (!(matcher.matches())){
            Toast.makeText(this, "Enter Valid email.", Toast.LENGTH_SHORT).show();
        } else  if (mobile_no.length() <10){
            Toast.makeText(this, "Enter valid number", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6 || password.length() < 6 || password.length() > 12 || conf_password.length() > 12){
            Toast.makeText(this, "Password length is min 6 & max length 12.", Toast.LENGTH_SHORT).show();
        } else if (!(password.equals(conf_password))){
            Toast.makeText(this, "enter same pass", Toast.LENGTH_SHORT).show();
        } else {

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    
                    if (task.isSuccessful()) {
                        Intent i = new Intent(getApplicationContext(), OtpVerfication.class);
                        i.putExtra("name", name);
                        i.putExtra("email", email);
                        i.putExtra("mobile_no", mobile_no);
                        i.putExtra("password", password);
                        i.putExtra("conf_password", conf_password);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "This email already taken. please choose another.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
