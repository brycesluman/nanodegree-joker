package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;

import com.example.JokeProvider;
import com.example.bryce.myapplication.backend.myApi.MyApi;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by bryce on 8/24/16.
 */
public class EndpointsAsyncTaskTest extends InstrumentationTestCase {
    private static MyApi myApiService = null;
    private String mResult;
    public void testJokeIsReturned() throws Throwable {
// create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     */
        final AsyncTask<String, Void, String> myTask = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... arg0) {

                if(myApiService == null) {  // Only do this once
                    myApiService = EndpointsAsyncTask.buildApi();
                }
                try {
                    return myApiService.tellJoke().execute().getData();
                } catch (IOException e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

            /* This is the key, normally you would use some type of listener
             * to notify your activity that the async call was finished.
             *
             * In your test method you would subscribe to that and signal
             * from there instead.
             */
                mResult = result;


                signal.countDown();
            }
        };

        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
                myTask.execute();
            }
        });

    /* The testing thread will wait here until the UI thread releases it
     * above with the countDown() or 30 seconds passes and it times out.
     */
        signal.await(15, TimeUnit.SECONDS);

        // The task is done, and now you can assert some things!
        assertEquals(JokeProvider.getStaticJoke(),mResult);
    }
}
