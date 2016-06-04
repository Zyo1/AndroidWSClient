package matthieu.merrheim.android.client.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import matthieu.merrheim.android.client.R;
import matthieu.merrheim.android.client.clientandroid.Data;
import matthieu.merrheim.android.client.entity.Conversation;
import matthieu.merrheim.android.client.entity.ConversationList;


public class UpdateFragmentListList extends FragmentList {

    private static final String LOG_TAG = UpdateFragmentListList.class.getSimpleName();

    private static final int LIST_ITEM_COUNT = 20;

    String messageClic = "";

    EditText editText1;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);
    }


    public void onListItemClick(ListView l, View v, int position, long id) {

        this.messageClic = (String) getListAdapter().getItem(position);

        new VoteTask(this.messageClic).execute();

    }

    private class VoteTask extends AsyncTask<MediaType, Void, String> {

        private String msg;

        public VoteTask(String message) {
            super();

            this.msg = message;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(MediaType... params) {

            try {


                MediaType mediaType = MediaType.APPLICATION_XML;


                String url2 = getString(R.string.base_uri) + "/vote/{message}";


                HttpHeaders requestHeaders = new HttpHeaders();
                List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
                acceptableMediaTypes.add(mediaType);
                requestHeaders.setAccept(acceptableMediaTypes);

                ;
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);


                RestTemplate restTemplate = new RestTemplate();


                restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());


                ResponseEntity<String> response = restTemplate.exchange(url2, HttpMethod.GET, requestEntity,
                        String.class, this.msg);


                return response.getBody();
            } catch (Exception e) {

            }

            return null;
        }


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        new UpdateListTask().execute();

        ListAdapter adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                Data.randomList(LIST_ITEM_COUNT));


        setListAdapter(adapter);


        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                initiateRefresh();
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }




    private void initiateRefresh() {


        new UpdateListTask().execute();
    }


    private void onRefreshComplete(List<Conversation> result) {



        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        adapter.clear();


        for (int i = 0; i < result.size(); i++) {
            adapter.add(result.get(i).getMessage());
        }


        setRefreshing(false);
    }

    private class UpdateListTask extends AsyncTask<Void, Void, List<Conversation>> {

        static final int TASK_DURATION = 3 * 1000;

        @Override
        protected List<Conversation> doInBackground(Void... params) {

            List<String> liste = new ArrayList<String>();
            try {
                Thread.sleep(TASK_DURATION);

                final String url = getString(R.string.base_uri) + "/getconv";


                HttpHeaders requestHeaders = new HttpHeaders();
                List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
                acceptableMediaTypes.add(MediaType.APPLICATION_XML);
                requestHeaders.setAccept(acceptableMediaTypes);


                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);


                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());


                ResponseEntity<ConversationList> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                        ConversationList.class);


                ConversationList stateList = responseEntity.getBody();


                return stateList.getConversations();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;

        }


        @Override
        protected void onPostExecute(List<Conversation> result) {
            super.onPostExecute(result);

            onRefreshComplete(result);
        }

    }


}
