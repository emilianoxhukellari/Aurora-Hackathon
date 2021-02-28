package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    public EditText name;
    private EditText email;
    private EditText password;
    private EditText password_confirm;
    private TextView wrong_text;
    private Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.sign_name);
        email = (EditText) findViewById(R.id.sign_email);
        password = (EditText) findViewById(R.id.sign_pass);
        password_confirm = (EditText) findViewById(R.id.sign_pass_con);
        wrong_text = (TextView) findViewById(R.id.sign_wrong);
        sign_up = (Button) findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(), email.getText().toString(), password.getText().toString(), password_confirm.getText().toString());
            }
        });
    }
    private void validate(String Name, String Email, String Password, String Pass_con) {
        if (Name.isEmpty() || Email.isEmpty() || Password.isEmpty() || Pass_con.isEmpty()) {
            wrong_text.setText("Empty fields");
        }
        else {
            if (Password.equals(Pass_con)) {
                wrong_text.setTextColor(Color.BLACK);
                wrong_text.setText("Sign in successful! Welcome!");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SignUp.this, Welcome.class);
                        startActivity(intent);
                    }
                }, 1500);
            }
            else {
                wrong_text.setTextColor(Color.RED);
                wrong_text.setText("Passwords do not match!");
            }
        }
    }
}