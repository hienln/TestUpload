/*
 * ErrXmlResponse.java
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

import co.jp.nissen.com.SearchUtil;
import co.jp.nissen.com.xml.IXmlData;
import co.jp.nissen.com.xml.XmlFormat;

/**
 * ErrXmlResponseクラス.<br>
 *
 * <pre>
 *   このクラスが処理がエラーとなる際、エラーXMLを作成するように、使用される。
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class ErrXmlResponse implements IXmlResponse {

    /** XMLフォーマットオブジェクト */
    private XmlFormat xmlFormat = null;

    /**
     * コンストラクター.<br>
     *
     * @param xmlData：XMLデータ
     */
    public ErrXmlResponse(IXmlData xmlData) {

        // sns.propertiesからエラーフォーマットファイルを検索する。
        SearchUtil search = new SearchUtil(APIConstants.PROPERTIES_FILE);
        String apiXmlErrFile = search.runSearch(APIConstants.PROP_XML_ERR_RESPONSE_FILE);

        xmlFormat = new XmlFormat(apiXmlErrFile);
        xmlFormat.addXmlData(xmlData);
    }

    /**
     * データ及びフォーマットファイルに基づき、XML文字列を作成する。<br>
     */
    public String getXmlResponse() {
        return xmlFormat.parseXmlString();
    }

    /**
     * xmlデータをセットする.<br>
     *
     * @param xmlData ：XMLデータ
     */
    public void addXmlData(IXmlData xmlData){
        xmlFormat.addXmlData(xmlData);
    }
}
