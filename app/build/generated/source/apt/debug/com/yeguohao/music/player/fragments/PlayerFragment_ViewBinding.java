// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.player.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import com.yeguohao.music.views.BlurImageView;
import com.yeguohao.music.views.MyProcess;
import com.yeguohao.music.views.PositionTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlayerFragment_ViewBinding implements Unbinder {
  private PlayerFragment target;

  private View view2131230890;

  private View view2131230893;

  private View view2131230892;

  private View view2131230891;

  private View view2131230889;

  @UiThread
  public PlayerFragment_ViewBinding(final PlayerFragment target, View source) {
    this.target = target;

    View view;
    target.background = Utils.findRequiredViewAsType(source, R.id.player_background, "field 'background'", BlurImageView.class);
    target.songText = Utils.findRequiredViewAsType(source, R.id.player_song, "field 'songText'", TextView.class);
    target.singer = Utils.findRequiredViewAsType(source, R.id.player_singer, "field 'singer'", TextView.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.player_viewpager, "field 'viewPager'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.player_mode, "field 'modeImageView' and method 'controlClicks'");
    target.modeImageView = Utils.castView(view, R.id.player_mode, "field 'modeImageView'", ImageView.class);
    view2131230890 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.controlClicks(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.player_prev, "field 'prev' and method 'controlClicks'");
    target.prev = Utils.castView(view, R.id.player_prev, "field 'prev'", ImageView.class);
    view2131230893 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.controlClicks(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.player_play, "field 'play' and method 'controlClicks'");
    target.play = Utils.castView(view, R.id.player_play, "field 'play'", ImageView.class);
    view2131230892 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.controlClicks(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.player_next, "field 'next' and method 'controlClicks'");
    target.next = Utils.castView(view, R.id.player_next, "field 'next'", ImageView.class);
    view2131230891 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.controlClicks(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.player_favorite, "field 'favoriteImageView' and method 'controlClicks'");
    target.favoriteImageView = Utils.castView(view, R.id.player_favorite, "field 'favoriteImageView'", ImageView.class);
    view2131230889 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.controlClicks(p0);
      }
    });
    target.currentTime = Utils.findRequiredViewAsType(source, R.id.player_current_time, "field 'currentTime'", PositionTextView.class);
    target.totalTime = Utils.findRequiredViewAsType(source, R.id.player_total_time, "field 'totalTime'", PositionTextView.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.player_progress, "field 'progress'", MyProcess.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayerFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.background = null;
    target.songText = null;
    target.singer = null;
    target.viewPager = null;
    target.modeImageView = null;
    target.prev = null;
    target.play = null;
    target.next = null;
    target.favoriteImageView = null;
    target.currentTime = null;
    target.totalTime = null;
    target.progress = null;

    view2131230890.setOnClickListener(null);
    view2131230890 = null;
    view2131230893.setOnClickListener(null);
    view2131230893 = null;
    view2131230892.setOnClickListener(null);
    view2131230892 = null;
    view2131230891.setOnClickListener(null);
    view2131230891 = null;
    view2131230889.setOnClickListener(null);
    view2131230889 = null;
  }
}
