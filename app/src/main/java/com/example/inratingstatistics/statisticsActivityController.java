package com.example.inratingstatistics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inratingstatistics.jsonParser.jsonParser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class statisticsActivityController extends AppCompatActivity {
    public static String gotPostID ;

    TextView postIdText;
    TextView viewCountText;
    TextView likesCountTextView;
    TextView comenntsCountTextView;
    TextView bookmarkTextView;
    TextView repostsTextView;
    TextView marksTextView;
    LinearLayout layoutLikes;
    LinearLayout layoutComments;
    LinearLayout layoutReposts;
    LinearLayout layoutMarks;

    private String viewsResponseStr;
    private String likersResponseStr;
    private String commentsResponseStr;
    private String repostsResponseStr;
    private String marksResponseStr;

    private static final String postsUrl = "https://api.inrating.top/v1/users/posts/get";
    private static final String likesUrl = "https://api.inrating.top/v1/users/posts/likers/all";
    private static final String repostsUrl = "https://api.inrating.top/v1/users/posts/reposters/all";
    private static final String commentsUrl = "https://api.inrating.top/v1/users/posts/commentators/all";
    private static final String marksUrl = "https://api.inrating.top/v1/users/posts/mentions/all";



    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.statistics_activity);

        postIdText = findViewById(R.id.postIdTextView);
        viewCountText = findViewById(R.id.viewCountTextView);
        likesCountTextView = findViewById(R.id.likesCountTextView);
        comenntsCountTextView = findViewById(R.id.comenntsCountTextView);
        bookmarkTextView = findViewById(R.id.bookmarkCount);
        layoutReposts = findViewById(R.id.repostsLinearLayout);
        layoutMarks = findViewById(R.id.marksLinearLayout);
        repostsTextView = findViewById(R.id.repostsTextVIew);
        marksTextView = findViewById(R.id.marksTextView);

        layoutLikes = (LinearLayout)findViewById(R.id.linearLikes);
        layoutComments = (LinearLayout) findViewById(R.id.commentLayout);
        gotPostID = getIntent().getStringExtra("postId");
        postIdText.append(gotPostID);
        System.out.println(gotPostID);

        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        viewsResponseStr = doPostRequest(gotPostID, postsUrl);
                        likersResponseStr = doPostRequest(gotPostID,likesUrl);
                        commentsResponseStr = doPostRequest(gotPostID,commentsUrl);
                        repostsResponseStr = doPostRequest(gotPostID,repostsUrl);
                        marksResponseStr = doPostRequest(gotPostID,marksUrl);

                        showImages(layoutLikes,likersResponseStr);
                        showImages(layoutComments,commentsResponseStr);
                        showImages(layoutReposts,repostsResponseStr);
                        showImages(layoutMarks,marksResponseStr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            thread.join();

        }catch (InterruptedException e){
            e.printStackTrace();
        }


        jsonParser parser = new jsonParser();

        viewCountText.setText(parser.getViewCount(viewsResponseStr,"views_count") + " Переглядів");
        likesCountTextView.append(" "+parser.getViewCount(viewsResponseStr,"likes_count"));
        comenntsCountTextView.append(" "+parser.getNicknames(commentsResponseStr).size());
        bookmarkTextView.setText(parser.getViewCount(viewsResponseStr,"bookmarks_count"));
        repostsTextView.append(" " + parser.getViewCount(viewsResponseStr,"reposts_count"));
        marksTextView.append(" " + parser.getNicknames(marksResponseStr).size());

    }

    private String doPostRequest(String postId, String url) throws IOException {
        Response response;
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id", postId)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImRiYTYzMGE0YzIxYWZlNzRhYTVlNTgwZjBiZjliMDQ1YThmYzY4NWViMjE0ZmFmZTY4ZDAzMGQzZjdiNThhMDg3M2M1MzU3MTNjNjIxNmE5In0.eyJhdWQiOiIyIiwianRpIjoiZGJhNjMwYTRjMjFhZmU3NGFhNWU1ODBmMGJmOWIwNDVhOGZjNjg1ZWIyMTRmYWZlNjhkMDMwZDNmN2I1OGEwODczYzUzNTcxM2M2MjE2YTkiLCJpYXQiOjE1Njg2MzI3MDEsIm5iZiI6MTU2ODYzMjcwMSwiZXhwIjoxNjAwMjU1MTAxLCJzdWIiOiIzMDQ2MTAiLCJzY29wZXMiOltdfQ.BB_dmBJDg-NO72-HSqudwt3Ql4kO7uyVjx4sMTpMURpJZfRpd1-7wUjfERWumhp1JcQzFwiRsihKTLN_rVeLNzspcXUHtp6OTRBEPowrzhQq3tB8-mQxTL-8KV2QPOaDmYufoowtCaxbTp7ciKs7Ybs4t9XYfGQoyBurWJsspOU_ikvWHlZViHZQjs82aAIblC-XyQh94sJz0_3mDHQPcGhlpBXp-RMi-hghGZsFS_ZhugSKvz5bKeR0bzDTui1baUoGnCFOWwSDn_tek1rSpAIdOe5WK1J2Opvjx7kjq7ycjbDy8ZNurWhEExa8rrFRRHlzN2AwiWkwAd6BmMHI_nVhgGbqUzDg88_393nX8T8Fdrek-IU1k461MzAycsRiKTj_vRXVyNie25v-wg3aQ9PciB-zQH1JbBM6wPmavwi-5zWD5Ab5m3FGijBviPCnN2Q-H9aBN3zPLYrYHG305Rmq6DHc_QoC_GYISk_BbbU3_DrvnUml2ey07B0bprVKJPZWYE9LHW5A3k4-yV3eAR5rf-JcQjCigONs0lkcA9uu91R-n3VxmtIyOIO76SLdkiZEebV8yvIC696zrxPMzNktoDUc-SP359MZDHIvmtL2uyy3s83n3LtgwgamZC4e6_-GmU89npACLD5hK7-lVXeVRGAKZuhTHKWuKbEqLJ8")
                    .build();
            response = client.newCall(request).execute();
            String respStr = response.body().string();
            //System.out.println(respStr);
            return respStr;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }catch (NullPointerException e){
            e.printStackTrace();
            return  null;
        }

    }

    private void showImages(final LinearLayout layout, final String response) throws InterruptedException{
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<View> allImages;
                List<String> imgUrls;
                List<String> nickNamesList;
                int count = 0;
                jsonParser parser = new jsonParser();
                imgUrls = parser.getUrls(response);
                nickNamesList = parser.getNicknames(response);
                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisc(true)
                        .build();
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .defaultDisplayImageOptions(defaultOptions)
                        .build();
                ImageLoader.getInstance().init(config);
                allImages = new ArrayList<View>();

                for(int i = 0;i < imgUrls.size();i++){
                    final View view = getLayoutInflater().inflate(R.layout.custom_imageview,null);
                    ImageView image = (ImageView) view.findViewById(R.id.likeImage);
                    TextView nickNameText = (TextView) view.findViewById(R.id.nickNameTextView);
                    ImageLoader.getInstance().displayImage(imgUrls.get(i),image);
                    nickNameText.setText(nickNamesList.get(i));
                    allImages.add(view);
                    layout.addView(view);
                }
            }
        });




    }

}

