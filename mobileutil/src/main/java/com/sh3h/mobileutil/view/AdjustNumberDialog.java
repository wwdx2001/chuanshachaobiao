package com.sh3h.mobileutil.view;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sh3h.mobileutil.R;
import com.sh3h.mobileutil.widget.IAdjustNumberResult;


/**
 * Created by xulongjun on 2015/8/24.
 */
public class AdjustNumberDialog extends BaseDialog implements View.OnClickListener  {

    private Context mContext;
    private String mbeiZhu;
    private String number;
    //    TextView mTv_kaiShiSJ;
    EditText EtChangeNumber;

    Button mBt_cancel;
    Button mBt_ok;
    private IAdjustNumberResult mInterface;

    public AdjustNumberDialog(Context context, IAdjustNumberResult adjustNumberResult) {
        super(context);
        mContext = context;
//        mbeiZhu = beiZhu;
        mInterface = adjustNumberResult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载View
        setContentView(R.layout.dialog_adjust_number);


//        mDataProvider = MeterReadingDataManager.getGreenDaoDataProvider();
//        mTv_kaiShiSJ = (TextView) findViewById(R.id.tv_beiZhuKaiSJ);
//        mTv_kaiShiSJ.setText(mbeiZhu);

        EtChangeNumber = (EditText) findViewById(R.id.et_number);


        mBt_cancel = (Button) findViewById(R.id.bt_cancel);
        mBt_cancel.setOnClickListener(this);
        mBt_ok = (Button) findViewById(R.id.bt_ok);
        mBt_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_ok) {
            number = EtChangeNumber.getText().toString().trim();

//                try {
//                    Integer.parseInt(number);
//                    return ;
//                } catch (NumberFormatException e) {
//                    Toast.makeText(getContext(),"你输入不是数字。",Toast.LENGTH_LONG).show();
//                    return ;
//                }

            mInterface.getParams(true, number);

            dismiss();

        } else if (id == R.id.bt_cancel) {
            mInterface.getParams(false, " ");
            dismiss();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)
                && (event.getAction() == KeyEvent.ACTION_DOWN)) {
            mInterface.getParams(false," ");
            dismiss();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
