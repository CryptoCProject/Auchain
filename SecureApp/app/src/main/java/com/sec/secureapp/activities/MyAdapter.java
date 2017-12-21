package com.sec.secureapp.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sec.secureapp.R;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    String[] auctionTitles;
    String[] auctionObject;
    String[] auctionAuctioneer;
    double[] auctionPrices;

    Context mContext;

    public MyAdapter(@NonNull Context context, String[] auctionTitles, String[] auctionObject, String[] auctionAuctioneer, double[] auctionPrices) {
        this.auctionTitles = auctionTitles;
        this.auctionObject = auctionObject;
        this.auctionAuctioneer = auctionAuctioneer;
        this.auctionPrices = auctionPrices;
        this.mContext = context;
    }

/*
    @Override
    public int getCount() {
        return auctionTitles.length;
    }
*/

   /* @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.recycle, parent, false);

            mViewHolder._auctionTitle = (TextView) convertView.findViewById(R.id.auction_title);
            mViewHolder._auctionObject = (TextView) convertView.findViewById(R.id.auction_object);
            mViewHolder._auctionAuctioneer = (TextView) convertView.findViewById(R.id.auction_auctioneer);
            mViewHolder._auctionPrice = (TextView) convertView.findViewById(R.id.auction_price);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder._auctionTitle.setText(auctionTitles[position]);
        mViewHolder._auctionObject.setText(auctionObject[position]);
        mViewHolder._auctionAuctioneer.setText(auctionAuctioneer[position]);
        mViewHolder._auctionPrice.setText(this.mContext.getString(R.string.auction_price, auctionPrices[position]));

        return convertView;
    }*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.recyclerview_item, parent, false);
        MyViewHolder mainHolder = new MyViewHolder(mainGroup) {
            @Override
            public String toString() {
                return super.toString();
            }
        };


        return mainHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MyViewHolder mainHolder = (MyViewHolder) holder;
        //Setting text over textview
        mainHolder._auctionTitle.setText(auctionTitles[position]);
        mainHolder._auctionObject.setText(auctionObject[position]);
        mainHolder._auctionAuctioneer.setText(auctionAuctioneer[position]);
        mainHolder._auctionPrice.setText(this.mContext.getString(R.string.auction_price, auctionPrices[position]));

    }
    @Override
    public int getItemCount() {
        return auctionTitles.length;
    }
/*
    static class ViewHolder {
        TextView _auctionTitle;
        TextView _auctionObject;
        TextView _auctionAuctioneer;
        TextView _auctionPrice;
    }*/
}
