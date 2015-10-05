package ru.furnituranatali.app;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vavan on 27.09.2015.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<CardData> mCards;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCard;
        public ViewHolder(CardView itemView) {
            super(itemView);
            mCard = itemView;
        }
    }

    public MainAdapter(List<CardData> mCards) {
        this.mCards = mCards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView txt_v = (TextView) holder.mCard.findViewById(R.id.textViewOfCard);
        txt_v.setText(mCards.get(position).getCaption());
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
