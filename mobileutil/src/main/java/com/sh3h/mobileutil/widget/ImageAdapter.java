package com.sh3h.mobileutil.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sh3h.mobileutil.R;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mImageIds;
	private View[] _images;

	public ImageAdapter(Context c, String[] ImageIds, int index) {
		mContext = c;
		mImageIds = ImageIds;
		_images = new View[mImageIds.length];
	}

	// public ImageView createImages(String imageId) {
	// //
	// // Bitmap bitmap = BitmapFactory.decodeFile(imageId.toString());
	// // ImageView imageView = new ImageView(mContext);
	// // LayoutParams params = imageView.getLayoutParams();
	// // params.height = 300;
	// // params.width = 250;
	// // imageView.setLayoutParams(params);
	// // imageView.setImageBitmap(bitmap);
	// // imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	//
	// // BitmapFactory.Options op = new BitmapFactory.Options();
	// // op.inJustDecodeBounds = true;
	// // op.inJustDecodeBounds = false;
	// // op.inSampleSize = 4;
	// // // 返回原图解码之后的bitmap对象
	// // Bitmap originalImage = BitmapFactory.decodeFile(imageId.toString(),
	// // op);
	// // ImageView imageView = new ImageView(mContext);
	// // int width = originalImage.getWidth();
	// // int height = originalImage.getHeight();
	// // // 创建矩阵对象
	// // Matrix matrix = new Matrix();
	// // matrix.preScale(-1f, -1f);
	// // Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	// // // 将上面创建的位图初始化到画布
	// // Canvas canvas = new Canvas(bitmap);
	// // canvas.drawBitmap(originalImage, 0, 0, null);
	// // canvas.drawBitmap(bitmap, 0, height, null);
	// //
	// // Paint paint = new Paint();
	// // paint.setAntiAlias(false);
	// // paint.setXfermode(new PorterDuffXfermode(
	// // android.graphics.PorterDuff.Mode.DST_IN));
	// //
	// // imageView.setImageBitmap(bitmap);
	//
	// return imageView;
	// }

	public int getCount() {
		return _images.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View viewImg = null;
		if (_images[position] == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			viewImg = inflater.inflate(R.layout.item_img, null);
			ImageView img = (ImageView) viewImg.findViewById(R.id.gallery_img);
			Bitmap bitmap = BitmapFactory.decodeFile(mImageIds[position]);
			img.setImageBitmap(bitmap);
			_images[position] = viewImg;
		} else {
			viewImg = _images[position];
		}
		return viewImg;
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}
	//
	// /**
	// * 图片任意的大小不变形状换算函数
	// *
	// * @param imgW
	// * 显示的图片的真实宽度
	// * @param imgH
	// * 显示的图片的真实高度
	// * @param cvW
	// * 显示区域的宽度（像素）
	// * @param cvH
	// * 显示区域的高度（像素）
	// * @param pW
	// * 显示屏宽的像素密度， 通过context.getResources().getDisplayMetrics().ydpi
	// * 得到
	// * @param pH
	// * 显示屏高的像素密度
	// * @return 图片的真实显示大小，其中re[0]为宽，re[1]为高
	// */
	// public static int[] getViewWH(int imgW, int imgH, int cvW, int cvH,
	// float pW, float pH) {
	// int[] re = new int[2];
	// float ratio = imgW / imgH * pW / pH;
	// if (cvW / ratio <= cvH) {
	// re[0] = cvW;
	// re[1] = (int) (cvW / ratio);
	// } else {
	// re[0] = (int) (cvH * ratio);
	// re[1] = cvH;
	// }
	// return re;
	// }

}