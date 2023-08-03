/**
 * @author qiweiwei
 *
 */
package com.sh3h.mobileutil.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sh3h.mobileutil.R;
import com.sh3h.mobileutil.util.TextUtil;

import java.lang.ref.WeakReference;

/**
 * BaseDialog
 */
public class BaseDialog extends Dialog {

	private Button mButton1 = null;
	private Button mButton2 = null;
	private Button mButton3 = null;

	private Message mButton1Message = null;
	private Message mButton2Message = null;
	private Message mButton3Message = null;

	private boolean mHasButtonVisiable = false;

	private Handler mHandler;

	/**
	 * .ctor
	 *
	 * @param context
	 *            上下文
	 */
	public BaseDialog(Context context) {
		super(context, R.style.BaseDialog);
		this.init();
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public BaseDialog(Context context, boolean cancelable,
					  OnCancelListener cancelListener) {
		this(context);
		super.setCancelable(cancelable);
		super.setOnCancelListener(cancelListener);
	}

	private void init() {

		this.getWindow().setContentView(R.layout.dialog);

		this.mHandler = new ButtonHandler(this);

		this.mButton1 = (Button) findViewById(android.R.id.button1);
		this.mButton2 = (Button) findViewById(android.R.id.button2);
		this.mButton3 = (Button) findViewById(android.R.id.button3);
	}

	private View.OnClickListener mButtonHandler = new View.OnClickListener() {
		public void onClick(View v) {
			Message m = null;
			if (v == mButton1 && mButton1Message != null) {
				m = Message.obtain(mButton1Message);
			} else if (v == mButton2 && mButton2Message != null) {
				m = Message.obtain(mButton2Message);
			} else if (v == mButton3 && mButton3Message != null) {
				m = Message.obtain(mButton3Message);
			}
			if (m != null) {
				m.sendToTarget();
			}

			// Post a message so we dismiss after the above handlers are
			// executed
			mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG,
					BaseDialog.this).sendToTarget();
		}
	};

	private static final class ButtonHandler extends Handler {
		// Button clicks have Message.what as the BUTTON{1,2,3} constant
		private static final int MSG_DISMISS_DIALOG = 1;

		private WeakReference<DialogInterface> mDialog;

