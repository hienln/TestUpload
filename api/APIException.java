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
 * APIException�N���X.<br>
 *
 * <pre>
 * ���̃N���X�͋��ʃG���[xml���쐬����ׂ̏����܂ށB
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class APIException extends Exception implements IXmlData{

	/** �V���A���o�[�W���� UID */
	private static final long serialVersionUID = -5777174154209231278L;

    /** �G���[�f�[�^�̊i�[���鏊 */
    private Properties data = null;

	/**
     * �R���X�g���N�^�[.<br>
     *
     * @param apiCode�FAPI�R�[�h
     * @param snsId�F SNS���ID
     * @param status�F�G���[�X�e�[�^�X
     * @param message�F�G���[���b�Z�[�W
	 */
    public APIException(String apiCode, String snsId, String status, String message){
        super(message);
        data = new Properties();
        data.setProperty(APIConstants.XML.API_CODE_KEY, apiCode);
        data.setProperty(APIConstants.XML.SNS_ID_KEY, snsId);
        data.setProperty(APIConstants.XML.STATUS_KEY, status);
	}

    /**
     * ���[�g�����擾����.<br>
     *
     * @���[�g���̃f�[�^��ԋp����
     */
    public String getRootName(){
        return APIConstants.XML.ERR_DATA_ROOT_NAME;
    }

    /**
     * ������̒l���擾.<br>
     *
     * <pre>
     * ���̋@�\��IXmlData�C���^�[�t�F�[�X�ɒ�`����āA�����Ɏ��������B
     * </pre>
     */
    public String getValue(String key){
        return data.getProperty(key);
    }

    /**
     * �f�[�^�̃T�C�Y���擾.<br>
     *
     * <pre>
     * ���̋@�\��IXmlData�C���^�t�F�[�X�ɒ�`����āA�����Ɏ�������K�v���Ȃ��B
     * </pre>
     */
    public int count(){
        // �_�~�[�l
        return 0;
    }

    /**
     * ������̒l���擾.<br>
     *
     * <pre>
     * ���̋@�\��IXmlData�C���^�t�F�[�X�ɒ�`����āA�����Ɏ�������K�v���Ȃ�
     * </pre>
     */
    public String getValue(int idx, String key){
        // �_�~�[�l
        return "";
    }
}
