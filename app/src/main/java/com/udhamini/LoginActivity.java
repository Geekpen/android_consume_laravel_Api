package com.udhamini;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Bind;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;




import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class LoginActivity extends AppCompatActivity {
    public String token_value;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        //authentication logic
        if (validate()) {

            RequestQueue queue = Volley.newRequestQueue(this);

            //String url = "http://udhamini.com/api/login";
            String url = String.format("http://udhamini.com/api/login?email=%1$s&password=%2$s",email,password);
            //String url = "http://udhamini.com/api/login?email=brianabungu@gmail.com&password=Aud!t2o12";



            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //Log.d(TAG, response.toString());
                    try {
                        // Parsing json object response
                        // response will be a json object
                        String status_code = response.getString("code");
                        String token_value = response.getString("token_value");

                        //Log.d(TAG, status_code);

                        if(status_code.equals("200")){

                            SharedPrefManager.getInstance(getApplicationContext())
                                    .userLogin(token_value);

                            //String UserToken = SharedPrefManager.getInstance(getApplicationContext())
                            //       .getToken();

                            //Log.d(TAG, UserToken);
                            GetDetailsFromApi();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


                        }
                        else{

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    // hide the progress dialog
                }
            });
            queue.add(jsonObjReq);
            //Intent intent = new Intent(this, MainActivity.class);
            //startActivity(intent);

        }


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }



    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }



    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public boolean GetDetailsFromApi() {
        RequestQueue detailsFromApi = Volley.newRequestQueue(this);
        String url = "http://udhamini.com/api/user-details";
        final String active_token = "Bearer "+SharedPrefManager.getToken();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //Log.d(TAG, response.toString());
                try {
                    // Parsing json object response
                    // response will be a json object
                    //String status_code = response.getString("code");
                    String get_user = response.getString("user");
                    JSONObject user = new JSONObject(get_user);

                    //User details
                    String user_id = user.getString("id");
                    String first_name = user.getString("first_name");
                    String last_name = user.getString("last_name");
                    String email_address = user.getString("email");

                    //write to SharedPrefManager
                    SharedPrefManager.getInstance(getApplicationContext())
                            .writeUserId(user_id);

                    SharedPrefManager.getInstance(getApplicationContext())
                            .writeFirstName(first_name);

                    SharedPrefManager.getInstance(getApplicationContext())
                            .writeLastName(last_name);

                    SharedPrefManager.getInstance(getApplicationContext())
                            .writeEmailAddress(email_address);


                    String email = SharedPrefManager.getInstance(getApplicationContext())
                            .getFirstName();


                    //Log.d(TAG, email);




                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", active_token);
                return headers;
            }
        };
        detailsFromApi.add(jsonObjReq);


        return true;

    }


}

