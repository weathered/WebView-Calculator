package com.alamin.webviewcalculator;


import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class CalculationActivity {
	
	WebViewCalculatorActivity calcActivity = null;
	WebView historyview;
	String operator = "@";
	char operator2;
	boolean exec = false, reloadFlag=false;
    double result=0;
    String text="";
    boolean errorFlag=false;
    Boolean point = false;
    WebView calculatorWebView2;
    String history, operand1, operand2, historytext;
    DBHandler dbHandler;
    
    CalculationActivity(Context c) {
    	calcActivity=(WebViewCalculatorActivity)c;
    }
    
    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String setOperand(String num) {
        if(exec){
            exec = false;
            operator="@";
        }
        text+=num;
        return text;
    }
    
    public String setOperation(String operation) {
		errorFlag=false;
		if(operator.equals("@")) operand1 = text;
        if(operator!="@" && !exec){
            if(isDouble(text))
            {
                if(operator.equals("+")){
                    result = result + Double.parseDouble(text);
                }
                if(operator.equals("-")){
                	result = result - Double.parseDouble(text);
                }
                if(operator.equals("*")){
                	result = result * Double.parseDouble(text);
                }
                if(operator.equals("/")){
                    if (Double.parseDouble(text) == 0) {
                        errorFlag = true;
                        result = 0;
                    } else {
                    	result = result / Double.parseDouble(text);
                    }
                }
            }
        }
        else{
            if(!exec)
            {
                if(!isDouble(text)) text = "0";
                result = Double.parseDouble(text);
            }
            else{
                exec=false;
            }
        }
        
        operator=operation;
        
        //Toast.makeText(calcActivity, 2839+"", Toast.LENGTH_SHORT).show();
        text = "";
        if(!errorFlag)
            return String.valueOf(result);
        else{
            return "0";
        }
        
    }
    
    public String onEqual() {
    	errorFlag=false;
        if(operator!="@"){
            if(isDouble(text))
            {
            	operand1 = String.valueOf(result);
            	operand2 = text;
            	if(operator.equals("+")){
                    result = result + Double.parseDouble(text);
                }
                if(operator.equals("-")){
                	result = result - Double.parseDouble(text);
                }
                if(operator.equals("*")){
                	result = result * Double.parseDouble(text);
                }
                if(operator.equals("/")){
                    if (Double.parseDouble(text) == 0) {
                        errorFlag = true;
                        result = 0;
                    } else {
                    	result = result / Double.parseDouble(text);
                    }
                }
            }
        }else
        {
            if(!isDouble(text)) text = "0";
            result = Double.parseDouble(text);
        }
        exec = true;
        history = operand1 + operator + operand2 + " = " + result;
        addHistory(history);
        Toast.makeText(calcActivity, history, Toast.LENGTH_LONG).show();
        if(!errorFlag)
            return String.valueOf(result);
        else
        {
            return "0";            
        }
    }
    
    public String setPoint() {
        if(exec){
            text = "";
            exec = false;
            operator="@";
        }
        if(isDouble(text + '.')){
            text+= '.';
        }else if(text.length()==1 && text.charAt(0)=='-'){
            text+='.';
        }else if(text.length()==0) text = ".";
        return text;
    }
    
    
    public String onDelete(String str) {
    	if(!exec){
    	   if (text.length() > 0) {
                text = text.substring(0, text.length()-1);
                return text;
           }
        }else{
        	//calcActivity.testFunct();
        	if(str.length()==1){
        		text = "";
        		result = 0;
        		return "0";
        	}
        	str = str.substring(0, str.length()-1);
    	    //calcActivity.updateStr = str;
    	    text = str;
    	    result = Double.parseDouble(text);
    	    //calcActivity.testFunct();
    	    //reload();
        }
        return str;
    }
    public String onClear() {
        operator = "@";
        result = 0;
        text = "";
        calcActivity.updateStr = "";
        exec=false;
        return text;
    }
    
    public void addHistory(String _history){
		String str = _history;
		History history = new History(str);
		dbHandler = new DBHandler(calcActivity, null, null, 1);
		dbHandler.addHistory(history);
	}
    
    public void historyFunction(){
    	//calcActivity.calculatorWebView.clearCache(true);
    	//calcActivity.calculatorWebView.loadUrl("file:///android_asset/calculator.html");
    	calcActivity.calculatorWebView.loadUrl("file:///android_asset/history.html");
    }
    
    public String showHistory(){
    	dbHandler = new DBHandler(calcActivity, null, null, 1);
    	historytext = dbHandler.displayHistory();
    	
    	//calcActivity.calculatorWebView.reload();
    	//calcActivity.calculatorWebView.loadUrl("file:///android_asset/history.html");
    	
    	return historytext;
    }
    
    /*
    public void display(){
    	calcActivity.calculatorWebView.setWebViewClient(new WebViewClient() {
    		
    		
         	public void onPageFinished(WebView view, String url) {
         			calcActivity.calculatorWebView.canGoBack();
         			calcActivity.calculatorWebView.loadUrl("file:///android_asset/calculator.html");
         			calcActivity.calculatorWebView.loadUrl("javascript: ( function() { document.getElementById('h').innerHTML= '" + hi +  "';}) ();"  );
         			
             }
         });
    }
    */
    
}
