package com.example.sid24rane.technoevents;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private Spinner eventName;
    private EditText otp;
    private EditText collegeCode;
    private RadioGroup collegeType;
    private Button submit;
    private ProgressDialog progressDialog;
    private TextView techno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        networkInit();
        init();

    }


    private void networkInit() {

        OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                .build();
        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);

    }

    private void init() {

        techno = (TextView) findViewById(R.id.technovanza);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/gang_wolfik.ttf");
        techno.setTypeface(face);

        eventName = (Spinner) findViewById(R.id.eventName);
        otp = (EditText) findViewById(R.id.otp);
        collegeCode = (EditText) findViewById(R.id.collegeCode);
        collegeType = (RadioGroup) findViewById(R.id.collegeSelect);
        submit = (Button) findViewById(R.id.submit);

        List<String> eventsNameDB = eventsNameData();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventsNameDB);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventName.setAdapter(dataAdapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_otp = otp.getText().toString();
                String user_event = eventName.getSelectedItem().toString().toLowerCase();
                String user_college_code = collegeCode.getText().toString().toUpperCase();

                    int selected = collegeType.getCheckedRadioButtonId();

                    if (selected == R.id.vjti){

                        if (user_otp.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter OTP!", Toast.LENGTH_SHORT).show();
                        }else{
                            updateDB(user_otp,user_event);
                        }

                    }else{

                        if (user_otp.isEmpty() || user_college_code.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter the missing details (OTP & CollegeCode)!", Toast.LENGTH_SHORT).show();
                        }else {
                            updateDB(user_otp, user_event, user_college_code);
                        }

                    }
                }

        });
    }

    private void updateDB(String otp,String event) {

        startLoading();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",event);
            jsonObject.put("type",1);
            jsonObject.put("otp",otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("http://technovanza.org/committeeApp")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        stopLoading();
                        int res = response.code();
                        if (res == 200){
                            Toast.makeText(MainActivity.this,"Success!", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }else if (res == 210){
                            Toast.makeText(MainActivity.this, "Participant not registered for the event OR Otp already used!", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }else if(res == 250){
                            Toast.makeText(MainActivity.this, "Invalid College !", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }else if( res == 220){
                            Toast.makeText(MainActivity.this, "Please enter proper OTP & College Code !", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }else if (res == 230 || res == 240){
                            Toast.makeText(MainActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        stopLoading();
                        Toast.makeText(MainActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void updateDB(String otp,String event, String code){

        startLoading();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",event);
            jsonObject.put("type",2);
            jsonObject.put("otp",otp);
            jsonObject.put("collegeCode",code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("http://technovanza.org/committeeApp")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        stopLoading();
                        int res = response.code();
                        if (res == 200){
                            Toast.makeText(MainActivity.this,"Success!", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }else if (res == 210){
                            Toast.makeText(MainActivity.this, "Participant not registered for the event OR Otp already used!", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }else if(res == 250){
                            Toast.makeText(MainActivity.this, "Invalid College !", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }else if( res == 220){
                            Toast.makeText(MainActivity.this, "Please enter proper OTP & College Code !", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }else if (res == 230 || res == 240){
                            Toast.makeText(MainActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                            clearAll();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        stopLoading();
                        Toast.makeText(MainActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startLoading(){

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Submitting...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    private void stopLoading(){

        progressDialog.cancel();
    }



    private void clearAll(){

        otp.setText("");
        collegeCode.setText("");
        eventName.setSelection(0);
        collegeType.check(R.id.vjti);

    }
    private List<String> eventsNameData(){

        List<String> events = new ArrayList<String>();
        events.add("AQUA");
        events.add("CLIMB-E-ROPE");
        events.add("CODE-IN-X");
        events.add("CODEROYALE");
        events.add("CODESWAP");
        events.add("CRYPTEXT");
        events.add("CWAY");
        events.add("DRONE");
        events.add("FAST-N-FURIOUS");
        events.add("IOT");
        events.add("JAVAGURU");
        events.add("MAKERS-SQUARE");
        events.add("MISSION-SQL");
        events.add("MONSTER-ARENA");
        events.add("MYST");
        events.add("RCMO");
        events.add("ROBO-MAZE");
        events.add("ROBOSOCCER");
        events.add("ROBOSUMO");
        events.add("ROBO-WARS");
        events.add("SHERLOCKED");
        events.add("SMART-CITY");
        events.add("TECHNO-HUNT");
        events.add("TPP");
        events.add("TRIMBLE-BIM-CONTEST");
        events.add("ULTIMATE-CODER");
        events.add("VRC");
        events.add("VSM");

        return events;
    }

}
