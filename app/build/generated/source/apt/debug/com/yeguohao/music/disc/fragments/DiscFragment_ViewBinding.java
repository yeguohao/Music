// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.disc.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DiscFragment_ViewBinding implements Unbinder {
  private DiscFragment target;

  @UiThread
  public DiscFragment_ViewBinding(DiscFragment target, View source) {
    this.target = target;

    target.backdrop = Utils.findRequiredViewAsType(source, R.id.disc_backdrop, "field 'backdrop'", ImageView.class);
    target.recycler = Utils.findRequiredViewAsType(source, R.id.disc_recycler, "field 'recycler'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.disc_toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DiscFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.backdrop = null;
    target.recycler = null;
    target.toolbar = null;
  }
}
