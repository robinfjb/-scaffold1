package robin.scaffold.baisc.base;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import robin.scaffold.baisc.R;

public class TitleManager {
    public interface RightLayoutListener{
        void onRightClick();
    }

    public interface LeftLayoutListener{
        void onLeftClick();
    }
    private Activity ac;
    public class Builder {
        private View view;
        private Resources rs;
        private int background;
        private int leftTxtId;
        private String leftTxt;
        private int leftImgId;
        private String titleTxt;
        private int rightTxtId;
        private String rightTxt;
        private int rightImg;
        private LeftLayoutListener leftLayoutListener;
        private RightLayoutListener rightLayoutListener;

        public Builder(@NonNull Activity ac) {
            rs = ac.getResources();
            view  = LayoutInflater.from(ac).inflate(R.layout.view_title_common, null);
        }

        public Builder setBackground(@DrawableRes int background) {
            this.background = background;
            return this;
        }


        public Builder setLeftTxt(@DrawableRes int leftTxtId) {
            this.leftTxtId = leftTxtId;
            return this;
        }

        public Builder setLeftTxt(String leftTxt) {
            this.leftTxt = leftTxt;
            return this;
        }

        public Builder setLeftImg(@DrawableRes int leftImgId) {
            this.leftImgId = leftImgId;
            return this;
        }

        public Builder setTitleTxt(String titleTxt) {
            this.titleTxt = titleTxt;
            return this;
        }

        public Builder setRightTxt(@DrawableRes int rightTxtId) {
            this.rightTxtId = rightTxtId;
            return this;
        }

        public Builder setRightTxt(String rightTxt) {
            this.rightTxt = rightTxt;
            return this;
        }

        public Builder setRightImg(@DrawableRes int rightImg) {
            this.rightImg = rightImg;
            return this;
        }

        public Builder setLeftListener(LeftLayoutListener leftLayoutListener) {
            this.leftLayoutListener = leftLayoutListener;
            return this;
        }

        public Builder setRightListener(RightLayoutListener rightLayoutListener) {
            this.rightLayoutListener = rightLayoutListener;
            return this;
        }

        public void set() {
            TextView leftTxtView = view.findViewById(R.id.left_txt);
            if(leftTxtId>0){
                leftTxtView.setText(rs.getString(leftTxtId));
            }
            if(!TextUtils.isEmpty(leftTxt)) {
                leftTxtView.setText(leftTxt);
            }
            if(leftImgId>0){
                ((ImageView)(view.findViewById(R.id.left_img))).setImageResource(leftImgId);
            } else {
                ((ImageView)(view.findViewById(R.id.left_img))).setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(titleTxt)) {
                ((TextView)(view.findViewById(R.id.title_txt))).setText(titleTxt);
            }
            TextView rightTxtView = view.findViewById(R.id.right_txt);
            if(rightTxtId>0){
                rightTxtView.setText(rs.getString(rightTxtId));
            }
            if(!TextUtils.isEmpty(rightTxt)) {
                rightTxtView.setText(rightTxt);
            }
            if(rightImg>0){
                ((ImageView)(view.findViewById(R.id.right_img))).setImageResource(rightImg);
            } else {
                ((ImageView)(view.findViewById(R.id.right_img))).setVisibility(View.GONE);
            }
            view.findViewById(R.id.left_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leftLayoutListener == null) {
                        ac.finish();
                    } else {
                        leftLayoutListener.onLeftClick();
                    }
                }
            });
            if(rightLayoutListener != null) {
                view.findViewById(R.id.right_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightLayoutListener.onRightClick();
                    }
                });
            }

            if(background > 0) {
                view.findViewById(R.id.re_layout).setBackgroundResource(background);
            }
        }
    }

    public TitleManager(Activity ac){
        this.ac = ac;
    }
}
