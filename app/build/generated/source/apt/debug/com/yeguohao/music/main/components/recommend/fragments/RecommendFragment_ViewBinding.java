// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.main.components.recommend.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import com.yeguohao.music.views.Banner;
import com.yeguohao.music.views.LoadingView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecommendFragment_ViewBinding implements Unbinder {
  private RecommendFragment target;

  @UiThread
  public RecommendFragment_ViewBinding(RecommendFragment target, View source) {
    this.target = target;

    target.banner = Utils.findRequiredViewAsType(source, R.id.recommend_banner, "field 'banner'", Banner.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.recommend_title, "field 'title'", TextView.class);
    target.recycler = Utils.findRequiredViewAsType(source, R.id.recommend_recycler, "field 'recycler'", RecyclerView.class);
    target.loadingView = Utils.findRequiredViewAsType(source, R.id.recommend_loading, "field 'loadingView'", LoadingView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecommendFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.banner = null;
    target.title = null;
    target.recycler = null;
    target.loadingView = null;
  }
}
