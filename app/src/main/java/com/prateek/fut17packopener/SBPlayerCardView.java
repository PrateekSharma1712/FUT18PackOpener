package com.prateek.fut17packopener;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prateek.fut17packopener.models.PackOpenerPlayer;

/**
 * Created by Prateek on 7/31/17.
 */

public class SBPlayerCardView extends RelativeLayout {

    private RelativeLayout card;
    private ImageView playerImage, clubImage, nationImage;
    private TextView rating;
    private TextView position;
    private TextView name;
    private TextView pac,sho,pas,dri,def,phy;
    private TextView pacLabel,shoLabel,pasLabel,driLabel,defLabel,phyLabel;

    public SBPlayerCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setLayout(this.getContext());
    }


    private void setLayout(Context context) {
        View view = inflate(context, R.layout.sb_player_card_layout, this);
        card = view.findViewById(R.id.card);
        playerImage = view.findViewById(R.id.imageView2);
        clubImage = view.findViewById(R.id.clubFlag);
        nationImage = view.findViewById(R.id.nationFlag);
        rating = findViewById(R.id.rating);
        position = findViewById(R.id.position);
        name = findViewById(R.id.name);
        pac = findViewById(R.id.pac);
        sho = findViewById(R.id.sho);
        pas = findViewById(R.id.pas);
        dri = findViewById(R.id.dri);
        def = findViewById(R.id.def);
        phy = findViewById(R.id.phy);
        pacLabel = findViewById(R.id.pacLabel);
        shoLabel = findViewById(R.id.shoLabel);
        pasLabel = findViewById(R.id.pasLabel);
        driLabel = findViewById(R.id.driLabel);
        defLabel = findViewById(R.id.defLabel);
        phyLabel = findViewById(R.id.phyLabel);

    }

    public void setValues(PackOpenerPlayer player) {
        CustomBindingAdapters.loadImage(playerImage, player.getImage());
        CustomBindingAdapters.loadImage(clubImage, player.getClub().image);
        CustomBindingAdapters.loadImage(nationImage, player.getNation().image);
        rating.setText(String.valueOf(player.getRating()));
        position.setText(String.valueOf(player.getPosition()));
        name.setText(player.getName());
        pac.setText(String.valueOf(player.getPace()));
        sho.setText(String.valueOf(player.getShot()));
        pas.setText(String.valueOf(player.getPass()));
        dri.setText(String.valueOf(player.getDribble()));
        def.setText(String.valueOf(player.getDefend()));
        phy.setText(String.valueOf(player.getPhysical()));

        switch (player.getColor()) {
            case "gold":
                card.setBackgroundResource(R.drawable.gold);
                setBlackColorForTop();
                setBlackColorForBottom();
                break;
            case "rare_gold":
                card.setBackgroundResource(R.drawable.rare_gold);
                setBlackColorForTop();
                setBlackColorForBottom();
                break;
            case "totw_gold":
                card.setBackgroundResource(R.drawable.totw_gold);
                setCustomColorForBottom(R.color.gold);
                setBlackColorForTop();
                break;
            case "legend":
                card.setBackgroundResource(R.drawable.legend);
                setCustomColorForTop(R.color.legend);
                setCustomColorForBottom(R.color.legend);
                break;
            case "ones_to_watch":
                card.setBackgroundResource(R.drawable.ones_to_watch);
                setCustomColorForTop(R.color.ones_to_watch);
                setCustomColorForBottom(R.color.ones_to_watch);
                name.setTextColor(ContextCompat.getColor(AppController.getInstance().getApplicationContext(), R.color.ones_to_watch_name));
                break;
            case "halloween":
                card.setBackgroundResource(R.drawable.ultimate_scream);
                setCustomColorForTop(R.color.halloween);
                setCustomColorForBottom(R.color.halloween);
                name.setTextColor(Color.BLACK);
                break;
            case "movember":
                card.setBackgroundResource(R.drawable.movember);
                setCustomColorForTop(R.color.movember_name);
                setCustomColorForBottom(R.color.movember);
                break;
            case "award_winner":
                card.setBackgroundResource(R.drawable.award_winner);
                setWhiteColorForTop();
                setWhiteColorForBottom();
                name.setTextColor(ContextCompat.getColor(AppController.getInstance().getApplicationContext(), R.color.award_winner_name));
                break;
            case "confederation_champions_motm":
                card.setBackgroundResource(R.drawable.confederation_champions_motm);
                setCustomColorForTop(R.color.blue);
                setWhiteColorForBottom();
                name.setTextColor(Color.WHITE);
                break;
            case "gotm":
                card.setBackgroundResource(R.drawable.totgs);
                setCustomColorForTop(R.color.gotm);
                setCustomColorForBottom(R.color.gotm);
                name.setTextColor(Color.WHITE);
                break;
            case "toty":
                card.setBackgroundResource(R.drawable.toty);
                setWhiteColorForTop();
                setWhiteColorForBottom();
                break;
            case "imotm":
                card.setBackgroundResource(R.drawable.red_blue);
                setBlackColorForTop();
                setWhiteColorForBottom();
                name.setTextColor(Color.WHITE);
                break;
            case "motm":
                card.setBackgroundResource(R.drawable.motm);
                setBlackColorForTop();
                setWhiteColorForBottom();
                break;
            case "st_patricks":
                card.setBackgroundResource(R.drawable.green);
                setCustomColorForTop(R.color.stpatrick_rating);
                setCustomColorForBottom(R.color.stpatrick);
                name.setTextColor(ContextCompat.getColor(AppController.getInstance().getApplicationContext(), R.color.stpatrick_name));
                break;
            case "fut_birthday":
                card.setBackgroundResource(R.drawable.birthday);
                setCustomColorForTop(R.color.fut_birthday);
                setWhiteColorForBottom();
                name.setTextColor(Color.WHITE);
                break;
            case "tots_gold":
                card.setBackgroundResource(R.drawable.tots_gold);
                setCustomColorForTop(R.color.dark_gold);
                setCustomColorForBottom(R.color.dark_gold);
                name.setTextColor(Color.BLACK);
                break;
            case "pink":
                card.setBackgroundResource(R.drawable.pink);
                setBlackColorForTop();
                setCustomColorForBottom(R.color.futties);
                name.setTextColor(ContextCompat.getColor(AppController.getInstance().getApplicationContext(), R.color.futties));
                break;
            case "futties_winner":
                card.setBackgroundResource(R.drawable.pink_gold);
                setBlackColorForTop();
                setCustomColorForBottom(R.color.futties);
                break;
            case "sbc_base":
                card.setBackgroundResource(R.drawable.sbc_base);
                setCustomColorForTop(R.color.sbc_base);
                setCustomColorForBottom(R.color.sbc_base_bottom);
                break;
        }
    }

    private void setBlackColorForTop() {
        rating.setTextColor(Color.BLACK);
        position.setTextColor(Color.BLACK);
    }

    private void setBlackColorForBottom() {
        name.setTextColor(Color.BLACK);
        pac.setTextColor(Color.BLACK);
        pacLabel.setTextColor(Color.BLACK);
        sho.setTextColor(Color.BLACK);
        shoLabel.setTextColor(Color.BLACK);
        pas.setTextColor(Color.BLACK);
        pasLabel.setTextColor(Color.BLACK);
        dri.setTextColor(Color.BLACK);
        driLabel.setTextColor(Color.BLACK);
        def.setTextColor(Color.BLACK);
        defLabel.setTextColor(Color.BLACK);
        phy.setTextColor(Color.BLACK);
        phyLabel.setTextColor(Color.BLACK);
    }

    private void setWhiteColorForTop() {
        rating.setTextColor(Color.WHITE);
        position.setTextColor(Color.WHITE);
    }

    private void setWhiteColorForBottom() {
        name.setTextColor(Color.WHITE);
        pac.setTextColor(Color.WHITE);
        pacLabel.setTextColor(Color.WHITE);
        sho.setTextColor(Color.WHITE);
        shoLabel.setTextColor(Color.WHITE);
        pas.setTextColor(Color.WHITE);
        pasLabel.setTextColor(Color.WHITE);
        dri.setTextColor(Color.WHITE);
        driLabel.setTextColor(Color.WHITE);
        def.setTextColor(Color.WHITE);
        defLabel.setTextColor(Color.WHITE);
        phy.setTextColor(Color.WHITE);
        phyLabel.setTextColor(Color.WHITE);
    }

    private void setCustomColorForTop(int colorId) {
        int color = ContextCompat.getColor(AppController.getInstance().getApplicationContext(), colorId);
        rating.setTextColor(color);
        position.setTextColor(color);
    }

    private void setCustomColorForBottom(int colorId) {
        int color = ContextCompat.getColor(AppController.getInstance().getApplicationContext(), colorId);
        name.setTextColor(color);
        pac.setTextColor(color);
        pacLabel.setTextColor(color);
        sho.setTextColor(color);
        shoLabel.setTextColor(color);
        pas.setTextColor(color);
        pasLabel.setTextColor(color);
        dri.setTextColor(color);
        driLabel.setTextColor(color);
        def.setTextColor(color);
        defLabel.setTextColor(color);
        phy.setTextColor(color);
        phyLabel.setTextColor(color);
    }

}
