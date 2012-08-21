package com.OpenthePodBayDoor;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.ConnectionInfo;
import com.trilead.ssh2.Session;

import java.io.File;
import java.io.IOException;


public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.main);
        final ProgressDialog dialog=ProgressDialog.show(this, "Open the pod bay doors, Hal","Trying to open the pod bay doors...", true);
        final File key= new File("/mnt/sdcard/ssh/id_rsa2");
        final String user="open";
        final Connection conn = new Connection("172.22.26.3");




        new Thread(new Runnable(){
            public void run() {

                try {
                    Session sess= null;
                    conn.connect(null, 3000, 3000);
                    conn.authenticateWithPublicKey(user, key,null);
                    sess = conn.openSession();
                    sess.startShell();
                }
                catch(IOException e){

                    Log.e("Test", e.getMessage());

                } finally {
                    conn.close();

                    dialog.dismiss();
                    finish();
                }

            }
            }
        ).start();

    }
}
