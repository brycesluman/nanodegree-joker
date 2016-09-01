package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.JokeProvider;

import org.sluman.jokedisplay.JokeDisplayActivity;


public class MainActivity extends ActionBarActivity
        implements EndpointsAsyncTask.PostExecuteListener,
        AdProvider.NextViewListener
{
    private AdProvider mAdProvider;
    private ProgressBar mSpinner;
    public static String JOKE_EXTRA = "joke_extra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinner=(ProgressBar)findViewById(R.id.progressBar1);
        mSpinner.setVisibility(View.GONE);
        mAdProvider = AdProvider.getInstance(this);
        mAdProvider.loadInterstitial(this);
    }

    @Override
    public void onRenderNextView(String joke) {
        Intent intent = new Intent(this, JokeDisplayActivity.class);
        intent.putExtra(JOKE_EXTRA, joke);
        startActivity(intent);
        mSpinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        mSpinner.setVisibility(View.VISIBLE);
        new EndpointsAsyncTask().execute(this);

    }


    @Override
    public void onPostExecute(String joke) {
        mAdProvider.showInterstitial(joke);
    }

}
