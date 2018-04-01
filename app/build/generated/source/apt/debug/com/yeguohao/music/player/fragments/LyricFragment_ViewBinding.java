// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.player.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LyricFragment_ViewBinding implements Unbinder {
  private LyricFragment target;

  @UiThread
  public LyricFragment_ViewBinding(LyricFragment target, View source) {
    this.target = target;

    target.recycler = Utils.findRequiredViewAsType(source, R.id.lyirc_recycler, "field 'recycler'", RecyclerView.class);
    target.tips = Utils.findRequiredViewAsType(source, R.id.lyirc_tips, "field 'tips'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LyricFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycler = null;
    target.tips = null;
  }
}
