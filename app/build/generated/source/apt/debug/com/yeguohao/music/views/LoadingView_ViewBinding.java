// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.views;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.view.View;
import butterknife.Unbinder;
import com.yeguohao.music.R;
import java.lang.Deprecated;
import java.lang.Override;

public class LoadingView_ViewBinding implements Unbinder {
  @UiThread
  public LoadingView_ViewBinding(LoadingView target) {
    this(target, target.getContext());
  }

  /**
   * @deprecated Use {@link #LoadingView_ViewBinding(LoadingView, Context)} for direct creation.
   *     Only present for runtime invocation through {@code ButterKnife.bind()}.
   */
  @Deprecated
  @UiThread
  public LoadingView_ViewBinding(LoadingView target, View source) {
    this(target, source.getContext());
  }

  @UiThread
  public LoadingView_ViewBinding(LoadingView target, Context context) {
    target.textColor = ContextCompat.getColor(context, R.color.textColorLight);
  }

  @Override
  @CallSuper
  public void unbind() {
  }
}
