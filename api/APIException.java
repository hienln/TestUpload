/*
 * APIException.java
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
import co.jp.nissen.api.APIConstants;

/**
 * APIExceptionクラス.<br>
 *
 * <pre>
 * このクラスは共通エラーxmlを作成する為の情報を含む。
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class APIException extends Exception implements IXmlData{

	/** シリアルバージョン UID */
	private static final long serialVersionUID = -5777174154209231278L;

    /** エラーデータの格納する所 */
    private Properties data = null;

	/**
     * コンストラクター.<br>
     *
     * @param apiCode：APIコード
     * @param snsId： SNS会員ID
     * @param status：エラーステータス
     * @param message：エラーメッセージ
	 */
    public APIException(String apiCode, String snsId, String status, String message){
        super(message);
        data = new Properties();
        data.setProperty(APIConstants.XML.API_CODE_KEY, apiCode);
        data.setProperty(APIConstants.XML.SNS_ID_KEY, snsId);
        data.setProperty(APIConstants.XML.STATUS_KEY, status);
	}

    /**
     * ルート名を取得する.<br>
     *
     * @ルート名のデータを返却する
     */
    public String getRootName(){
        return APIConstants.XML.ERR_DATA_ROOT_NAME;
    }

    /**
     * 文字列の値を取得.<br>
     *
     * <pre>
     * この機能がIXmlDataインターフェースに定義されて、ここに実装される。
     * </pre>
     */
    public String getValue(String key){
        return data.getProperty(key);
    }

    /**
     * データのサイズを取得.<br>
     *
     * <pre>
     * この機能がIXmlDataインタフェースに定義されて、ここに実装する必要がない。
     * </pre>
     */
    public int count(){
        // ダミー値
        return 0;
    }

    /**
     * 文字列の値を取得.<br>
     *
     * <pre>
     * この機能がIXmlDataインタフェースに定義されて、ここに実装する必要がない
     * </pre>
     */
    public String getValue(int idx, String key){
        // ダミー値
        return "";
    }
}
