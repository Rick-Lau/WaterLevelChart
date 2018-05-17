package com.example.rick.waterlevelchart;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

/**
 * 作者： Rick.Lau
 * 时间： 2018/5/17
 * 邮箱：Rick.Lau@aliyun.com
 * 描述：
 */
public class WaterLevelChartView extends View {

    private String temp;   //当前值
    private float topTem;  //高警戒值
    private float lowTem;  //低警戒值
    private Paint currentTmp;
    private Paint mPaint;
    private Paint textPaint;
    private Paint paintCircle;
    private Paint paintLine;
    private Bitmap bitmaplv;
    private Bitmap bitmaplan;
    private Bitmap bitmapred;
    private Context context;
    private int m;              //量筒的高度
    private Paint mPaintOther;
    private float lowerLimit;   //量程起始值
    private float upperLimit;   //量程终点值
    private String unit;

    public WaterLevelChartView(Context context) {
        this(context, null);
    }

    public WaterLevelChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterLevelChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        currentTmp = new Paint();
        currentTmp.setAntiAlias(true);
        currentTmp.setTextSize(20);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#F2DED7"));
        // mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(18);
        paintCircle = new Paint();
        // paintCircle.setColor(Color.parseColor("#61BEE7"));
        paintCircle.setAntiAlias(true);
        paintCircle.setTextSize(45);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        paintLine = new Paint();
        paintLine.setStrokeWidth(2.5f);
        paintLine.setColor(Color.BLUE);
        bitmaplv = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_s);
        bitmaplan = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_red_small);
        bitmapred = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_lan_small);
        Paint rightPaint = new Paint();
        rightPaint.setAntiAlias(true);
        mPaintOther = new Paint();
        mPaintOther.setColor(Color.parseColor("#030102")); //黑色
        mPaintOther.setAntiAlias(true);
        mPaintOther.setStrokeWidth(1);

    }
    /**
     * @param unit      单位
     * @param lower     量程最小值
     * @param upper     量程最大值
     * @param temp      当前值
     * @param startTeme 高警戒值
     * @param endTeme   低警戒值
     * @param context
     * @param m         量筒高度（每一个温度是50M）
     */
    public void setTemp(String unit, float lower, float upper, String temp, float startTeme, float endTeme,
                        Context context, int m) {
        this.unit = unit;
        this.temp = temp;
        this.topTem = endTeme;
        this.lowTem = startTeme;
        this.context = context;
        this.m = m;
        this.lowerLimit = lower;
        this.upperLimit = upper;
        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, m + 120);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressWarnings("deprecation")
        // 屏幕宽度的一半
                int width = getWidth() / 8;
        // 当前温度
        float tem1 = Float.parseFloat(temp); //传过来的温度 temp
        //量程总共是多少度
        float num = upperLimit - lowerLimit;
        int part = 6;  //平分6个
        // 根据温度得到需要绘制的高度
        int a = (int) ((tem1 - lowerLimit) / (num / 5 / part));   //当前温度占多少格
        int ju = m / 5 / part;      //距离占得一格多少m
        int y = m - a * ju;    //绘制的当前温度
        // 温度计 矩形
        canvas.drawRect(width - 20, 0, width + 20, m, mPaint);
        //温度计 圆形
        canvas.drawCircle(width, m + 25, 35, paintCircle);
        // 右侧三角形刻度 字体颜色
        // 水位过低
        if (tem1 >= lowerLimit && tem1 < lowTem) {
            currentTmp.setColor(Color.parseColor("#F65402"));  //红色
            paintCircle.setColor(Color.parseColor("#F65402"));
            // 当前温度表示 矩形
            canvas.drawRect(width - 20, y, width + 20, m, paintCircle);
            // 圆形
            canvas.drawCircle(width, m + 25, 35, paintCircle);
            // 右侧三角形刻度
            canvas.drawBitmap(bitmaplan, width + 40, y - bitmaplan.getHeight()/ 2, mPaint);
            // 当前温度字体
            canvas.drawText(temp + unit,width + 40 + bitmaplan.getWidth() + 15,
                    y + bitmaplan.getHeight() / 4, paintCircle);
            canvas.drawText("当前温度", width + 40 + bitmaplan.getWidth() + 15 + 5,
                    y + bitmaplan.getHeight() / 2 + 10 + 5, currentTmp);
            //正常水位
        } else if (tem1 >= lowTem && tem1 < topTem) {
            currentTmp.setColor(Color.parseColor("#3DB475")); //绿色
            paintCircle.setColor(Color.parseColor("#3DB475"));
            // 当前温度表示 矩形
            canvas.drawRect(width - 20, y, width + 20, m, paintCircle);
            // 圆形
            canvas.drawCircle(width, m + 25, 35, paintCircle);
            // 右侧三角形刻度
            canvas.drawBitmap(bitmaplv, width + 20, y - bitmaplv.getHeight()
                    / 4, mPaint);
            // 当前温度字体
            canvas.drawText(temp + unit, width + 20 + bitmaplv.getWidth() + 5,
                    y + bitmaplv.getHeight() / 4, paintCircle);
            canvas.drawText("当前温度", width + 20 + bitmaplv.getWidth() + 15 + 5,
                    y + bitmaplv.getHeight() / 2 + 10 + 5, currentTmp);
        } else if (tem1 >= topTem) {        //(tem1 + 0.1) >= topTem

            // 水位过高
            currentTmp.setColor(Color.parseColor("#497aed"));  //蓝色
            paintCircle.setColor(Color.parseColor("#497aed"));
            // 当前温度表示 矩形
            canvas.drawRect(width - 20, y, width + 20, m, paintCircle);
            // 圆形
            canvas.drawCircle(width, m + 25, 35, paintCircle);
            // 右侧三角形刻度
            canvas.drawBitmap(bitmapred, width + 40, y - bitmaplv.getHeight()
                    / 2, mPaint);
            // 当前温度字体
            canvas.drawText(temp + unit,
                    width + 40 + bitmapred.getWidth() + 15,
                    y + bitmapred.getHeight() / 4, paintCircle);
            canvas.drawText("当前温度", width + 40 + bitmapred.getWidth() + 15 + 5,
                    y + bitmapred.getHeight() / 2 + 10 + 5, currentTmp);
        }
        /**
         * 量筒的刻度标尺
         */
        int rule = m;
        DecimalFormat df = new DecimalFormat("##0.0");
        for (int i = 0; i < 5 * part + 1; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(width - 0 - 18, rule, width - 0, rule,
                        paintLine);
                textPaint.setColor(Color.parseColor("#17171a"));//黑色
                if (i == 0) {
                    canvas.drawText(lowerLimit + unit, width - 7 - 70, rule + 4, textPaint);//TODO:这里可以加上单位
                } else {
                    lowerLimit += 5 * (num  / 5 / part);
                    canvas.drawText(df.format(lowerLimit) + unit, width - 7 - 70, rule + 4, textPaint);
                }

            } else {
                canvas.drawLine(width - 10, rule, width - 20, rule, mPaintOther);   //短的标尺
            }
            rule = rule - m / (5 * part);
        }

    }
}
