package com.iezview.managerdemo;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miqt on 2016/11/30.
 */

public class View3DManager {
    private static List<View> mViews;
    private static Activity mActivity;
    private static int mcx;
    private static int mcy;
    private static int mr;
    private static int mScreenWidth;
    private static int mScreenHeight;

    //这几个参数是摸索的参数
    public static int fuyangHelper = 0; //为了倒扣效果，如果倒扣的越厉害，这个值就要给的越大,赤道上的话这个值为零
    public static float yuanjin = 3; //为了有远近的效果，如果让远的更小，近的更大，那么就增大这个值把
    public static float fuyang = 0;


    //根据传来的坐标点刷新所有View位置
    public static void updateData(float x, float y, float z) {
        float lastscale = -100;
        for (View v :
                mViews) {
            //先取消隐身
            v.setVisibility(View.VISIBLE);
            //得到它拍摄时候的坐标点
            Point point = (Point) v.getTag();
            //偏角是根据指南针设置
            v.setRotationY(-(x + point.getX()));
            //位置 水平方向根据指南针是数据x设置
            v.setX((float) (Math.sin(Math.toRadians(x + point.getX())) * mr) + mr);
            // TODO: 2016/11/30  位置 上下方向根据俯仰角设置0

            //大小根据远近视觉，远的小近的大,最小的为原来的0.77777777到1.333333333
            v.setScaleX((float) (1 - Math.cos(Math.toRadians(x + point.getX())) / yuanjin));
            v.setScaleY((float) (1 - Math.cos(Math.toRadians(x + point.getX())) / yuanjin));
            if (v.getScaleX() >= 1) {
                View parentView = (View) v.getParent();// 解决遮盖问题
                v.bringToFront();
                parentView.requestLayout();
                parentView.invalidate();
                //俯仰角根据原数据和当前的俯仰角数据设置
                v.setRotationX(-fuyang);
                float flog = v.getScaleX() - 1;
                //-0.3333  -- 0  -- +0.33333
                if (flog >= lastscale) {   //0-0.3333
                    lastscale = flog;
                    v.setRotation(-((flog - (1 / yuanjin)) * fuyangHelper));
                } else {                  //0.333333-0
                    lastscale = flog;
                    v.setRotation(((flog - (1 / yuanjin)) * fuyangHelper));
                }
            } else {
                v.setRotationX(fuyang);
                float flog = 1 - v.getScaleX();
                //-0.3333  -- 0  -- +0.33333
                if (flog >= lastscale) {   //0-0.3333
                    lastscale = flog;
                    v.setRotation(-((flog - (1 / yuanjin)) * fuyangHelper));
                } else {                  //0.333333-0
                    lastscale = flog;
                    v.setRotation(((flog - (1 / yuanjin)) * fuyangHelper));
                }
            }
        }
    }

    public static void addView(View view) {
        if (!(view.getTag() instanceof Point)) {
            throw new IllegalStateException("the view.getTag type mast be Point");
        } else {
            mViews.add(view);
            //根据view携带的位置addcontentview,默认位于右上角
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(90/2, 120/2);
            view.setVisibility(View.GONE);
            view.setY(mcy);
            mActivity.addContentView(view, params);
        }
    }

    public static void init(Activity activity, int cx, int cy, int r) {
        mActivity = activity;
        mViews = new ArrayList<>();
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        mcx = cx;
        mcy = cy;
        mr = r;
    }
}
