package com.sh3h.thirdparty.task;

/**
 * 描述：通用监听器.
 * 直接子类MyTaskListListener，MyTaskObjectListener
 */
public abstract class MyTaskListener {
    /**
     * 执行开始后调用.
     */
    public abstract void get(MyTaskItem item);

    /**
     * 执行开始后调用.
     */
    public void update() {}

    /**
     * 监听进度变化.
     */
    public void onProgressUpdate(Integer... values) {}

}