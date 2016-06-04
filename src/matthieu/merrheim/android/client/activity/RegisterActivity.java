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

import matthieu.merrheim.android.client.R;
import matthieu.merrheim.android.client.asynchone.AbstractAsynchroneActivity;
import matthieu.merrheim.android.client.entity.Utilisateur;


public class RegisterActivity extends AbstractAsynchroneActivity {

    protected static final String TAG = RegisterActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        final Button buttonJson = (Button) findViewById(R.id.button_register);
        buttonJson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new PostMessageTask().execute(MediaType.APPLICATION_JSON);

            }
        });



        final Button buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent homeIntent = new Intent(getApplicationContext(),LoginActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });


    }


    private void showResult(String result) {
        if (result != null) {

            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            Intent homeIntent = new Intent(getApplicationContext(),LoginActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);

        } else {
            Toast.makeText(this, "Problème !", Toast.LENGTH_LONG).show();
        }
    }


    private class PostMessageTask extends AsyncTask<MediaType, Void, String> {

        private Utilisateur utilisateur;

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();


            EditText editText = (EditText) findViewById(R.id.edit_text_utilisateur_email);

            utilisateur = new Utilisateur();



            utilisateur.setEmail(editText.getText().toString());

            editText = (EditText) findViewById(R.id.edit_text_utilisateur_password);
            utilisateur.setPassword(editText.getText().toString());

        }

        @Override
        protected String doInBackground(MediaType... params) {
            try {
                if (params.length <= 0) {
                    return null;
                }

                MediaType mediaType = params[0];


                final String url = getString(R.string.base_uri) + "/registeruser";


                HttpHeaders requestHeaders = new HttpHeaders();


                requestHeaders.setContentType(mediaType);


                HttpEntity<Utilisateur> requestEntity = new HttpEntity<Utilisateur>(utilisateur, requestHeaders);


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
            showResult(result);
        }

    }

}