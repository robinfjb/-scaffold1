package robin.scaffold.sample1.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import robin.scaffold.sample1.R;

public class BottomTabView extends FrameLayout {

    private Context mContext;

    private int mTabPosition;

    private BottomTabEntity mBottomTabEntity;

    private TextView mTvTitle;

    private ImageView mIvImage;

    public BottomTabView(Context context) {
        this(context, null, 0);
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, null);
    }

    public BottomTabView(Context context, BottomTabEntity bottomTabEntity) {
        this(context, null, 0, bottomTabEntity);
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, BottomTabEntity bottomTabEntity) {
        super(context, attrs, defStyleAttr);
        init(context, bottomTabEntity);
    }

    private void init(Context context, BottomTabEntity bottomTabEntity) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_tab_view, this, true);
        mTvTitle = (TextView) tabView.findViewById(R.id.tv_title);
        mIvImage = (ImageView) tabView.findViewById(R.id.iv_image);
        mBottomTabEntity = bottomTabEntity;
        setNormalStatus();
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            setSelectedStatus();
        } else {
            setNormalStatus();
        }
    }

    private void setNormalStatus() {
        if (TextUtils.isEmpty(mBottomTabEntity.getNormalPath())) {
            mIvImage.setImageResource(mBottomTabEntity.getDefaultNormalId());
        } else {
            Glide.with(mContext)
                    .load(mBottomTabEntity.getNormalPath())
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(mBottomTabEntity.getDefaultNormalId())
                    .into(mIvImage);
        }

        if (TextUtils.isEmpty(mBottomTabEntity.getTitle())) {
            mTvTitle.setText(mBottomTabEntity.getDefaultTitle());
        } else {
            mTvTitle.setText(mBottomTabEntity.getTitle());
        }
        mTvTitle.setTextColor(mBottomTabEntity.getNormalTextColor());
    }

    private void setSelectedStatus() {
        if (TextUtils.isEmpty(mBottomTabEntity.getSelectPath())) {
            mIvImage.setImageResource(mBottomTabEntity.getDefaultSelectedId());
        } else {
            Glide.with(mContext)
                    .load(mBottomTabEntity.getSelectPath())
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(mBottomTabEntity.getDefaultSelectedId())
                    .into(mIvImage);
        }

        if (TextUtils.isEmpty(mBottomTabEntity.getTitle())) {
            mTvTitle.setText(mBottomTabEntity.getDefaultTitle());
        } else {
            mTvTitle.setText(mBottomTabEntity.getTitle());
        }
        mTvTitle.setTextColor(mBottomTabEntity.getSelectedTextColor());
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }


}