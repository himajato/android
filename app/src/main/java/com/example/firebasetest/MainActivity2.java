package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebasetest.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.net.Authenticator;

public class MainActivity2 extends AppCompatActivity {
    EditText mEmail,mPassword,mRepassword,mName;
    Button confirm,cancel;
    ProgressBar progessBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        intiView();
        mAuth = FirebaseAuth.getInstance();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void registerUser() {
        progessBar.setVisibility(View.VISIBLE);
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString().trim();
        String reenter = mRepassword.getText().toString().trim();
        String name = mName.getText().toString();
        if(email.isEmpty()){
            mEmail.setError("Email không được để trống");
            mEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            mPassword.setError("Password không được để trống");
            mPassword.requestFocus();
            return;
        }
        if(reenter.isEmpty()){
            mRepassword.setError("Hãy nhập lại password");
            mRepassword.requestFocus();
            return;
        }
        if(name.isEmpty()){
            mName.setError("Vui lòng nhập họ tên của bạn");
            mName.requestFocus();
            return;
        }
        if(!password.equals(reenter)){
            mRepassword.setError("Nhập lại mật khẩu chưa đúng");
            mRepassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(name,email);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Information")
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity2.this,"Đăng kí tài khoản thành công",Toast.LENGTH_LONG).show();
                                progessBar.setVisibility(View.GONE);
                                finish();

                            } else {
                                Toast.makeText(MainActivity2.this,"Đăng kí tài khoản thất bại, hãy thử lại",Toast.LENGTH_LONG).show();
                                progessBar.setVisibility(View.GONE);
                            }
                        }
                    });
                    //Toast.makeText(MainActivity2.this,"Đăng kí thành công",Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(MainActivity2.this,MainActivity.class));
                } else{
                    Toast.makeText(MainActivity2.this,"Đăng kí thất bại, hãy thử lại",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void intiView() {
        mName = findViewById(R.id.editTextName);
        confirm = findViewById(R.id.btConfirm);
        cancel = findViewById(R.id.btCancel);
        mEmail = findViewById(R.id.editTextEmail);
        mPassword = findViewById(R.id.editTextPassword);
        mRepassword = findViewById(R.id.editTextReenter);
        progessBar = findViewById(R.id.progessBarRegister);

    }
}