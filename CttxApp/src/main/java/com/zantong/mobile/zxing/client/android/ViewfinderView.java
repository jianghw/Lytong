/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zantong.mobile.zxing.client.android;

import com.zantong.mobile.zxing.ResultPoint;
import com.zantong.mobile.zxing.client.android.camera.CameraManager;
import com.zantong.mobile.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

  private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
  private static final long ANIMATION_DELAY = 80L;
  private static final int CURRENT_POINT_OPACITY = 0xA0;
  private static final int MAX_RESULT_POINTS = 20;
  private static final int POINT_SIZE = 6;

  private CameraManager cameraManager;
  private final Paint paint;
  private Bitmap resultBitmap;
  private final int maskColor;
  private final int resultColor;
  private final int laserColor;
  private final int resultPointColor;
  private int scannerAlpha;
  private List<ResultPoint> possibleResultPoints;
  private List<ResultPoint> lastPossibleResultPoints;

  private int laserFrameBoundColor = R.color.colorQueryOtherCar;//扫描框4角颜色
  private int laserFrameCornerWidth = 15;//扫描框4角宽
  private int laserFrameCornerLength = 40;//扫描框4角高
  private int laserLineTop;// 扫描线最顶端位置
  private int laserLineHeight;//扫描线默认高度
  private int laserMoveSpeed;// 扫描线默认移动距离px
  private int laserLineResId = R.mipmap.bg_sweep;//扫描线图片资源
  private String drawText = "将二维码放入框内，即可自动扫描";//提示文字
  private int drawTextSize;//提示文字大小
  private int drawTextColor = Color.WHITE;//提示文字颜色
  private boolean drawTextGravityBottom = true;//提示文字位置
  private int drawTextMargin;//提示文字与扫描框距离
  private Bitmap laserLineBitmap;
  private boolean isLaserGridLine = true;

  // This constructor is used when the class is built from an XML resource.
  public ViewfinderView(Context context, AttributeSet attrs) {
    super(context, attrs);

    // Initialize these once for performance rather than calling them every time in onDraw().
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Resources resources = getResources();
    maskColor = resources.getColor(R.color.viewfinder_mask);
    resultColor = resources.getColor(R.color.result_view);
    laserColor = resources.getColor(R.color.viewfinder_laser);
    resultPointColor = resources.getColor(R.color.possible_result_points);
    scannerAlpha = 0;

    possibleResultPoints = new ArrayList<>(5);
    lastPossibleResultPoints = null;
  }

  public void setCameraManager(CameraManager cameraManager) {
    this.cameraManager = cameraManager;
  }

  @SuppressLint("DrawAllocation")
  @Override
  public void onDraw(Canvas canvas) {
    if (cameraManager == null) {
      return; // not ready yet, early draw before done configuring
    }
    Rect frame = cameraManager.getFramingRect();
    Rect previewFrame = cameraManager.getFramingRectInPreview();    
    if (frame == null || previewFrame == null) {
      return;
    }
    int width = canvas.getWidth();
    int height = canvas.getHeight();
    // Draw the exterior (i.e. outside the framing rect) darkened
    paint.setColor(resultBitmap != null ? resultColor : maskColor);
    canvas.drawRect(0, 0, width, frame.top, paint);
    canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
    canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
    canvas.drawRect(0, frame.bottom + 1, width, height, paint);
//    drawMask(canvas, frame);
    // 如果有二维码结果的Bitmap，在扫取景框内绘制不透明的result Bitmap
    if (resultBitmap != null) {
      paint.setAlpha(CURRENT_POINT_OPACITY);
      canvas.drawBitmap(resultBitmap, null, frame, paint);
    } else {
//      drawFrame(canvas, frame);//绘制扫描框
      drawFrameCorner(canvas, frame);//绘制扫描框4角
//      drawText(canvas, frame);// 画扫描框下面的字
//      drawLaserLine(canvas, frame);//绘制扫描线
//      drawResultPoint(canvas, frame, previewFrame);//绘制扫描点标记
//      moveLaserSpeed(frame);//计算移动位置
    }

    // Draw the exterior (i.e. outside the framing rect) darkened
//    paint.setColor(resultBitmap != null ? resultColor : maskColor);
//    canvas.drawRect(0, 0, width, frame.top, paint);
//    canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
//    canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
//    canvas.drawRect(0, frame.bottom + 1, width, height, paint);

    if (resultBitmap != null) {
      // Draw the opaque result bitmap over the scanning rectangle
      paint.setAlpha(CURRENT_POINT_OPACITY);
      canvas.drawBitmap(resultBitmap, null, frame, paint);
    } else {
      //自定义



      // Draw a red "laser scanner" line through the middle to show decoding is active
//      paint.setColor(laserColor);
//      paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
//      scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
//      //TODO 自定义
//      int middle = frame.height() / 2 + frame.top;
//      canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
      
      float scaleX = frame.width() / (float) previewFrame.width();
      float scaleY = frame.height() / (float) previewFrame.height();

      List<ResultPoint> currentPossible = possibleResultPoints;
      List<ResultPoint> currentLast = lastPossibleResultPoints;
      int frameLeft = frame.left;
      int frameTop = frame.top;
      if (currentPossible.isEmpty()) {
        lastPossibleResultPoints = null;
      } else {
        possibleResultPoints = new ArrayList<>(5);
        lastPossibleResultPoints = currentPossible;
        paint.setAlpha(CURRENT_POINT_OPACITY);
        paint.setColor(resultPointColor);
        synchronized (currentPossible) {
          for (ResultPoint point : currentPossible) {
            canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                              frameTop + (int) (point.getY() * scaleY),
                              POINT_SIZE, paint);
          }
        }
      }
      if (currentLast != null) {
        paint.setAlpha(CURRENT_POINT_OPACITY / 2);
        paint.setColor(resultPointColor);
        synchronized (currentLast) {
          float radius = POINT_SIZE / 2.0f;
          for (ResultPoint point : currentLast) {
            canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                              frameTop + (int) (point.getY() * scaleY),
                              radius, paint);
          }
        }
      }

      // Request another update at the animation interval, but only repaint the laser line,
      // not the entire viewfinder mask.
      postInvalidateDelayed(ANIMATION_DELAY,
                            frame.left - POINT_SIZE,
                            frame.top - POINT_SIZE,
                            frame.right + POINT_SIZE,
                            frame.bottom + POINT_SIZE);
    }
  }

  public void drawViewfinder() {
    Bitmap resultBitmap = this.resultBitmap;
    this.resultBitmap = null;
    if (resultBitmap != null) {
      resultBitmap.recycle();
    }
    invalidate();
  }

  /**
   * Draw a bitmap with the result points highlighted instead of the live scanning display.
   *
   * @param barcode An image of the decoded barcode.
   */
  public void drawResultBitmap(Bitmap barcode) {
    resultBitmap = barcode;
    invalidate();
  }

  //自定义

  /**
   * 绘制扫描框4角(内角)
   *
   * @param canvas
   * @param frame
   */
  private void drawFrameCorner(Canvas canvas, Rect frame) {
    paint.setColor(Color.argb(100, 19, 177, 245));
    paint.setStyle(Paint.Style.FILL);
    // 左上角
    canvas.drawRect(frame.left, frame.top, frame.left + laserFrameCornerWidth, frame.top
            + laserFrameCornerLength, paint);
    canvas.drawRect(frame.left + laserFrameCornerWidth, frame.top, frame.left
            + laserFrameCornerLength, frame.top + laserFrameCornerWidth, paint);
    // 右上角
    canvas.drawRect(frame.right-laserFrameCornerWidth, frame.top, frame.right,
            frame.top + laserFrameCornerLength, paint);
    canvas.drawRect(frame.right - laserFrameCornerLength, frame.top,
            frame.right - laserFrameCornerWidth, frame.top + laserFrameCornerWidth, paint);
    // 左下角
    canvas.drawRect(frame.left, frame.bottom - laserFrameCornerLength,
            frame.left + laserFrameCornerWidth, frame.bottom - laserFrameCornerWidth, paint);
    canvas.drawRect(frame.left, frame.bottom - laserFrameCornerWidth, frame.left
            + laserFrameCornerLength, frame.bottom, paint);
    // 右下角
    canvas.drawRect(frame.right - laserFrameCornerWidth, frame.bottom - laserFrameCornerLength, frame.right
            , frame.bottom - laserFrameCornerWidth, paint);
    canvas.drawRect(frame.right - laserFrameCornerLength, frame.bottom - laserFrameCornerWidth, frame.right
            , frame.bottom, paint);

    laserLineBitmap = BitmapFactory.decodeResource(getResources(), laserLineResId);
    int height = laserLineBitmap.getHeight();//取原图高
    int width = laserLineBitmap.getWidth();
    int startLeft = frame.left+(frame.width()-width)/2;
    int startTop = frame.top+(frame.height()-height)/2;
//    Rect srcRect = new Rect((int) dstRectF.left, (int) dstRectF.top, (int) dstRectF.right, (int) dstRectF.bottom);
//    canvas.drawBitmap(laserLineBitmap, srcRect, dstRectF, paint);
    canvas.drawBitmap(laserLineBitmap, startLeft, startTop, paint);
    paint.setColor(Color.parseColor("#ffffff"));
    paint.setTextSize(27);
    canvas.drawText("请将罚单条形码放入框内,并调整位置",frame.width() / 2,frame.top - 14,paint);
//    canvas.drawBitmap();
  }

