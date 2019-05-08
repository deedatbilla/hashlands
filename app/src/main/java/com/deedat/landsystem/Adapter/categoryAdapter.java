package com.deedat.landsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deedat.landsystem.Model.category;
import com.deedat.landsystem.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.MyViewHolder> {
    private onItemClickListener mListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private  onfavoriteClickListener onfavoriteClick;

    public interface onfavoriteClickListener {
        void onfavClick(int position);
    }

    private Context context;
    private List<category> categoryList;


    public categoryAdapter (Context context, List<category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;

    }



    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public void onfavoriteClick(onfavoriteClickListener listener) {
        onfavoriteClick = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryname;
        ImageView thumbnail;


        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener, final onfavoriteClickListener fav_listener) {
            super(itemView);
            thumbnail=itemView.findViewById(R.id.category_image);
            categoryname=itemView.findViewById(R.id.category_name);
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
                .inflate(R.layout.categories, parent, false);

        return new MyViewHolder(itemView, mListener,onfavoriteClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final category cat = categoryList.get(position);

        holder.categoryname.setText(cat.getCategory_name());
        Glide.with(context)
                .load(cat.getThumbnail())
                .into(holder.thumbnail);



    }


    @Override
    public int getItemCount() {
        return   categoryList.size();
    }





}
