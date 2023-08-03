/**
 * @author qiweiwei@shanghai3h.com
 */
package com.sh3h.mobileutil.view;

import android.content.Context;
import android.content.DialogInterface;

import com.sh3h.mobileutil.R;
import com.sh3h.mobileutil.util.ApplicationsUtil;


/**
 *
 */
public class ConfirmDialog extends BaseDialog implements
		DialogInterface.OnClickListener {

	/**
	 * 用户操作结束，回调接口
	 */
	public interface OnConfirmListener {

		public final static int RESULT_OK = BUTTON1;
		public final static int RESULT_CANCEL = BUTTON2;

		public final static int RESULT_YES = BUTTON1;
		public final static int RESULT_NO = BUTTON3;

		/**
		 * 用户选择结束，回调方法
		 *
		 * @param result
		 *            用户选择, YES, NO, CANCEL
		 */
		void onResult(int result);
	}

	public final static int BUTTON_YES_NO = 1;
	public final static int BUTTON_OK = 2;
	public final static int BUTTON_YES_NO_CANCEL = 3;
	public final static int BUTTON_OK_CANCEL = 4;

	private int _buttons = BUTTON_OK;
	private OnConfirmListener _listener;

	/**
	 * 默认，按钮只有YES
	 *
	 * @param context
	 */
	public ConfirmDialog(Context context, OnConfirmListener listener) {
		this(context, BUTTON_OK, listener);
	}

	public ConfirmDialog(Context context, int buttons,
						 OnConfirmListener listener) {
		this(context, buttons, -1, -1, listener);
	}

	public ConfirmDialog(Context context, int buttons, int titleResourceId,
						 int messageResourceId, OnConfirmListener listener) {
		super(context);

		this._buttons = buttons;
		this._listener = listener;

		this.init(titleResourceId, messageResourceId);
	}

	public ConfirmDialog(Context context, int buttons, int titleResourceId,
						 String messageResource, OnConfirmListener listener) {
		super(context);

		this._buttons = buttons;
		this._listener = listener;
		this.setMessage(messageResource);
		this.init(titleResourceId, 0);
	}

	private void init(int titleResourceId, int messageResourceId) {

		this.setTitle(titleResourceId);
		if (messageResourceId != 0) {
			this.setMessage(super.getContext().getString(messageResourceId));
		}
		this.setIcon(0);

		if (this._buttons == BUTTON_OK) {
			this.setButton1(super.getContext().getString(R.string.text_yes),
					this);
		}

		if (this._buttons == BUTTON_OK_CANCEL) {
			this.setButton1(super.getContext().getString(R.string.text_ok),
					this);
			this.setButton2(super.getContext().getString(R.string.text_cancel),
					this);
		}

		if (this._buttons == BUTTON_YES_NO) {
			this.setButton1(super.getContext().getString(R.string.text_yes),
					this);
			this.setButton3(super.getContext().getString(R.string.text_no),
					this);
		}

		if (this._buttons == BUTTON_YES_NO_CANCEL) {
			this.setButton1(super.getContext().getString(R.string.text_yes),
					this);
			this.setButton2(super.getContext().getString(R.string.text_cancel),
					this);
			this.setButton3(super.getContext().getString(R.string.text_no),
					this);
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (this._listener != null && !ApplicationsUtil.isFastClick()) {
			this._listener.onResult(which);
		}
		dismiss();
	}

	@Override
	public void cancel() {
		super.cancel();

		if (this._listener != null && !ApplicationsUtil.isFastClick()) {
			this._listener.onResult(OnConfirmListener.RESULT_CANCEL);
		}
	}
}
