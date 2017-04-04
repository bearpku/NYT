package com.codepath.richard_huang.nyt.utils;

import android.util.Log;

import com.codepath.richard_huang.nyt.adapters.ArticleAdapter;
import com.codepath.richard_huang.nyt.models.Article;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by richard_huang on 4/3/17.
 */

public class Utils {
    private static Date lastFetch = null;
    public static void fetchArticles(RequestParams params, final ArticleAdapter articleAdapter) {
        if (lastFetch != null && new Date().getTime() - lastFetch.getTime() < 1000) {
            return;
        }

        lastFetch = new Date();

        AsyncHttpClient client = new AsyncHttpClient();
        final Gson gson = new GsonBuilder().create();

        params.put("api-key", Constants.API_KEY);
        Log.d("params", params.toString());

        client.get(Constants.BASE_API_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray articlesJson = response.getJSONObject("response").getJSONArray("docs");
                    for (int i = 0; i < articlesJson.length(); i++) {
                        JSONObject articleJson = articlesJson.getJSONObject(i);
                        Article article = gson.fromJson(articleJson.toString(), Article.class);
                        articleAdapter.addItem(article);
                    }
                    articleAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("error", e.toString());
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });
    }

    public static String getFullImageURL(String imageURL) {
        return Constants.BASE_IMAGE_URL + imageURL;
    }
}
