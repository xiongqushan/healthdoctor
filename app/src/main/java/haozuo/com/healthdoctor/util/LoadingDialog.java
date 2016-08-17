package haozuo.com.healthdoctor.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import haozuo.com.healthdoctor.R;

/**
 * Created by xiongwei on 16/5/17.
 */
public class LoadingDialog extends Dialog {

    TextView tvTip;
    ImageView ivLoading;
    ObjectAnimator animator;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_loading);
        LayoutInflater inflater = LayoutInflater.from(context);
        View vContent = inflater.inflate(R.layout.dialog_loading, null);
        UIHelper.CreateShape(vContent, 2, "#000000", 150);
        setContentView(vContent);

        ivLoading = (ImageView) vContent.findViewById(R.id.ivLoading);
        animator = ObjectAnimator.ofFloat(ivLoading, "rotation", 0, 360).setDuration(800);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                dismiss();
            }
        });
        tvTip = (TextView) vContent.findViewById(R.id.tvTip);// 提示文字
        setCancelable(false);
    }

    public void setMessage(String msg) {
        tvTip.setText(msg);
    }

    @Override
    public void show() {
        animator.start();
        super.show();
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
            animator.cancel();
            ivLoading.clearAnimation();
        } catch (Exception e) {
        }
    }
}