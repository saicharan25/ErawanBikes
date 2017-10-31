package erawanbikes.com.sample.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer on 10/12/2017.
 */

public class RegisterTokenResponse {
    @SerializedName("status")
    @Expose
    private String status ;
    @SerializedName("message")
    @Expose
    private String message ;
    @SerializedName("token")
    @Expose
    private String token ;

    public RegisterTokenResponse(String status, String token,String message) {
        this.status = status;
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
