package cm.deone.mesextensions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mConnexion;
    private Button mRegister;
    private Button mAnnuler;

    private ProgressBar mProgress_bar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.email_login);
        mPassword = (EditText) findViewById(R.id.password_login);
        mConnexion = (Button) findViewById(R.id.login_btn);
        mRegister = (Button) findViewById(R.id.register_btn);
        mAnnuler = (Button) findViewById(R.id.cnx_back_btn);

        mProgress_bar = (ProgressBar) findViewById(R.id.loading_progressBar);

        mConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()) return;
                inProgress(true);
                mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignInActivity.this, "User signed in", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignInActivity.this, AgentListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(SignInActivity.this, "User signed in failed!"+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()) return;
                inProgress(true);
                mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignInActivity.this, "User registered successfully!", Toast.LENGTH_LONG).show();
                        inProgress(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(SignInActivity.this, "Registration failed!"+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        mAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();return;
            }
        });

    }

    private void inProgress (boolean b){
        if (b){
            mProgress_bar.setVisibility(View.VISIBLE);
            mConnexion.setEnabled(false);
            mRegister.setEnabled(false);
            mAnnuler.setEnabled(false);
        }else{
            mProgress_bar.setVisibility(View.GONE);
            mConnexion.setEnabled(true);
            mRegister.setEnabled(true);
            mAnnuler.setEnabled(true);
        }
    }
    private boolean isEmpty(){
        if(TextUtils.isEmpty(mEmail.getText().toString())){
            mEmail.setError("REQUIRED!!!");
            return true;
        }
        if(TextUtils.isEmpty(mPassword.getText().toString())){
            mPassword.setError("REQUIRED!!!");
            return true;
        }
        return false;
    }
}
