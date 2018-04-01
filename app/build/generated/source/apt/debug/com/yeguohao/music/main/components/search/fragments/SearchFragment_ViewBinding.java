// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.main.components.search.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import com.yeguohao.music.views.FlowLayout;
import com.yeguohao.music.views.SearchHistory;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchFragment_ViewBinding implements Unbinder {
  private SearchFragment target;

  @UiThread
  public SearchFragment_ViewBinding(SearchFragment target, View source) {
    this.target = target;

    target.hotGroup = Utils.findRequiredViewAsType(source, R.id.search_hot_group, "field 'hotGroup'", FlowLayout.class);
    target.recycler = Utils.findRequiredViewAsType(source, R.id.search_recycler, "field 'recycler'", RecyclerView.class);
    target.editText = Utils.findRequiredViewAsType(source, R.id.search_edit, "field 'editText'", EditText.class);
    target.close = Utils.findRequiredViewAsType(source, R.id.search_close, "field 'close'", ImageView.class);
    target.history = Utils.findRequiredViewAsType(source, R.id.search_history, "field 'history'", SearchHistory.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.hotGroup = null;
    target.recycler = null;
    target.editText = null;
    target.close = null;
    target.history = null;
  }
}
