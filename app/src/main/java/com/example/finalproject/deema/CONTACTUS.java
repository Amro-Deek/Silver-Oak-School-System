package com.example.finalproject.deema;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;

public class CONTACTUS extends AppCompatActivity {
    EditText name ,email ,phone,role,subject,msg;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contactus);

        name = findViewById(R.id.nametxt);
        email = findViewById(R.id.emailtxt);
        phone = findViewById(R.id.phonetxt);
        role = findViewById(R.id.roletxt);
        subject = findViewById(R.id.subjecttxt);
        msg = findViewById(R.id.msgtxt);
        btn =findViewById(R.id.button2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pname=name.getText().toString();
                String pemail=email.getText().toString();
                String pphone=phone.getText().toString();
                String prole=role.getText().toString();
                String psubject= subject.getText().toString();
                String pmsg=msg.getText().toString();
                //database implementation goes here
            }
        });

    }


}