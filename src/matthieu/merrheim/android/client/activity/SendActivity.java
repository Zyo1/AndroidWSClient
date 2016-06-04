package matthieu.merrheim.android.client.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import matthieu.merrheim.android.client.asynchone.AbstractAsynchroneActivity;
import matthieu.merrheim.android.client.entity.Conversation;
import matthieu.merrheim.android.client.R;

/**
 * @author Matthieu MERRHEIM
 */
public class SendActivity extends AbstractAsynchroneActivity {

    protected static final String TAG = RegisterActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);


        final Button buttonSendMsg = (Button) findViewById(R.id.btn_sendmessage);
        buttonSendMsg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SaveconvTask().execute(MediaType.APPLICATION_JSON);
            }
        });


        final Button buttonRetour = (Button) findViewById(R.id.btn_retour);
        buttonRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent homeIntent = new Intent(getApplicationContext(), MessageActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }


    private void run(String result) {
        if (result != null) {

            Toast.makeText(this, result, Toast.LENGTH_LONG).show();

            Intent homeIntent = new Intent(getApplicationContext(), MessageActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);

        } else {
            Toast.makeText(this, "Il y a eu un pb pour save le message", Toast.LENGTH_LONG).show();
            Intent homeIntent = new Intent(getApplicationContext(), MessageActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }

    private class SaveconvTask extends AsyncTask<MediaType, Void, String> {

        private Conversation conv;

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

            EditText editTextMessage = (EditText) findViewById(R.id.message);
            conv = new Conversation();
            conv.setMessage(editTextMessage.getText().toString());
        }

        @Override
        protected String doInBackground(MediaType... params) {
            try {
                if (params.length <= 0) {
                    return null;
                }

                MediaType mediaType = params[0];


                final String url = getString(R.string.base_uri) + "/saveconv";


                HttpHeaders requestHeaders = new HttpHeaders();


                requestHeaders.setContentType(mediaType);


                HttpEntity<Conversation> requestEntity = new HttpEntity<Conversation>(conv, requestHeaders);


                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                if (mediaType == MediaType.APPLICATION_JSON) {
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                } else if (mediaType == MediaType.APPLICATION_XML) {
                    restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());
                }

                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                        String.class);


                return response.getBody();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dismissProgressDialog();
            run(result);
        }

    }

}