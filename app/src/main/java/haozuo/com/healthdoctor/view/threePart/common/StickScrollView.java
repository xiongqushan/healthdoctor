package haozuo.com.healthdoctor.view.threePart.common;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import haozuo.com.healthdoctor.R;


public class StickScrollView extends ScrollView {
	// TODO 0329
	private static final long DELAY = 100;
	private int currentScroll;
	private Runnable scrollCheckTask;

	private void init() {
		scrollCheckTask = new Runnable() {
			@Override
			public void run() {
				int newScroll = getScrollY();
				if (currentScroll == newScroll) {
					if (onScrollListener != null) {
						onScrollListener.onScrollStopped();
					}
				} else {
					if (onScrollListener != null) {
						onScrollListener.onScrolling();
					}
					currentScroll = getScrollY();
					postDelayed(scrollCheckTask, DELAY);
				}
			}
		};
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					currentScroll = getScrollY();
					postDelayed(scrollCheckTask, DELAY);
				}
				return false;

			}
		});
	}

	public interface OnScrollListener {
		public void onScrollChanged(int x, int y, int oldX, int oldY);

		public void onScrollStopped();

		public void onScrolling();
	}

	private OnScrollListener onScrollListener;

	/**
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	public boolean isChildVisible(View child) {
		if (child == null) {
			return false;
		}
		Rect scrollBounds = new Rect();
		getHitRect(scrollBounds);
		return child.getLocalVisibleRect(scrollBounds);
	}

	public boolean isAtTop() {
		return getScrollY() <= 0;
	}

	public boolean isAtBottom() {
		return getChildAt(getChildCount() - 1).getBottom() + getPaddingBottom() == getHeight()
				+ getScrollY();
	}

	// ============================
	private static final String STICKY = "sticky";
	private View mCurrentStickyView;
	private Drawable mShadowDrawable;
	private List<View> mStickyViews;
	private int mStickyViewTopOffset;
	private int defaultShadowHeight = 4;// 阴影高度
	private float density;
	private boolean redirectTouchToStickyView;
	private GestureDetector gestureDetector = null;

	public void setGestureDetector(GestureDetector gestureDetector) {
		this.gestureDetector = gestureDetector;
	}

	/**
	 * 当点击Sticky的时候，实现某些背景的渐�?
	 */
	private Runnable mInvalidataRunnable = new Runnable() {

		@Override
		public void run() {
			if (mCurrentStickyView != null) {
				int left = mCurrentStickyView.getLeft();
				int top = mCurrentStickyView.getTop();
				int right = mCurrentStickyView.getRight();
				int bottom = getScrollY()
						+ (mCurrentStickyView.getHeight() + mStickyViewTopOffset);

				invalidate(left, top, right, bottom);
			}

			postDelayed(this, 16);

		}
	};

	public StickScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init();
	}

	public StickScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mShadowDrawable = context.getResources().getDrawable(
				R.drawable.sticky_shadow_default);
		mStickyViews = new LinkedList<View>();
		density = context.getResources().getDisplayMetrics().density;
		init();
	}

	/**
	 * 找到设置tag的View
	 * 
	 * @param viewGroup
	 */
	private void findViewByStickyTag(ViewGroup viewGroup) {
		int childCount = ((ViewGroup) viewGroup).getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = viewGroup.getChildAt(i);

			if (getStringTagForView(child).contains(STICKY)) {
				mStickyViews.add(child);
			}

			if (child instanceof ViewGroup) {
				findViewByStickyTag((ViewGroup) child);
			}
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			findViewByStickyTag((ViewGroup) getChildAt(0));
		}
		showStickyView();
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		showStickyView();
		// TODO 0329
		if (onScrollListener != null) {
			onScrollListener.onScrollChanged(l, t, oldl, oldt);
		}
	}

	/**
		 * 
		 */
	private void showStickyView() {
		View curStickyView = null;
		View nextStickyView = null;

		for (View v : mStickyViews) {
			int topOffset = v.getTop() - getScrollY();

			if (topOffset <= 0) {
				if (curStickyView == null
						|| topOffset > curStickyView.getTop() - getScrollY()) {
					curStickyView = v;
				}
			} else {
				if (nextStickyView == null
						|| topOffset < nextStickyView.getTop() - getScrollY()) {
					nextStickyView = v;
				}
			}
		}

		if (curStickyView != null) {
			mStickyViewTopOffset = nextStickyView == null ? 0 : Math.min(
					0,
					nextStickyView.getTop() - getScrollY()
							- curStickyView.getHeight());
			mCurrentStickyView = curStickyView;
			post(mInvalidataRunnable);
		} else {
			mCurrentStickyView = null;
			removeCallbacks(mInvalidataRunnable);

		}

	}

	private String getStringTagForView(View v) {
		Object tag = v.getTag();
		return String.valueOf(tag);
	}

	/**
	 * 将sticky画出�?
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mCurrentStickyView != null) {
			// 先保存起�?
			canvas.save();
			// 将坐标原点移动到(0, getScrollY() + mStickyViewTopOffset)
			canvas.translate(0, getScrollY() + mStickyViewTopOffset);

			if (mShadowDrawable != null) {
				int left = 0;
				int top = mCurrentStickyView.getHeight() + mStickyViewTopOffset;
				int right = mCurrentStickyView.getWidth();
				int bottom = top + (int) (density * defaultShadowHeight + 0.5f);
				mShadowDrawable.setBounds(left, top, right, bottom);
				mShadowDrawable.draw(canvas);
			}

			canvas.clipRect(0, mStickyViewTopOffset,
					mCurrentStickyView.getWidth(),
					mCurrentStickyView.getHeight());

			mCurrentStickyView.draw(canvas);

			// 重置坐标原点参数
			canvas.restore();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			redirectTouchToStickyView = true;
		}

		if (redirectTouchToStickyView) {
			redirectTouchToStickyView = mCurrentStickyView != null;

			if (redirectTouchToStickyView) {
				redirectTouchToStickyView = ev.getY() <= (mCurrentStickyView
						.getHeight() + mStickyViewTopOffset)
						&& ev.getX() >= mCurrentStickyView.getLeft()
						&& ev.getX() <= mCurrentStickyView.getRight();
			}
		}

		if (redirectTouchToStickyView) {
			ev.offsetLocation(
					0,
					-1
							* ((getScrollY() + mStickyViewTopOffset) - mCurrentStickyView
									.getTop()));
		}
		// return super.dispatchTouchEvent(ev);
		if (gestureDetector != null) {
			gestureDetector.onTouchEvent(ev);
		}

		boolean stick = super.dispatchTouchEvent(ev);
		return stick;
	}

	private boolean hasNotDoneActionDown = true;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (redirectTouchToStickyView) {
			ev.offsetLocation(0,
					((getScrollY() + mStickyViewTopOffset) - mCurrentStickyView
							.getTop()));
		}

		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			hasNotDoneActionDown = false;
		}

		if (hasNotDoneActionDown) {
			MotionEvent down = MotionEvent.obtain(ev);
			down.setAction(MotionEvent.ACTION_DOWN);
			super.onTouchEvent(down);
			hasNotDoneActionDown = false;
		}

		if (ev.getAction() == MotionEvent.ACTION_UP
				|| ev.getAction() == MotionEvent.ACTION_CANCEL) {
			hasNotDoneActionDown = true;
		}
		return super.onTouchEvent(ev);
	}

//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent event) {
//
//		// TODO 0413首页截处理日期选择手势
//		boolean value = super.onInterceptTouchEvent(event);
//		if (value) {
//			float startx = 0;
//			float starty = 0;
//			if (event.getAction() == MotionEvent.ACTION_DOWN) {
//				startx = event.getX();
//				starty = event.getY();
//			}
//			if (event.getAction() == MotionEvent.ACTION_MOVE) {
//				float deltax = event.getX() - startx;
//				float deltay = event.getY() - starty;
//				if (Math.abs(deltay) > Math.abs(deltax)) {// 说明确实是上下滑动的
//					return true;
//				}
//				return false;
//			}
//		}
//		return super.onInterceptTouchEvent(event);
//	}

}
