package erawanbikes.com.sample.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Models.RegisterTokenResponse;
import erawanbikes.com.sample.R;
import erawanbikes.com.sample.Retrofitservices.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by acer on 10/13/2017.
 */

public class ForgotFragment extends SlideFragment {

    private AppCompatEditText email;
    private ProgressDialog progressDialog;
    private Button forgot;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    public ForgotFragment() {
        // Required empty public constructor
    }

    public static ForgotFragment newInstance() {
        return new ForgotFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_forgot_pwd, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        this.email = (AppCompatEditText) view.findViewById(R.id.email);
        ImageView backimage = (ImageView)view.findViewById(R.id.backimage);
        this.forgot = (Button) view.findViewById(R.id.forgot);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        //  progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        this.forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ( email.getText() != null) {

                        String emal = email.getText().toString().trim();
                        if ( emal.length() != 0 && validate(emal)) {
                            upload(emal);
                        }
                        if (emal.length() == 0) {
                            email.setError("Email can't be empty");
                        }
                    }
                    if (email.getText() == null) {
                        email.setError("Email can't be empty");
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
                if (charSequence != null) {
                    String s = charSequence.toString();
                    if (!validate(s)) {
                        email.setError("Please enter valid email");
                    } else {
                        email.setError(null);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void upload(String emal) {
        //login
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", emal);
        ApiUtils.getErawanbikesService().register_email(map).enqueue(new Callback<RegisterTokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterTokenResponse> call, @NonNull Response<RegisterTokenResponse> response) {

                try {
                    Log.d("rsponse", "response: " + response.body());
                    if (response.body() != null && response.body().getToken() != null && response.body().getToken().trim().length() != 0) {
                        Toast.makeText(getActivity(), "Successfully logged in ...", Toast.LENGTH_SHORT).show();
                        Helper.storeLocally("token", response.body().getToken(), getActivity());
                        Log.d("rsponse", "response: " + response.body().getToken());
                        //  startActivity(new Intent(getActivity(), HomeActivity.class));
                        Intent i = new Intent(getActivity(),MainActivity.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(getActivity(), Helper.errorMsg, Toast.LENGTH_SHORT).show();
                        Helper.showSnackBar(ForgotFragment.this.getView(),"Invalid Credentials");
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
