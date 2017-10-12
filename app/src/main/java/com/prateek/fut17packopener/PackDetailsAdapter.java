package com.prateek.fut17packopener;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.prateek.fut17packopener.models.PackOpenerPlayer;
import com.prateek.fut17packopener.ui.PackDetailsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by prateek on 8/8/17.
 */

public class PackDetailsAdapter extends RecyclerView.Adapter<PackDetailsAdapter.PlayerHolder> {

    private List<PackOpenerPlayer> players;
    private ItemClickListener mListener;
    private PackDetailsActivity mActivity;

    public void updateData(ArrayList<PackOpenerPlayer> players) {
        this.players = players;
        Collections.sort(this.players);
        notifyDataSetChanged();
    }

    public void removePlayer(PackOpenerPlayer player) {
        this.players.remove(player);
        Collections.sort(players);
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClicked(PackOpenerPlayer player);
    }

    public PackDetailsAdapter(ItemClickListener listener, PackDetailsActivity activity) {
        this.players = PlayerManager.getInstance().getPackPlayers();
        mListener = listener;
        mActivity = activity;
        Collections.sort(this.players);
    }

    public PackDetailsAdapter(ItemClickListener listener, ArrayList<PackOpenerPlayer> players) {
        this.players = players;
        mListener = listener;
        Collections.sort(this.players);
    }

    private void setBlackColorForTop(PlayerHolder holder) {
        holder.rating.setTextColor(Color.BLACK);
        holder.position.setTextColor(Color.BLACK);
    }

    private void setBlackColorForBottom(PlayerHolder holder) {
        holder.playerName.setTextColor(Color.BLACK);
        holder.pac.setTextColor(Color.BLACK);
        holder.pacLabel.setTextColor(Color.BLACK);
        holder.sho.setTextColor(Color.BLACK);
        holder.shoLabel.setTextColor(Color.BLACK);
        holder.pas.setTextColor(Color.BLACK);
        holder.pasLabel.setTextColor(Color.BLACK);
        holder.dri.setTextColor(Color.BLACK);
        holder.driLabel.setTextColor(Color.BLACK);
        holder.def.setTextColor(Color.BLACK);
        holder.defLabel.setTextColor(Color.BLACK);
        holder.phy.setTextColor(Color.BLACK);
        holder.phyLabel.setTextColor(Color.BLACK);
    }

    private void setWhiteColorForTop(PlayerHolder holder) {
        holder.rating.setTextColor(Color.WHITE);
        holder.position.setTextColor(Color.WHITE);
    }

    private void setWhiteColorForBottom(PlayerHolder holder) {
        holder.playerName.setTextColor(Color.WHITE);
        holder.pac.setTextColor(Color.WHITE);
        holder.pacLabel.setTextColor(Color.WHITE);
        holder.sho.setTextColor(Color.WHITE);
        holder.shoLabel.setTextColor(Color.WHITE);
        holder.pas.setTextColor(Color.WHITE);
        holder.pasLabel.setTextColor(Color.WHITE);
        holder.dri.setTextColor(Color.WHITE);
        holder.driLabel.setTextColor(Color.WHITE);
        holder.def.setTextColor(Color.WHITE);
        holder.defLabel.setTextColor(Color.WHITE);
        holder.phy.setTextColor(Color.WHITE);
        holder.phyLabel.setTextColor(Color.WHITE);
    }

    private void setCustomColorForTop(PlayerHolder holder, int colorId) {
        int color = ContextCompat.getColor(AppController.getInstance().getApplicationContext(), colorId);
        holder.rating.setTextColor(color);
        holder.position.setTextColor(color);
    }