//  /**
//   * 绘制扫描框4角(外角)
//   *
//   * @param canvas
//   * @param frame
//   */
//  private void drawFrameCorner(Canvas canvas, Rect frame) {
//    paint.setColor(laserFrameBoundColor);
//    paint.setStyle(Paint.Style.FILL);
//    // 左上角
//    canvas.drawRect(frame.left - laserFrameCornerWidth, frame.top, frame.left, frame.top
//            + laserFrameCornerLength, paint);
//    canvas.drawRect(frame.left - laserFrameCornerWidth, frame.top - laserFrameCornerWidth, frame.left
//            + laserFrameCornerLength, frame.top, paint);
//    // 右上角
//    canvas.drawRect(frame.right, frame.top, frame.right + laserFrameCornerWidth,
//            frame.top + laserFrameCornerLength, paint);
//    canvas.drawRect(frame.right - laserFrameCornerLength, frame.top - laserFrameCornerWidth,
//            frame.right + laserFrameCornerWidth, frame.top, paint);
//    // 左下角
//    canvas.drawRect(frame.left - laserFrameCornerWidth, frame.bottom - laserFrameCornerLength,
//            frame.left, frame.bottom, paint);
//    canvas.drawRect(frame.left - laserFrameCornerWidth, frame.bottom, frame.left
//            + laserFrameCornerLength, frame.bottom + laserFrameCornerWidth, paint);
//    // 右下角
//    canvas.drawRect(frame.right, frame.bottom - laserFrameCornerLength, frame.right
//            + laserFrameCornerWidth, frame.bottom, paint);
//    canvas.drawRect(frame.right - laserFrameCornerLength, frame.bottom, frame.right
//            + laserFrameCornerWidth, frame.bottom + laserFrameCornerWidth, paint);
//
//    laserLineBitmap = BitmapFactory.decodeResource(getResources(), laserLineResId);
//    int height = laserLineBitmap.getHeight();//取原图高
//    int width = laserLineBitmap.getWidth();
//    int startLeft = frame.left+(frame.width()-width)/2;
//    int startTop = frame.top+(frame.height()-height)/2;
////    Rect srcRect = new Rect((int) dstRectF.left, (int) dstRectF.top, (int) dstRectF.right, (int) dstRectF.bottom);
////    canvas.drawBitmap(laserLineBitmap, srcRect, dstRectF, paint);
//    canvas.drawBitmap(laserLineBitmap, startLeft, startTop, paint);
////    canvas.drawBitmap();
//  }

