package com.example.sid24rane.technoevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;

public class Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        PasscodeView passcodeView = (PasscodeView) findViewById(R.id.password);
        passcodeView.setLocalPasscode("24051997");
        passcodeView.setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {
            }
            @Override
            public void onSuccess(String number) {
                Intent i = new Intent(Password.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

}
