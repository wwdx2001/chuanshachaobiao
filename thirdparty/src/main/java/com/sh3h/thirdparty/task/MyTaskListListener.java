package com.sh3h.thirdparty.task;

import java.util.List;

/**
 * 描述：返回列表的监听器.
 */
public abstract class MyTaskListListener extends MyTaskListener {

    /**
     * 描述：执行完成后回调.
     * 不管成功与否都会执行
     * @param paramList 返回的List
     */
    public abstract void update(List<?> paramList);

}