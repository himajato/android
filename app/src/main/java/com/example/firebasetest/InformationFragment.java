package com.example.firebasetest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformationFragment extends Fragment{
    DatabaseReference mData;
    TextView email,hoten;
    View v;
    ProgressBar progressBar;
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) throws NullPointerException{
        v = inflater.inflate(R.layout.information_fragment,container,false);
        initView();
        progressBar.setVisibility(View.VISIBLE);
        mData = FirebaseDatabase.getInstance().getReference("Users");
        mData.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Information").child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoten.setText(snapshot.getValue().toString());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        progressBar.setVisibility(View.VISIBLE);
        mData.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Information").child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email.setText(snapshot.getValue().toString());
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    private void initView() {
        hoten = v.findViewById(R.id.textHoten);
        email = v.findViewById(R.id.textEmail);
        progressBar = v.findViewById(R.id.progessBarInfor);
    }

}
