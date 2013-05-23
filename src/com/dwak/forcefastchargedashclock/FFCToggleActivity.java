package com.dwak.forcefastchargedashclock;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
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

			os.writeBytes("ffc" + "\n");
			os.flush();
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
			Toast.makeText(this.getBaseContext(), "Force Fast Charge Toggled!"  , Toast.LENGTH_SHORT).show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
