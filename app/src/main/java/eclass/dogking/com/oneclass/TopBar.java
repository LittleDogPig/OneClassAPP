package eclass.dogking.com.oneclass;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import eclass.dogking.com.oneclass.R;

public class TopBar extends RelativeLayout {
	
	public TopBar(Context context, AttributeSet attrs){
		super(context,attrs);
		LayoutInflater.from(context).inflate(R.layout.title,this);
		
		ImageButton leftButton=(ImageButton) findViewById(R.id.title_back);
		TextView titleTextView = (TextView) findViewById(R.id.title_text);
		leftButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				((Activity) getContext()).finish();
				
			}
			
		});
		
		 TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
	         
	        String titleText = typedArray.getString(R.styleable.TopBar_titleText);
	        typedArray.recycle();
	        titleTextView.setText(titleText);  
	        
		
	}
}
