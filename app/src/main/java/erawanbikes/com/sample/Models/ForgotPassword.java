package erawanbikes.com.sample.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer on 10/13/2017.
 */

public class ForgotPassword {
    @SerializedName("token")
    @Expose
    private String token ;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
