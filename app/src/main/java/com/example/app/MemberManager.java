package com.example.app;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class MemberManager extends Observable {
    private static MemberManager instance = null;
    public static String path = Environment.getExternalStorageDirectory()+"/members.txt";
    private int userId;

    private MemberManager() {
    }
    public static MemberManager getInstance() {
        if(MemberManager.instance == null)
            MemberManager.instance = new MemberManager();
        return MemberManager.instance;
    }

    public void cancel(int requestId) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("requestId", String.valueOf(requestId)));
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost("cancelRequest", nameValuePairs);
        asyncHttpPost.execute("http://1.234.88.155:9338/cancel/");
    }

    public void requestList() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userId", String.valueOf(this.userId)));
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost("requestList", nameValuePairs);
        asyncHttpPost.execute("http://1.234.88.155:9338/requests/");
    }

    public void requestSpon(String productName, String address, String requiredTime) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userId", String.valueOf(this.userId)));
        nameValuePairs.add(new BasicNameValuePair("productName", productName));
        nameValuePairs.add(new BasicNameValuePair("address", address));
        nameValuePairs.add(new BasicNameValuePair("requiredTime", requiredTime));
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost("requestSpon", nameValuePairs);
        asyncHttpPost.execute("http://1.234.88.155:9338/request/");
    }

    public void login(String username, String password) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost("login", nameValuePairs);
        asyncHttpPost.execute("http://1.234.88.155:9338/login/");
    }

    public void registerMember(Member member){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", member.getUsername()));
        nameValuePairs.add(new BasicNameValuePair("password", member.getPassword()));
        nameValuePairs.add(new BasicNameValuePair("groupName", member.getGroupName()));
        nameValuePairs.add(new BasicNameValuePair("phoneNumber", member.getPhoneNumber()));
        nameValuePairs.add(new BasicNameValuePair("groupMemberCount", String.valueOf(member.getGroupMemberCount())));
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost("register", nameValuePairs);
        asyncHttpPost.execute("http://1.234.88.155:9338/register/");
    }

    public class AsyncHttpPost extends AsyncTask<String, String, String> {
        private ArrayList<NameValuePair> nameValuePairs = null;
        private String name = null;

        public AsyncHttpPost(String name, ArrayList<NameValuePair> nameValuePairs) {
            this.name = name;
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
                str = EntityUtils.toString(response.getEntity());
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
        protected void onPostExecute(String jsonStr) {
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("name", this.name);
            data.put("result", jsonStr);
            MemberManager.this.setChanged();
            MemberManager.this.notifyObservers(data);
        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

