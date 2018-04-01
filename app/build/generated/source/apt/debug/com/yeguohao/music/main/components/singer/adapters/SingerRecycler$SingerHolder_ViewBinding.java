// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.main.components.singer.adapters;

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

public class SingerRecycler$SingerHolder_ViewBinding implements Unbinder {
  private SingerRecycler.SingerHolder target;

  @UiThread
  public SingerRecycler$SingerHolder_ViewBinding(SingerRecycler.SingerHolder target, View source) {
    this.target = target;

    target.img = Utils.findRequiredViewAsType(source, R.id.singer_img, "field 'img'", ImageView.class);
    target.singer = Utils.findRequiredViewAsType(source, R.id.singer, "field 'singer'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SingerRecycler.SingerHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img = null;
    target.singer = null;
  }
}
