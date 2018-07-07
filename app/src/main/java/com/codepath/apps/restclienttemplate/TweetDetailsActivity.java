package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    public ImageView ivProfileImage;
    public TextView tvUsername;
    public TextView tvScreenName;
    public TextView tvBody;
    public TextView tvTime;
    public String profileImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        this.profileImageUrl = tweet.user.profileImageUrl;

        tvUsername = findViewById(R.id.tvUserName);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvBody = findViewById(R.id.tvBody);
        tvTime = findViewById(R.id.tvTime);
        ivProfileImage = findViewById(R.id.ivProfileImage);

        tvUsername.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);
        tvTime.setText(tweet.absoluteTime);

        Glide.with(this)
                .load(profileImageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(100, 0)))
                .into(ivProfileImage);
    }
}
