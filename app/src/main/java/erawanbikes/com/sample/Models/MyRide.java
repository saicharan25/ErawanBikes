package erawanbikes.com.sample.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by acer on 10/28/2017.
 */

public class MyRide {


    @SerializedName("data")
    @Expose
    private List<MyRidesResponse> data = null;

    public List<MyRidesResponse> getData() {
        return data;
    }

    public void setData(List<MyRidesResponse> data) {
        this.data = data;
    }
}

