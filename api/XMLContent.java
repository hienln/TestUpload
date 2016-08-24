/*
 * APIConstants.java
 *
 * Version 1.0
 *
 * Copyright 2008 FPT-Software, All rights resevered
 *
 * Modification Logs:
 *  DATE         AUTHOR            DESCRIPTION
 *  --------------------------------------------------
 *  22-01-2009   Dinh Hong Nam     First created
 */
package co.jp.nissen.api;

import java.util.Properties;
import co.jp.nissen.com.xml.IXmlData;

/**
 * MailInfoDataクラス.<br>
 *
 * <pre>
 *   ここクラスは、SNS顧客のメール情報を格納するのに使用される。
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class XMLContent implements IXmlData {

    /** データオブジェクト */
    private Properties data = null;

    /** ルート名のデータ */
    private String rootName = null;

    /**
     * コンストラクター.<br>
     *
     * @param rootName:ルートデータ名
     */
    public XMLContent(String rootName){
        data = new Properties();
        this.rootName = rootName;
    }

    /**
     * ルートデータ名を取得する.<br>
     *
     * @return：ルートデータ名
     */
    public String getRootName(){
        return this.rootName;
    }

    /**
     * データオブジェクトに値をセットする.<br>
     *
     * @param key：データキー
     * @param value：データ値e
     */
    public void setValue(String key, String value){
        data.setProperty(key, value);
    }

    /**
     * 文字列の値を取得する.<br>
     *
     * <pre>
     * この機能は、IXmlDataインタフェースに定義されて、ここに実装する必要がない
     * </pre>
     */
    public String getValue(String key){
        return data.getProperty(key);
    }

    /**
     * データのサイズを取得する.<br>
     *
     * <pre>
     * この機能は、IXmlDataインタフェースに定義されて、ここに実装する必要がない
     * </pre>
     */
    public int count(){
        // ダミー値
        return 0;
    }

    /**
     * 文字列の値を取得する.<br>
     *
     * <pre>
     * この機能、IXmlDataインタフェースに定義されて、ここに実装する必要がない。
     * </pre>
     */
    public String getValue(int idx, String key){
        // ダミー値
        return "";
    }
}
