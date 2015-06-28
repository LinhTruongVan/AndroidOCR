
package app.com.augmentedreality;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class GoogleSearchFragment extends Fragment {
    private EditText txtGoogleSearch;
    private Button btnGoogleSearch;
    private WebView webViewGoogleSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.google_search_tab, container, false);
        txtGoogleSearch =(EditText) rootView.findViewById(R.id.txtGoogleSearch);
        webViewGoogleSearch =(WebView) rootView.findViewById(R.id.webViewGoogleSearch);
        webViewGoogleSearch.setBackgroundColor(Color.parseColor("#B4D8E7"));
        webViewGoogleSearch.setWebViewClient(new WebViewClient());
        btnGoogleSearch =(Button) rootView.findViewById(R.id.btnGoogleSearch);
        btnGoogleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonSearchTask(txtGoogleSearch.getText().toString()).execute();
            }
        });

        return rootView;
    }

    private class JsonSearchTask extends AsyncTask<Void, Void, Void> {

        String searchResult = "";
        String search_url = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&rsz=8&q=";
        String search_query;
        String search_item;

        JsonSearchTask(String item){

            try {
                search_item = URLEncoder.encode(item, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            search_query = search_url + search_item;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                searchResult = ParseResult(sendQuery(search_query));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            btnGoogleSearch.setEnabled(false);
            btnGoogleSearch.setText("Wait...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            webViewGoogleSearch.loadDataWithBaseURL(null, searchResult, "text/html", "UTF-8", null);
            btnGoogleSearch.setEnabled(true);
            btnGoogleSearch.setText("Search");

            super.onPostExecute(result);
        }

    }

    private String sendQuery(String query) throws IOException {
        String result = "";

        URL searchURL = new URL(query);

        HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();

        if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader,
                    8192);

            String line = null;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            bufferedReader.close();
        }

        return result;
    }

    private String ParseResult(String json) throws JSONException {
        String parsedResult = "";

        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonObject_responseData = jsonObject.getJSONObject("responseData");
        JSONArray jsonArray_results = jsonObject_responseData.getJSONArray("results");

        parsedResult += "Number of results returned = <b>" + jsonArray_results.length() + "</b><br/><br/>";

        for(int i = 0; i < jsonArray_results.length(); i++){

            JSONObject jsonObject_i = jsonArray_results.getJSONObject(i);

            String iTitle = jsonObject_i.getString("title");
            String iContent = jsonObject_i.getString("content");
            String iUrl = jsonObject_i.getString("url");

            parsedResult += "<a href='" + iUrl + "'>" + iTitle + "</a><br/>";
            parsedResult += iContent + "<br/><br/>";

        }
        return parsedResult;
    }

}