package com.examplea.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ロギング処理のサンプルを動かすためのコントローラ.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
public class LogSampleController {

	// ロガーを取得する。（引数にはこのロガーを使用するクラスのClassオブジェクトを渡す）
	private static final Logger LOGGER = LoggerFactory.getLogger(LogSampleController.class);

	/**
	 * ロガーに含まれる各ログレベルのメソッドを呼ぶ.
	 * 
	 * <pre>
	 * デフォルトの状態では、ERROR,WORN,INFOのみのログが
	 * コンソールに表示されます。
	 * もしDEBUG,TRACEも表示させたい場合はlogback-spring.xmlで
	 * 設定を行います。
	 * </pre>
	 * 
	 * @return ログ出力完了画面
	 */
	@GetMapping("/loglevel")
	public String logLevel() {
		LOGGER.error("エラーが発生しました");
		LOGGER.warn("警告です");
		LOGGER.info("情報です");
		LOGGER.debug("デバッグ情報です");
		LOGGER.trace("細かいトレース情報です");
		return "finished-output-logs";
	}
	
	/**
	 * 演習の解答例.
	 * 
	 * @return ログ出力完了画面
	 */
	@GetMapping("/log")
	public String log() {
		LOGGER.info("log()メソッド開始！");
		String strAge = "28";
		LOGGER.debug("変数の中身" + strAge);
		try {
			int age = Integer.parseInt(strAge);	
			LOGGER.info("あなたは" + age + "歳です");
		}catch (Exception e) {
			LOGGER.error("年齢が不正です");
		}
		LOGGER.info("log()メソッド終了！");
		return "finished-output-logs";
	}

	/**
	 * システム内で例外発生を行うメソッド.<br>
	 * ここで発生した例外はGlobalExceptionHandlerが捕獲し処理をします
	 * 
	 * @throws ArithmeticException このメソッドは必ずArithmeticExceptionを発生します
	 */
	@GetMapping("/exception")
	public String throwsException() {
		// 0で除算、非検査例外であるArithmeticExceptionが発生！
		System.out.println("例外発生前");
		System.out.println(10 / 0); // ←このタイミングでGlobalExceptionHandlerに処理が飛ぶ
		System.out.println("例外発生後");

		return "通常はここにHTML名を書くが、ここまで処理は来ない";
	}

	/**
	 * ログイン画面に移動する.<br>
	 * 
	 * @throws ログイン画面
	 */
	@GetMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
}
