package com.example.justforfun.dribbble.Dribbble.auth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Auth {
    public static final int REQ_CODE = 100;

    private static final String KEY_CODE = "code";
    private static final String KEY_CLIENT_ID = "client_id";
    private static final String KEY_CLIENT_SECRET = "client_secret";
    private static final String KEY_REDIRECT_URI = "redirect_uri";
    private static final String KEY_SCOPE = "scope";
    private static final String KEY_ACCESS_TOKEN = "access_token";

    private static final String CLIENT_ID = "3d7a4b9abac2d3a0d5d136254646a9c797c1ee2c33eb88a4bbd862c6332d914d";
    private static final String CLIENT_SECRET = "6f16c9085005778a8cfe30524b1a896276ac9173437c472046f71b6d2e9d2fd6";

    private static final String SCOPE = "public+upload";

    public static final String REDIRECT_URI = "http://www.google.com";
    private static final String URI_TOKEN = "https://dribbble.com/oauth/token";
    private static final String URI_AUTHORIZE = "https://dribbble.com/oauth/authorize";

    private static String getAuthorizeUrl(){
        String url = Uri.parse(URI_AUTHORIZE).buildUpon()
                .appendQueryParameter(KEY_CLIENT_ID, CLIENT_ID)
                .build().toString();

        url += "&" + KEY_REDIRECT_URI + "=" + REDIRECT_URI;
        url += "&" + KEY_SCOPE + "=" + SCOPE;

        return url;
    }

    private static String getTokenUrl(String authCode) {
        return Uri.parse(URI_TOKEN)
                .buildUpon()
                .appendQueryParameter(KEY_CLIENT_ID, CLIENT_ID)
                .appendQueryParameter(KEY_CLIENT_SECRET, CLIENT_SECRET)
                .appendQueryParameter(KEY_CODE, authCode)
                .appendQueryParameter(KEY_REDIRECT_URI, REDIRECT_URI)
                .build()
                .toString();
    }

    public static void openAuthActivity(Activity activity){
        Intent intent = new Intent(activity,AuthActivity.class);
        intent.putExtra(AuthActivity.KEY_URL, getAuthorizeUrl());

        activity.startActivityForResult(intent, REQ_CODE);
    }

    public static String fetchAccessToke(String authCode) throws IOException{
        OkHttpClient client = new OkHttpClient();
        RequestBody postBody = new FormBody.Builder()
                .add(KEY_CLIENT_ID, CLIENT_ID)
                .add(KEY_CLIENT_SECRET,CLIENT_SECRET)
                .add(KEY_CODE, authCode)
                .add(KEY_REDIRECT_URI,REDIRECT_URI).build();

        Request request = new Request.Builder()
                .url(URI_TOKEN)
                .post(postBody).build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        try{
            JSONObject obj = new JSONObject(responseString);
            return obj.getString(KEY_ACCESS_TOKEN);
        }catch(JSONException e){
            e.printStackTrace();
            return "";
        }
    }
}
