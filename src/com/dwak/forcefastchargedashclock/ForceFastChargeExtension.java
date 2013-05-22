package com.dwak.forcefastchargedashclock;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Intent;
import android.util.Log;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

public class ForceFastChargeExtension extends DashClockExtension {

	private String ffc_status = null;
	@Override
	protected void onUpdateData(int arg0) {
		Process p;
		try {
			p= Runtime.getRuntime().exec("cat /sys/kernel/fast_charge/force_fast_charge");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			int read;
			String out = new String();
			
			//read method will wait forever if there is nothing in the stream
			//so we need to readit in another way than while((read=stdout.read(buffer))>0)
			String line;
			while((line = reader.readLine()) != null){
				out +=line + "\n";
				System.out.println(out);
				break;
			}
			Log.v("FFC",out);
			if(Integer.parseInt(out.trim())==0){
				ffc_status = "OFF";
			}
			else
				ffc_status="ON";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		publishUpdate(new ExtensionData()
		.visible(true)
		.icon(R.drawable.ic_launcher)
		.status("FFC: " + ffc_status)
		.expandedTitle("FFC: "+ffc_status)
		.expandedBody("Click to toggle!")
		.clickIntent(new Intent(this,FFCToggleActivity.class)));

	}

}
