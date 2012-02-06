package se.willliamgustafsson.tab_gui2;


import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

//-----------------------------------------------

//public class ServerActivity extends Activity{
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		TextView textview = new TextView(this);
//		textview.setText("This is the Server tab");
//		setContentView(textview);
//		
//		
//	}
//
//}

//--------------------------------------
//public class ServerActivity extends ListActivity {
//	
//	private LayoutInflater mInflater;
//	private Vector<RowData> data;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		
//		setContentView(R.layout.list_gui);
//		
//		
//		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//		data = new Vector<RowData>();
//		RowData rd = new RowData("item1", "description1");
//		data.add(rd);
//		rd = new RowData("item2", "description2");
//		data.add(rd);
//		rd = new RowData("item2", "description3");
//		data.add(rd);
//	}
//}

//------------------------------------

/*public class ServerActivity extends ListActivity {

 static final String[] MONSTERS = new String[] { "Zombie", "Vampire",
 "Warewulf", "Blob" };

 @Override
 public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);

 setListAdapter(new ArrayAdapter<String>(this,
 android.R.layout.simple_list_item_1, MONSTERS));

 ListView lv = getListView(); // Grabs the ListView widget that is tied
 // to the Activity.
 lv.setTextFilterEnabled(true); // Enable the Text Filter. Typing when
 // this view has focus will filter the
 // children to match the users input

 lv.setOnItemClickListener(new OnItemClickListener() {
 public void onItemClick(AdapterView<?> parent, View view,
 int position, long id) {

 Toast.makeText(getApplicationContext(),
 ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

 }
 });

 }
 }
 */

//--------------------------------------------

public class ServerActivity extends ListActivity {
	private LayoutInflater mInflater;
	private Vector<RowData> data;
	private TableRow Row_button;
	CustomAdapter adapter;
	MessageHandler msgHandler=new MessageHandler();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_gui);

		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();

		//just to fill upp the view with a few elements
		RowData rd = new RowData("Server 1", "Adress 1", "Mulle");
		data.add(rd);
		rd = new RowData("Server 2", "Adress 2", "Mull2");
		data.add(rd);
		rd = new RowData("Server 3", "Adress 3", "Mull3");
		data.add(rd);

		// Addknappen
		Row_button = (TableRow) findViewById(R.id.tableRow1);

		//om man klickar add
		Row_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				LayoutInflater factory = LayoutInflater
						.from(ServerActivity.this);

				final View textEntryView = factory.inflate(
						R.layout.builder_edittext, null);
				AlertDialog.Builder alert = new AlertDialog.Builder(
						ServerActivity.this);
				alert.setTitle("Add Server");
				alert.setMessage("Enter Servername and adress:");
				alert.setView(textEntryView);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int whichButton) {

								EditText hostname_temp = (EditText) textEntryView.findViewById(R.id.editTextHostname);
								String hostname = hostname_temp.getText().toString();
								
								EditText hostaddr_temp = (EditText) textEntryView.findViewById(R.id.editTextHostadress);
								String hostaddr;
								
								//defaults server if none given
								if (hostaddr_temp.getText().toString().equals("")) {
									hostaddr = getString(R.string.hostadress);
								} else {
									hostaddr = hostaddr_temp.getText().toString();
								}
					
								RowData hostrow = new RowData(hostname,	hostaddr, "GO GO SUPER MULLE!");
								adapter.add(hostrow);
								return;
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int which) {
								return;
							}
						});
				alert.show();
			}
		});


		// ---------------------
		// switch (state) {
		// case 0:
		// Row_button.setBackgroundColor(Color.YELLOW);
		// // RowData rd2 = new RowData("lol", "desc", "top");
		// //adapter.add(rd2);
		// state = 1;
		// break;
		// case 1:
		// Row_button.setBackgroundColor(Color.TRANSPARENT);
		// state = 0;
		// break;
		// -------------------

		// custom_row.xml should contain a list of views or something like that
		adapter = new CustomAdapter(this, R.layout.custom_row, R.id.item, data);

		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);

	}

	// RowName = (TableRow) findViewById(R.id.RowName);
	// RowName.setBackgroundColor(Color.TRANSPARENT);
	//
	// RowName.setOnClickListener(new View.OnClickListener() {
	//
	// public void onClick(View v) {
	//
	// if (RowName.equals(Color.TRANSPARENT))
	// RowName.setBackgroundColor(Color.YELLOW);
	//
	// else if (RowName.equals(Color.YELLOW))
	// RowName.setBackgroundColor(Color.TRANSPARENT);
	// }
	// });
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		CustomAdapter adapter = (CustomAdapter) parent.getAdapter();
		RowData row = adapter.getItem(position);
		Builder builder = new AlertDialog.Builder(this);
		
		//TODO: PINGA SERVERN ELLER NGT COOLT
		// skicka grejer med msgHandler vid bättre tillfällen och med ngt
		// användbart i
		// MessageHandler msgHandler = new MessageHandler();
		// msgHandler.CoAPGET("mulle.csproject.org", "lol", "temperature");
		//msgHandler = new MessageHandler();
		//TODO: check if mServeraddr is INETaddr
		//TODO: HÅRDKODAT
		msgHandler.CoAPGET(row.mServeraddr, "", "counterservice");	
		//SystemClock.sleep(10);
	    
		builder.setTitle(row.mHostname);
		builder.setMessage(row.mHostname + "\n" + row.mServeraddr + "\n counter: " + msgHandler.temp);
		builder.setPositiveButton("ok", null);
		builder.show();
	}

	/**
	 * Data type used for custom adapter. Single item of the adapter.
	 */
	private class RowData {
		protected String mHostname;
		protected String mServeraddr;
		protected String mlol;

		RowData(String hostname, String serveraddr, String lol) {
			mHostname = hostname;
			mServeraddr = serveraddr;
			mlol = lol;
		}

		@Override
		public String toString() {
			return mHostname + " " + mServeraddr + " " + mlol;
		}
	}

	private class CustomAdapter extends ArrayAdapter<RowData> {

		public CustomAdapter(Context context, int resource,
				int textViewResourceId, List<RowData> objects) {
			super(context, resource, textViewResourceId, objects);

		}

		@Override
		public void add(RowData rw) {
			super.add(rw);
			super.notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			// widgets displayed by each item in your list
			TextView hostname = null;
			TextView serveraddr = null;
			TextView lol = null;

			// data from your adapter
			RowData rowData = getItem(position);

			// we want to reuse already constructed row views...
			if (null == convertView) {
				convertView = mInflater.inflate(R.layout.custom_row, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			hostname = holder.getHostname();
			hostname.setText(rowData.mHostname);

			serveraddr = holder.getserveraddr();
			serveraddr.setText(rowData.mServeraddr);

			lol = holder.getlol();
			lol.setText(rowData.mlol);

			return convertView;
		}
	}

	/**
	 * Wrapper for row data.
	 * 
	 */
	private class ViewHolder {
		private View mRow;
		private TextView serveraddr = null;
		private TextView hostname = null;
		private TextView lol = null;

		public ViewHolder(View row) {
			mRow = row;
		}

		public TextView getserveraddr() {
			if (null == serveraddr) {
				serveraddr = (TextView) mRow.findViewById(R.id.description);
			}
			return serveraddr;
		}

		public TextView getHostname() {
			if (null == hostname) {
				hostname = (TextView) mRow.findViewById(R.id.item);
			}
			return hostname;
		}

		public TextView getlol() {
			if (null == lol) {
				lol = (TextView) mRow.findViewById(R.id.lol);
			}
			return lol;
		}
	}

	
}