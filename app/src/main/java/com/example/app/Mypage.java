package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Mypage extends ActionBarActivity implements Observer{

    private String hutWater = "헛개수", likeFirst = "처음처럼";
    private LinearLayout currentLayout;
    private TextView defaultView;
    private ImageData imageDataHutWater = new ImageData(R.drawable.hutwater, hutWater);
    private ImageData imageDataLikeFirst = new ImageData(R.drawable.likefirst, likeFirst);
    private ImageData imageDataCancel = new ImageData(R.drawable.cancel_button, "Cancel");
    private int requestId=1;
    private Map<ImageView, Integer> map = new HashMap<ImageView, Integer>();
    private RelativeLayout deletedLayout;
    private boolean defaultOrNot = true;

    @Override
    public void update(Observable observable, Object o) {
        HashMap<String, Object> data = (HashMap<String, Object>)o;

        if(data.get("name").equals("requestList")) {
            try {
                JSONArray list = new JSONArray((String)data.get("result"));
                Log.w("list", list.toString());

                for(int i=0; i<list.length(); ++i) {
                    requestId = list.getJSONObject(i).getInt("requestId");
                    String productName = list.getJSONObject(i).getString("productName");
                    String address = list.getJSONObject(i).getString("address");
                    String requiredTime = list.getJSONObject(i).getString("requiredTime");
                    showRequest(productName, address, requiredTime, requestId);
                    if(defaultOrNot){
                        this.defaultOrNot = false;
                        this.currentLayout.removeView(defaultView);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(data.get("name").equals("cancelRequest")) {
            try {
                JSONObject reader = new JSONObject((String)data.get("result"));
                if(reader.getString("status").equals("OK")) {
                    //취소되었습ㄴ디ㅏ.
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("알림");
                    builder.setMessage("스폰이 취소되었습니다.");
                    builder.setNeutralButton("확인", new CommitDialog(this, deletedLayout, currentLayout));
                    builder.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onDestroy() {
        MemberManager.getInstance().deleteObserver(this);
        super.onDestroy();
    }

    public void showRequest(String productName, String address, String requiredTime, int requestId){
        RelativeLayout relativeLayout = new RelativeLayout(this);
        TextView productNameText = makeText("물품명 : ", productName);
        FrameLayout frameLayout = new FrameLayout(this);
        ImageView imageView = makeImageIfRequested(productName);
        TextView addressText = makeText("도착예정지 : ", address);
        TextView requiredTimeText = makeText("도착예정일 : ", requiredTime);
        ImageView cancel = makeButton(imageDataCancel);

        frameLayout.setForegroundGravity(FrameLayout.TEXT_ALIGNMENT_CENTER);
        FrameLayout.LayoutParams cancelParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        cancelParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        cancelParams.setMargins(0, 0, 10, 10);
        FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        imageParams.gravity = Gravity.CENTER_HORIZONTAL;
        frameLayout.addView(imageView, imageParams);
        frameLayout.addView(cancel, cancelParams);

        RelativeLayout.LayoutParams productParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams frameParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams addressParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams requiredTimeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        productNameText.setId(1);
        frameLayout.setId(2);
        addressText.setId(4);
        requiredTimeText.setId(5);
        cancel.setId(6);

        productParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        productParams.setMargins(20, 0, 0, 10);
        frameParams.addRule(RelativeLayout.BELOW, productNameText.getId());
        frameParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        frameParams.setMargins(0, 10, 0, 0);
        addressParams.addRule(RelativeLayout.BELOW, frameLayout.getId());
        addressParams.setMargins(20, 10, 0, 10);
        requiredTimeParams.addRule(RelativeLayout.BELOW, addressText.getId());
        requiredTimeParams.setMargins(20, 0, 0, 0);

        relativeLayout.addView(productNameText, productParams);
        relativeLayout.addView(frameLayout, frameParams);
        relativeLayout.addView(addressText, addressParams);
        relativeLayout.addView(requiredTimeText, requiredTimeParams);

        LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        relativeParams.setMargins(0, 30, 0, 0);
        relativeLayout.setPadding(30, 30, 30, 30);
        relativeLayout.setBackgroundColor(Color.parseColor("#FFDEC0"));
        this.currentLayout.addView(relativeLayout, relativeParams);

        map.put(cancel, requestId);
    }

    private TextView makeText(String label, String text){
        TextView textView = new TextView(this);
        textView.setText(label + text);
        textView.setTextSize(20);
        return textView;
    }

    private ImageView makeImageIfRequested(String productName){
        if(productName.equals(imageDataLikeFirst.getProductName())){
            return makeImage(imageDataLikeFirst);
        }
        else{
            return makeImage(imageDataHutWater);
        }
    }

    private ImageView makeImage(ImageData imageData){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imageData.getDrawableImage());
        return imageView;
    }

    private ImageView makeButton(ImageData imageData){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imageData.getDrawableImage());
        imageView.setOnClickListener(new ImageClick(this));
        return imageView;
    }

    class ImageData{
        private int drawableImage;
        private String productName;
        public ImageData(int drawableImage, String productName){
            this.drawableImage = drawableImage;
            this.productName = productName;
        }
        public int getDrawableImage() { return drawableImage; }
        public String getProductName() { return productName; }
    }

    class CommitDialog implements DialogInterface.OnClickListener{
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;
        Mypage mypage;
        public CommitDialog(Mypage mypage, RelativeLayout relativeLayout, LinearLayout linearLayout){
            this.mypage = mypage;
            this.relativeLayout = relativeLayout;
            this.linearLayout = linearLayout;
        }
        public void onClick(DialogInterface dialog, int which){
            linearLayout.removeView(relativeLayout);
            if(map.size() == 0){
                defaultOrNot = true;
                defaultView = new TextView(mypage);
                defaultView.setText("현재 신청하신 내역이 없습니다.");
                defaultView.setTextSize(20);
                defaultView.setTextColor(Color.parseColor("#444444"));
                LinearLayout.LayoutParams defaultParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                defaultParams.gravity = Gravity.CENTER;
                currentLayout.addView(defaultView, defaultParams);
            }
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

    class ImageClick implements View.OnClickListener{
        private Mypage mypage;
        public ImageClick(Mypage mypage){
            this.mypage = mypage;
        }
        @Override
        public void onClick(View view) {
            int requestId = map.get((ImageView)view);
            deletedLayout = (RelativeLayout)(view.getParent().getParent());
            MemberManager.getInstance().cancel(requestId);
            map.remove((ImageView)view);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        this.currentLayout = (LinearLayout)findViewById(R.id.requsted_products);
        this.currentLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        if(defaultOrNot){
            this.defaultView = new TextView(this);
            defaultView.setText("현재 신청하신 내역이 없습니다.");
            defaultView.setTextSize(20);
            defaultView.setTextColor(Color.parseColor("#666666"));
            LinearLayout.LayoutParams defaultParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            defaultParams.gravity = Gravity.CENTER;
            this.currentLayout.addView(defaultView, defaultParams);
        }
        MemberManager.getInstance().addObserver(this);
        MemberManager.getInstance().requestList();
        /*Intent intent = getIntent();
        MemberManager.getInstance().addObserver(this);
        MemberManager.getInstance().requestList();

        TextView groupName_text = (TextView)findViewById(R.id.groupName_text);
        TextView groupMemberCount_text = (TextView)findViewById(R.id.groupMemberCount_text);
        groupName_text.setText(groupName);
        groupMemberCount_text.setText(groupMemberCount);*/
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

    public void main(View view){
        Intent intent = new Intent(this, MainActivity2.class);
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
