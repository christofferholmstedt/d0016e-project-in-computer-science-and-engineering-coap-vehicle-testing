

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

	//If making a new child of rowdata, extend these typenumbers for NewItem()
	final public int typeIsOn = 1;
	final public int typeGetValue = 2;
	final public int typeSendValue = 3; //TODO: doesn't exist

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_gui_activity);

		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();

		/*TODO:  dividers to separate the devices? as:

		 * -----------------
		 * Device1               <-- Divider
		 * -----------------
		 * service 1             <-- list item
		 * _________________
		 * service 2             <-- list item
		 * _________________
		 * -----------------
		 * Device2				 <-- Divider
		 * -----------------
		 */

		adapter = new CustomAdapter(this, R.layout.item_checkbox, R.id.checkbox_text, data);

		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);

		//ADDING RANDOM STUFF, Try moving these test-addings to a later place in the code when the getView of the CustomAdapter has been called
		newItem("device 1", "service 1, IsOn", typeIsOn, 0);
		newItem("device 1", "service 2, IsOn", typeIsOn, 1);
		newItem("device 1", "service 3, GetValue", typeGetValue, 0);
		newItem("device 2", "service 1", typeGetValue, 1);
		newItem("device 2", "service 2", typeIsOn, 1);

	}

	/**
	 * @author Gurr3
	 * @param devicename
	 * @param servicename
	 * @param type should be called with predefined values typeIsOn, typeGetValue
	 * @param Value
	 */
	public void newItem(String devicename, String servicename, int type, int Value){ //TODO: should Value be a float or string perhaps ?

		RowData rd = null;
		if (type==typeIsOn)
		{rd = new RowData_IsOn(servicename, devicename);}
		else if (type==typeGetValue) 
		{rd = new RowData_GetValue(servicename, devicename);}
		else 
		{System.out.println("no such type available"); return;}

		adapter.add(rd);
		//rd.Update(Value);	
		//TODO: rd.Update(); doesn't work since getView() in CustomAdapter gets called when changing tab to this.
		// Therefore it becomes a nullpointerexception when newItem is called from the onCreate
		//Solution, make sure this tab is active before calling newItem, perhaps moving the earlier test-addings to a later place than OnCreate should fix that
	}

	/**
	 * @author Gurr3
	 * When clicking on the list, this code runs. Planned use is to call the active Rowdata's onClick method.
	 * should NOT change the Value or anything on the listitem, these should change with a onRecieve call
	 */
	public void onListItemClick(ListView parent, View v, int position, long id) {
		CustomAdapter adapter = (CustomAdapter) parent.getAdapter();
		RowData row = adapter.getItem(position);
		//row.onClick();
		
		//Only for debug purposes
		if (row.getlocalValue()==1)
			row.Update(0);
		else
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
		public void onCreate(View row);//notice: this one get called on alot of random places, like when scrolling, so making if statements with if x == null is a good idea
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
		public void onClick() {	Send();}

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

			if (Value == 1)
				this.thebox.setChecked(true); //doesn't work when initiating
			else if (Value == 0)
				this.thebox.setChecked(false);

			setlocalValue(Value);
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

			
			if (null == thebox){
				this.thebox = (CheckBox) mRow.findViewById(R.id.checkBox1);
				this.thebox.setFocusable(false);
				this.thebox.setClickable(false);
			}
			if (!(mServiceName==null))	
				Servicename.setText(mServiceName);


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
		}

		@Override
		public void Send() {/*TODO: do stuff */}

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
	 * Creates an item into the view, getView calls the Rowdata onCreate.
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

		/**
		 * Gets called whenever something happens to the View, like scrolling, changing to this tab and so on\\
		 * is currently used to load the view
		 */
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
