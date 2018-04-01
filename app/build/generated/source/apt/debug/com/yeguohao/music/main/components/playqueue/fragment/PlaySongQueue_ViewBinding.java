// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.main.components.playqueue.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlaySongQueue_ViewBinding implements Unbinder {
  private PlaySongQueue target;

  private View view2131230771;

  @UiThread
  public PlaySongQueue_ViewBinding(final PlaySongQueue target, View source) {
    this.target = target;

    View view;
    target.recycler = Utils.findRequiredViewAsType(source, R.id.queue_recycler, "field 'recycler'", RecyclerView.class);
    target.playMode = Utils.findRequiredViewAsType(source, R.id.play_mode, "field 'playMode'", ImageView.class);
    target.playModeText = Utils.findRequiredViewAsType(source, R.id.play_mode_text, "field 'playModeText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.clear_queue, "method 'clearQueue'");
    view2131230771 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clearQueue();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PlaySongQueue target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycler = null;
    target.playMode = null;
    target.playModeText = null;

    view2131230771.setOnClickListener(null);
    view2131230771 = null;
  }
}
