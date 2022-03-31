package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
public class VerifyOTPActivity extends AppCompatActivity {
    
    private EditText inputcode1, inputcode2, inputcode3, inputcode4, inputcode5, inputcode6;
    private String verificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        TextView textMobile = findViewById(R.id.textMobile);
        textMobile.setText(String.format(
                "+91-%s",getIntent().getStringExtra("mobile")
        ));

        inputcode1 = findViewById(R.id.inputCode1);
        inputcode2 = findViewById(R.id.inputCode2);
        inputcode3 = findViewById(R.id.inputCode3);
        inputcode4 = findViewById(R.id.inputCode4);
        inputcode5 = findViewById(R.id.inputCode5);
        inputcode6 = findViewById(R.id.inputCode6);

        setupOTPInputs();

        final ProgressBar progressBar = findViewById(R.id.progressbar);
        final Button buttonVerify = findViewById(R.id.buttonGetVerify);

        verificationID = getIntent().getStringExtra("verificationID");
        buttonVerify.setOnClickListener(view -> {

            if(inputcode1.getText().toString().trim().isEmpty()
            || inputcode2.getText().toString().trim().isEmpty()
            || inputcode3.getText().toString().trim().isEmpty()
            || inputcode4.getText().toString().trim().isEmpty()
            || inputcode5.getText().toString().trim().isEmpty()
            || inputcode6.getText().toString().trim().isEmpty()){

                Toast.makeText(VerifyOTPActivity.this, "Please Enter Valid Code",Toast.LENGTH_SHORT).show();
                return;
            }
            String code =
                    inputcode1.getText().toString() +
                            inputcode2.getText().toString() +
                            inputcode3.getText().toString() +
                            inputcode4.getText().toString() +
                            inputcode5.getText().toString() +
                            inputcode6.getText().toString();
            if (verificationID != null){
                progressBar.setVisibility(View.VISIBLE);
                buttonVerify.setVisibility(View.INVISIBLE);
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationID,
                        code
                );
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(task -> {
                            progressBar.setVisibility(View.GONE);
                            buttonVerify.setVisibility(View.VISIBLE);
                            if (task.isSuccessful())
                            {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(VerifyOTPActivity.this, "The Verification Code Entered was Invalid", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        findViewById(R.id.textResendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+ getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyOTPActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newverificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                               verificationID = newverificationID;
                               Toast.makeText(VerifyOTPActivity.this, "OTP sent",Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }
        });

    }

    private void setupOTPInputs(){
        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if(!s.toString().trim().isEmpty())
                {
                    inputcode2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if(!s.toString().trim().isEmpty())
                {
                    inputcode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if(!s.toString().trim().isEmpty())
                {
                    inputcode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if(!s.toString().trim().isEmpty())
                {
                    inputcode5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if(!s.toString().trim().isEmpty())
                {
                    inputcode6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}