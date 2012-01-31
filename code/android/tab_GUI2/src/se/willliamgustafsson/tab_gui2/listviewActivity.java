package se.willliamgustafsson.tab_gui2;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class listviewActivity extends ListActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	/**
	 * @param args
	 */
	protected void listStuff(String stuffthatgoesintolist) {
		// TODO Auto-generated method stub

		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1));
		
		ArrayAdapter<String> AAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		AAdapter.add(stuffthatgoesintolist);
		setListAdapter(AAdapter);
		
		ListView lv = getListView(); // Grabs the ListView widget that is tied to the Activity.
		lv.setTextFilterEnabled(true); // Enable the Text Filter. Typing when this view has focus will filter the children to match the users input
	
		
	//	onListItemClick(lv, this, BIND_AUTO_CREATE, id)
		


		lv.setOnItemClickListener(new OnItemClickListener()
		{
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		    {
		    	
		    	Toast.makeText(getApplicationContext(), ((TextView) view).getText(),Toast.LENGTH_SHORT).show();
		    }
		});
	}

}
