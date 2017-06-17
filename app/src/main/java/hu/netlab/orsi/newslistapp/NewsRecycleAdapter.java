package hu.netlab.orsi.newslistapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class NewsRecycleAdapter extends RecyclerView.Adapter<NewsRecycleAdapter.NewsViewHolder> {
    private ArrayList<NewsItem> list;

    private static final int maxTitleLength = 30;
    private static final int maxContentLength = 60;

    public OnItemClickListener mOnItemClickListener;

    public NewsRecycleAdapter(ArrayList<NewsItem> newsItems) {
        list = newsItems;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        int len = (list.get(position).getTitle().length()<maxTitleLength)?list.get(position).getTitle().length()-1:maxTitleLength;
        holder.titleView.setText(list.get(position).getTitle().substring(0,len));

        holder.sectionNameView.setText(list.get(position).getSectionName());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        holder.publishedDateView.setText(df.format(list.get(position).getPublishedDate()));

        len = (list.get(position).getContentText().length()<maxContentLength)?list.get(position).getContentText().length()-1:maxContentLength;
        holder.contentTextView.setText(list.get(position).getContentText().substring(0,len));
        Picasso.with(holder.thumbnailView.getContext()).load(list.get(position).getThumbnail()).into(holder.thumbnailView);

        final NewsItem newsItem = list.get(position);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(newsItem);
            }
        };

        holder.thumbnailView.setOnClickListener(listener);
        holder.titleView.setOnClickListener(listener);
        holder.sectionNameView.setOnClickListener(listener);
        holder.publishedDateView.setOnClickListener(listener);
        holder.contentTextView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;
        public TextView sectionNameView;
        public TextView publishedDateView;
        public String url;
        public TextView contentTextView;
        public ImageView thumbnailView;

        public NewsViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.title);
            sectionNameView = (TextView) v.findViewById(R.id.section_name);
            publishedDateView = (TextView) v.findViewById(R.id.published_date);
            contentTextView = (TextView) v.findViewById(R.id.content_text);

            thumbnailView = (ImageView) v.findViewById(R.id.image);

        }
    }

    public OnItemClickListener getOnItemClickListener() {return mOnItemClickListener; }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
