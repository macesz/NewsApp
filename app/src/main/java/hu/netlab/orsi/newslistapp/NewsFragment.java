package hu.netlab.orsi.newslistapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment implements LoaderCallbacks<List<NewsItem>> {

    private int LOADER_ID = 0;

    ArrayList<NewsItem> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;

    LoaderManager loaderManager;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsFragment.
     */
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeList(); //Start the news search
    }

    //Start the news search
    private void initializeList() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(LOADER_ID, null, this);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        MyRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new NewsRecycleAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    @Override
    public android.content.Loader<List<NewsItem>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(getActivity());
    }
    @Override
    public void onLoadFinished(android.content.Loader<List<NewsItem>> loader, List<NewsItem> newsItems) {
        listitems.clear();
        if(newsItems !=null && !newsItems.isEmpty()) {
            listitems.addAll(newsItems);
        }
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new NewsRecycleAdapter(listitems));
            ((NewsRecycleAdapter) MyRecyclerView.getAdapter()).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(NewsItem item) {
                    if(item!=null) {
                        Uri newsUri = Uri.parse(item.getUrl());
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                        startActivity(webIntent);
                    }
                }
            });
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<NewsItem>> loader) {
        listitems.clear();
    }

}
