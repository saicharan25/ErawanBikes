package erawanbikes.com.sample.Models;

/**
 * Created by acer on 10/18/2017.
 */
public class Bikes
{
    private String bike_id;

    private String weekEnd_price;

    private String bike_model;

    private String weekDay_price;

    private String bike_image;

    public String getBike_id ()
    {
        return bike_id;
    }

    public void setBike_id (String bike_id)
    {
        this.bike_id = bike_id;
    }

    public String getWeekEnd_price ()
    {
        return weekEnd_price;
    }

    public void setWeekEnd_price (String weekEnd_price)
    {
        this.weekEnd_price = weekEnd_price;
    }

    public String getBike_model ()
    {
        return bike_model;
    }

    public void setBike_model (String bike_model)
    {
        this.bike_model = bike_model;
    }

    public String getWeekDay_price ()
    {
        return weekDay_price;
    }

    public void setWeekDay_price (String weekDay_price)
    {
        this.weekDay_price = weekDay_price;
    }

    public String getBike_image ()
    {
        return bike_image;
    }

    public void setBike_image (String bike_image)
    {
        this.bike_image = bike_image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [bike_id = "+bike_id+", weekEnd_price = "+weekEnd_price+", bike_model = "+bike_model+", weekDay_price = "+weekDay_price+", bike_image = "+bike_image+"]";
    }
}
