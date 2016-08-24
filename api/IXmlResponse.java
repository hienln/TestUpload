/*
 * IXmlResponse.java
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

import co.jp.nissen.com.xml.IXmlData;

/**
 * IXmlResponseクラス.<br>
 *
 * <pre>
 *   このインターフェースは応答XMLの全てのテンプレートである。
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public interface IXmlResponse {
    /**
     * 応答XMLを取得する.<br>
     *
     * @XML文字列を返却する
     */
    public String getXmlResponse();

    /**
     * xmlデータをセットする.<br>
     *
     * @param xmlData：XMLデータ
     */
    public void addXmlData(IXmlData xmlData);
}
