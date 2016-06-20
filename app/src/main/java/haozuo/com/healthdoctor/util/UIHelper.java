package haozuo.com.healthdoctor.util;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

/**
 * Created by xiongwei on 16/5/17.
 */
public class UIHelper {

    public static void CreateShape(View view, float radius, String fillColor,int alpha) {
        CreateShape(view, radius, 0, "#ffffff", fillColor,alpha);
    }

    //alpha 0~255
    public static void CreateShape(View view, float radius, int strokeWidth,
                                   String strokeColor, String fillColor,int alpha) {

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setColor(Color.parseColor(fillColor));
        gd.setStroke(strokeWidth, Color.parseColor(strokeColor));
        gd.setAlpha(alpha);
        view.setBackgroundDrawable(gd);

    }
}
