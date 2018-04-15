package com.example.root.realheart;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserInfoAct extends AppCompatActivity {
    AutoCompleteTextView fname,lname,mno,email,age,bg;
    Button submit;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        fname=(AutoCompleteTextView)findViewById(R.id.firstName);
        lname=(AutoCompleteTextView)findViewById(R.id.lastName);
        mno=(AutoCompleteTextView)findViewById(R.id.number);
        email=(AutoCompleteTextView)findViewById(R.id.email);
        age=(AutoCompleteTextView)findViewById(R.id.age);
        rg=(RadioGroup)findViewById(R.id.gender);
        bg=(AutoCompleteTextView)findViewById(R.id.bg);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Shared Prefrences code goes here

                if(!fname.getText().toString().equals("") && !lname.getText().toString().equals("") && !mno.getText().toString().equals("") && !email.getText().toString().equals("") && !age.getText().toString().equals("") && !bg.getText().toString().equals("") && rg.getCheckedRadioButtonId()!=-1)
                {
                    SharedPreferences sp=getSharedPreferences("ass", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=sp.edit();
                    edit.putString("fname",fname.getText().toString());
                    edit.putString("lname",lname.getText().toString());
                    edit.putString("mno",mno.getText().toString());
                    edit.putString("email",email.getText().toString());
                    edit.putString("age",age.getText().toString());
                    edit.putString("bg",bg.getText().toString());
                    edit.putInt("rg_id",rg.getCheckedRadioButtonId());
                    edit.commit();
                    Toast.makeText(UserInfoAct.this, "Information saved succesfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UserInfoAct.this,"Fields can not be left empty",Toast.LENGTH_LONG).show();
                }
            }
        });

        age.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(!age.getText().toString().equals("") && Integer.parseInt(age.getText().toString())>150)
                {
                    age.setText(age.getText().toString().substring(0,2));
                    SharedPreferences sp=getSharedPreferences("ass",Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=sp.edit();
                    edit.putString("age",age.getText().toString());
                }
                return false;
            }
        });

        SharedPreferences sp=getSharedPreferences("ass",Context.MODE_PRIVATE);
        fname.setText(sp.getString("fname",null));
        lname.setText(sp.getString("lname",null));
        mno.setText(sp.getString("mno",null));
        email.setText(sp.getString("email",null));
        age.setText(sp.getString("age",null));
        bg.setText(sp.getString("bg",null));
        rg.check(sp.getInt("rg_id",-1));

    }
}
