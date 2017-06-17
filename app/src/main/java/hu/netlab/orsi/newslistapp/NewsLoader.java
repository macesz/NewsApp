package hu.netlab.orsi.newslistapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class NewsLoader extends AsyncTaskLoader{
    private String mUrl;

    public NewsLoader(Context context) {
        super(context);

        mUrl = context.getString(R.string.apiUrl);
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        return QueryUtils.fetchData(mUrl);
    }
}
