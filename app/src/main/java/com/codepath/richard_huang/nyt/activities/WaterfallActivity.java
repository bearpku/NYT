package com.codepath.richard_huang.nyt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.codepath.richard_huang.nyt.R;
import com.codepath.richard_huang.nyt.adapters.ArticleAdapter;
import com.codepath.richard_huang.nyt.adapters.ArticleItemClickListener;
import com.codepath.richard_huang.nyt.fragments.FilterFragment;
import com.codepath.richard_huang.nyt.models.Article;
import com.codepath.richard_huang.nyt.utils.AutoPaginationListener;
import com.codepath.richard_huang.nyt.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by richard_huang on 4/3/17.
 */

public class WaterfallActivity extends AppCompatActivity {
    private List<Article> articles;
    private ArticleAdapter articleAdapter;
    private RecyclerView recyclerView;
    private HandlerThread handlerThread;
    private Handler handler;
    private WebView webView;
    private String beginDate;
    private AutoPaginationListener scrollListener;
    private String prevQuery = "";
    private String sort = "Newest";
    private boolean arts = false, fashion = false, sports = false;

//    private Runnable getSearchArticles(final SearchQuery query, final boolean isNewSearch) {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                loadArticles(query, isNewSearch);
//            }
//        };
//        return runnable;
//    }
//
//    private void loadNextDataFromApi(int page) {
//        handler.postDelayed(getSearchArticles(new SearchQuery(prevQuery), false), 500);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);

        // load initial data
        articles = new ArrayList<>();
        articleAdapter = new ArticleAdapter(this, articles);
        Utils.fetchArticles(articles, articleAdapter);

        recyclerView = (RecyclerView) findViewById(R.id.articlesView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(articleAdapter);
        scrollListener = new AutoPaginationListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                loadNextPage(page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        articleAdapter.setOnItemClickListener(new ArticleItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Article article = articles.get(position);

                Intent intent = new Intent(WaterfallActivity.this, WebViewActivity.class);
                intent.putExtra("web_url", article.getWebUrl());
                startActivity(intent);
            }
        });

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        handlerThread = new HandlerThread("RequestHandler");
        handlerThread.start();
        handler = new Handler(Looper.getMainLooper());

        Calendar c = Calendar.getInstance();
        String mm = Integer.toString(c.get(Calendar.MONTH));
        String dd = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        if (c.get(Calendar.MONTH) < 10) mm = "0" + mm;
        if (c.get(Calendar.DAY_OF_MONTH) < 10) dd = "0" + dd;
        beginDate = Integer.toString(c.get(Calendar.YEAR)) + mm + dd;
        Log.d("begin_date", beginDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search_text);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                prevQuery = query;
//                handler.postDelayed(getSearchArticles(new SearchQuery(prevQuery), true), 500);
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_text) {
            return true;
        } else if (id == R.id.search_filter) {
            FilterFragment.newInstance(beginDate, sort, arts, fashion, sports)
                    .show(getSupportFragmentManager(), "editFilter");
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveEdit(String order, String begin_date, boolean arts, boolean fashion, boolean sports) {
        sort = order;
        this.beginDate = begin_date;
        this.arts = arts;
        this.fashion = fashion;
        this.sports = sports;
        String fq = "";
        StringBuilder sb = new StringBuilder();
        if (arts || fashion || sports) {
            fq = sb.append("news_desk:(")
                    .append(arts ? "\"Arts\"" : "")
                    .append(fashion ? "\"Fashion &amp; Style\" " : "")
                    .append(sports ? "\"Sports\" " : "")
                    .append(")")
                    .toString();
        }
//        handler.postDelayed(getSearchArticles(new SearchQuery(prevQuery, fq, order, begin_date), true)  , 500);
    }
}
