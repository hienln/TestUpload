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
 * ErrXmlResponse�N���X.<br>
 *
 * <pre>
 *   ���̃N���X���������G���[�ƂȂ�ہA�G���[XML���쐬����悤�ɁA�g�p�����B
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class ErrXmlResponse implements IXmlResponse {

    /** XML�t�H�[�}�b�g�I�u�W�F�N�g */
    private XmlFormat xmlFormat = null;

    /**
     * �R���X�g���N�^�[.<br>
     *
     * @param xmlData�FXML�f�[�^
     */
    public ErrXmlResponse(IXmlData xmlData) {

        // sns.properties����G���[�t�H�[�}�b�g�t�@�C������������B
        SearchUtil search = new SearchUtil(APIConstants.PROPERTIES_FILE);
        String apiXmlErrFile = search.runSearch(APIConstants.PROP_XML_ERR_RESPONSE_FILE);

        xmlFormat = new XmlFormat(apiXmlErrFile);
        xmlFormat.addXmlData(xmlData);
    }

    /**
     * �f�[�^�y�уt�H�[�}�b�g�t�@�C���Ɋ�Â��AXML��������쐬����B<br>
     */
    public String getXmlResponse() {
        return xmlFormat.parseXmlString();
    }

    /**
     * xml�f�[�^���Z�b�g����.<br>
     *
     * @param xmlData �FXML�f�[�^
     */
    public void addXmlData(IXmlData xmlData){
        xmlFormat.addXmlData(xmlData);
    }
}
