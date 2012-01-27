package se.williamgustafsson.GoogleTabExample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CoapActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView textview = new TextView(this);
		textview.setText("This is the CoAp tab");
		setContentView(textview);
		
		
	}
}
