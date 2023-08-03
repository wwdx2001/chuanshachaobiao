package com.sh3h.thirdparty.task;

/**
 * 描述：返回对象的监听器.
 */
public abstract class MyTaskObjectListener extends MyTaskListener {

    /**
     * 描述：执行开始后调用.
     * @param <T> 返回的对象
     * */
    public abstract <T> void update(T entity);


}