// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.views;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MiniPlayer_ViewBinding implements Unbinder {
  private MiniPlayer target;

  @UiThread
  public MiniPlayer_ViewBinding(MiniPlayer target) {
    this(target, target);
  }

  @UiThread
  public MiniPlayer_ViewBinding(MiniPlayer target, View source) {
    this.target = target;

    target.play = Utils.findRequiredViewAsType(source, R.id.mini_player_play_icon, "field 'play'", ImageView.class);
    target.playList = Utils.findRequiredViewAsType(source, R.id.mini_player_playlist_icon, "field 'playList'", ImageView.class);
    target.songThumbnail = Utils.findRequiredViewAsType(source, R.id.mini_player_thumbnail, "field 'songThumbnail'", ImageView.class);
    target.songText = Utils.findRequiredViewAsType(source, R.id.mini_player_song, "field 'songText'", TextView.class);
    target.singer = Utils.findRequiredViewAsType(source, R.id.mini_player_singer, "field 'singer'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MiniPlayer target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.play = null;
    target.playList = null;
    target.songThumbnail = null;
    target.songText = null;
    target.singer = null;
  }
}
