// Generated code from Butter Knife. Do not modify!
package com.yeguohao.music.flavour.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.yeguohao.music.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FlavourActivity_ViewBinding implements Unbinder {
  private FlavourActivity target;

  private View view2131230999;

  private View view2131231001;

  private View view2131230906;

  @UiThread
  public FlavourActivity_ViewBinding(FlavourActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FlavourActivity_ViewBinding(final FlavourActivity target, View source) {
    this.target = target;

    View view;
    target.recycler = Utils.findRequiredViewAsType(source, R.id.flavour_recycler, "field 'recycler'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.title_flavour, "field 'flavour' and method 'choose'");
    target.flavour = Utils.castView(view, R.id.title_flavour, "field 'flavour'", TextView.class);
    view2131230999 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.choose(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.title_recently, "field 'recently' and method 'choose'");
    target.recently = Utils.castView(view, R.id.title_recently, "field 'recently'", TextView.class);
    view2131231001 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.choose(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.random_play, "method 'randomPlay'");
    view2131230906 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.randomPlay();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FlavourActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycler = null;
    target.flavour = null;
    target.recently = null;

    view2131230999.setOnClickListener(null);
    view2131230999 = null;
    view2131231001.setOnClickListener(null);
    view2131231001 = null;
    view2131230906.setOnClickListener(null);
    view2131230906 = null;
  }
}
