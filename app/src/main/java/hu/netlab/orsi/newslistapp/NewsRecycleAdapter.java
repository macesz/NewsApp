package hu.netlab.orsi.newslistapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

// Az adapter osztályok,  összekötik a listát a háttérben álló adatokkal,
// az adatkészlet elemeit ábrázoló nézetek megjelenítéséért felelős.

public class NewsRecycleAdapter extends RecyclerView.Adapter<NewsRecycleAdapter.NewsViewHolder> {

    private ArrayList<NewsItem> list;

    private static final int maxTitleLength = 30;
    private static final int maxContentLength = 60;

    public OnItemClickListener mOnItemClickListener;

    public NewsRecycleAdapter(ArrayList<NewsItem> newsItems) {
        list = newsItems;
    }

    //Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    //This new ViewHolder should be constructed with a new View that can represent the items of the given type.
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view,  Inflate it from an XML layout file.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }

    //Called by RecyclerView to display the data at the specified position.
    // This method should update the contents of the itemView to reflect the item at the given position.
    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {

        //Returns the Adapter position of the item represented by this ViewHolder.
        // I need the position of an item later on in a click listener.
        final NewsItem newsItem = list.get(position);

        // Set the titleview, and how long it can be.
        int len = (list.get(position).getTitle().length()<maxTitleLength)?list.get(position).getTitle().length()-1:maxTitleLength;
        holder.titleView.setText(list.get(position).getTitle().substring(0,len));

        holder.sectionNameView.setText(list.get(position).getSectionName());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        holder.publishedDateView.setText(df.format(list.get(position).getPublishedDate()));

        // Set the contentview, and how long it can be.
        len = (list.get(position).getContentText().length()<maxContentLength)?list.get(position).getContentText().length()-1:maxContentLength;
        holder.contentTextView.setText(list.get(position).getContentText().substring(0,len));
        Picasso.with(holder.thumbnailView.getContext()).load(list.get(position).getThumbnail()).into(holder.thumbnailView);

        //Create an instance (peldany) of OnClickListener  and override the onClick-method.
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the item from onClick
                mOnItemClickListener.onItemClick(newsItem);
            }
        };

        //set OnClickListeners on viewholder items.
        holder.thumbnailView.setOnClickListener(listener);
        holder.titleView.setOnClickListener(listener);
        holder.sectionNameView.setOnClickListener(listener);
        holder.publishedDateView.setOnClickListener(listener);
        holder.contentTextView.setOnClickListener(listener);
    }

    //Returns the total number of items in the data set held by the adapter.
    @Override
    public int getItemCount() {
        return list.size();
    }

    //A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    public class NewsViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;
        public TextView sectionNameView;
        public TextView publishedDateView;
        public TextView contentTextView;
        public ImageView thumbnailView;

        //subclass ViewHolder add fields for caching potentially expensive findViewById(int) results.
        public NewsViewHolder(View v) {
            super(v);

            titleView = v.findViewById(R.id.title);
            sectionNameView = v.findViewById(R.id.section_name);
            publishedDateView = v.findViewById(R.id.published_date);
            contentTextView = v.findViewById(R.id.content_text);
            thumbnailView = v.findViewById(R.id.image);

        }
    }

    // The callback to be invoked with an item in this AdapterView has been clicked, or null id no callback has been set.
    public OnItemClickListener getOnItemClickListener() {return mOnItemClickListener; }

    //Register a callback to be invoked when an item in this AdapterView has been clicked.
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
