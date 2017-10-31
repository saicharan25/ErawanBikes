package erawanbikes.com.sample.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.lang3.CharEncoding;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Models.ChangePasswordResponse;
import erawanbikes.com.sample.R;
import erawanbikes.com.sample.Retrofitservices.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by acer on 10/17/2017.
 */

public class ChangePasswordFragment extends Fragment {
    private AppCompatEditText change_password, confirm_password;
    private Button change_password_button;
    private ProgressDialog progressDialog;
    public String response;
    int status;
    String message;
    InputStream is = null;
    String pass, cpass;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file

        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.change_password = (AppCompatEditText) view.findViewById(R.id.change_password);
        this.confirm_password = (AppCompatEditText) view.findViewById(R.id.confirm_password);
        this.change_password_button = (Button) view.findViewById(R.id.change_password_button);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        //  progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        this.change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("firstpwd", "pwd1: " + change_password.getText().toString());
                Log.d("confirmpwd", "pwd2: " + confirm_password.getText().toString());
                try {
                    if (Helper.checkNow(getActivity())) {
                        if (change_password.getText() != null && confirm_password.getText() != null) {
                            pass = change_password.getText().toString();
                            cpass = confirm_password.getText().toString();
                            if (pass.length() != 0 && cpass.length() != 0 && validateequelpassword()) {
                                upload(pass, cpass);
                            }
                            if (pass.length() == 0) {
                                change_password.setError("Password can't be empty");
                            }
                            if (cpass.length() == 0) {
                                confirm_password.setError("ConfirmPassword can't be empty");
                            }
                        }
                        if (change_password.getText() == null) {
                            change_password.setError("Password can't be empty");
                        }
                        if (confirm_password.getText() == null) {
                            confirm_password.setError("ConfirmPassword can't be empty");
                        }
                    } else {
                        Helper.showSnackBar(ChangePasswordFragment.this.getView(), "No Internet Connection...!");
                    }
                } catch (Exception e) {
                }
            }
        });
    }
    private boolean validateequelpassword() {
        boolean temp = true;
        if (!pass.equals(cpass)) {
            Toast.makeText(getActivity(), "Passwords mismatch...!", Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return temp;
    }
   /*

    private boolean validatePassword() {
        if (!this.change_password.getText().toString().trim().isEmpty()
                && !this.confirm_password.getText().toString().trim().isEmpty()) {
            return true;
        }
        this.change_password.setError(getString(R.string.err_msg_enter_password));
        Helper.showSnackBar(ChangePasswordFragment.this.getView(), getString(R.string.err_msg_enter_password));
        return false;
    }*/

    class MyAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog;
        ArrayList paramsList = new ArrayList();

        MyAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                this.asyncDialog = new ProgressDialog(getActivity());
                this.asyncDialog.setMessage(getActivity().getString(R.string.loading));
                this.asyncDialog.show();
                ChangePasswordFragment.this.response = "";
                paramsList.add(new BasicNameValuePair("password", change_password.getText().toString()));
                paramsList.add(new BasicNameValuePair("password_confirmation", confirm_password.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(String aDouble) {
            super.onPostExecute(aDouble);
            try {
                this.asyncDialog.dismiss();
                try {
                    JSONObject data = new JSONObject(response);
                    status = Integer.parseInt(data.getString("status"));
                    message = data.getString("message");
                    if (status == 200) {
                        Helper.showSnackBar(ChangePasswordFragment.this.getView(), "Redirect to Login Page!!!!");
                    } else if (status == 400) {
                        Helper.showSnackBar(ChangePasswordFragment.this.getView(), "Failure");
                    } else {

                    }

                } catch (JSONException e) {
                    Helper.showSnackBar(ChangePasswordFragment.this.getView(), "Try Again!");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(String... params) {
            try {
                ChangePasswordFragment.this.response = postData(paramsList, "http://erawanbikes.com/api/changepassword");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ChangePasswordFragment.this.response;
        }

        public String postData(ArrayList params, String serviceURL) {
            HttpClient httpclient = new DefaultHttpClient();
            String responseString = "";
            HttpPost httppost = new HttpPost(serviceURL);

            try {
                Log.i("Tag", "params:" + params);
                httppost.setHeader("Content-Type", "application/json");
                httppost.setHeader("Authorization", Helper.getAuthenticationHeader(getActivity()));
                httppost.setEntity(new UrlEncodedFormEntity(params));
                responseString = EntityUtils.toString(httpclient.execute(httppost).getEntity(), CharEncoding.UTF_8);
                Log.d("Tag", "token: " + Helper.getAuthenticationHeader(getActivity()));
                Log.i("Tag", "postData: " + responseString);
                return responseString;
            } catch (ClientProtocolException e) {
                return responseString;
            } catch (IOException e2) {
                return responseString;
            }
        }
    }

    private void upload(String password, String cpassword) {
        //changepassword
        HashMap<String, Object> map = new HashMap<>();
        map.put("password", password);
        map.put("password_confirmation", cpassword);
        Log.d("Tag", "changepwdtoken: " + Helper.getAuthenticationHeader(getActivity()));
        Log.d("Tag", "map: " + map);


        ApiUtils.getErawanbikesService().change_password(Helper.getAuthenticationHeader(getActivity()), map)
                .enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ChangePasswordResponse> call, @NonNull Response<ChangePasswordResponse> response) {

                        try {
                            if (response.body() != null && response.body().getStatus() != null
                                    && response.body().getStatus() == "200") {
                                Log.d("Tag", "status: " + response.body().getStatus());
                                Log.d("Tag", "message: " + response.body().getMessage());
                                Helper.showSnackBar(ChangePasswordFragment.this.getView(),"Updated Password Successfully");
                            } else if (response.body().getStatus() == "400") {
                                Helper.showSnackBar(ChangePasswordFragment.this.getView(),"Try Again!");
                            } else {
                                response.errorBody(); // do something with that
                            }

                        } catch (Exception e) {
                            Log.d("Tag", "message: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
