package com.example.mileagetracker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {
	//main activity components
	private CalendarView cal;
	private TextView monthlyTotal;
	private TextView weeklyTotal;
	
	private double monthTotal = 0;
	private double weekTotal = 0;
	
	private double[] monthlyTotalArray = new double[12];
	private int currentMonth;
	private int previousMonth;
//	private int[] weeklyTotalArray = new int[52];
	
	//popup components
	private PopupWindow popUp;
	private Button btnClosePopup;
	private Button btnSubmit;
	private EditText entryBox;
	
	private String entryString;
	//private double entryDouble;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        for(@SuppressWarnings("unused") double number : monthlyTotalArray ) {
        	number = 0;
        }
        
        Calendar c = new GregorianCalendar();
        previousMonth = c.get(Calendar.MONTH);
        
        monthlyTotal = (TextView) findViewById(R.id.monthlyTotalDisplay);
        weeklyTotal = (TextView) findViewById(R.id.weeklyTotalDisplay);
        
        cal = (CalendarView) findViewById(R.id.calendarView1);
        
        cal.setOnDateChangeListener(new OnDateChangeListener() {
        	
		@Override
		public void onSelectedDayChange(CalendarView view, int year, int month,
				int dayOfMonth) {
			

//			Toast.makeText(getBaseContext(),"Selected Date is\n\n"
//					+ month + " : " + dayOfMonth + " : " + year , 
//					Toast.LENGTH_LONG).show();
			
			currentMonth = month;
			if (currentMonth != previousMonth) {
				monthlyTotal.setText(Double.toString(monthlyTotalArray[currentMonth]));
				previousMonth = currentMonth;
			}
			initiatePopupWindow();
		}
	});
    }

    private void initiatePopupWindow() {
    	try {
    		LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		View layout = inflater.inflate(R.layout.activity_screen_popup,
    				(ViewGroup) findViewById(R.id.popup_element));
    		popUp = new PopupWindow(layout, 300, 370, true);
    		popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);

    		entryBox = (EditText) layout.findViewById(R.id.entryBox);
    		
    		btnSubmit = (Button) layout.findViewById(R.id.btnSubmit);
    		btnSubmit.setOnClickListener(submit_button_listener);
    		
    		btnClosePopup = (Button) layout.findViewById(R.id.btnClose);
    		btnClosePopup.setOnClickListener(cancel_button_click_listener);
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    private OnClickListener submit_button_listener = new OnClickListener() {
		public void onClick(View v) {
			entryString = entryBox.getText().toString();
			
			monthlyTotalArray[currentMonth] += Double.parseDouble(entryString);
//			monthTotal += Double.parseDouble(entryString);
			
//			String monthStringTotal = Double.toString(monthTotal);
//			monthlyTotal.setText(monthStringTotal);
			String monthStringTotal = Double.toString(monthlyTotalArray[currentMonth]);
			monthlyTotal.setText(monthStringTotal);
		}
    };
    
    private OnClickListener cancel_button_click_listener = new OnClickListener() {
    	public void onClick(View v) {
    		popUp.dismiss();
    	}
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
