package com.solutionplanets.navkar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SummaryAdapter extends FirestoreRecyclerAdapter<Summary, SummaryAdapter.SummaryHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SummaryAdapter(@NonNull FirestoreRecyclerOptions<Summary> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SummaryHolder holder, int position, @NonNull Summary model) {
        holder.date.setText(model.getDate());
        holder.duration.setText(model.getDuration());
        holder.count.setText(model.getCount());
    }

    @NonNull
    @Override
    public SummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_item, parent, false);
        return new SummaryHolder(view);
    }

    //setup view holder class
    class SummaryHolder extends RecyclerView.ViewHolder{
        TextView date, duration, count;

        public SummaryHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);
            count = itemView.findViewById(R.id.count);
        }
    }
}
