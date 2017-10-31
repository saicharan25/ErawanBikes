package erawanbikes.com.sample.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import erawanbikes.com.sample.R;

/**
 * Created by acer on 10/13/2017.
 */

public class LoginActivity  extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        startActivity(new Intent(this, MainIntroActivity.class));
    }
}
