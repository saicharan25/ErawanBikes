package erawanbikes.com.sample.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import erawanbikes.com.sample.R;


/**
 * Created by acer on 10/17/2017.
 */

public class AboutusFragment extends Fragment implements View.OnClickListener {
    private EditText from_time, to_time;
    private Button search;
    private  WebView mWebView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        View v=inflater.inflate(R.layout.fragment_aboutus, container, false);
        mWebView = (WebView) v.findViewById(R.id.webView);
        mWebView.loadUrl("http://erawanbikes.com/aboutus");
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
        return v;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

        }
    }

}
