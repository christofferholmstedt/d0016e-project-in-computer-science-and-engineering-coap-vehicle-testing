package se.willliamgustafsson.tab_gui2;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_gui); //TODO: important
		
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();
		RowData rd = new RowData("item1", "description1");
		data.add(rd);
		rd = new RowData("item2", "description2");
		data.add(rd);
		rd = new RowData("item2", "description3");
		data.add(rd);

		CustomAdapter adapter = new CustomAdapter(this, R.layout.custom_row, R.id.item, data); //TODO: kolla
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		CustomAdapter adapter = (CustomAdapter) parent.getAdapter();
		RowData row = adapter.getItem(position);
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(row.mItem);
		builder.setMessage(row.mDescription + " -> " + position);
		builder.setPositiveButton("ok", null);
		builder.show();
	}

	/**
	 * Data type used for custom adapter. Single item of the adapter.
	 */
	private class RowData {
		protected String mItem;
		protected String mDescription;

		RowData(String item, String description) {
			mItem = item;
			mDescription = description;
		}

		@Override
		public String toString() {
			return mItem + " " + mDescription;
		}
	}

	private class CustomAdapter extends ArrayAdapter<RowData> {

		public CustomAdapter(Context context, int resource,
				int textViewResourceId, List<RowData> objects) {
			super(context, resource, textViewResourceId, objects);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			// widgets displayed by each item in your list
			TextView item = null;
			TextView description = null;

			// data from your adapter
			RowData rowData = getItem(position);

			// we want to reuse already constructed row views...
			if (null == convertView) {
				convertView = mInflater.inflate(R.layout.custom_row, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			//
			holder = (ViewHolder) convertView.getTag();
			item = holder.getItem();
			item.setText(rowData.mItem);

			description = holder.getDescription();
			description.setText(rowData.mDescription);

			return convertView;
		}
	}

	/**
	 * Wrapper for row data.
	 * 
	 */
	private class ViewHolder {
		private View mRow;
		private TextView description = null;
		private TextView item = null;

		public ViewHolder(View row) {
			mRow = row;
		}

		public TextView getDescription() {
			if (null == description) {
				description = (TextView) mRow.findViewById(R.id.description);
			}
			return description;
		}

		public TextView getItem() {
			if (null == item) {
				item = (TextView) mRow.findViewById(R.id.item);
			}
			return item;
		}
	}
}
