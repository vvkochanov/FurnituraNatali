package ru.furnituranatali.app;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Vavan on 27.09.2015.
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout mCard;
        public ViewHolder(FrameLayout itemView) {
            super(itemView);
            mCard = itemView;
        }
    }

    public TestAdapter(String[] textDataSet) {
        mDataset = textDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.test_card, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView txt_v = (TextView) holder.mCard.findViewById(R.id.textViewOfCard);
        txt_v.setText(String.format(mDataset[position], position));
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
