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
import com.example.app.Member;
import com.example.app.MemberManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class LoginActivity extends ActionBarActivity implements Observer {

    private EditText username, password;

    @Override
    public void update(Observable observable, Object o) {
        HashMap<String, Object> data = (HashMap<String, Object>)o;
        if(data.get("name").equals("login")) {
            try {
                JSONObject reader = new JSONObject((String)data.get("result"));
                int isValid = reader.getInt("is_valid");
                if(isValid == 1){
                    MemberManager.getInstance().setUserId(reader.getJSONObject("user").getInt("id"));
                    Intent intent = new Intent(this, MainActivity2.class);
                    intent.putExtra("username", this.username.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("알림");
                    builder.setMessage("로그인 정보가 올바르지 않습니다.");
                    builder.setNeutralButton("확인", new CommitDialog());
                    builder.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class CommitDialog implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int which){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MemberManager.getInstance().addObserver(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    public void logIn(View view) throws NullPointerException{
        this.username = (EditText)findViewById(R.id.input_id);
        this.password = (EditText)findViewById(R.id.input_password);
        MemberManager.getInstance().login(this.username.getText().toString(),
                                          this.password.getText().toString());
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
            View rootView = inflater.inflate(R.layout.activity_login, container, false);
            return rootView;
        }
    }
}
