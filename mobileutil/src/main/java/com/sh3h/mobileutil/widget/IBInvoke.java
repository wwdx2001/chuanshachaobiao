package com.sh3h.mobileutil.widget;

public interface IBInvoke {
	public void before();

	public void after();

    public <T> void after(T object);
}
