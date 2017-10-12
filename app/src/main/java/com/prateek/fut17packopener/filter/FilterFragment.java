package com.prateek.fut17packopener.filter;

import android.app.Dialog;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.prateek.fut17packopener.PlayerManager;
import com.prateek.fut17packopener.R;
import com.prateek.fut17packopener.models.FilterTypes;
import com.prateek.fut17packopener.models.League;
import com.prateek.fut17packopener.models.Nation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prateek on 8/28/17.
 */

public class FilterFragment extends AAH_FabulousFragment {

    private ArrayMap<FilterTypes, List<Object>> applied_filters = new ArrayMap<>();
    private TextView applyFilter, resetFilter;
    private TabLayout tabs_types;
    private List<TextView> textViews = new ArrayList<>();
    private ArrayList<Nation> nations;
    private ArrayList<League> leagues;
    private FilterPagerAdapter mAdapter;

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {

        View contentView = View.inflate(getContext(), R.layout.filter_view, null);

        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.rl_content);
        LinearLayout ll_buttons = (LinearLayout) contentView.findViewById(R.id.ll_buttons);
        resetFilter = (TextView) contentView.findViewById(R.id.resetFilters);
        applyFilter = (TextView) contentView.findViewById(R.id.applyFilters);
        ViewPager vp_types = (ViewPager) contentView.findViewById(R.id.vp_types);
        tabs_types = (TabLayout) contentView.findViewById(R.id.tabs_types);

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter(applied_filters);
            }
        });

        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTextViews();
                applied_filters.clear();
            }
        });

        mAdapter = new FilterPagerAdapter(this);
        vp_types.setOffscreenPageLimit(10);
        vp_types.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tabs_types.setupWithViewPager(vp_types);


        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewgroupStatic(ll_buttons); // optional; layout to stick at bottom on slide
        setViewPager(vp_types); //optional; if you use viewpager that has scrollview
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }

    public void addTextView(TextView textView) {
        textViews.add(textView);
    }

    public void clearTextViews() {
        for (TextView tv : textViews) {
            tv.setTag("unselected");
            tv.setBackgroundResource(R.drawable.chip_unselected);
            tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
        }
    }

    public void addToSelectedMap(FilterTypes key, Object value) {
        if (applied_filters.get(key) != null && !applied_filters.get(key).contains(value)) {
            applied_filters.get(key).add(value);
        } else {
            List<Object> temp = new ArrayList<>();
            temp.add(value);
            applied_filters.put(key, temp);
        }
    }

    public void removeFromSelectedMap(FilterTypes key, Object value) {
        if (applied_filters.get(key).size() == 1) {
            applied_filters.remove(key);
        } else {
            applied_filters.get(key).remove(value);
        }
    }

    public boolean isFilterSelected(FilterTypes filter_category, int finalI, List<Object> keys) {
        return (applied_filters != null && applied_filters.get(filter_category) != null && applied_filters.get(filter_category).contains(keys.get(finalI)));
    }
}
