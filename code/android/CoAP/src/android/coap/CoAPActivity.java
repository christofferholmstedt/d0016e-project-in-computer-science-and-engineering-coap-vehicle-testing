package android.coap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class CoAPActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Toast.makeText(this, "messagehandler start", 1);
        
        MessageHandler messageHandler = new MessageHandler();
       // messageHandler.runTestClient();
    }
}