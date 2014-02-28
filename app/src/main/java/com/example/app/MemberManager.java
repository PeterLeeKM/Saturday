package com.example.app;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MemberManager {
    private static MemberManager instance = null;
    private ArrayList<Member> members = null;
    public static String path = Environment.getExternalStorageDirectory()+"/members.txt";

    private MemberManager() {
        this.members = new ArrayList<Member>();
    }
    public static MemberManager getInstance() {
        if(MemberManager.instance == null)
            MemberManager.instance = new MemberManager();
        return MemberManager.instance;
    }

    public Member findMember(String username){
        for(Member member : this.members){
            if(member.getUsername().equals(username)){
                return member;
            }
        }
        return null;
    }

    public void registerMember(Member member){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", member.getUsername()));
        nameValuePairs.add(new BasicNameValuePair("password", member.getPassword()));
        nameValuePairs.add(new BasicNameValuePair("groupName", member.getGroupName()));
        nameValuePairs.add(new BasicNameValuePair("phoneNumber", member.getPhoneNumber()));
        nameValuePairs.add(new BasicNameValuePair("groupMemberCount", String.valueOf(member.getGroupMemberCount())));
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(nameValuePairs);
        asyncHttpPost.execute("http://1.234.88.155:9338/register/");
    }

    public class AsyncHttpPost extends AsyncTask<String, String, String> {
        private ArrayList<NameValuePair> nameValuePairs = null;

        public AsyncHttpPost(ArrayList<NameValuePair> nameValuePairs) {
            this.nameValuePairs = nameValuePairs;
        }

        @Override
        protected String doInBackground(String... params) {
            String str = "";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(params[0]);// in this case, params[0] is URL
            try {
                post.setEntity(new UrlEncodedFormEntity(this.nameValuePairs, "UTF-8"));
                HttpResponse response = client.execute(post);
                Log.w("response11", response.toString());
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
            }
            return str;
        }

        /**
         * on getting result
         */
        @Override
        protected void onPostExecute(String result) {
            Log.w("response", result);
        }
    }
}

