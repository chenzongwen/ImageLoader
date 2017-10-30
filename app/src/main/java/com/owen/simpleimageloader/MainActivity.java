package com.owen.simpleimageloader;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.owen.imageloader.config.ImageLoaderConfig;
import com.owen.imageloader.target.TargetParams;
import com.owen.imageloader.util.ImageLoaderUtil;
import com.owen.imageloader.policy.ReversePolicy;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListView.setAdapter(new ImageAdapter(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private static class ImageAdapter extends BaseAdapter {

        private Context mContext;

        ImageAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return ImageUri.IMG_URL.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.mImageView = convertView.findViewById(R.id.image);
                viewHolder.mTextView = convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ImageLoaderUtil.getInstance().into(new TargetParams()
                    .setImageView(viewHolder.mImageView)
                    .setUri(ImageUri.IMG_URL[position]));
            viewHolder.mTextView.setText(ImageUri.IMG_URL[position]);
            return convertView;
        }
    }

    private static class ViewHolder {
        ImageView mImageView;
        TextView mTextView;
    }

}
