package com.deedat.landsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LandInfoAdapter extends RecyclerView.Adapter<LandInfoAdapter.MyViewHolder> {
    private onItemClickListener mListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private Context context;
    private List<LandInfo> landInfos;

    public LandInfoAdapter(Context context, List<LandInfo> xlandinfo) {
        this.context = context;
        this.landInfos = xlandinfo;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView landcode, dimen, location, owner;

        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            landcode = itemView.findViewById(R.id.landcode);
            dimen = itemView.findViewById(R.id.dimen);
            location = itemView.findViewById(R.id.location);
            owner = itemView.findViewById(R.id.owner_name);

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
                .inflate(R.layout.land_info_fav, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final LandInfo landDetails = landInfos.get(position);
        holder.landcode.setText(landDetails.getLandcode());
        holder.owner.setText(landDetails.getOwner_name());
        holder.location.setText(landDetails.getLocation());
        holder.dimen.setText(landDetails.getDimen());

    }

    @Override
    public int getItemCount() {
      return   landInfos.size();
    }

}
