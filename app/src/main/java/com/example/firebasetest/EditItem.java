package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasetest.Model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditItem extends AppCompatActivity {
    Button delete,ok,cancel;
    EditText tilte,date,money;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        initView();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditItem.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int i1, int i2) {
                        date.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final Intent intent = getIntent();
        if(intent.getSerializableExtra("item")!=null){
            final Item item = (Item) intent.getSerializableExtra("item");
            tilte.setText(item.getTilte());
            money.setText(item.getMoney());
            date.setText(item.getDate());
            reference = FirebaseDatabase.getInstance()
                    .getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Chi_phi").child(item.getTilte());
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item item = new Item();
                    item.setTilte(tilte.getText().toString());
                    item.setMoney(money.getText().toString());
                    item.setDate(date.getText().toString());
                    reference.setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(EditItem.this,"Sửa thành công",Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditItem.this,"Sửa không thành công, hãy thử lại",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(EditItem.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditItem.this,"Xóa không thành công, hãy thử lại",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }

    private void initView() {
        delete = findViewById(R.id.buttonDelete);
        ok = findViewById(R.id.buttonOkAddItemEdit);
        cancel = findViewById(R.id.buttonCancelAddItemEdit);
        tilte = findViewById(R.id.editTextTitleEdit);
        date = findViewById(R.id.editTextDateEdit);
        money = findViewById(R.id.editTextMoneyEdit);
    }


}