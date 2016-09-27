package com.example.shareb;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) this.findViewById(R.id.textView1);

        try {
            //获取程序A的context
            Context ctx = this.createPackageContext("com.example.sharea", Context.CONTEXT_IGNORE_SECURITY);
            String msg = ReadSettings(ctx);
            Toast.makeText(this, "DealFile2 Settings read" + msg,
                    Toast.LENGTH_SHORT).show();
            WriteSettings(ctx, "deal file2 write");
			
			//获取A中SharedPreferences中的资源id，然后根据id找到该资源
            SharedPreferences sp = ctx.getSharedPreferences("sp", MODE_PRIVATE);
            int Rkey = sp.getInt("Rkey", 0);
            String ts = ctx.getResources().getString(Rkey);
            textView2.setText(ts);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public String ReadSettings(Context context) {
        FileInputStream fIn = null;
        InputStreamReader isr = null;
        char[] inputBuffer = new char[255];
        String data = null;
        try {
            //此处调用并没有区别，但context此时是从程序A里面获取的
            fIn = context.openFileInput("settings.txt");
            isr = new InputStreamReader(fIn);
            isr.read(inputBuffer);
            data = new String(inputBuffer);
            textView.setText(data);
            Toast.makeText(context, "Settings read", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Settings not read", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                isr.close();
                fIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public void WriteSettings(Context context, String data) {
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;
        try {
            fOut = context.openFileOutput("settings.txt", MODE_PRIVATE);
            //此处调用并没有区别，但context此时是从程序A里面获取的
            osw = new OutputStreamWriter(fOut);
            osw.write(data);
            osw.flush();
            Toast.makeText(context, "Settings saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Settings not saved", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                osw.close();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
