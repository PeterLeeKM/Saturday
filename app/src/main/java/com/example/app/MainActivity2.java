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
import com.example.app.MemberManager;
import com.example.app.Member;

public class MainActivity2 extends ActionBarActivity {

    private String username;

    class CommitDialog implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int which){

        }
    }

    class CommitDialog2 implements DialogInterface.OnClickListener{

        MainActivity2 main_activity2;
        CommitDialog2(MainActivity2 m){
            main_activity2 = m;
        }
        public void onClick(DialogInterface dialog, int which){
            Intent intent = new Intent(main_activity2, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
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

    public void selectHutwater(View view){
        Intent intent = new Intent(this, AskSponsorship.class);
        Bundle b = new Bundle();
        b.putString("name", "헛개수");
        intent.putExtras(b);
        startActivity(intent);
    }

    public void selectLikefirst(View view){
        Intent intent = new Intent(this, AskSponsorship.class);
        Bundle b = new Bundle();
        b.putString("name", "처음처럼");
        intent.putExtras(b);
        startActivity(intent);
    }

    public void logOut(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("계정이 로그아웃 되었습니다.");
        builder.setNeutralButton("확인", new CommitDialog2(this));
        builder.show();
    }

    public void myPage(View view){
        Intent intent = new Intent(this, Mypage.class);
        intent.putExtra("username", this.username);
        startActivity(intent);
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
            View rootView = inflater.inflate(R.layout.activity_main2, container, false);
            return rootView;
        }
    }
}
