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
 * XmlResponse�N���X.<br>
 *
 * <pre>
 *   ���̃N���X������xml���쐬����悤�Ɏg�p�����B
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class XmlResponse implements IXmlResponse {

    /** XML�t�H�[�}�b�g�I�u�W�F�N�g */
    private XmlFormat xmlFormat = null;

    /**
     * �R���X�g���N�^�[.<br>
     *
     * @param xmlFileName�FXML�t�@�C����
     */
    public XmlResponse(String xmlFileName) {
        xmlFormat = new XmlFormat(xmlFileName);
    }

    /**
     * xml�f�[�^���Z�b�g����.<br>
     *
     * @param xmlData�F XML�f�[�^
     */
    public void addXmlData(IXmlData xmlData){
        xmlFormat.addXmlData(xmlData);
    }

    /**
     * �f�[�^�y�уt�H�[�}�b�g�t�@�C������Â��AXML��������쐬����B<br>
     */
    public String getXmlResponse() {
        return xmlFormat.parseXmlString();
    }
}
