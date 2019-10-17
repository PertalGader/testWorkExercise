package com.example.inratingstatistics.jsonParser;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class jsonParser {


    public String getViewCount(String jsonStr,String property){
        String viewCount = "";
        switch (property){
            case "views_count":
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    viewCount = jsonObject.getString("views_count");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return viewCount;

            case "likes_count":
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    viewCount = jsonObject.getString("likes_count");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return viewCount;
            case "comments_count":
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    viewCount = jsonObject.getString("comments_count");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return viewCount;
            case "bookmarks_count":
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    viewCount = jsonObject.getString("bookmarks_count");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return viewCount;
            case "reposts_count":
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    viewCount = jsonObject.getString("reposts_count");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return viewCount;
        }
        return null;
    }

    public List<String> getUrls(String jsonStr){
        List<String> urlsList = new ArrayList<String>();
        try{
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jArr = jsonObject.getJSONArray("data");
            for(int i = 0;i < jArr.length();i++){
                String url = jArr.getJSONObject(i).getJSONObject("avatar_image").getString("url_small");
                urlsList.add(url);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return urlsList;
    }
    public List<String> getNicknames(String jsonStr){
        List<String> nickNamesList = new ArrayList<String>();
        try{
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jArr = jsonObject.getJSONArray("data");
            for(int i = 0;i < jArr.length();i++){
                String url = jArr.getJSONObject(i).getString("nickname");
                nickNamesList.add(url);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return nickNamesList;
    }


}
