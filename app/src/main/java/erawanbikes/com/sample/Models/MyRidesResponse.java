package erawanbikes.com.sample.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer on 10/28/2017.
 */

public class MyRidesResponse {
    @SerializedName("bike_id")
    @Expose
    private String bikeId;
    @SerializedName("bike_model_name")
    @Expose
    private String bikeModelName;
    @SerializedName("warehouse_id")
    @Expose
    private String warehouseId;
    @SerializedName("warehouse_name")
    @Expose
    private String warehouseName;
    @SerializedName("booking_id")
    @Expose
    private Integer bookingId;
    @SerializedName("trip_status")
    @Expose
    private Object tripStatus;
    @SerializedName("total_price")
    @Expose
    private Object totalPrice;
    @SerializedName("location")
    @Expose
    private String location;

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getBikeModelName() {
        return bikeModelName;
    }

    public void setBikeModelName(String bikeModelName) {
        this.bikeModelName = bikeModelName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Object getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(Object tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Object getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Object totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
