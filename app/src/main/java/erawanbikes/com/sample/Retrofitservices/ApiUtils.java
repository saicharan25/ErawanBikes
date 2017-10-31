package erawanbikes.com.sample.Retrofitservices;


/**
 * Created by acer on 10/12/2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://erawanbikes.com";

    public static ErawanbikesServices getErawanbikesService() {
        return  RetrofitClient.getClient(BASE_URL).create(ErawanbikesServices.class);
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

}
