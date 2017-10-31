package erawanbikes.com.sample.Fragments;

import android.app.ProgressDialog;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Models.ProfileResponse;
import erawanbikes.com.sample.R;
import erawanbikes.com.sample.Retrofitservices.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by acer on 10/17/2017.
 */

public class EditProfileFragment extends Fragment {
    private AppCompatEditText fullname, date_birth, email, phn_nbr, address;
    private Button save_change;
    private Spinner gender;
    private ProgressDialog progressDialog;
    public String response;
    String selected_val, value;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.fullname = (AppCompatEditText) view.findViewById(R.id.fullname);
        this.date_birth = (AppCompatEditText) view.findViewById(R.id.date_birth);
        this.email = (AppCompatEditText) view.findViewById(R.id.email);
        this.phn_nbr = (AppCompatEditText) view.findViewById(R.id.phn_nbr);
        this.address = (AppCompatEditText) view.findViewById(R.id.address);
        this.save_change = (Button) view.findViewById(R.id.save_change);
        this.gender = (Spinner) view.findViewById(R.id.gender);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        //  progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        selected_val = gender.getSelectedItem().toString();
        if (selected_val.contains("Select")) {
            value = "Select";
        } else if (selected_val.contains("Male")) {
            value = "Male";
        } else if (selected_val.contains("Female")) {
            value = "Female";
        } else if (selected_val.contains("Other")) {
            value = "Other";
        }
        this.save_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.checkNow(getActivity())) {
                    upload(fullname.getText().toString(), gender.getSelectedItem().toString(), date_birth.getText().toString(), email.getText().toString()
                            , phn_nbr.getText().toString(), address.getText().toString());
                } else {
                    Helper.showSnackBar(EditProfileFragment.this.getView(), "No internet connection");
                }
            }
        });
    }

    private void upload(String name, String selected_val, String dob, String mail, String nbr, String adrs) {
        //EditProfile
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("gender", selected_val);
        map.put("dob", dob);
        map.put("email", mail);
        map.put("mobile", nbr);
        map.put("address", adrs);
        Log.d("Tag", "token: " + Helper.getAuthenticationHeader(getActivity()));
        Log.d("Tag", "map: " + map);

        ApiUtils.getErawanbikesService().update_profile(Helper.getAuthenticationHeader(getActivity()), map)
                .enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {

                        try {
                            if (response.body() != null && response.body().getStatus() != null
                                    && response.body().getStatus() == "200") {
                                Log.d("Tag", "status: " + response.body().getStatus());
                                Log.d("Tag", "message: " + response.body().getMessage());
                                Helper.showSnackBar(EditProfileFragment.this.getView(),"Profile Updated");
                                String Address = response.body().getAddress();
                                Log.d("Tag", "Address: " + Address);

                            } else if (response.body().getStatus() == "400") {
                                Helper.showSnackBar(EditProfileFragment.this.getView(),"Try Again!");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
