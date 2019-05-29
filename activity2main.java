package com.example.ex3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    EditText etuid,etpwd,etretype;
    Button blogin,bcancel,bnewregistration;
    SQLiteDatabase db;
    Cursor c;
    String un;
    int f=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etuid=(EditText)findViewById(R.id.editTextUID);
        etpwd=(EditText)findViewById(R.id.editTextPassword);
        etretype=(EditText)findViewById(R.id.editTextRetype);
        blogin=(Button)findViewById(R.id.loginbutton);
        bcancel=(Button)findViewById(R.id.cancelbutton);
        bnewregistration = (Button)findViewById(R.id.buttonnewreg);

        try{
            db= openOrCreateDatabase("logindb", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS logintable(username VARCHAR,password VARCHAR);");

            c=db.rawQuery("SELECT * FROM logintable", null);


        }catch(Exception e){
            Toast.makeText(getBaseContext(), "Error:"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }



        blogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                c.moveToFirst();
                while(c.moveToNext())
                {
                    un=c.getString(0);
                    if( etuid.getText().toString().equals(un) ) {

                        Toast.makeText(getBaseContext(), " User Name alreDY EXIST", Toast.LENGTH_LONG).show();
                        f=1;
                    }
            }
                if(etpwd.getText().toString().equals(etretype.getText().toString())&& !etuid.getText().toString().equals("")&& !etuid.getText().toString().equals(un))
                {
                    db.execSQL("INSERT INTO logintable VALUES('"+etuid.getText().toString()+"','"+etpwd.getText().toString()+"');");
                    c=db.rawQuery("SELECT * FROM logintable", null);
                    db.close();
                    Intent i= new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                }
                else{
                    if(f==0) {
                        Toast.makeText(getBaseContext(), "Pasword Not Matching!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        bcancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                db.close();
                finishActivity(0);
                System.exit(0);

            }

        });


    }
}
