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
 * IXmlResponse�N���X.<br>
 *
 * <pre>
 *   ���̃C���^�[�t�F�[�X�͉���XML�̑S�Ẵe���v���[�g�ł���B
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public interface IXmlResponse {
    /**
     * ����XML���擾����.<br>
     *
     * @XML�������ԋp����
     */
    public String getXmlResponse();

    /**
     * xml�f�[�^���Z�b�g����.<br>
     *
     * @param xmlData�FXML�f�[�^
     */
    public void addXmlData(IXmlData xmlData);
}
