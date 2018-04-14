// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.main.components.search.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import com.yeguohao.music.views.FlowLayout;
import com.yeguohao.music.views.SearchHistory;
import com.yeguohao.music.views.SearchView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchFragment_ViewBinding implements Unbinder {
  private SearchFragment target;

  @UiThread
  public SearchFragment_ViewBinding(SearchFragment target, View source) {
    this.target = target;

    target.hotGroup = Utils.findRequiredViewAsType(source, R.id.search_hot_group, "field 'hotGroup'", FlowLayout.class);
    target.history = Utils.findRequiredViewAsType(source, R.id.search_history, "field 'history'", SearchHistory.class);
    target.searchView = Utils.findRequiredViewAsType(source, R.id.search, "field 'searchView'", SearchView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.hotGroup = null;
    target.history = null;
    target.searchView = null;
  }
}
