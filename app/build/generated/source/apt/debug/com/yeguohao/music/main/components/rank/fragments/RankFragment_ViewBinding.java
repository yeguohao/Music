// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.main.components.rank.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RankFragment_ViewBinding implements Unbinder {
  private RankFragment target;

  @UiThread
  public RankFragment_ViewBinding(RankFragment target, View source) {
    this.target = target;

    target.recycler = Utils.findRequiredViewAsType(source, R.id.rank_recycler, "field 'recycler'", RecyclerView.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.color = ContextCompat.getColor(context, R.color.colorPrimary);
    target.dp20 = res.getDimensionPixelSize(R.dimen.dp20);
  }

  @Override
  @CallSuper
  public void unbind() {
    RankFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycler = null;
  }
}
