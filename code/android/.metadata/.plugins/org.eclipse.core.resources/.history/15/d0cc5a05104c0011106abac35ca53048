package android.coap;

import android.app.Activity;
import android.os.Bundle;

public class CoAPActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MessageHandler messageHandler = new MessageHandler();
        messageHandler.runTestClient();
    }
}