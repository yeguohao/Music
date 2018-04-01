// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.main.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import com.yeguohao.music.views.FixedTextTabLayout;
import com.yeguohao.music.views.MiniPlayer;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.tab = Utils.findRequiredViewAsType(source, R.id.main_tab, "field 'tab'", FixedTextTabLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.main_viewpager, "field 'viewPager'", ViewPager.class);
    target.miniPlayer = Utils.findRequiredViewAsType(source, R.id.mini_player, "field 'miniPlayer'", MiniPlayer.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tab = null;
    target.viewPager = null;
    target.miniPlayer = null;
  }
}
