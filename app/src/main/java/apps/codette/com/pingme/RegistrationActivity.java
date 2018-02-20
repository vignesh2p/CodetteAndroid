package apps.codette.com.pingme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {

    private EditText orgView;

    private EditText userView;

    private EditText emailView;

    private EditText phoneView;

    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        mPasswordView = (EditText) findViewById(R.id.registration_password);
        phoneView = (EditText) findViewById(R.id.phone);
        emailView = (EditText) findViewById(R.id.email);
        orgView = (EditText) findViewById(R.id.organisation);
        userView = (EditText) findViewById(R.id.userid);


        /*mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        })*/;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                registerUser();
            }
        });
    }


    private void registerUser(){
        String org = null, email = null, phn = null, password = null, user= null;
        org = orgView.getText().toString();
        email = emailView.getText().toString();
        phn = phoneView.getText().toString();
        user = userView.getText().toString();
        password = mPasswordView.getText().toString();

        RequestParams params = new RequestParams();
        params.add("userid",email);
        params.add("password",password);
        params.add("orgname",org);
        params.add("orgemail",email);
        params.add("username",user);
        params.add("orgid", UUID.randomUUID().toString());
        params.add("orgphoneno", phn);

        RestCall.post("register",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONObject testV= null;
                try {
                    testV = new JSONObject(new String(responseBody));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                Toast.makeText(RegistrationActivity.this,"  response :: "+testV.toString() ,Toast.LENGTH_LONG).show();

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(RegistrationActivity.this,"name ::"+ responseBody ,Toast.LENGTH_LONG).show();

            }
        });

    }
}
