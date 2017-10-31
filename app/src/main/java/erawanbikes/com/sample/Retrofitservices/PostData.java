package erawanbikes.com.sample.Retrofitservices;

/**
 * Created by acer on 9/7/2017.
 */

import android.util.Log;

import org.apache.commons.lang3.CharEncoding;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostData {
    public static String postData(ArrayList params, String serviceURL) {
        HttpClient httpclient = new DefaultHttpClient();
        String responseString = "";
        HttpPost httppost = new HttpPost(serviceURL);
        try {
            Log.i("Tag", "params:" + params);
            List nameValuePairs = new ArrayList();
            httppost.setEntity(new UrlEncodedFormEntity(params));
            responseString = EntityUtils.toString(httpclient.execute(httppost).getEntity(), CharEncoding.UTF_8);
            Log.i("Tag", "postData: " + responseString);
            return responseString;
        } catch (ClientProtocolException e) {
            return responseString;
        } catch (IOException e2) {
            return responseString;
        }
    }
}