package com.sh3h.thirdparty.task;

import java.util.List;

import android.os.AsyncTask;

/**
 *
 * 名称：AbAsyncTask.java
 * 描述：下载数据的任务实现，单次下载
 */
public class MyTask extends AsyncTask<MyTaskItem, Integer, MyTaskItem> {

    private MyTaskListener listener;

    public MyTask() {
        super();
    }

    @Override
    protected MyTaskItem doInBackground(MyTaskItem... items) {
        MyTaskItem item = items[0];
        this.listener = item.getListener();
        if (this.listener != null) {
            this.listener.get(item);
        }
        return item;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(MyTaskItem item) {
        if (this.listener != null) {
            if (this.listener instanceof MyTaskListListener) {
                ((MyTaskListListener)this.listener).update((List<?>)item.getResult());
            }
            else if (this.listener instanceof MyTaskObjectListener) {
                ((MyTaskObjectListener)this.listener).update(item.getResult());
            }
            else{
                this.listener.update();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (this.listener != null) {
            this.listener.onProgressUpdate(values);
        }
    }
}
