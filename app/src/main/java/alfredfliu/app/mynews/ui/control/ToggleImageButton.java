//package alfredfliu.app.mynews.ui.control;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.support.v4.content.ContextCompat;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.widget.Checkable;
//
//import alfredfliu.app.mynews.R;
//import alfredfliu.app.mynews.util.MyLog;
//import lombok.var;
//
//public class ToggleImageButton extends android.support.v7.widget.AppCompatImageButton   implements Checkable {
//
//    private  int onImageResId;
//    private  int offImageResId;
//    boolean mChecked = false;
//    Context context;
//
//
//    public ToggleImageButton(Context context) {
//        this(context,null);
//    }
//
//    public ToggleImageButton(Context context, AttributeSet attrs) {
//        this(context, attrs,0);
//    }
//
//    public ToggleImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//
//        this.context = context;
//        var typeArray = context.obtainStyledAttributes(attrs, R.styleable.ToggleImageButton);
//        try {
//             onImageResId = typeArray.getResourceId(R.styleable.ToggleImageButton_onImage,-1);
//             offImageResId = typeArray.getResourceId(R.styleable.ToggleImageButton_offImage,-1);
//
//            setImageResource(offImageResId);
//        }
//        finally {
//            typeArray.recycle();
//        }
//
//    }
//
//    public void setOnImage(int id) {
//
//       onImageResId =id;
//
//        //requestLayout();
//
//        setImageResource(id);
//        invalidate();
//
//    }
//    public void setOffImage(int id) {
//        offImageResId = id;
//
//        //requestLayout();
//        setImageResource(id);
//        invalidate();
//    }
//
//    @Override
//    public void setChecked(boolean checked) {
//        mChecked = checked;
//    }
//
//    @Override
//    public boolean isChecked() {
//        return mChecked;
//    }
//
//    @Override
//    public void toggle() {
//        mChecked = !mChecked;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        var flag= super.onTouchEvent(event);
//
//        if(mChecked)
//            setOnImage(onImageResId);
//        else
//            setOffImage(offImageResId);
//
//        return  flag;
//    }
////    @Override
////    protected void onDraw(Canvas canvas) {
////        super.onDraw(canvas);
////
////        int paddingLeft = getPaddingLeft();
////
////        int paddingTop = getPaddingTop();
////
////        int paddingRight = getPaddingRight();
////
////        int paddingBottom = getPaddingBottom();
////
////        int width = getWidth() - paddingLeft - paddingRight;
////
////        int height = getHeight() - paddingTop - paddingBottom;
////
////        var bitmap = mChecked?onBitmap:offBitmap;
////        canvas.drawBitmap(bitmap, (float) ((width - bitmap.getWidth())), (float) (height  - bitmap.getHeight() ),null);
////    }
//}
