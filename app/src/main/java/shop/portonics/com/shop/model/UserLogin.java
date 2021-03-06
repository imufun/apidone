package shop.portonics.com.shop.model;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import shop.portonics.com.shop.MainActivity;
import shop.portonics.com.shop.R;
import shop.portonics.com.shop.utils.AppController;

@SuppressWarnings("Convert2Diamond")
public class UserLogin extends AppCompatActivity {

    EditText email, password;
    Button UserLogin;
    String mMail, mPassword;
    TextView mRegisterActivity;
    AlertDialog.Builder mBuilder;
    RequestQueue mRequestQueue;
    private String Login_url = "http://localhost:8080/AndroidApi/UserLogin.php";

    Register mRegister;
    RegisterActivity registerActivity;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mBuilder = new AlertDialog.Builder(this);
        mRegister = new Register();

        email = (EditText) findViewById(R.id.UserEmail);
        password = (EditText) findViewById(R.id.UserPass);

        UserLogin = (Button) findViewById(R.id.btnLogn);
        mRegisterActivity = (TextView) findViewById(R.id.mRegisterActivity);

        mRequestQueue = Volley.newRequestQueue(this);

        UserLogin userLogin = new UserLogin();

        userLogin.RegisterACtivity();



        mRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        UserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMail = email.getText().toString();
                mPassword = password.getText().toString();


                if (mMail.equals("") || mPassword.equals("")) {
                    mBuilder.setTitle("Something went to wrong");
                    AlerDalogBox("Enter a valid Email & Password  ");
                } else {

                    //Request a string response from the provided URL.
                    StringRequest stringRequestUserLogin = new StringRequest(Request.Method.POST, Login_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if (code.equals("login_faild")) {
                                            mBuilder.setTitle("Login Error");
                                            AlerDalogBox(jsonObject.getString("message"));
                                        } else {
                                            Intent intent = new Intent(UserLogin.this, MainActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("email", jsonObject.getString("email"));
                                            bundle.putString("password", jsonObject.getString("password"));
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(UserLogin.getContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @SuppressWarnings("Convert2Diamond")
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //noinspection Convert2Diamond,Convert2Diamond,Convert2Diamond
                            @SuppressWarnings("Convert2Diamond") Map<String, String> params = new HashMap<String, String>();
                            params.put("email", mMail);
                            params.put("password", mPassword);

                            return params;
                        }
                    };

                    Log.d("userlogin", Login_url);
                    AppController.getInstance().addToRequestQueue(stringRequestUserLogin);
                }

            }

        });
    }


    public void AlerDalogBox(String message) {
        mBuilder.setMessage(message);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                email.setText("");
                password.setText("");
            }
        });
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    public void RegisterACtivity() {
//        Intent intent = new Intent(UserLogin.this, RegisterActivity.class);
//        startActivity(intent);
//

    }



}
