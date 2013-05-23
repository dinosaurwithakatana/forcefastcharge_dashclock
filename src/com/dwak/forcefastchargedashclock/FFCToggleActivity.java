package com.dwak.forcefastchargedashclock;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class FFCToggleActivity extends Activity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		RunAsRoot();
		finish();
	}

	private void RunAsRoot(){
		Runtime rt = Runtime.getRuntime();
		Process process;
		try {
			process = rt.exec("su");

			DataOutputStream os = new DataOutputStream(process.getOutputStream());

			os.writeBytes("cat /sys/kernel/fast_charge/force_fast_charge" + "\n");
			os.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			int read;
			String out = new String();
			String line;
			while((line = reader.readLine()) != null){
				out +=line + "\n";
				System.out.println(out);
				break;
			}
			Log.v("FFCToggle",out);
			if(Integer.parseInt(out.trim())==0){
				os.writeBytes("echo 1 > /sys/kernel/fast_charge/force_fast_charge\n");
				Toast.makeText(this.getBaseContext(), "Force Fast Charge Enabled!"  , Toast.LENGTH_SHORT).show();
			}
			else{
				os.writeBytes("echo 0 > /sys/kernel/fast_charge/force_fast_charge\n");
				Toast.makeText(this.getBaseContext(), "Force Fast Charge Disabled!"  , Toast.LENGTH_SHORT).show();
			}
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
