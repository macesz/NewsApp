package hu.netlab.orsi.newslistapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * A custom Loader that loads all the news.
 */
public class NewsLoader extends AsyncTaskLoader{
    private String mUrl;

    // Retrieve the package manager for later use;  we don't
    // use 'context' directly but instead the save global application
    // context returned by getContext().
    public NewsLoader(Context context) {
        super(context);

        mUrl = context.getString(R.string.apiUrl);
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is where the bulk of our work is done.  This function is
     * called in a background thread and should generate a new set of
     * data to be published by the loader.
     */
    @Override
    public Object loadInBackground() {
        return QueryUtils.fetchData(mUrl);
    }
}
