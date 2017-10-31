package erawanbikes.com.sample.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Login.LoginFragment;
import erawanbikes.com.sample.Models.RegisterTokenResponse;
import erawanbikes.com.sample.R;
import erawanbikes.com.sample.Retrofitservices.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by acer on 10/12/2017.
 */

public class RegistrationFragment extends SlideFragment {

    private AppCompatEditText firstname, email, password;
    private TextView sigin;
    private ProgressDialog progressDialog;
    private Button signup;
    Fragment newFragment;
    boolean t_c;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ImageView backimage = (ImageView)view.findViewById(R.id.backimage);
        this.sigin = (TextView) view.findViewById(R.id.sigin);
        this.firstname = (AppCompatEditText) view.findViewById(R.id.firstname);
        this.email = (AppCompatEditText) view.findViewById(R.id.email);
        this.password = (AppCompatEditText) view.findViewById(R.id.password);
        SwitchCompat switchCompat = (SwitchCompat)view.findViewById(R.id.rememberme);
        this.signup = (Button) view.findViewById(R.id.signup);
        this.sigin.setText(Html.fromHtml("Already have an Account ? <u><b>Sign In </b> </u>"));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        //  progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RegistrationFragment.this.t_c = b;
            }
        });
        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        this.sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment = new LoginFragment();
                transaction.replace(R.id.intro, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (Helper.checkNow(getActivity())){
                    if (firstname.getText() != null && password.getText() != null && email.getText() != null) {
                        String frstname = firstname.getText().toString().trim();
                        String passwrd = password.getText().toString().trim();
                        String emal = email.getText().toString().trim();
                        if (frstname.length() != 0 && passwrd.length() != 0 && emal.length() != 0 && validate(emal)) {

                            if (t_c) {
                                upload(frstname, emal, passwrd);
                            } else
                                Helper.showSnackBar(RegistrationFragment.this.getView(),"Please agree our terms and conditions");
                        }
                        if (frstname.length() == 0) {
                            firstname.setError("Name can't be empty");
                        }
                        if (passwrd.length() == 0) {
                            password.setError("Password can't be empty");
                        }
                        if (emal.length() == 0) {
                            email.setError("Email can't be empty");
                        }
                    }
                    if (firstname.getText() == null) {
                        firstname.setError("Name can't be empty");
                    }
                    if (password.getText() == null) {
                        password.setError("Password can't be empty");
                    }
                    if (email.getText() == null) {
                        email.setError("Email can't be empty");
                    }
                    }else{
                        Helper.showSnackBar(RegistrationFragment.this.getView(), "No internet connection");
                    }
                } catch (Exception e) {

                }
            }
        });
        firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    firstname.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    String s = charSequence.toString();
                    if (!validate(s)) {
                        email.setError("Please enter valid email");
                    } else {
                        password.setError(null);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null)
                    password.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void upload(String frstname, String email, String password) {
        //login
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", frstname);
        map.put("email", email);
        map.put("password", password);
        ApiUtils.getErawanbikesService().register_email(map).enqueue(new Callback<RegisterTokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterTokenResponse> call, @NonNull Response<RegisterTokenResponse> response) {

                try {
                    Log.d("rsponse", "response: " + response.body());
                    if (response.body() != null && response.body().getToken() != null && response.body().getToken().trim().length() != 0) {
                       Helper.storeLocally("token", response.body().getToken(), getActivity());
                        Log.d("rsponse", "response: " + response.body().getToken());
                        Intent i = new Intent(getActivity(),MainActivity.class);
                        i.putExtra("name", firstname.getText().toString().trim());
                        i.putExtra("tokenid", response.body().getToken());
                        Log.d("NAME", "response: " + firstname.getText().toString().trim());
                        startActivity(i);

                    } else {
                        Toast.makeText(getActivity(), Helper.errorMsg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<RegisterTokenResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}


