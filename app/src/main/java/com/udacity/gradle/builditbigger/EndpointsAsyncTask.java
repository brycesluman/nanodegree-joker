package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.example.bryce.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by bryce on 8/16/16.
 */
class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private PostExecuteListener mListener;

    @Override
    protected String doInBackground(Context ... params) {
        if(myApiService == null) {  // Only do this once
            myApiService = buildApi();
        }
        context = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public static MyApi buildApi() {

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            builder.setApplicationName("jokester");
            return builder.build();
    }

    @Override
    protected void onPostExecute(String result) {
        if (context instanceof PostExecuteListener) {
            mListener = (PostExecuteListener) context;
        }
        if (mListener!=null) {
            mListener.onPostExecute(result);
        }
    }

    public interface PostExecuteListener {
        void onPostExecute(String joke);
    }
}
