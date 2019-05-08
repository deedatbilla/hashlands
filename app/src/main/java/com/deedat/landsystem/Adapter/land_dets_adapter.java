package com.deedat.landsystem.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deedat.landsystem.Activity.PropertyActivity;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class land_dets_adapter extends RecyclerView.Adapter<land_dets_adapter.MyViewHolder> {
    private onItemClickListener mListener;
    public boolean clicked;
    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private  onfavoriteClickListener onfavoriteClick;

    public interface onfavoriteClickListener {
        void onfavClick(int position);
    }

    private Context context;
    private List<LandInfo> landInfos;
    private boolean disable;

    public  land_dets_adapter(Context context, List<LandInfo> xlandinfo,Boolean disable) {
        this.context = context;
        this.landInfos = xlandinfo;
        this.disable=disable;

    }

   private String message;
   public boolean is_sale_clicked;


    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public void onfavoriteClick(onfavoriteClickListener listener) {
        onfavoriteClick = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView landcode, dimen, location, owner,sale;
        ImageView menu,thumbnail;
        ImageButton btn_fav;

        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener, final onfavoriteClickListener fav_listener) {
            super(itemView);
           landcode=itemView.findViewById(R.id.code);
           dimen=itemView.findViewById(R.id.dimensions);
           location=itemView.findViewById(R.id.location);
           owner=itemView.findViewById(R.id.owner_name);
           btn_fav=itemView.findViewById(R.id.fav);
           sale=itemView.findViewById(R.id.sale);

           thumbnail=itemView.findViewById(R.id.llimage);



           if (disable) {
               btn_fav.setVisibility(View.GONE);
               sale.setVisibility(View.GONE);
           }
           else {
               btn_fav.setVisibility(View.VISIBLE);
               sale.setVisibility(View.VISIBLE);
           }


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
//
               @Override
               public void onClick(View v) {
                   boolean isFavourite = readState();
                   if (isFavourite) {
                       btn_fav.setBackgroundResource(R.drawable.ic_favorite_off);
                       isFavourite = false;
                       saveState(isFavourite);

                   } else {
                       btn_fav.setBackgroundResource(R.drawable.ic_favorite_on);
                       isFavourite = true;
                       saveState(isFavourite);

                   }
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
                .inflate(R.layout.land_list_new, parent, false);

        return new MyViewHolder(itemView, mListener,onfavoriteClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final LandInfo landDetails = landInfos.get(position);
        holder.landcode.setText(landDetails.getLandcode());
        holder.owner.setText(landDetails.getOwner_name());
        holder.location.setText(landDetails.getLandregion()+"-"+landDetails.getLandarea());
        holder.dimen.setText(landDetails.getLength()+" by "+landDetails.getWidth()+" sq.ft");


            Glide.with(context)
                    .load(landDetails.getThumbnail())
                    .into(holder.thumbnail);





    }


    @Override
    public int getItemCount() {
        return   landInfos.size();
    }

    private void saveState(boolean isFavourite) {
        SharedPreferences aSharedPreferences = context.getSharedPreferences(
                "Favourite", Context.MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferencesEdit = aSharedPreferences
                .edit();
        aSharedPreferencesEdit.putBoolean("State", isFavourite);
        aSharedPreferencesEdit.commit();
    }

    private boolean readState() {
        SharedPreferences aSharedPreferences = context.getSharedPreferences(
                "Favourite", Context.MODE_PRIVATE);
        return aSharedPreferences.getBoolean("State", false);
    }



}