//  /**
//   * 画扫描线
//   *
//   * @param canvas
//   * @param frame
//   */
//  private void drawLaserLine(Canvas canvas, Rect frame) {
//    if (laserLineResId == 0) {
//      paint.setStyle(Paint.Style.FILL);
//      paint.setColor(laserColor);// 设置扫描线颜色
//      canvas.drawRect(frame.left, laserLineTop, frame.right, laserLineTop + laserLineHeight, paint);
//    } else {
//      if (laserLineBitmap == null)//图片资源文件转为 Bitmap
//        laserLineBitmap = BitmapFactory.decodeResource(getResources(), laserLineResId);
//      int height = laserLineBitmap.getHeight();//取原图高
//      //网格图片
//      if (isLaserGridLine) {
//        RectF dstRectF = new RectF(frame.left, frame.top, frame.right, laserLineTop);
//        Rect srcRect = new Rect(0, (int) (height - dstRectF.height()), laserLineBitmap.getWidth(), height);
//        canvas.drawBitmap(laserLineBitmap, srcRect, dstRectF, paint);
//      }
//      //线条图片
//      else {
//        //如果没有设置线条高度，则用图片原始高度
//        if (laserLineHeight == Scanner.dp2px(getContext(), DEFAULT_LASER_LINE_HEIGHT)) {
//          laserLineHeight = laserLineBitmap.getHeight() / 2;
//        }
//        Rect laserRect = new Rect(frame.left, laserLineTop, frame.right, laserLineTop + laserLineHeight);
//        canvas.drawBitmap(laserLineBitmap, null, laserRect, paint);
//      }
//    }
//  }

  public void addPossibleResultPoint(ResultPoint point) {
    List<ResultPoint> points = possibleResultPoints;
    synchronized (points) {
      points.add(point);
      int size = points.size();
      if (size > MAX_RESULT_POINTS) {
        // trim it
        points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
      }
    }
  }

}
