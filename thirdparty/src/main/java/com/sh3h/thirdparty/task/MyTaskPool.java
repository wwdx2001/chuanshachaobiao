package com.sh3h.thirdparty.task;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


/**
 *
 * Copyright (c) 2012 All rights reserved
 * 名称：AbTaskPool.java
 * 描述：线程池,程序中只有1个
 */

public class MyTaskPool{

    /** The tag. */
    private static String TAG = "MyTaskPool";

    /** The Constant D. */
    private static final boolean D = true;

    /** 单例对象 The http pool. */
    private static MyTaskPool mMyTaskPool = null;

    /** 固定4个线程来执行任务. */
    private static int nThreads  = 6;

    /** 线程执行器. */
    private static ExecutorService executorService = null;

    /** 下载完成后的消息句柄. */
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MyTaskItem item = (MyTaskItem)msg.obj;
            if (item.getListener() instanceof MyTaskListListener) {
                ((MyTaskListListener)item.getListener()).update((List<?>)item.getResult());
            }
            else if(item.getListener() instanceof MyTaskObjectListener) {
                ((MyTaskObjectListener)item.getListener()).update(item.getResult());
            }
            else {
                item.getListener().update();
            }
        }
    };

    /**
     * 初始化线程池
     */
    static{
        nThreads = getNumCores();
        mMyTaskPool = new MyTaskPool(nThreads * 5);
    }

    /**
     * 构造线程池.
     *
     * @param nThreads 初始的线程数
     */
    protected MyTaskPool(int nThreads) {
        executorService = Executors.newFixedThreadPool(nThreads);
    }

    /**
     * 单例构造图片下载器.
     *
     * @return single instance of AbHttpPool
     */
    public static MyTaskPool getInstance() {
        return mMyTaskPool;
    }

    /**
     * 执行任务.
     * @param item the item
     */
    public void execute(final MyTaskItem item) {
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    //定义了回调
                    if (item.getListener() != null) {
                        item.getListener().get(item);
                        //交由UI线程处理
                        Message msg = handler.obtainMessage();
                        msg.obj = item;
                        handler.sendMessage(msg);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *
     * 描述：获取线程池的执行器
     * @return executorService
     * @throws
     */

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * 描述：立即关闭.
     */
    public void shutdownNow(){
        if (!executorService.isTerminated()) {
            executorService.shutdownNow();
            listenShutdown();
        }

    }

    /**
     * 描述：平滑关闭.
     */
    public void shutdown(){
        if (!executorService.isTerminated()) {
            executorService.shutdown();
            listenShutdown();
        }
    }

    /**
     * 描述：关闭监听.
     */
    public void listenShutdown(){
        try {
            while (!executorService.awaitTermination(1, TimeUnit.MILLISECONDS)) {
                if (D) {
                    Log.d(TAG, "线程池未关闭");
                }
            }

            if(D) {
                Log.d(TAG, "线程池已关闭");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     * @return The number of cores, or 1 if failed to get result
     */
    public static int getNumCores() {
        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new FileFilter(){

                @Override
                public boolean accept(File pathname) {
                    //Check if filename is "cpu", followed by a single digit number
                    if(Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            });

            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Default to return 1 core
            return 1;
        }
    }
}