    private void setCustomColorForBottom(PlayerHolder holder, int colorId) {
        int color = ContextCompat.getColor(AppController.getInstance().getApplicationContext(), colorId);
        holder.playerName.setTextColor(color);
        holder.pac.setTextColor(color);
        holder.pacLabel.setTextColor(color);
        holder.sho.setTextColor(color);
        holder.shoLabel.setTextColor(color);
        holder.pas.setTextColor(color);
        holder.pasLabel.setTextColor(color);
        holder.dri.setTextColor(color);
        holder.driLabel.setTextColor(color);
        holder.def.setTextColor(color);
        holder.defLabel.setTextColor(color);
        holder.phy.setTextColor(color);
        holder.phyLabel.setTextColor(color);
    }

    @Override
    public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_item_pack_detail, parent, false);
        return new PlayerHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlayerHolder holder, int position) {
        final PackOpenerPlayer player = players.get(position);
        holder.binding.setVariable(BR.player, player);
        holder.playerImage.setImageURI(Uri.parse(player.getImage()));
        holder.clubImage.setImageURI(Uri.parse(player.getClub().image));
        holder.nationImage.setImageURI(Uri.parse(player.getNation().image));

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(players.get(holder.getAdapterPosition()));
            }
        });

        if (player.getPosition().equalsIgnoreCase("GK")) {
            holder.pacLabel.setText("DIV");
            holder.shoLabel.setText("HAN");
            holder.pasLabel.setText("KIC");
            holder.driLabel.setText("REF");
            holder.defLabel.setText("SPD");
            holder.phyLabel.setText("POS");
        } else {
            holder.pacLabel.setText("PAC");
            holder.shoLabel.setText("SHO");
            holder.pasLabel.setText("PAS");
            holder.driLabel.setText("DRI");
            holder.defLabel.setText("DEF");
            holder.phyLabel.setText("PHY");
        }

        switch (player.getColor()) {
            case "gold":
                holder.card.setBackgroundResource(R.drawable.gold);
                setBlackColorForTop(holder);
                setBlackColorForBottom(holder);
                break;
            case "rare_gold":
                holder.card.setBackgroundResource(R.drawable.rare_gold);
                setBlackColorForTop(holder);
                setBlackColorForBottom(holder);
                break;
            case "totw_gold":
                holder.card.setBackgroundResource(R.drawable.totw_gold);
                setCustomColorForBottom(holder, R.color.gold);
                setBlackColorForTop(holder);
                break;
            case "legend":
                holder.card.setBackgroundResource(R.drawable.legend);
                setCustomColorForTop(holder, R.color.legend);
                setCustomColorForBottom(holder, R.color.legend);
                break;
            case "ones_to_watch":
                holder.card.setBackgroundResource(R.drawable.ones_to_watch);
                setCustomColorForTop(holder, R.color.ones_to_watch);
                setCustomColorForBottom(holder, R.color.ones_to_watch);
                holder.playerName.setTextColor(ContextCompat.getColor(AppController.getInstance().getApplicationContext(), R.color.ones_to_watch_name));
                break;
            case "halloween":
                holder.card.setBackgroundResource(R.drawable.ultimate_scream);
                setCustomColorForTop(holder, R.color.halloween);
                setCustomColorForBottom(holder, R.color.halloween);
                holder.playerName.setTextColor(Color.BLACK);
                break;
            case "movember":
                holder.card.setBackgroundResource(R.drawable.movember);
                setCustomColorForTop(holder, R.color.movember_name);
                setCustomColorForBottom(holder, R.color.movember);
                break;
            case "award_winner":
                holder.card.setBackgroundResource(R.drawable.award_winner);
                setWhiteColorForTop(holder);
                setWhiteColorForBottom(holder);
                holder.playerName.setTextColor(ContextCompat.getColor(AppController.getInstance().getApplicationContext(), R.color.award_winner_name));
                break;
            case "confederation_champions_motm":
                holder.card.setBackgroundResource(R.drawable.confederation_champions_motm);
                setCustomColorForTop(holder, R.color.blue);
                setWhiteColorForBottom(holder);
                holder.playerName.setTextColor(Color.WHITE);
                break;
            case "gotm":
                holder.card.setBackgroundResource(R.drawable.totgs);
                setCustomColorForTop(holder, R.color.gotm);
                setCustomColorForBottom(holder, R.color.gotm);
                holder.playerName.setTextColor(Color.WHITE);
                break;
            case "toty":
                holder.card.setBackgroundResource(R.drawable.toty);
                setWhiteColorForTop(holder);
                setWhiteColorForBottom(holder);
                break;
            case "imotm":
                holder.card.setBackgroundResource(R.drawable.red_blue);
                setBlackColorForTop(holder);
                setWhiteColorForBottom(holder);
                holder.playerName.setTextColor(Color.WHITE);
                break;
            case "motm":
                holder.card.setBackgroundResource(R.drawable.motm);
                setBlackColorForTop(holder);
                setWhiteColorForBottom(holder);
                break;
            case "st_patricks":
                holder.card.setBackgroundResource(R.drawable.green);
                setCustomColorForTop(holder, R.color.stpatrick_rating);
                setCustomColorForBottom(holder, R.color.stpatrick);
                holder.playerName.setTextColor(ContextCompat.getColor(AppController.getInstance().getApplicationContext(), R.color.stpatrick_name));
                break;
            case "fut_birthday":
                holder.card.setBackgroundResource(R.drawable.birthday);
                setCustomColorForTop(holder, R.color.fut_birthday);
                setWhiteColorForBottom(holder);
                holder.playerName.setTextColor(Color.WHITE);
                break;
            case "tots_gold":
                holder.card.setBackgroundResource(R.drawable.tots_gold);
                setCustomColorForTop(holder, R.color.dark_gold);
                setCustomColorForBottom(holder, R.color.dark_gold);
                holder.playerName.setTextColor(Color.BLACK);
                break;
            case "pink":
                holder.card.setBackgroundResource(R.drawable.pink);
                setBlackColorForTop(holder);
                setCustomColorForBottom(holder, R.color.futties);
                holder.playerName.setTextColor(ContextCompat.getColor(AppController.getInstance().getApplicationContext(), R.color.futties));
                break;
            case "futties_winner":
                holder.card.setBackgroundResource(R.drawable.pink_gold);
                setBlackColorForTop(holder);
                setCustomColorForBottom(holder, R.color.futties);
                break;
            case "sbc_base":
                holder.card.setBackgroundResource(R.drawable.sbc_base);
                setCustomColorForTop(holder, R.color.sbc_base);
                setCustomColorForBottom(holder, R.color.sbc_base_bottom);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    class PlayerHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;
        SimpleDraweeView playerImage, clubImage, nationImage;
        RelativeLayout card;
        TextView rating, position, playerName, pac, pacLabel, sho, shoLabel, pas, pasLabel, dri, driLabel, def, defLabel, phy, phyLabel;

        public PlayerHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            playerImage = binding.getRoot().findViewById(R.id.playerImage);
            nationImage = binding.getRoot().findViewById(R.id.nationFlag);
            clubImage = binding.getRoot().findViewById(R.id.clubFlag);
            card = binding.getRoot().findViewById(R.id.card);
            rating = binding.getRoot().findViewById(R.id.rating);
            position = binding.getRoot().findViewById(R.id.position);
            playerName = binding.getRoot().findViewById(R.id.playerName);
            pac = binding.getRoot().findViewById(R.id.pac);
            pacLabel = binding.getRoot().findViewById(R.id.pacLabel);
            sho = binding.getRoot().findViewById(R.id.sho);
            shoLabel = binding.getRoot().findViewById(R.id.shoLabel);
            pas = binding.getRoot().findViewById(R.id.pas);
            pasLabel = binding.getRoot().findViewById(R.id.pasLabel);
            dri = binding.getRoot().findViewById(R.id.dri);
            driLabel = binding.getRoot().findViewById(R.id.driLabel);
            def = binding.getRoot().findViewById(R.id.def);
            defLabel = binding.getRoot().findViewById(R.id.defLabel);
            phy = binding.getRoot().findViewById(R.id.phy);
            phyLabel = binding.getRoot().findViewById(R.id.phyLabel);
        }
    }
}
