package com.yeguohao.music.components.recommend.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeguohao.music.javabean.RecommendBean;

import java.util.ArrayList;
import java.util.List;

public class RecommendPagerAdapter extends PagerAdapter {

    private List<RecommendBean.Data.Slider> sliders = new ArrayList<>();

    public void setSliders(List<RecommendBean.Data.Slider> sliders) {
        this.sliders.clear();
        this.sliders.addAll(sliders);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return sliders.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Glide.with(container.getContext()).load(sliders.get(position).getPicUrl()).into(imageView);
        container.addView(imageView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

}
