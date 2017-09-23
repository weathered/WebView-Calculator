package com.alamin.webviewcalculator;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewCalculatorActivity extends Activity {
public String updateStr;
WebView calculatorWebView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        calculatorWebView = (WebView)findViewById(R.id.CalculatorView);
        //calculatorWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        calculatorWebView.addJavascriptInterface(new CalculationActivity(this), "Android");
        calculatorWebView.getSettings().setJavaScriptEnabled(true);
        
        calculatorWebView.getSettings().setDomStorageEnabled(true); // http://stackoverflow.com/questions/4157184/android-making-webview-domstorage-persistant-after-app-closed
        calculatorWebView.getSettings().setDatabaseEnabled(true);
        calculatorWebView.getSettings().setDatabasePath("/data/data/"+ getPackageName()+"/databases/");
        //testFunct();
        
        if(isNetworkStatusAvailable(getApplicationContext())){
        	Toast.makeText(getApplicationContext(), "internet available", Toast.LENGTH_SHORT).show();
        	calculatorWebView.loadUrl("file:///android_asset/calculator.html");
        }else{
        	Toast.makeText(getApplicationContext(), "internet not available", Toast.LENGTH_SHORT).show();
        	calculatorWebView.loadUrl("file:///android_asset/offlinecalculator.html");
        }
    }
    
    
   /* public void testFunct() {
    	calculatorWebView.setWebViewClient(new WebViewClient() {
    		
         	public void onPageFinished(WebView view, String url) {
         		//calculatorWebView.loadUrl("javascript: ( function() { document.getElementById('displa').innerHTML= '" + updateStr +  "';}) ();"  );
         		calculatorWebView.reload();
             }
         });
    }*/
    
    public static boolean isNetworkStatusAvailable (Context context) {
        // https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
    	ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    	NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    	boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    	return isConnected;
    }
    
    @Override
    public void onBackPressed() {
        if (calculatorWebView.canGoBack()) {
        	calculatorWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    
    
    @Override
	protected void onResume() {
		super.onResume();
	}  
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}  

	@Override
	protected void onPause() {
		super.onPause();
	}
}