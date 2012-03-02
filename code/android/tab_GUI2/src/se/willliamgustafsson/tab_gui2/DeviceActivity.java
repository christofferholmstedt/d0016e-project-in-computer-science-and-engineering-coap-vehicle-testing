

package se.willliamgustafsson.tab_gui2;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Gurr3
 */
public class DeviceActivity extends ListActivity {
	private LayoutInflater mInflater;
	private Vector<RowData> data;
	CustomAdapter adapter;
	final public int typeIsOn = 1;
	final public int typeGetValue = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_gui_activity);

		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();

		/*TODO:  dividers to separate the devices? as:

		 * -----------------
		 * Server1               <-- Divider
		 * -----------------
		 * service 1             <-- list item
		 * _________________
		 * service 2             <-- list item
		 * _________________
		 */

		//ADDING RANDOM STUFF TO GET A VIEW OF HOW IT WILL LOOK
		//		RowData_IsOn rdio = new RowData_IsOn("service 1", "device 1");
		//		data.add(rdio);
		//		rdio = new RowData_IsOn("service 2", "device 1");
		//		data.add(rdio);
		//
		//		RowData_GetValue rdgv = new RowData_GetValue("service 3", "device 1");
		//		data.add(rdgv);
		//		rdgv = new RowData_GetValue("service 1", "device 2");
		//		data.add(rdgv);
		//
		//		rdio = new RowData_IsOn("service 2", "device 2");
		//		data.add(rdio);


		adapter = new CustomAdapter(this, R.layout.item_checkbox, R.id.checkbox_text, data);

		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
		
		//ADDING RANDOM STUFF
		newItem("device 1", "service 1", typeIsOn, 0);
		newItem("device 1", "service 2", typeIsOn, 1);
		newItem("device 1", "service 3", typeGetValue, 0);
		newItem("device 2", "service 1", typeGetValue, 1);
		newItem("device 2", "service 2", typeIsOn, 1);

	}

	/**
	 * @author Gurr3
	 * @param devicename
	 * @param servicename
	 * @param type should be called with predefined values typeIsOn, typeGetValue
	 * @param Value
	 * 
	 */
	public void newItem(String devicename, String servicename, int type, int Value){

		RowData rd = null;
		if (type==typeIsOn)
		{rd = new RowData_IsOn(servicename, devicename);}
		else if (type==typeGetValue) 
		{rd = new RowData_GetValue(servicename, devicename);}
		else 
		{System.out.println("no such type available"); return;}

		adapter.add(rd);
		
		//GO GO Gadget error :(
		rd.Update(Value);	
		//rd.Update calls notifydatasetchanged
	}

	/**
	 * @author Gurr3
	 * When clicking on the list, this code runs. Planned use is to call the active Rowdata's onClick method.
	 */
	public void onListItemClick(ListView parent, View v, int position, long id) {
		CustomAdapter adapter = (CustomAdapter) parent.getAdapter();
		RowData row = adapter.getItem(position);
		row.Update(1);

	}

	/*
	 * ------------------------------------------------------------------------------------------------------------------- 
	 * ---------------------------------WARNING: STUFF UNDER THIS LINE CONTAINS CODE--------------------------------------
	 * -------------------------------------------------------------------------------------------------------------------
	 */


	/*
	 * ***********************************
	 * ********* ROWDATA TYPES************
	 * ***********************************
	 */

	/**
	 * @author Gurr3
	 * Interface to be extended when creating an Item
	 */
	private interface RowData { //TODO: make abstract instead of interface
		//		String mServiceName;
		//		String mDeviceName;
		//		String mType;
		//		int mValue;
		//
		//		mServiceName = servicename;
		//		mDeviceName = devicename;
		//		mType = type;
		//		mValue = Value;

		//		RowData(String devicename, String servicename, String type , int Value);
		public String getServiceName();
		//	public void setServiceName(String servicename);
		public String getDeviceName();
		//	public void setDeviceName(String devicename);
		public String getType();
		public int getlocalValue();
		public void setlocalValue(int Value);
		public void onClick();		
		public void Update(int Value);		
		public void Send();
		public void onCreate(View row);
		public int getviewtype();
	}

	/**
	 * @author Gurr3
	 */
	private class RowData_IsOn implements RowData {
		protected String mServiceName;
		protected String mDeviceName;
		protected String mType = "IsOn";

		protected int mValue;

		protected View mRow = null;
		private int mViewType = R.layout.item_checkbox;
		private TextView Servicename = null;
		private CheckBox thebox = null;

		public RowData_IsOn(String servicename, String devicename) {
			mServiceName = servicename;
			mDeviceName = devicename;
		}

		@Override
		public String getServiceName() {return mServiceName;}
		@Override
		public String getDeviceName() {return mDeviceName;}
		@Override
		public String getType() {return mType;}
		@Override
		public int getlocalValue() {return mValue;}
		@Override
		public void setlocalValue(int Value) { mValue=Value;}

		/**
		 * calls Send from current Value
		 */
		@Override
		public void onClick() {	Send(); }

		/**
		 * Should be called by Onreceive
		 * changes the checkbox status and the mValue, call with 1 or 0
		 */
		@Override
		public void Update(int Value) {

			if (Value >1 || Value < 0){
				System.out.println("fel värde");
				return;
			}
			if (Value == 1){
			//	if (!this.thebox.isChecked())
					this.thebox.setChecked(true);
			}
			else if (Value == 0){
			//	if( this.thebox.isChecked())
					this.thebox.setChecked(false);
			}

			setlocalValue(Value);
		//	adapter.notifyDataSetChanged();
		}

		@Override
		public void Send() {
			//TODO: call the real send with !mValue
		}

		@Override
		public void onCreate(View row) {
			if (null == mRow) 
				this.mRow = row;
			if (null == Servicename) 
				this.Servicename = (TextView) mRow.findViewById(R.id.checkbox_text);
			System.out.println("lol1");
			if (null == thebox)
				System.out.println("lol");
			this.thebox = (CheckBox) mRow.findViewById(R.id.checkBox1);
			if (!(mServiceName==null))	
				Servicename.setText(mServiceName);

			this.thebox.setFocusable(false);
			this.thebox.setClickable(false);
		}

		@Override
		public int getviewtype() {return mViewType;}
	}


	private class RowData_GetValue implements RowData{
		protected String mServiceName;
		protected String mDeviceName;
		protected String mType="GetValue";

		protected int mValue;

		protected View mRow = null;
		private int mViewType = R.layout.item_getvalue;
		private TextView Servicename = null;
		private TextView Value;

		public RowData_GetValue(String servicename, String devicename) {
			this.mServiceName = servicename;
			this.mDeviceName = devicename;
		}
		@Override
		public String getServiceName() {return mServiceName;}
		@Override
		public String getDeviceName() {return mDeviceName;}
		@Override
		public String getType() {return mType;}
		@Override
		public int getlocalValue() {return mValue;}
		@Override
		public void setlocalValue(int Value) {mValue = Value;}
		@Override
		public void onClick() {	Send();	}

		@Override
		public void Update(int value) {
			mValue = value;

			this.Value.setText("" + value);
		//	adapter.notifyDataSetChanged();
		}
		@Override
		public void Send() {
			// TODO Auto-generated method stub
		}

		@Override
		public void onCreate(View row) {

			if (null == mRow) 
				this.mRow = row;
			if (null == Servicename)
				Servicename = (TextView) mRow.findViewById(R.id.item_gv_service);
			if (null == Value) 
				Value = (TextView) mRow.findViewById(R.id.item_gv_thevalue);
			if (!(mServiceName==null))	
				Servicename.setText(mServiceName);
		}

		@Override
		public int getviewtype() {return mViewType;}
	}

	/*
	 * ***********************************
	 * *******END OF ROWDATA TYPES********
	 * ***********************************
	 */
	/**
	 * 
	 * @author Gurr3
	 * Creates an item into the view, calls the Rowdata onCreate.
	 */
	private class CustomAdapter extends ArrayAdapter<RowData> {

		public CustomAdapter(Context context, int resource,	int textViewResourceId, List<RowData> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public void add(RowData rw) {
			super.add(rw);
			super.notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			RowData rowData = getItem(position);

			// we want to reuse already constructed row views...
			if (null == convertView) {
				convertView = mInflater.inflate(rowData.getviewtype(), null);
				convertView.setTag(rowData);
			}

			rowData.onCreate(convertView);
			return convertView;
		}
	}
}
