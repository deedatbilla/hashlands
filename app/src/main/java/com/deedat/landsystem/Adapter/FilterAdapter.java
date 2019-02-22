package com.deedat.landsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.deedat.landsystem.Model.filter;
import com.deedat.landsystem.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterAdapter extends RecyclerView.Adapter< FilterAdapter.MyViewHolder> {
    private onItemClickListener mListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private Context context;
    private List<filter> filtertag;

    public  FilterAdapter(Context context, List<filter> xfilterTag) {
        this.context = context;
        this.filtertag = xfilterTag;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tag;

        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            tag = itemView.findViewById(R.id.tag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_tags, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final filter item=filtertag.get(position);
      //  holder.tag.setText(item.getFilterText());


    }

    @Override
    public int getItemCount() {
        return   filtertag.size();
    }

}
