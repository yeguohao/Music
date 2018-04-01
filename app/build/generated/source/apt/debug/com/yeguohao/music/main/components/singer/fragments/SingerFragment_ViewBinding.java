// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.main.components.singer.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import com.yeguohao.music.views.LetterIndexView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SingerFragment_ViewBinding implements Unbinder {
  private SingerFragment target;

  @UiThread
  public SingerFragment_ViewBinding(SingerFragment target, View source) {
    this.target = target;

    target.recycler = Utils.findRequiredViewAsType(source, R.id.singer_recycler, "field 'recycler'", RecyclerView.class);
    target.letterIndex = Utils.findRequiredViewAsType(source, R.id.singer_letter_index, "field 'letterIndex'", LetterIndexView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SingerFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycler = null;
    target.letterIndex = null;
  }
}
