package com.example.justforfun.dribbble.Dribbble.auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.justforfun.dribbble.Model.User;
import com.example.justforfun.dribbble.Utils.ModelUtils;
import com.example.justforfun.dribbble.View.LoginActivity;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Dribbble {

    private static final String SP_AUTH = "auth";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_USER = "user";
    private static final String KEY_LOGIN = "login";

    private static final String API_URL = "https://api.dribbble.com/v2/";
    private static final String USER_END_POINT = API_URL+"user";

    private static String accessToken;
    private static User user;

        public static void init(Context context) {
            /*accessToken = loadASccessToken(context);
            if(accessToken != null){
                user = loadUser(context);
            }*/
            SharedPreferences sp = context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE);
            boolean login_status = sp.getBoolean(KEY_LOGIN,false);
            if(login_status == true){
                user = loadUser(context);
            }
        }

        public static boolean isLoggedIn(Context context){
            SharedPreferences sp = context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE);
            boolean login_status = sp.getBoolean(KEY_LOGIN,false);
            return login_status == true;
        }

        private static String loadASccessToken(Context context){
            SharedPreferences sp = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE);
            return sp.getString(KEY_ACCESS_TOKEN, null);
        }

        public static User loadUser(Context context){
            return ModelUtils.read(context,KEY_USER, new TypeToken<User>(){});
        }

        public static void login(Context context, String token) throws IOException,JSONException {
            Dribbble.accessToken = token;
            storeAccessToken(context, token);

            Dribbble.user = getUser();
            storeUser(context, user);
            SharedPreferences sp = context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE);
            sp.edit().putBoolean(KEY_LOGIN,true);
        }

        public static void logout(Context context){
            storeAccessToken(context, null);
            storeUser(context,null);

            accessToken = null;
            user = null;

            SharedPreferences sp = context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE);
            sp.edit().putBoolean(KEY_LOGIN,false);
        }

        public static void storeAccessToken(Context context, String token){
            SharedPreferences sp = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE);
            sp.edit().putString(KEY_ACCESS_TOKEN, token).apply();
        }

        public static User getUser() throws IOException, JSONException{
            Request.Builder builder = new Request.Builder().
                    addHeader("Authorization","Bearer "+accessToken).
                    url(USER_END_POINT);
            Request request = builder.build();

            Response response = makeRequest(request);
            User user = parseResponce(response, new TypeToken<User>(){});
            return user;
        }

        public static void storeUser(Context context, User user){
            ModelUtils.save(context, KEY_USER, user);
        }

        public static User getCurrentUser(){
            return user;
        }

        private static Response makeGetRequest(String url) throws IOException{
            Request request = authRequestBuider(url).build();
            return makeRequest(request);
        }
        private static Request.Builder authRequestBuider(String url){

            return new Request.Builder().addHeader("Authorization","Bearer "+accessToken).url(url);
        }

        private static Response makeRequest(Request request) throws IOException{
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            return response;
        }

        private static <T> T parseResponce(Response response, TypeToken<T> typeToken) throws IOException,JSONException{
            String responseString = response.body().string();
            return ModelUtils.toObject(responseString,typeToken);
        }
}
