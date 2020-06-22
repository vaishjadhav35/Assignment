package com.example.assignment.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.assignment.R;
import com.example.assignment.module.Modules;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterModuleList extends RecyclerView.Adapter<AdapterModuleList.ViewHolder> {

    private Context context;
    private List<Modules> modulesList;


    public AdapterModuleList(Context context, List<Modules> modulesList) {
        this.context = context;
        this.modulesList = modulesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_module_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvTitle.setText(modulesList.get(position).getTitle());
        holder.tvLastUpdate.setText(""+modulesList.get(position).getLastUpdate());
        holder.tvShortDes.setText(modulesList.get(position).getShortDescription());

        Glide.with(context)
                .load(modulesList.get(position).getAvatar())
                .into(holder.ivAvatar);


    }

    @Override
    public int getItemCount() {
        return modulesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_last_update)
        TextView tvLastUpdate;

        @BindView(R.id.tv_short_des)
        TextView tvShortDes;

        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }





}
