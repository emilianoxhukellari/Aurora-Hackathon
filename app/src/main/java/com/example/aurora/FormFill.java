package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FormFill extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_ISSUE = "com.example.aurora.EXTRA_ISSUE";
    public static final String EXTRA_TYPE = "com.example.aurora.EXTRA_TYPE";
    public static final String EXTRA_COMMENT = "com.example.aurora.EXTRA_COMMENT";

    private Button form_next;
    private EditText form_issue;
    private EditText form_desc;
    private TextView i_issue;
    private TextView i_desc;
    private String Issue;
    private String Description;
    private String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_fill);

        Spinner spinner = findViewById(R.id.form_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.issue_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        form_issue = (EditText) findViewById(R.id.form_issue);
        i_issue = (TextView) findViewById(R.id.ignore_issue);
        form_desc = (EditText) findViewById(R.id.form_desc);
        i_desc = (TextView) findViewById(R.id.ignore_desc);
        form_next = (Button) findViewById(R.id.form_next);
        form_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(form_issue.getText().toString(), form_desc.getText().toString());
            }
        });
    }
    public void validate(String issue, String Desc) {
        if (issue.isEmpty()) {
            i_issue.setText("Issue field is empty!");
        }
        else if (Desc.isEmpty()) {
            i_desc.setText("Issue field is empty!");
        }
        else if ((!issue.isEmpty()) && (!Desc.isEmpty())) {
            i_issue.setText("");
            i_desc.setText("");
            Issue = form_issue.getText().toString();
            Description = form_desc.getText().toString();
            openCamera();
        }
    }
    public void openCamera() {
        Intent intent = new Intent(this, Camera.class);
        intent.putExtra(EXTRA_ISSUE, Issue);
        intent.putExtra(EXTRA_TYPE, Type);
        intent.putExtra(EXTRA_COMMENT, Description);
        startActivity(intent);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) view).setTextColor(Color.BLACK);
        ((TextView) view).setTextSize(15f);
        Type = parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}