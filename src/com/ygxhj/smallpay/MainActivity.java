package com.ygxhj.smallpay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ygxhj.smallpay.linster.SumOkLinster;
import com.ygxhj.smallpay.util.DateUtil;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		EditText startTime = (EditText)findViewById(R.id.startTime);
		startTime.setText(DateUtil.getDateDesc_() + " 00:00:00");
		EditText endTime = (EditText)findViewById(R.id.endTime);
		endTime.setText(DateUtil.getDateDesc_() + " 23:59:59");
		
		
		EditText text = (EditText)findViewById(R.id.sumTxt);
		text.setFocusable(false);
		StringBuffer sb = new StringBuffer();
		sb.append("当前收入：请点击查询\n");
		sb.append("本月收入：请点击查询\n");
		sb.append("上月收入：请点击查询\n");
		text.setText(sb.toString());
		
		
		Button ok = (Button)findViewById(R.id.OK);
		ok.setOnClickListener(new SumOkLinster(startTime,endTime,text));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
			return super.onCreateView(inflater, container, savedInstanceState);
		}
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
        	finish();
        	return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
