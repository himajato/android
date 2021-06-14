package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.Model.Item;
import com.example.firebasetest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    View v;
    DatabaseReference reference;
    ProgressBar progressBar;
    ArrayList<Item> listItem;
    FloatingActionButton fb;
    ItemAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment,container,false);
        progressBar = v.findViewById(R.id.progessBarHome);

        fb = v.findViewById(R.id.floatButton);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = v.findViewById(R.id.recyclerViewHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.child("Chi_phi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listItem = new ArrayList<Item>();
                if(snapshot.exists()){
                    for (DataSnapshot itemm: snapshot.getChildren()){
                        Item item = itemm.getValue(Item.class);

                        listItem.add(item);
                        //adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }

                    adapter = new ItemAdapter(getContext(),listItem);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AddItem.class));
            }
        });

        return v;
    }

    private void clearAll() {
        if(listItem!=null){
            listItem.clear();
            if(adapter !=null){
                adapter.notifyDataSetChanged();
            }
        }
        listItem = new ArrayList<>();
    }

}
