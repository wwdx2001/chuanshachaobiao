package com.sh3h.thirdparty.task;

import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 持久的线程.
 */
public class MyDurableThread extends Thread {

    /** 下载单位. */
    public MyTaskItem mMyTaskItem = null;

    private int mInterval;

    /** 停止的标记. */
    private boolean mQuit = false;

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
    public MyDurableThread() {
        mQuit = false;
    }

    /**
     * 开始一个下载任务.
     *
     * @param item 下载单位
     */
    public void execute(MyTaskItem item, int interval) {
        mMyTaskItem = item;
        mInterval = interval;
        start();
    }

    /**
     * 描述：终止队列释放线程.
     */
    public void quit() {
        mQuit = true;
        interrupted();
    }

    /**
     * 描述：线程运行.
     *
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        if ((mMyTaskItem == null) || (mMyTaskItem.getListener() == null)) {
            return;
        }

        while (!mQuit) {
            try {
                mMyTaskItem.getListener().get(mMyTaskItem);
                //交由UI线程处理
                Message msg = handler.obtainMessage();
                msg.obj = mMyTaskItem;
                handler.sendMessage(msg);

                //没有执行项时等待
                sleep(mInterval);
            }
            catch (InterruptedException e) {
                //Log.e(TAG, "收到线程中断请求");
                e.printStackTrace();
                //被中断的是退出就结束，否则继续
                if (mQuit) {
                    return;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
