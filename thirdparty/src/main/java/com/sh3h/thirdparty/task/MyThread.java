package com.sh3h.thirdparty.task;

import java.util.List;

import android.os.Handler;
import android.os.Message;

/**
 * 下载线程.
 */
public class MyThread extends Thread {

    /** 下载单位. */
    public MyTaskItem item = null;

    /** 下载完成后的消息句柄. */
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MyTaskItem item = (MyTaskItem)msg.obj;
            if (item.getListener() instanceof MyTaskListListener) {
                ((MyTaskListListener)item.getListener()).update((List<?>)item.getResult());
            }
            else if (item.getListener() instanceof MyTaskObjectListener) {
                ((MyTaskObjectListener)item.getListener()).update(item.getResult());
            }
            else {
                item.getListener().update();
            }
        }
    };

    /**
     * 构造下载线程队列.
     */
    public MyThread() {
    }

    /**
     * 开始一个下载任务.
     *
     * @param item 下载单位
     */
    public void execute(MyTaskItem item) {
        this.item = item;
        this.start();
    }

    /**
     * 描述：线程运行.
     *
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        if (item != null) {
            //定义了回调
            if (item.getListener() != null) {
                item.getListener().get(item);
                //交由UI线程处理
                Message msg = handler.obtainMessage();
                msg.obj = item;
                handler.sendMessage(msg);
            }
        }
    }

}
