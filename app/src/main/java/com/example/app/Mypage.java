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
import android.widget.TextView;

public class Mypage extends ActionBarActivity {

    private String username, groupName, groupMemberCount;

    class CommitDialog implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int which){

        }
    }

    class CommitDialog2 implements DialogInterface.OnClickListener{

        Mypage mypage;
        CommitDialog2(Mypage m){
            mypage = m;
        }
        public void onClick(DialogInterface dialog, int which){
            Intent intent = new Intent(mypage, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");

        MemberManager memberManager = MemberManager.getInstance();
        Member member = memberManager.findMember(this.username);
        this.groupName = member.getGroupName();
        this.groupMemberCount = String.valueOf(member.getGroupMemberCount());

        TextView groupName_text = (TextView)findViewById(R.id.groupName_text);
        TextView groupMemberCount_text = (TextView)findViewById(R.id.groupMemberCount_text);
        groupName_text.setText(groupName);
        groupMemberCount_text.setText(groupMemberCount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mypage, menu);
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

    public void cancleSpon(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("죄송합니다. 현재 물품은 이미 배송이 완료되어 취소할 수 없습니다.");
        builder.setNeutralButton("확인", new CommitDialog());
        builder.show();
    }

    public void main(View view){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("username", username);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void logOut(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("계정이 로그아웃 되었습니다.");
        builder.setNeutralButton("확인", new CommitDialog2(this));
        builder.show();
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
            View rootView = inflater.inflate(R.layout.activity_mypage, container, false);
            return rootView;
        }
    }

}
