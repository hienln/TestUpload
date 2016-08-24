/*
 * XmlResponse.java
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
import co.jp.nissen.com.xml.XmlFormat;

/**
 * XmlResponseクラス.<br>
 *
 * <pre>
 *   このクラスが応答xmlを作成するように使用される。
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class XmlResponse implements IXmlResponse {

    /** XMLフォーマットオブジェクト */
    private XmlFormat xmlFormat = null;

    /**
     * コンストラクター.<br>
     *
     * @param xmlFileName：XMLファイル名
     */
    public XmlResponse(String xmlFileName) {
        xmlFormat = new XmlFormat(xmlFileName);
    }

    /**
     * xmlデータをセットする.<br>
     *
     * @param xmlData： XMLデータ
     */
    public void addXmlData(IXmlData xmlData){
        xmlFormat.addXmlData(xmlData);
    }

    /**
     * データ及びフォーマットファイルを基づき、XML文字列を作成する。<br>
     */
    public String getXmlResponse() {
        return xmlFormat.parseXmlString();
    }
}
