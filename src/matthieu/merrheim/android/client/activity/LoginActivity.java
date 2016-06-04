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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import matthieu.merrheim.android.client.asynchone.AbstractAsynchroneActivity;
import matthieu.merrheim.android.client.R;
import matthieu.merrheim.android.client.entity.ServerResponse;


public class LoginActivity extends AbstractAsynchroneActivity {

    protected static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final Button loginbouton = (Button) findViewById(R.id.btn_login);
        loginbouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new callTask().execute(MediaType.APPLICATION_JSON);
            }
        });

        final Button registerbouton = (Button) findViewById(R.id.btn_register);
        registerbouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent homeIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });


    }


    private void run(ServerResponse serverResponse) {


        if (serverResponse != null && serverResponse.getServerResponse().equals("OK")) {

            Intent homeIntent = new Intent(getApplicationContext(), MessageActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        } else {
            Toast.makeText(this, "Vous n\'etes pas inscrit", Toast.LENGTH_LONG).show();
        }


    }


    private class callTask extends AsyncTask<MediaType, Void, ServerResponse> {

        private String login;
        private String password;

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();


            EditText editText = (EditText) findViewById(R.id.edit_text_utilisateur_email);

            login = editText.getText().toString();

            editText = (EditText) findViewById(R.id.edit_text_utilisateur_password);

            password = editText.getText().toString();
        }

        @Override
        protected ServerResponse doInBackground(MediaType... params) {
            try {
                if (params.length <= 0) {
                    return null;
                }

                MediaType mediaType = params[0];


                final String url = getString(R.string.base_uri) + "/login/{login}/{password}";


                HttpHeaders requestHeaders = new HttpHeaders();
                List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
                acceptableMediaTypes.add(mediaType);
                requestHeaders.setAccept(acceptableMediaTypes);


                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);


                RestTemplate restTemplate = new RestTemplate();
                if (mediaType.equals(MediaType.APPLICATION_JSON)) {
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                } else {
                    restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());
                }


                ResponseEntity<ServerResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                        ServerResponse.class, login, password);


                return responseEntity.getBody();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ServerResponse serverResponse) {
            dismissProgressDialog();
            run(serverResponse);
        }

    }

}