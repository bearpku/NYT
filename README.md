# Homework 2 - The New York Times

**NYT** is an android app that allows a user to search for articles on web using simple filters.
The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **12** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [x] User can click on "settings" which allows selection of **advanced search options** to filter results
* [x] User can configure advanced search filters such as:
* [x] Begin Date (using a date picker)
* [x] News desk values (Arts, Fashion & Style, Sports)
* [x] Sort order (oldest or newest)
* [x] Subsequent searches have any filters applied to the search results
* [x] User can tap on any article in results to view the contents in an embedded browser.
* [x] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:

* [x] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText

The following **bonus** features are implemented:

* [x] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the `StaggeredGridLayoutManager` to display improve the grid of image results
* [x] Leverages the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [ ] Replace the embedded `WebView` with [Chrome Custom Tabs](http://guides.codepath.com/android/Chrome-Custom-Tabs) using a custom action button for sharing. (_**2 points**_)

## Video Walkthrough

Here's a walkthrough of implemented user stories:

#### loading the initial articles, and open it in webview
![img](https://cl.ly/0l360P1X0f1O/Screen%20Recording%202017-04-03%20at%2011.26%20PM.gif)

#### Search
![img](https://cl.ly/2i1j1w1J1P3k/Screen%20Recording%202017-04-03%20at%2011.27%20PM.gif)

#### Pagination
![img](https://cl.ly/2m0W2743142G/Screen%20Recording%202017-04-03%20at%2011.29%20PM.gif)

#### Advanced Filter
![img](https://cl.ly/0N1W0T3i0D1f/Screen%20Recording%202017-04-03%20at%2011.31%20PM.gif)


## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
