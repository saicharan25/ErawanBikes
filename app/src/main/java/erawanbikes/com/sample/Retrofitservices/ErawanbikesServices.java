package erawanbikes.com.sample.Retrofitservices;

import java.util.HashMap;


import erawanbikes.com.sample.Models.ChangePasswordResponse;
import erawanbikes.com.sample.Models.Data;
import erawanbikes.com.sample.Models.LoginTokenResponse;
import erawanbikes.com.sample.Models.MobilenumberRespopnse;
import erawanbikes.com.sample.Models.MyRidesResponse;
import erawanbikes.com.sample.Models.OTPResponse;
import erawanbikes.com.sample.Models.ProfileResponse;
import erawanbikes.com.sample.Models.RegisterTokenResponse;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by acer on 10/12/2017.
 */

public interface ErawanbikesServices {

    @FormUrlEncoded
    @POST("api/login")
    Call<LoginTokenResponse> login(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("api/signup")
    Call<RegisterTokenResponse> register_email(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("api/changepassword")
    Call<ChangePasswordResponse> change_password(@Header("Authorization") String token, @FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @Headers({"Content-Type: application/json"})
    @POST("api/edit_profile")
    Call<ProfileResponse> update_profile(@Header("Authorization") String token, @FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("api/verify")
    Call<MobilenumberRespopnse> mobile_number(@Header("Authorization") String token, @FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("api/verify_otp")
    Call<OTPResponse> otp_verify(@Header("Authorization") String token, @FieldMap HashMap<String, Object> map);


    /*@Headers({"Content-Type: application/json"})
    @POST("api/bookings")
    Call<Data> my_rides(@Header("Authorization") String token);*/

}
