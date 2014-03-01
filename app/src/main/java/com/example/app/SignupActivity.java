package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class SignupActivity extends ActionBarActivity {

    private String username, password, password_confirmation, groupName, phoneNumber, groupMemberCount;

    class CommitDialog implements DialogInterface.OnClickListener{

        public void onClick(DialogInterface dialog, int which) throws NullPointerException{

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ((EditText)findViewById(R.id.register_id)).setImeOptions(EditorInfo.IME_ACTION_NEXT);

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
        username = ((EditText)findViewById(R.id.register_id)).getText().toString();
        password = ((EditText)findViewById(R.id.register_password)).getText().toString();
        password_confirmation = ((EditText)findViewById(R.id.register_password_confirmation)).getText().toString();
        groupName = ((EditText)findViewById(R.id.register_groupname)).getText().toString();
        phoneNumber = ((EditText)findViewById(R.id.register_phone_number)).getText().toString();
        groupMemberCount = ((EditText)findViewById(R.id.register_number_of_people)).getText().toString();

        if(password.equals(password_confirmation)){
            memberManager.registerMember(new Member(username, password, groupName, phoneNumber, Integer.parseInt(groupMemberCount)));
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("username", username);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("알림");
            builder.setMessage("비밀번호 확인이 올바르지 않습니다.");
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
            View rootView = inflater.inflate(R.layout.activity_signup, container, false);
            return rootView;
        }
    }
}
