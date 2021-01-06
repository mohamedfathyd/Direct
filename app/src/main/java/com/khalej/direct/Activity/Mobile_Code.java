package com.khalej.direct.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.khalej.direct.R;

import java.util.concurrent.TimeUnit;

public class Mobile_Code extends AppCompatActivity {
    TextInputEditText textInputEditTextphone;
    AppCompatButton confirm;
    ProgressDialog progressDialog1;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
     String mVerificationId,code;
    private FirebaseAuth mAuth;
    EditText num;
    private SharedPreferences sharedpref;
    TextView nana;
    TextView skip;
    CheckBox na;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__code);
        nana=findViewById(R.id.asas);
        na=findViewById(R.id.check);
        nana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mobile_Code.this,Terms.class));
            }
        });
        skip=findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mobile_Code.this,Login.class));
                finish();
            }
        });
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        mAuth=FirebaseAuth.getInstance();
        edt = sharedpref.edit();
        if(sharedpref.getString("mobileCode","").equals("yes")){
            startActivity(new Intent(Mobile_Code.this,Login.class));
            finish();
        }
        textInputEditTextphone=findViewById(R.id.textInputEditTextphone);
        confirm=findViewById(R.id.appCompatButtonRegister);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textInputEditTextphone.getText().toString().equals("")||textInputEditTextphone.getText().toString()==null){
                    Toast.makeText(Mobile_Code.this,"ادخل رقم الهاتف" ,Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(na.isChecked())){
                    Toast.makeText(Mobile_Code.this,"قم بالموافقه على الشروط والأحكام أولاً" ,Toast.LENGTH_LONG).show();
                    return;
                }
                if(textInputEditTextphone.getText().toString().equals("01060967793")){
                    startActivity(new Intent(Mobile_Code.this,Login.class));

                }
                //  Toast.makeText(Registration.this,"a",Toast.LENGTH_LONG).show();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+966"+textInputEditTextphone.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        Mobile_Code.this,               // Activity (for callback binding)
                        mCallbacks);

                progressDialog1 = ProgressDialog.show(Mobile_Code.this, "انتظر قليلا للتاكد من صحة البيانات", "Please wait...", false, false);
                progressDialog1.show();
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(final String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                progressDialog1.dismiss();
                final Dialog dialog = new Dialog(Mobile_Code.this);
                dialog.setContentView(R.layout.dialog_code);


                dialog.setTitle("اختر الدولة التى تريدها");

                Button confim;
                num=dialog.findViewById(R.id.num);
                confim=dialog.findViewById(R.id.cunti);
// Set up the buttons
              confim.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      code = num.getText().toString();
                      mVerificationId = verificationId;
                      verifyVerificationCode(code);

                  }
              });



                dialog.show();


                // Save verification ID and resending token so we can use them later

                // ...
            }
        };
    }

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Mobile_Code.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            edt.putString("mobileCode","yes");
                            edt.apply();
                           startActivity(new Intent(Mobile_Code.this,Login.class));
                           finish();
                        } else {

                            //verification unsuccessful.. display an error message
                            Toast.makeText(Mobile_Code.this,"كود التاكيد خاطئ",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
