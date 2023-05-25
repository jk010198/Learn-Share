package ithub.com.blogposting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import ithub.com.blogposting.Models.RegistrationModel;

public class OtpVerfication extends AppCompatActivity {

    String  otp;
    EditText et_first_digit, et_second_digit, et_third_digit, et_four_digit, et_five_digit, et_six_digit;
    DatabaseReference dbref;
    FirebaseAuth auth;
    String codesnt;
    String id, name, email, mobileno, pass, conf_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verfication);

        et_first_digit = findViewById(R.id.et_otp_first_digit);
        et_second_digit = findViewById(R.id.et_otp_second_digit);
        et_third_digit = findViewById(R.id.et_otp_third_digit);
        et_four_digit = findViewById(R.id.et_otp_four_digit);
        et_five_digit = findViewById(R.id.et_otp_five_digit);
        et_six_digit = findViewById(R.id.et_otp_six_digit);

        et_first_digit.setGravity(Gravity.CENTER);
        et_second_digit.setGravity(Gravity.CENTER);
        et_third_digit.setGravity(Gravity.CENTER);
        et_four_digit.setGravity(Gravity.CENTER);
        et_five_digit.setGravity(Gravity.CENTER);
        et_six_digit.setGravity(Gravity.CENTER);

        ////////////////////////////// db con
        dbref = FirebaseDatabase.getInstance().getReference("Customer");
        auth = FirebaseAuth.getInstance();
        //////////////////////////////

        ////////////////////////////// get data from prev activity
        id = dbref.push().getKey();
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        mobileno = getIntent().getStringExtra("mobile_no");
        pass = getIntent().getStringExtra("password");
        conf_password = getIntent().getStringExtra("conf_password");
        //////////////////////////////

        //////////////////////////////// for moving cursor & set otp value automatically //////////////////////////////////////////////
        et_first_digit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() ==1) {
                    //et_first_digit
                    et_second_digit.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_second_digit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() ==1) {
                    et_third_digit.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_third_digit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() ==1) {
                    et_four_digit.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_four_digit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() ==1) {
                    et_five_digit.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_five_digit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() ==1) {
                    et_six_digit.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_six_digit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                /*
                if (s.length() ==1) {
                    et_six_digit.requestFocus();
                }*/
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
        ////////////////////////////// end moving cursor code & auto fill otp ////////////////////////////////////

        sendVerificationCode();

    }

    public void Submit(View view) {
        verifyCode();
    }

    private void verifyCode() {
        otp = et_first_digit.getText().toString() + "" + et_second_digit.getText().toString() + "" + et_third_digit.getText().toString()
                + "" + et_four_digit.getText().toString() + "" + et_five_digit.getText().toString() + "" + et_six_digit.getText().toString();
        String code = otp.trim();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesnt, code);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            RegistrationModel rm = new RegistrationModel(id, name, email, mobileno, pass);
                            dbref.child(id).setValue(rm);
                            Toast.makeText(OtpVerfication.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(OtpVerfication.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileno,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codesnt = s;
        }
    };
}