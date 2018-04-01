// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.song.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SongFragment_ViewBinding implements Unbinder {
  private SongFragment target;

  @UiThread
  public SongFragment_ViewBinding(SongFragment target, View source) {
    this.target = target;

    target.recycler = Utils.findRequiredViewAsType(source, R.id.song_recycler, "field 'recycler'", RecyclerView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.song_title, "field 'title'", TextView.class);
    target.background = Utils.findRequiredViewAsType(source, R.id.song_background, "field 'background'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SongFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycler = null;
    target.title = null;
    target.background = null;
  }
}
