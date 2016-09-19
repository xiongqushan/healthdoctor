package haozuo.com.healthdoctor.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import haozuo.com.healthdoctor.R;

/**
 * Created by xiongwei on 16/5/17.
 */
public class LoadingDialog extends Dialog {

    TextView tvTip;
    View ivLoading;
    ObjectAnimator animator;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_loading);

        LayoutInflater inflater = LayoutInflater.from(context);
        View vContent = inflater.inflate(R.layout.dialog_loading, null);
        UIHelper.CreateShape(vContent, 20, "#ffffff", 255);
        setContentView(vContent);

        ivLoading = vContent.findViewById(R.id.ivLoading);
        UIHelper.CreateShape(ivLoading, 0, "#ffffff", 150);
        animator = ObjectAnimator.ofFloat(ivLoading, "translationY", 0, -150).setDuration(2000);
//        animator = ObjectAnimator.ofFloat(ivLoading, "rotation", 0, 360).setDuration(800);
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
        setCancelable(false); //不可以使用返回键取消
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