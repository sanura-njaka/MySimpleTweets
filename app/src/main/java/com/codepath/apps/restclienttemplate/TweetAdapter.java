package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets;
    Context context;
    private final ClickListener listener;
    private static Activity activity;
    private static String profileImageUrl;

    // pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets, ClickListener listener, Activity activity) {
        this.listener = listener;
        mTweets = tweets;
        this.activity = activity;
    }

    // for each row, inflate the layout and cache references into ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView, listener);
        return viewHolder;
    }

    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data according to position
        Tweet tweet = mTweets.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvScreenName.setText("@" + tweet.user.screenName);
        holder.tvBody.setText(tweet.body);
        holder.tvTime.setText(tweet.formattedTime);

        this.profileImageUrl = tweet.user.profileImageUrl;

        Glide.with(context)
                .load(profileImageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(100, 0)))
                .into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(ArrayList<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

    // create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeakReference<ClickListener> listenerRef;

        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvScreenName;
        public TextView tvBody;
        public TextView tvTime;
        public Button btReply;
        public ImageView ivRetweet;
        public ImageView ivFavorite;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);

            // perform findViewByItem lookups
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivRetweet = itemView.findViewById(R.id.ivRetweet);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
            btReply = itemView.findViewById(R.id.btReply);

            listenerRef = new WeakReference<>(listener);
            itemView.setOnClickListener(this);
            ivRetweet.setOnClickListener(this);
            ivFavorite.setOnClickListener(this);
            btReply.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            if (v.getId() == btReply.getId()) {
                String screenName = tvScreenName.getText().toString();

                Intent intent = new Intent(itemView.getContext(), ComposeActivity.class);
                intent.putExtra("isReply", true);
                intent.putExtra("replyTo", screenName);
                //itemView.getContext().startActivity(intent);
                activity.startActivityForResult(intent, 17);
            } else {
                TimelineActivity activity = (TimelineActivity) itemView.getContext();
                activity.openTweetDetails(mTweets.get(getAdapterPosition()));
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
