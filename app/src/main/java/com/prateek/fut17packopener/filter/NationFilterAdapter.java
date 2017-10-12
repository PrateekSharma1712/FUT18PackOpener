package com.prateek.fut17packopener.filter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.prateek.fut17packopener.R;
import com.prateek.fut17packopener.database.PlayerDAO;
import com.prateek.fut17packopener.models.Nation;

import java.util.ArrayList;

/**
 * Created by prateek on 7/9/17.
 */

public class NationFilterAdapter extends RecyclerView.Adapter<NationFilterAdapter.NationFilterHolder> {

    private ArrayList<Nation> nations;

    public NationFilterAdapter(ArrayList<Nation> nations) {
        this.nations = nations;
    }

    public class NationFilterHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView nationFlag;
        private TextView nationName;

        public NationFilterHolder(View itemView) {
            super(itemView);
            nationFlag = itemView.findViewById(R.id.nationFlag);
            nationName = itemView.findViewById(R.id.nationName);
        }
    }

    @Override
    public NationFilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NationFilterHolder holder = new NationFilterHolder(inflater.inflate(R.layout.line_item_filter_nation, null, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NationFilterHolder holder, int position) {
        Nation nation = nations.get(position);
        holder.nationFlag.setImageURI(Uri.parse(nation.image));
        holder.nationName.setText(nation.name);
    }

    @Override
    public int getItemCount() {
        return nations.size();
    }
}
