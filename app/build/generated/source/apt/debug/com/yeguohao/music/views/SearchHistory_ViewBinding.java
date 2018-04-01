// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.views;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchHistory_ViewBinding implements Unbinder {
  private SearchHistory target;

  @UiThread
  public SearchHistory_ViewBinding(SearchHistory target) {
    this(target, target);
  }

  @UiThread
  public SearchHistory_ViewBinding(SearchHistory target, View source) {
    this.target = target;

    target.clear = Utils.findRequiredViewAsType(source, R.id.search_clear, "field 'clear'", ImageView.class);
    target.recycler = Utils.findRequiredViewAsType(source, R.id.search_history_recycler, "field 'recycler'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchHistory target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.clear = null;
    target.recycler = null;
  }
}
