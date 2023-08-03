package com.sh3h.thirdparty.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;

/**
 * 线程队列.
 */
public class MyTaskQueue extends Thread {

    /** The tag. */
    private static String TAG = "MyTaskQueue";

    /** The Constant D. */
    private static final boolean D = true;

    /** 等待执行的任务. */
    private static List<MyTaskItem> mMyTaskItemList = null;

    /**单例对象 */
    private static MyTaskQueue mMyTaskQueue = null;

    /** 停止的标记. */
    private boolean mQuit = false;

    /** 执行完成后的消息句柄. */
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
     * 单例构造.
     */
    public static MyTaskQueue getInstance() {
        if (mMyTaskQueue == null) {
            mMyTaskQueue = new MyTaskQueue();
        }
        return mMyTaskQueue;
    }

    /**
     * 构造执行线程队列.
     *
     */
    private MyTaskQueue() {
        mQuit = false;
        mMyTaskItemList = new ArrayList<MyTaskItem>();
        //设置优先级
        //Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        //从线程池中获取
        ExecutorService mExecutorService  = MyTaskPool.getExecutorService();
        mExecutorService.submit(this);
    }

    /**
     * 开始一个执行任务.
     *
     * @param item 执行单位
     */
    public void execute(MyTaskItem item) {
        addTaskItem(item);
    }


    /**
     * 开始一个执行任务并清除原来队列.
     * @param item 执行单位
     * @param clean 清空之前的任务
     */
    public void execute(MyTaskItem item, boolean clean) {
        if (clean) {
            if (mMyTaskQueue != null) {
                mMyTaskQueue.quit();
            }
        }

        addTaskItem(item);
    }

    /**
     * 描述：添加到执行线程队列.
     *
     * @param item 执行单位
     */
    private void addTaskItem(MyTaskItem item) {
        synchronized(this) {
            if (item == null) {
                return;
            }

            if (mMyTaskQueue == null) {
                mMyTaskQueue = new MyTaskQueue();
            }

            switch (item.getPriority()) {
                case HIGHER:
                case HIGHEST:
                    mMyTaskItemList.add(0, item);
                    break;
                default:
                    mMyTaskItemList.add(item);
                    break;
            }

            //添加了执行项就激活本线程
            this.notify();
        }
    }

    /**
     * 描述：线程运行.
     *
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        while (!mQuit) {
            try {
                int count = 0;
                while (true) {
                    synchronized (this) {
                        count = mMyTaskItemList.size();
                    }

                    if (count <= 0) {
                        break;
                    }

                    MyTaskItem item = null;
                    synchronized (this) {
                        item = mMyTaskItemList.remove(0);
                    }

                    //定义了回调
                    if ((item != null) && (item.getListener() != null)) {
                        item.getListener().get(item);
                        //交由UI线程处理
                        Message msg = handler.obtainMessage();
                        msg.obj = item;
                        handler.sendMessage(msg);
                    }

                    //停止后清空
                    if (mQuit) {
                        synchronized (this) {
                            mMyTaskItemList.clear();
                        }
                        return;
                    }
                }

                try {
                    //没有执行项时等待
                    synchronized(this) {
                        this.wait();
                    }
                }
                catch (InterruptedException e) {
                    Log.e(TAG, "收到线程中断请求");
                    e.printStackTrace();
                    //被中断的是退出就结束，否则继续
                    if (mQuit) {
                        synchronized (this) {
                            mMyTaskItemList.clear();
                        }
                        return;
                    }
                    continue;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 描述：终止队列释放线程.
     */
    public void quit() {
        mQuit  = true;
        interrupted();
        mMyTaskQueue  = null;
    }

}
