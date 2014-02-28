package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class SignupActivity extends ActionBarActivity {

    private String username, password, groupName, phoneNumber, groupMemberCount;

    class CommitDialog implements DialogInterface.OnClickListener{

        SignupActivity signup;
        CommitDialog(SignupActivity m){
            signup = m;
        }
        public void onClick(DialogInterface dialog, int which) throws NullPointerException{
            MemberManager memberManager = MemberManager.getInstance();
            Log.i("register", "11111111");
            username = ((EditText)findViewById(R.id.edit_ID)).getText().toString();
            Log.i("register", "22222222");
            password = ((EditText)findViewById(R.id.edit_password)).getText().toString();
            groupName = ((EditText)findViewById(R.id.edit_group_name)).getText().toString();
            phoneNumber = ((EditText)findViewById(R.id.edit_phone_number)).getText().toString();
            groupMemberCount = ((EditText)findViewById(R.id.edit_number_of_member)).getText().toString();
            Log.i("register", "33333333");

            memberManager.registerMember(new Member(username, password, groupName, phoneNumber, Integer.parseInt(groupMemberCount)));
            Intent intent = new Intent(signup, MainActivity2.class);
            intent.putExtra("username", username);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ((EditText)findViewById(R.id.edit_ID)).setImeOptions(EditorInfo.IME_ACTION_NEXT);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signup, menu);
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

    public void signUp(View view){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("알림");
//        builder.setMessage("가입 완료!");
//        builder.setNeutralButton("확인", new CommitDialog(this));
//        builder.show();
        MemberManager memberManager = MemberManager.getInstance();
        username = ((EditText)findViewById(R.id.edit_ID)).getText().toString();
        password = ((EditText)findViewById(R.id.edit_password)).getText().toString();
        groupName = ((EditText)findViewById(R.id.edit_group_name)).getText().toString();
        phoneNumber = ((EditText)findViewById(R.id.edit_phone_number)).getText().toString();
        groupMemberCount = ((EditText)findViewById(R.id.edit_number_of_member)).getText().toString();

        memberManager.registerMember(new Member(username, password, groupName, phoneNumber, Integer.parseInt(groupMemberCount)));
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("username", username);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.onStop();
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
            View rootView = inflater.inflate(R.layout.activity_signup, container, false);
            return rootView;
        }
    }
}
