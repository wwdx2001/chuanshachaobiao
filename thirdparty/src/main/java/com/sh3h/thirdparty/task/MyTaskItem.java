package com.sh3h.thirdparty.task;

/**
 * 描述：数据执行单位.
 */
public class MyTaskItem {
    public enum Priority {
        LOWEST,     // -2
        LOWER,      // -1
        NORMAL,     // 0
        HIGHER,     // 1
        HIGHEST     // 2
    }

    /** 记录的当前索引. */
    private int position;

    /** 执行完成的回调接口. */
    private MyTaskListener listener;

    /** parameter */
    private Object param;

    /** 执行完成的结果. */
    private Object result;

    // priority
    private Priority mPriority;

    public MyTaskItem() {
        super();
        mPriority = Priority.NORMAL;
    }

    public MyTaskItem(MyTaskListener listener) {
        super();
        this.listener = listener;
        mPriority = Priority.NORMAL;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MyTaskListener getListener() {
        return listener;
    }

    public void setListener(MyTaskListener listener) {
        this.listener = listener;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }
}