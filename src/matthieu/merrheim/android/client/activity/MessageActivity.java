package matthieu.merrheim.android.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import matthieu.merrheim.android.client.R;
import matthieu.merrheim.android.client.fragment.UpdateFragmentListList;


public class MessageActivity extends FragmentActivity {

    public static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_activity);

        final Button buttonJson = (Button) findViewById(R.id.button_addmessage);
        buttonJson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent homeIntent = new Intent(getApplicationContext(), SendActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            UpdateFragmentListList fragment = new UpdateFragmentListList();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}
