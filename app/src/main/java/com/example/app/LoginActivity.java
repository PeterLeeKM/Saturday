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

public class LoginActivity extends ActionBarActivity {

    private EditText username, password;

    class CommitDialog implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int which){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        Member member;
        MemberManager membermanager = MemberManager.getInstance();
        this.username = (EditText)findViewById(R.id.edit_ID);
        this.password = (EditText)findViewById(R.id.edit_password);
        member = membermanager.findMember(this.username.getText().toString());
        if(member.getPassword().equals(this.password.getText().toString())){
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("username", this.username.getText().toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("알림");
            builder.setMessage("로그인 정보가 올바르지 않습니다.");
            builder.setNeutralButton("확인", new CommitDialog());
            builder.show();
        }
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
