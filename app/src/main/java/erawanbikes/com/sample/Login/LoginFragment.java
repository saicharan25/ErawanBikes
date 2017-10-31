package erawanbikes.com.sample.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import erawanbikes.com.sample.Fragments.ForgotFragment;
import erawanbikes.com.sample.Fragments.MainActivity;
import erawanbikes.com.sample.Fragments.RegistrationFragment;
import erawanbikes.com.sample.Models.LoginTokenResponse;
import erawanbikes.com.sample.Models.LoginTokenResponse;
import erawanbikes.com.sample.R;
import erawanbikes.com.sample.Retrofitservices.ApiUtils;
import erawanbikes.com.sample.Retrofitservices.ErawanbikesServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends SlideFragment {

    private AppCompatEditText email, password;
    private TextView forgotpassword, signup;
    private ProgressDialog progressDialog;
    private Button login;
    boolean status;
    CheckBox rememberme;
    ErawanbikesServices mService;
    public String usernamestr, passwordstr;
    Fragment newFragment;
    private String Email, Password;
    boolean remembermevalue;
    public String response;
    String token;
    boolean t_c;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        this.signup = (TextView) view.findViewById(R.id.signup);
        this.forgotpassword = (TextView) view.findViewById(R.id.forgotpassword);
        this.email = (AppCompatEditText) view.findViewById(R.id.email);
        this.password = (AppCompatEditText) view.findViewById(R.id.password);
        this.login = (Button) view.findViewById(R.id.login);
        ImageView backimage = (ImageView)view.findViewById(R.id.backimage);
        SwitchCompat switchCompat = (SwitchCompat)view.findViewById(R.id.rememberme);
        //   this.rememberme.setOnCheckedChangeListener(new RememberClass());
        this.remembermevalue = true;
        this.forgotpassword.setText(Html.fromHtml("Forgot Password ?"));
        this.signup.setText(Html.fromHtml("Don't have an account ? <u><b>Sign up </b> </u>"));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        //  progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LoginFragment.this.t_c = b;
            }
        });
        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        this.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment = new RegistrationFragment();
                transaction.replace(R.id.intro, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        this.forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment = new ForgotFragment();
                transaction.replace(R.id.intro, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Helper.checkNow(getActivity())){
                    if ( password.getText() != null && email.getText() != null) {
                        String passwrd = password.getText().toString().trim();
                        String emal = email.getText().toString().trim();
                        if ( passwrd.length() != 0 && emal.length() != 0 && validate(emal)) {
                            upload(emal, passwrd);
                          //  Toast.makeText(getActivity(), "Login Called", Toast.LENGTH_SHORT).show();
                        }
                        if (passwrd.length() == 0) {
                            password.setError("Password can't be empty");
                        }
                        if (emal.length() == 0) {
                            email.setError("Email can't be empty");
                        }
                    }
                    if (password.getText() == null) {
                        password.setError("Password can't be empty");
                    }
                    if (email.getText() == null) {
                        email.setError("Email can't be empty");
                    }
                    }else{
                        Helper.showSnackBar(LoginFragment.this.getView(), "No internet connection");
                    }
                } catch (Exception e) {
                }
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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
    private void upload( String email, String password) {
        //login
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        ApiUtils.getErawanbikesService().login(map).enqueue(new Callback<LoginTokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginTokenResponse> call, @NonNull Response<LoginTokenResponse> response) {

                try {
                    Log.d("Tag", "response: " + response.body());
                    if (response.body() != null && response.body().getToken() != null && response.body().getToken().trim().length() != 0) {
                      //  Toast.makeText(getActivity(), "Successfully logged in ...", Toast.LENGTH_SHORT).show();
                        Helper.storeLocally("token", response.body().getToken(), getActivity());
                        Log.d("Tag", "tokenloginresponse: " + response.body().getToken());

                        if (LoginFragment.this.remembermevalue) {
                            Helper.store_locally("username", Email, LoginFragment.this.getContext());
                            Helper.store_locally("password", Password, LoginFragment.this.getContext());
                        } else {
                            SharedPreferences.Editor editor = LoginFragment.this.getContext().getSharedPreferences("erawanbikes", 0).edit();
                            editor.remove("username");
                            editor.remove("password");
                            editor.apply();
                        }
                        startActivity(new Intent(getActivity(), MainActivity.class));

                    } else {
                        Toast.makeText(getActivity(), Helper.errorMsg, Toast.LENGTH_SHORT).show();
                        Helper.showSnackBar(LoginFragment.this.getView(),"Invalid Credentials");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<LoginTokenResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
    /*class RememberClass implements CompoundButton.OnCheckedChangeListener {
        RememberClass() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            LoginFragment.this.remembermevalue = b;
        }
    }*/