		public ButtonHandler(DialogInterface dialog) {
			mDialog = new WeakReference<DialogInterface>(dialog);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

				case DialogInterface.BUTTON1:
				case DialogInterface.BUTTON2:
				case DialogInterface.BUTTON3:
					((OnClickListener) msg.obj).onClick(
							mDialog.get(), msg.what);
					break;

				case MSG_DISMISS_DIALOG:
					((DialogInterface) msg.obj).dismiss();
			}
		}
	}

	/**
	 * 设置标题内容，使用字符串资源Id
	 *
	 * @param resId
	 *            字符串资源编号
	 */
	public void setTitle(int resId) {
		setTitle(resId != 0 ? getContext().getString(resId) : TextUtil.EMPTY);
	}

	/**
	 * 设置标题内容
	 *
	 * @param title
	 *            标题内容
	 */
	public void setTitle(String title) {
		if (!TextUtil.isNullOrEmpty(title)) {
			((TextView) findViewById(R.id.dialog_title)).setText(title);
		} else {
			findViewById(R.id.dialog_header).setVisibility(View.GONE);
		}
	}

	public void setIcon(int resId) {
		setIcon(null);
	}

	public void setIcon(Drawable icon) {
		if (icon != null) {
			((ImageView) findViewById(R.id.dialog_icon)).setImageDrawable(icon);
		} else {
			findViewById(R.id.dialog_icon).setVisibility(View.GONE);
		}
	}

	public void setView(View view) {
		findViewById(R.id.dialog_message).setVisibility(View.GONE);
		RelativeLayout content = (RelativeLayout) findViewById(R.id.dialog_content);
		content.addView(view);
	}

	public void setMessage(CharSequence message) {
		TextView tvMessage = (TextView) findViewById(R.id.dialog_message);
		tvMessage.setVisibility(View.VISIBLE);
		tvMessage.setText(message);
	}

	private void setButton(int whichButton, CharSequence text,
						   OnClickListener clickListener, Message msg) {

		if (msg == null && clickListener != null) {
			msg = mHandler.obtainMessage(whichButton, clickListener);
		}

		Button btn = null;

		switch (whichButton) {

			case DialogInterface.BUTTON1:
				btn = mButton1;
				mButton1Message = msg;
				break;

			case DialogInterface.BUTTON2:
				btn = mButton2;
				mButton2Message = msg;
				break;

			case DialogInterface.BUTTON3:
				btn = mButton3;
				mButton3Message = msg;
				break;

			default:
				throw new IllegalArgumentException("Button does not exist");
		}

		if (!mHasButtonVisiable) {
			// ((RelativeLayout)findViewById(R.id.dialog_content))
			// .setLayoutParams(new
			// LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
			// 40));

			findViewById(R.id.dialog_footer).setVisibility(View.VISIBLE);
			mHasButtonVisiable = true;
		}

		btn.setText(text);
		btn.setVisibility(View.VISIBLE);
		btn.setOnClickListener(mButtonHandler);

	}

	public void setButton(int whichButton, int resId,
						  OnClickListener clickListener) {
		setButton(whichButton, getContext().getString(resId), clickListener,
				null);
	}

	public void setButton(int whichButton, CharSequence text,
						  OnClickListener clickListener) {
		setButton(whichButton, text, clickListener, null);
	}

	public void setButton(int whichButton, int resId, Message message) {
		setButton(whichButton, getContext().getString(resId), null, message);
	}

	public void setButton(int whichButton, CharSequence text, Message message) {
		setButton(whichButton, text, null, message);
	}

	public void setButton1(int resId, OnClickListener clickListener) {
		setButton1(getContext().getString(resId), clickListener);
	}

	public void setButton1(CharSequence text, OnClickListener clickListener) {
		setButton(DialogInterface.BUTTON1, text, clickListener);
	}

	public void setButton1(int resId, Message msg) {
		setButton1(getContext().getString(resId), msg);
	}

	public void setButton1(CharSequence text, Message msg) {
		setButton(DialogInterface.BUTTON1, text, msg);
	}

	public void setButton2(int resId, OnClickListener clickListener) {
		setButton2(getContext().getString(resId), clickListener);
	}

	public void setButton2(CharSequence text, OnClickListener clickListener) {
		setButton(DialogInterface.BUTTON2, text, clickListener);
	}

	public void setButton2(int resId, Message msg) {
		setButton2(getContext().getString(resId), msg);
	}

	public void setButton2(CharSequence text, Message msg) {
		setButton(DialogInterface.BUTTON2, text, msg);
	}

	/**
	 * append button3
	 *
	 * @param resId
	 * @param clickListener
	 */
	public void setButton3(int resId, OnClickListener clickListener) {
		setButton3(getContext().getString(resId), clickListener);
	}

	public void setButton3(CharSequence text, OnClickListener clickListener) {
		setButton(DialogInterface.BUTTON3, text, clickListener);
	}

	public void setButton3(int resId, Message msg) {
		setButton3(getContext().getString(resId), msg);
	}

	public void setButton3(CharSequence text, Message msg) {
		setButton(DialogInterface.BUTTON3, text, msg);
	}

	/**
	 * get button
	 *
	 * @param whichButton
	 * @return
	 */
	public Button getButton(int whichButton) {
		Button btn = null;

		switch (whichButton) {
			case DialogInterface.BUTTON1:
				btn = (Button) findViewById(android.R.id.button1);
				break;
			case DialogInterface.BUTTON2:
				btn = (Button) findViewById(android.R.id.button1);
				break;
			case DialogInterface.BUTTON3:
				btn = (Button) findViewById(android.R.id.button1);
				break;
			default:
				break;
		}

		return btn;
	}

	public void showLeftOrRight(boolean leftOrRight){
		if (leftOrRight){
			getWindow().setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		}else {
			getWindow().setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		}
	}

}
