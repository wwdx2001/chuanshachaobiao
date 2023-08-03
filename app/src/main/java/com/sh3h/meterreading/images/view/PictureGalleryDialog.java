package com.sh3h.meterreading.images.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sh3h.meterreading.R;
import com.sh3h.meterreading.images.entity.BaseEquipment;
import com.sh3h.mobileutil.view.BaseDialog;
import com.sh3h.mobileutil.widget.ImageAdapter;

import java.util.List;


public class PictureGalleryDialog extends BaseDialog implements
        OnItemSelectedListener {

    private List<BaseEquipment> _listPicture;
    private LinearLayout _linearLayout;
    private Gallery galleryFlow = null;
    private ImageAdapter _adapter;
    private ImageView[] _image = null;
    private RadioGroup _dotGroupButton;
    private Context _context;
    private int _width = 320;
    private int _height = 400;

    public PictureGalleryDialog(Context context, BaseEquipment picture,
                                List<BaseEquipment> listPicture, int index) {
        super(context);
        _context = context;
        setPictureList(listPicture);
        initView(context, index);
    }

    public PictureGalleryDialog(Context context, boolean cancelable,
                                OnCancelListener cancelListener, BaseEquipment picture, int index) {
        super(context, cancelable, cancelListener);
        initView(context, index);
    }

    private void initImage(int size) {
        _image = new ImageView[size];
    }

    private void initView(final Context context, final int index) {
        this.setIcon(0);
        LayoutInflater inflater = LayoutInflater.from(context);
        this._linearLayout = (LinearLayout) inflater.inflate(
                R.layout.dialog_picture_gallery, null);

        galleryFlow = (Gallery) this._linearLayout.findViewById(R.id.gallery);
        setTitle("图片浏览");
        int size = this._listPicture.size();
        String[] images = new String[size];

        for (int i = 0, s = size - 1; i < size; i++, s--) {
            images[i] = this._listPicture.get(s).getUrl();
        }
        initImage(images.length);
        _adapter = new ImageAdapter(context, images, index);

        galleryFlow.setFadingEdgeLength(0);
        galleryFlow.setSpacing(0); // 图片之间的间距
        galleryFlow.setAdapter(_adapter);
        galleryFlow.setSelection(index);

        _dotGroupButton = (RadioGroup) _linearLayout
                .findViewById(R.id.dotGroupButton);
        for (int i = 0; i < images.length; i++) {
            final RadioButton dotButton = new RadioButton(context);
            dotButton.setId(i);
            dotButton.setLayoutParams(new RadioGroup.LayoutParams(35, 30));
            dotButton.setPadding(0, 0, 0, 0);
            dotButton.setTag(i);
            // 为点注册checked事件，当点击对应的点时，Viewpager切换到对应的page,并将点击的点设置为高亮
            dotButton
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            if (isChecked) {
                                galleryFlow.setSelection((Integer) dotButton
                                        .getTag());
                            }
                        }
                    });
            dotButton.setButtonDrawable(R.drawable.radio_bg);
            _dotGroupButton.addView(dotButton);

        }

        setAttributes();
        _dotGroupButton.check(index);
        setView(_linearLayout);
        // 点击空白处退出
        this.setCanceledOnTouchOutside(true);
        galleryFlow.setOnItemSelectedListener(this);
    }

    /**
     * 设置dialog在屏幕显示的大小
     */
    private void setAttributes() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // set width,height by density and gravity
        float density = getDensity(_context);
        params.width = (int) (_width * density);
        params.height = (int) (_height * density);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    /**
     * 获取屏幕分辨
     *
     * @param context 上下文
     * @return float
     */
    private static float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

    public void setPictureList(List<BaseEquipment> listPicture) {
        this._listPicture = listPicture;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        ((RadioButton) _dotGroupButton.getChildAt(arg2)).setChecked(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}
