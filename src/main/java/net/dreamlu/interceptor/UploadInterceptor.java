package net.dreamlu.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class UploadInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		inv.invoke();
	}

}
