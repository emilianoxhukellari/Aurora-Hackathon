package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button login_btn;
    private Button sign_btn;
    private Button forget_btn;
    private CheckBox main_check;

    private EditText username;
    private EditText password;
    private TextView wrong_credentials;
    private int counter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.main_user);
        password = (EditText) findViewById(R.id.main_pass);
        wrong_credentials = (TextView) findViewById(R.id.main_wrong);
        login_btn = (Button) findViewById(R.id.login_btn);
        sign_btn = (Button) findViewById(R.id.sign_btn);
        forget_btn = (Button) findViewById(R.id.forget_btn);
        main_check = (CheckBox) findViewById(R.id.main_check);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username.getText().toString(), password.getText().toString());
            }
        });
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });
        forget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgetPass();
            }
        });
        main_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main_check.isChecked()) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void validate(String userName, String passWord) {
        if ((userName.isEmpty()) || (passWord.isEmpty())) {
            wrong_credentials.setText("Username or password empty");
        }
        else if ((!userName.equals("admin")) || (!passWord.equals("admin"))) {
            wrong_credentials.setText("Wrong username or password");
        }
        else if ((userName.equals("admin")) && (passWord.equals("admin"))) {
            wrong_credentials.setText("");
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
        }
        else {
            counter--;
            if (counter == 0) {
                openForgetPass();
            }
        }
    }
    public void openSignUp() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void openForgetPass() {
        Intent intent = new Intent(this, ForgetPass.class);
        startActivity(intent);
    }
}