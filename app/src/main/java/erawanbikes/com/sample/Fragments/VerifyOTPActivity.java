package erawanbikes.com.sample.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Models.MobilenumberRespopnse;
import erawanbikes.com.sample.Models.OTPResponse;
import erawanbikes.com.sample.R;
import erawanbikes.com.sample.Retrofitservices.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by acer on 10/23/2017.
 */

public class VerifyOTPActivity extends Activity {

    Button mobile_send, otp_send, resend_otp;
    EditText mobile, otp;
    String locationVal, fromVal, toVal, bikeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_booking);

        mobile = (EditText) findViewById(R.id.mobile);
        otp = (EditText) findViewById(R.id.otp);
        mobile_send = (Button) findViewById(R.id.mobile_send);
        otp_send = (Button) findViewById(R.id.otp_send);
        resend_otp = (Button) findViewById(R.id.resend_otp);

        locationVal = Helper.getLocalValue(getApplicationContext(), "location");
        fromVal = Helper.getLocalValue(getApplicationContext(), "from_date");
        toVal = Helper.getLocalValue(getApplicationContext(), "to_date");
        bikeId = getIntent().getStringExtra("bikeid");

        mobile_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadnumber(mobile.getText().toString());
            }
        });
        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadnumber(mobile.getText().toString());
            }
        });
        otp_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadotp(otp.getText().toString(), "1", getIntent().getStringExtra("bikeid"), Helper.getLocalValue(getApplicationContext(), "location"),
                        Helper.getLocalValue(getApplicationContext(), "from_date"), Helper.getLocalValue(getApplicationContext(), "to_date"));
            }
        });
    }

    private void uploadnumber(String mobile) {
        //EditProfile
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile_number", mobile);

        Log.d("Tag", "token: " + Helper.getAuthenticationHeader(VerifyOTPActivity.this));
        Log.d("Tag", "map: " + map);

        ApiUtils.getErawanbikesService().mobile_number(Helper.getAuthenticationHeader(VerifyOTPActivity.this), map)
                .enqueue(new Callback<MobilenumberRespopnse>() {
                    @Override
                    public void onResponse(@NonNull Call<MobilenumberRespopnse> call, @NonNull retrofit2.Response<MobilenumberRespopnse> response) {

                        try {
                            if (response.body() != null && response.body().getStatus() != null
                                    && response.body().getStatus() == "200") {
                                Toast.makeText(VerifyOTPActivity.this, "OTP has been sent to your mobile number", Toast.LENGTH_SHORT).show();
                                Log.d("Tag", "status: " + response.body().getStatus());
                                Log.d("Tag", "message: " + response.body().getMessage());
                            } else if (response.body().getStatus() == "400") {
                                Toast.makeText(getApplicationContext(), "Some parameters is missing", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<MobilenumberRespopnse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void uploadotp(String otpnbr, String warehouseid, String bikeidVal, String pickuppoint, String fromTime, String toTime) {
        //EditProfile
        HashMap<String, Object> map = new HashMap<>();
        map.put("otp", otpnbr);
        map.put("warehouse_id", warehouseid);
        map.put("bike_id", bikeidVal);
        map.put("pickup_location", pickuppoint);
        map.put("from_date", fromTime);
        map.put("to_date", toTime);

        Log.d("Tag", "token: " + Helper.getAuthenticationHeader(VerifyOTPActivity.this));
        Log.d("Tag", "map: " + map);

        ApiUtils.getErawanbikesService().otp_verify(Helper.getAuthenticationHeader(VerifyOTPActivity.this), map)
                .enqueue(new Callback<OTPResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<OTPResponse> call, @NonNull retrofit2.Response<OTPResponse> response) {

                        try {
                            if (response.body() != null && response.body().getStatus() != null
                                    && response.body().getStatus() == "200") {
                                Toast.makeText(getApplicationContext(), "Booking is confirmed", Toast.LENGTH_SHORT).show();
                            } else if (response.body().getStatus() == "400") {
                                Toast.makeText(getApplicationContext(), "Some parameters is missing", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<OTPResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
