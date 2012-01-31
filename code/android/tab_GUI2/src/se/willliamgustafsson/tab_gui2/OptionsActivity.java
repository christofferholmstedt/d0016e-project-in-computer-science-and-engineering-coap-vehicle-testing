package se.willliamgustafsson.tab_gui2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OptionsActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView textview = new TextView(this);
		textview.setText("This is the Options tab");
		setContentView(textview);
		
		
	}
}
