/**
 *
 */
package com.sh3h.meterreading.images.view;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sh3h.meterreading.R;
import com.sh3h.mobileutil.view.BaseDialog;


/**
 * 列表选择对话框
 */
public class KeyboardDialog extends BaseDialog implements OnClickListener, OnLongClickListener {
    private Context _context;
    private EditText _keyboard_number;
    private String _subtitle = null;
    private String _content = "0";
    private KeyboardListener _listener;
    public static final String KEYBOARD_NUMBER = "keyboard_number";

    public KeyboardDialog(Context context, KeyboardListener listener) {
        super(context);
        this._context = context;
        this._listener = listener;
        //init();
    }

    public KeyboardDialog setSubtitile(String subtitle) {
        _subtitle = subtitle;
        return this;
    }

    public KeyboardDialog init(boolean leftOrRight) {
        super.setTitle(0);
        super.setIcon(0);

        LayoutInflater inflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layoutResource = leftOrRight ? R.layout.keyboard_view_left : R.layout.keyboard_view_right;
        View view = inflater.inflate(layoutResource, null);
        if (_subtitle != null) {
            TextView textView = (TextView) view.findViewById(R.id.keyboard_subtitle);
            textView.setText(_subtitle);
        }
        _keyboard_number = (EditText) view.findViewById(R.id.keyboard_number);
        _keyboard_number.setInputType(InputType.TYPE_NULL);
        Button kayboard_one = (Button) view.findViewById(R.id.kayboard_one);
        kayboard_one.setOnClickListener(this);
        Button kayboard_two = (Button) view.findViewById(R.id.kayboard_two);
        kayboard_two.setOnClickListener(this);
        Button kayboard_three = (Button) view.findViewById(R.id.kayboard_three);
        kayboard_three.setOnClickListener(this);
        Button kayboard_four = (Button) view.findViewById(R.id.kayboard_four);
        kayboard_four.setOnClickListener(this);
        Button kayboard_five = (Button) view.findViewById(R.id.kayboard_five);
        kayboard_five.setOnClickListener(this);
        Button kayboard_six = (Button) view.findViewById(R.id.kayboard_six);
        kayboard_six.setOnClickListener(this);
        Button kayboard_seven = (Button) view.findViewById(R.id.kayboard_seven);
        kayboard_seven.setOnClickListener(this);
        Button kayboard_eight = (Button) view.findViewById(R.id.kayboard_eight);
        kayboard_eight.setOnClickListener(this);
        Button kayboard_nine = (Button) view.findViewById(R.id.kayboard_nine);
        kayboard_nine.setOnClickListener(this);
        Button kayboard_ok = (Button) view.findViewById(R.id.kayboard_ok);
        kayboard_ok.setOnClickListener(this);
        Button kayboard_null = (Button) view.findViewById(R.id.kayboard_null);
        kayboard_null.setOnClickListener(this);

        ImageButton kayboard_delete = (ImageButton) view.findViewById(R.id.kayboard_delete);
        kayboard_delete.setOnClickListener(this);
        kayboard_delete.setOnLongClickListener(this);

        this._keyboard_number.setText(this._content);
        this._keyboard_number.setSelection(1);

        super.setView(view);
        return this;
    }

    public KeyboardDialog moveVLength(int length) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.y += length;
        getWindow().setAttributes(lp);
        return this;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.kayboard_one:
                setValue(1);
                break;
            case R.id.kayboard_two:
                setValue(2);
                break;
            case R.id.kayboard_three:
                setValue(3);
                break;
            case R.id.kayboard_four:
                setValue(4);
                break;
            case R.id.kayboard_five:
                setValue(5);
                break;
            case R.id.kayboard_six:
                setValue(6);
                break;
            case R.id.kayboard_seven:
                setValue(7);
                break;
            case R.id.kayboard_eight:
                setValue(8);
                break;
            case R.id.kayboard_nine:
                setValue(9);
                break;
            case R.id.kayboard_null:
                setValue(0);
                break;
            case R.id.kayboard_ok:
                try {
                    if (!_content.equals("")) {
                        long number = Long.valueOf(_content);
                        _listener.getNumber(number);
                        this.dismiss();
                    } else {
                        _listener.getNumber(0);
                        this.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    this.dismiss();
                }
                break;
            case R.id.kayboard_delete:
                if (!this._content.equals("")) {
                    _content = _content.substring(0, _content.length() - 1);
                }
                break;

            default:
                break;
        }

        this._keyboard_number.setText(_content);
        this._keyboard_number.setSelection(_content.length());
    }

    private void setValue(int number) {

        if (this._content.length() == 10) {
            Toast.makeText(this._context, "抄码长度不允许超过10位。", Toast.LENGTH_SHORT).show();
            return;
        }

        if (this._content.equals("0")) {
            _content = String.valueOf(number);
        } else {
            _content = _content + number;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (id == R.id.kayboard_delete) {
            this._content = "";
            this._keyboard_number.setText(this._content);
        }
        return false;
    }
}
