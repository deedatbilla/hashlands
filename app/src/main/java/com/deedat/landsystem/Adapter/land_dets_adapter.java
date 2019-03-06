package com.deedat.landsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class land_dets_adapter extends RecyclerView.Adapter<land_dets_adapter.MyViewHolder> {
    private onItemClickListener mListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private  onfavoriteClickListener onfavoriteClick;

    public interface onfavoriteClickListener {
        void onfavClick(int position);
    }

    private Context context;
    private List<LandInfo> landInfos;

    public  land_dets_adapter(Context context, List<LandInfo> xlandinfo) {
        this.context = context;
        this.landInfos = xlandinfo;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public void onfavoriteClick(onfavoriteClickListener listener) {
        onfavoriteClick = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView landcode, dimen, location, owner;
        ImageView call,thumbnail;
        Button btn_fav;

        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener, final onfavoriteClickListener fav_listener) {
            super(itemView);
           landcode=itemView.findViewById(R.id.code);
           dimen=itemView.findViewById(R.id.dimensions);
           location=itemView.findViewById(R.id.location);
           owner=itemView.findViewById(R.id.owner_name);
           btn_fav=itemView.findViewById(R.id.btn_fav);
           call=itemView.findViewById(R.id.call);
           thumbnail=itemView.findViewById(R.id.llimage);

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

            btn_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fav_listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                          fav_listener.onfavClick(position);
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
                .inflate(R.layout.lands_list, parent, false);

        return new MyViewHolder(itemView, mListener,onfavoriteClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final LandInfo landDetails = landInfos.get(position);
        holder.landcode.setText(landDetails.getLandcode());
        holder.owner.setText(landDetails.getOwner_name());
        holder.location.setText(landDetails.getLocation());
        holder.dimen.setText(landDetails.getDimen());
        Glide.with(context)
                .load(landDetails.getThumbnail())
                .into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return   landInfos.size();
    }

}
