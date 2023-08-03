/**
 * @author qiweiwei
 *
 */
package com.sh3h.mobileutil.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
//import java.util.logging.Logger;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;
//import android.util.Log;

import com.nostra13.universalimageloader.utils.L;
import com.orhanobut.logger.Logger;

public class LogUtil {
	//private static Logger _logger = null;

	private static boolean mIsLoggerInit = false;

	public synchronized static void initLogger(String path) {
		if (path == null) {
			return;
		}

//		File sdDir = Environment.getExternalStorageDirectory();
//		File logDir = new File(sdDir, folder);
//		if (!logDir.exists()) {
//			logDir.mkdirs();
//		}
//
//		String name = String.format("log-%s", TextUtil.format(new Date(), TextUtil.FORMAT_DATE));
//		File logFile = new File(logDir, name);

		/*try {
			LogManager lm = LogManager.getLogManager();
			_logger = lm.getLogger(TextUtil.EMPTY);
			_logger.addHandler(new FileHandler(path));
			_logger.setLevel(Level.ALL);
		} catch (IOException e) {
			e.printStackTrace();
			_logger = null;
		}*/

		Logger.init();
		Logger.initLogFile(path);
		mIsLoggerInit = true;
	}

	public synchronized static void closeLogger() {
		if (mIsLoggerInit) {
			mIsLoggerInit = false;
			Logger.closeLogFile();
		}
	}

//	private static void initLogWithFluentAPI(LogManager lm, String logFilePath) {
//		_logger = lm.getLogger(TextUtil.EMPTY);
//		// _logger = LogManager.getLogManager().getLogger("MAIN");
//
//		//_logger.addHandler(new ConsoleHandler());
//
//		try {
//			_logger.addHandler(new FileHandler(logFilePath));
//			_logger.setLevel(Level.ALL);
//		} catch (IOException e) {
//			e.printStackTrace();
//			_logger = null;
//			return;
//		}
//
//		//_logger.addHandler(new AndroidHandler());
//	}

	public synchronized static void d(String tag, String msg) {
		//append(Level.CONFIG, tag, msg);
		if (mIsLoggerInit) {
			Logger.t(tag).d(msg);
		}
	}

	public synchronized static void d(String tag, String format, Object... args) {
		//append(Level.CONFIG, tag, format, args);
		if (mIsLoggerInit) {
			Logger.t(tag).d(format, args);
		}
	}

	public synchronized static void i(String tag, String msg) {
		//append(Level.INFO, tag, msg);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
		Date date = new Date(System.currentTimeMillis());
		msg+="---time："+simpleDateFormat.format(date);
		if (mIsLoggerInit) {
			Logger.t(tag).i(msg);
		}
	}

	public synchronized static void i(String tag, String format, Object... args) {
		//append(Level.INFO, tag, format, args);
		if (mIsLoggerInit) {
			Logger.t(tag).i(format, args);
		}
	}

	public synchronized static void w(String tag, String msg) {
		//append(Level.WARNING, tag, msg);
		if (mIsLoggerInit) {
			Logger.t(tag).w(msg);
		}
	}

	public synchronized static void w(String tag, String format, Object... args) {
		//append(Level.WARNING, tag, format, args);
		if (mIsLoggerInit) {
			Logger.t(tag).w(format, args);
		}
	}

	public synchronized static void w(String tag, String msg, Throwable tr) {
		//append(Level.WARNING, tag, msg);
		if (mIsLoggerInit) {
			Logger.t(tag).w(msg);
		}
	}

	public synchronized static void w(String tag, Throwable tr, String format, Object... args) {
		//append(Level.WARNING, tag, tr, format, args);
		if (mIsLoggerInit) {
			Logger.t(tag).w(format, args);
		}
	}

	public synchronized static void e(String tag, String msg) {
		//append(Level.SEVERE, tag, msg);
		if (mIsLoggerInit) {
			Logger.t(tag).e(msg);
		}
	}

	public synchronized static void e(String tag, String format, Object... args) {
		//append(Level.SEVERE, tag, format, args);
		if (mIsLoggerInit) {
			Logger.t(tag).e(format, args);
		}
	}

	public synchronized static void e(String tag, String msg, Throwable tr) {
		//append(Level.SEVERE, tag, msg, tr);
		if (mIsLoggerInit) {
			Logger.t(tag).e(msg);
		}
	}

	public synchronized static void e(String tag, Throwable tr, String format, Object... args) {
		//append(Level.SEVERE, tag, tr, format, args);
		if (mIsLoggerInit) {
			Logger.t(tag).e(format, args);
		}
	}

	private synchronized static void append(Level level, String tag, Throwable tr, String format, Object[] args) {
		//append(level, tag, String.format(format, args), tr);
	}

	private synchronized static void append(Level level, String tag, String msg, Throwable tr) {
//		Log.i(tag, msg);
//		if (_logger != null) {
//			_logger.log(level, String.format("TAG:%s, %s", tag, msg), tr);
//		}
		//Logger.t(tag).i(msg);
	}

	private synchronized static void append(Level level, String tag, String format, Object[] args) {
		//append(level, tag, String.format(format, args));
	}

	private synchronized static void append(Level level, String tag, String msg) {
//		if (level.getName().equals("SEVERE")) {
//			Log.e(tag, msg);
//		} else {
//			Log.i(tag, msg);
//		}
//
//		if (_logger != null) {
//			_logger.log(level, String.format("TAG:%s, %s", tag, msg));
//		}
		//Logger.t(tag).i(msg);
	}

}
