package tw.jiangsir.Utils.Async;

import javax.servlet.AsyncContext;

public class AsyncRequest implements Runnable {
	private AsyncContext asyncContext;

	public AsyncRequest(AsyncContext asyncContext) {
		this.asyncContext = asyncContext;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(10 * 1000);
			asyncContext.complete();
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
