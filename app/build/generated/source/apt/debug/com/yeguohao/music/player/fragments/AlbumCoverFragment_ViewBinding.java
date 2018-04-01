// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.player.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AlbumCoverFragment_ViewBinding implements Unbinder {
  private AlbumCoverFragment target;

  @UiThread
  public AlbumCoverFragment_ViewBinding(AlbumCoverFragment target, View source) {
    this.target = target;

    target.albumCover = Utils.findRequiredViewAsType(source, R.id.album_cover, "field 'albumCover'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AlbumCoverFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.albumCover = null;
  }
}
