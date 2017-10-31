package erawanbikes.com.sample.Models;

/**
 * Created by acer on 10/26/2017.
 */

public class Data
{
    private String warehouse_name;

    private String bike_id;

    private String booking_id;

    private String warehouse_id;

    private String created_at;

    private String trip_status;

    private String total_price;

    private String bike_model_name;

    public String getWarehouse_name ()
    {
        return warehouse_name;
    }

    public void setWarehouse_name (String warehouse_name)
    {
        this.warehouse_name = warehouse_name;
    }

    public String getBike_id ()
    {
        return bike_id;
    }

    public void setBike_id (String bike_id)
    {
        this.bike_id = bike_id;
    }

    public String getBooking_id ()
    {
        return booking_id;
    }

    public void setBooking_id (String booking_id)
    {
        this.booking_id = booking_id;
    }

    public String getWarehouse_id ()
    {
        return warehouse_id;
    }

    public void setWarehouse_id (String warehouse_id)
    {
        this.warehouse_id = warehouse_id;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getTrip_status ()
    {
        return trip_status;
    }

    public void setTrip_status (String trip_status)
    {
        this.trip_status = trip_status;
    }

    public String getTotal_price ()
    {
        return total_price;
    }

    public void setTotal_price (String total_price)
    {
        this.total_price = total_price;
    }

    public String getBike_model_name ()
    {
        return bike_model_name;
    }

    public void setBike_model_name (String bike_model_name)
    {
        this.bike_model_name = bike_model_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [warehouse_name = "+warehouse_name+", bike_id = "+bike_id+", booking_id = "+booking_id+", warehouse_id = "+warehouse_id+", created_at = "+created_at+", trip_status = "+trip_status+", total_price = "+total_price+", bike_model_name = "+bike_model_name+"]";
    }
}

