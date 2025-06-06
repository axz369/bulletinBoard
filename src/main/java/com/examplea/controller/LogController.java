package com.examplea.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * ロギング処理を動かすためのコントローラ.
 */
@Controller
@RequestMapping("/logtest")
public class LogController {

    // ロガーを取得する。(引数にはこのロガーを使用するクラスのClassオブジェクトを渡す)
    private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log")
    public String log(){
        LOGGER.info("log()メソッド開始!");
        String strAge = "村田";
        LOGGER.debug("偏素の中身を出力 : " + strAge + "歳です");
        try{
            int age = Integer.parseInt(strAge);
            LOGGER.info("あなたは" + age +"歳です");
        }catch(Exception e){
            LOGGER.error("年齢が不正です");
        }
        LOGGER.info("log()メソッド終了!");
        return "finished-output-logs";
    }
}
