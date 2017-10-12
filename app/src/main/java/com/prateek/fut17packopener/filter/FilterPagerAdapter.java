package com.prateek.fut17packopener.filter;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayout;
import com.prateek.fut17packopener.PlayerManager;
import com.prateek.fut17packopener.R;
import com.prateek.fut17packopener.models.FilterTypes;
import com.prateek.fut17packopener.models.League;
import com.prateek.fut17packopener.models.Nation;

import java.util.ArrayList;
import java.util.List;

import static com.prateek.fut17packopener.models.FilterTypes.CARD;
import static com.prateek.fut17packopener.models.FilterTypes.LEAGUE;
import static com.prateek.fut17packopener.models.FilterTypes.NATION;

/**
 * Created by Prateek on 8/28/17.
 */

public class FilterPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private FilterFragment callingFragment;
    private RecyclerView nationsList;
    private ArrayList<Nation> nations;
    private ArrayList<League> leagues;
    private NationFilterAdapter mAdapter;
    private LinearLayout.LayoutParams layoutParams;

    FilterPagerAdapter(FilterFragment callingFragment) {
        this.callingFragment = callingFragment;
        this.nations = PlayerManager.getInstance().getNations(callingFragment.getContext());
        this.leagues = PlayerManager.getInstance().getLeagues(callingFragment.getContext());
        mAdapter = new NationFilterAdapter(nations);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(4,4,4,4);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = LayoutInflater.from(callingFragment.getContext());
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_filters_sorters, container, false);
        FlexboxLayout fbl = (FlexboxLayout) layout.findViewById(R.id.fbl);

        switch (position) {
            case 0:
                inflateLayoutWithFilters(NATION, fbl);
                break;
            case 1:
                inflateLayoutWithFilters(LEAGUE, fbl);
                break;
            case 2:
                inflateLayoutWithFilters(CARD, fbl);
                break;
        }
        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return NATION.getFilterName();
            case 1: return LEAGUE.getFilterName();
            case 2: return CARD.getFilterName();
            default: return "";
        }
    }

    private void inflateLayoutWithFilters(final FilterTypes filter_category, FlexboxLayout fbl) {
        List<String> keys = new ArrayList<>();
        switch (filter_category) {
            case NATION:
                createNationsView(fbl, filter_category);
                break;
            case LEAGUE:
                createLeaguesView(fbl, filter_category);
                break;
            case CARD:
                break;
            default:
                break;
        }
    }

    private void createLeaguesView(FlexboxLayout fbl, final FilterTypes filterTypes) {
        for (int i = 0;i<leagues.size();i++) {
            League league = leagues.get(i);
            final View view = inflater.inflate(R.layout.filter_leagues, null);
            final TextView leagueName = view.findViewById(R.id.leagueName);
            final LinearLayout parent = view.findViewById(R.id.filter_league_layout);
            leagueName.setText(league.name);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view.getTag() != null && view.getTag().equals("selected")) {
                        view.setTag("unselected");
                        parent.setBackgroundResource(R.drawable.chip_unselected);
                        leagueName.setTextColor(ContextCompat.getColor(callingFragment.getContext(), android.R.color.white));
                        callingFragment.removeFromSelectedMap(filterTypes, leagues.get(finalI));
                    } else {
                        view.setTag("selected");
                        parent.setBackgroundResource(R.drawable.chip_selected);
                        leagueName.setTextColor(ContextCompat.getColor(callingFragment.getContext(), R.color.colorPrimaryDark));
                        callingFragment.addToSelectedMap(filterTypes, leagues.get(finalI));
                    }
                }
            });

            if (callingFragment.isFilterSelected(filterTypes, finalI, (ArrayList)leagues)) {
                view.setTag("selected");
                view.setBackgroundResource(R.drawable.chip_selected);
                leagueName.setTextColor(ContextCompat.getColor(callingFragment.getContext(), R.color.colorPrimaryDark));
            } else {
                view.setBackgroundResource(R.drawable.chip_unselected);
                leagueName.setTextColor(ContextCompat.getColor(callingFragment.getContext(), R.color.filters_chips));
            }
            callingFragment.addTextView(leagueName);
            fbl.addView(view);
        }
    }

    private void createNationsView(FlexboxLayout fbl, final FilterTypes filter_category) {
        for (int i = 0;i<nations.size();i++) {
            Nation nation = nations.get(i);
            final View view = inflater.inflate(R.layout.filter_nations, null);
            final TextView nationName = view.findViewById(R.id.nationName);
            final SimpleDraweeView nationFlag = view.findViewById(R.id.nationFlag);
            final LinearLayout parent = view.findViewById(R.id.filter_nation_layout);
            nationName.setText(nation.name);
            nationFlag.setImageURI(Uri.parse(nation.image));
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view.getTag() != null && view.getTag().equals("selected")) {
                        view.setTag("unselected");
                        parent.setBackgroundResource(R.drawable.chip_unselected);
                        nationName.setTextColor(ContextCompat.getColor(callingFragment.getContext(), android.R.color.white));
                        callingFragment.removeFromSelectedMap(filter_category, nations.get(finalI));
                    } else {
                        view.setTag("selected");
                        parent.setBackgroundResource(R.drawable.chip_selected);
                        nationName.setTextColor(ContextCompat.getColor(callingFragment.getContext(), R.color.colorPrimaryDark));
                        callingFragment.addToSelectedMap(filter_category, nations.get(finalI));
                    }
                }
            });

            if (callingFragment.isFilterSelected(filter_category, finalI, (ArrayList)nations)) {
                view.setTag("selected");
                view.setBackgroundResource(R.drawable.chip_selected);
                nationName.setTextColor(ContextCompat.getColor(callingFragment.getContext(), R.color.colorPrimaryDark));
            } else {
                view.setBackgroundResource(R.drawable.chip_unselected);
                nationName.setTextColor(ContextCompat.getColor(callingFragment.getContext(), R.color.filters_chips));
            }
        /*nationsList = view.findViewById(R.id.filtersList);
        nationsList.setLayoutManager(new GridLayoutManager(callingFragment.getContext(), 2, LinearLayoutManager.VERTICAL, false));
        Drawable horizontalDivider = ContextCompat.getDrawable(callingFragment.getContext(), R.drawable.divider_horizontal);
        nationsList.addItemDecoration(new GridDividerItemDecoration(horizontalDivider, horizontalDivider, 2));
        nationsList.setHasFixedSize(true);
        nationsList.setAdapter(mAdapter);*/
        callingFragment.addTextView(nationName);
        fbl.addView(view);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
