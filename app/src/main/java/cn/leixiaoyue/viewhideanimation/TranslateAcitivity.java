package cn.leixiaoyue.viewhideanimation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by 80119424 on 2016/1/13.
 */
public class TranslateAcitivity extends Activity {
    private final long Duration = 500;
    private ValueAnimator mAnimator = null;
    private boolean mIsHiden = false;
    private View mEmptyView;
    private Button mBtn;
    private float mTranslateY = 0;
    private float mEmptyViewHeight;
    private boolean mFirstInFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate_layout);
        initView();
        mAnimator = ValueAnimator.ofFloat(0, 0);
        initListener();
    }

    private void initView(){
        mEmptyView = findViewById(R.id.emptyview);
        mBtn = (Button)findViewById(R.id.button);
    }

    private void initListener(){
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                mTranslateY = mEmptyViewHeight * currentValue;
                mEmptyView.setTranslationY(mTranslateY);
                mEmptyView.getParent().requestLayout();
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mBtn.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(mEmptyViewHeight == mTranslateY){
                    mBtn.setText(R.string.show);
                    mIsHiden = true;
                }else{
                    mBtn.setText(R.string.hide);
                    mIsHiden = false;
                }
                mBtn.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mBtn.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void reverse(View view) {
        if (mFirstInFlag) {
            mFirstInFlag = false;
            mEmptyViewHeight = mEmptyView.getHeight();
        }
        if (mIsHiden) {
            show();
        } else {
            hide();
        }
    }

    private void show() {
        float ratio = mTranslateY / mEmptyViewHeight;
        mAnimator.cancel();
        mAnimator.setFloatValues(ratio, 0);
        mAnimator.setDuration((long)ratio * Duration);
        mAnimator.start();
    }

    private void hide() {
        float ratio = mTranslateY / mEmptyViewHeight;
        mAnimator.cancel();
        mAnimator.setFloatValues(ratio, 1);
        mAnimator.setDuration((long) (1 - ratio) * Duration);
        mAnimator.start();
    }
}
