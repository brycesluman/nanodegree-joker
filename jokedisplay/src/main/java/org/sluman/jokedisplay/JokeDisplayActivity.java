package org.sluman.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        String joke = getIntent().getStringExtra("joke_extra");

        TextView textView = (TextView) findViewById(R.id.joke_text);
        if(textView!=null) {
            textView.setText(joke);
        }

    }
}
