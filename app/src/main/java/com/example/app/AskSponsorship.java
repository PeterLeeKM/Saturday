package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class AskSponsorship extends ActionBarActivity implements Observer {

    @Override
    public void update(Observable observable, Object o) {
        HashMap<String, Object> data = (HashMap<String, Object>)o;
        if(data.get("name").equals("requestSpon")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("알림");
            builder.setMessage("스폰 신청이 접수되었습니다.");
            builder.setNeutralButton("확인", new CommitDialog());
            builder.show();
        }
    }

    class CommitDialog implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int which){ }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_sponsorship);
        MemberManager.getInstance().addObserver(this);

        Intent b = getIntent();
        String name = b.getStringExtra("name");

        if(name.equals("헛개수")){
            TextView textView = (TextView)findViewById(R.id.catchphrase_product);
            textView.setText(R.string.hutwater);

            ImageView imageView = (ImageView)findViewById(R.id.explanation_product);
            imageView.setImageResource(R.drawable.hutwater_spon);
        }
        else{
            TextView textView = (TextView)findViewById(R.id.catchphrase_product);
            textView.setText(R.string.likefirst);

            ImageView imageView = (ImageView)findViewById(R.id.explanation_product);
            imageView.setImageResource(R.drawable.likefirst_spon);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ask_sponsorship, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void askSpon(View view){
        Intent b = getIntent();
        MemberManager.getInstance().requestSpon(
            b.getStringExtra("name"),
            ((EditText)findViewById(R.id.address)).getText().toString(),
            ((EditText)findViewById(R.id.required_time)).getText().toString()
        );
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_ask_sponsorship, container, false);
            return rootView;
        }
    }
}
