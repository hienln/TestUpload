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
 * MailInfoData�N���X.<br>
 *
 * <pre>
 *   �����N���X�́ASNS�ڋq�̃��[�������i�[����̂Ɏg�p�����B
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class XMLContent implements IXmlData {

    /** �f�[�^�I�u�W�F�N�g */
    private Properties data = null;

    /** ���[�g���̃f�[�^ */
    private String rootName = null;

    /**
     * �R���X�g���N�^�[.<br>
     *
     * @param rootName:���[�g�f�[�^��
     */
    public XMLContent(String rootName){
        data = new Properties();
        this.rootName = rootName;
    }

    /**
     * ���[�g�f�[�^�����擾����.<br>
     *
     * @return�F���[�g�f�[�^��
     */
    public String getRootName(){
        return this.rootName;
    }

    /**
     * �f�[�^�I�u�W�F�N�g�ɒl���Z�b�g����.<br>
     *
     * @param key�F�f�[�^�L�[
     * @param value�F�f�[�^�le
     */
    public void setValue(String key, String value){
        data.setProperty(key, value);
    }

    /**
     * ������̒l���擾����.<br>
     *
     * <pre>
     * ���̋@�\�́AIXmlData�C���^�t�F�[�X�ɒ�`����āA�����Ɏ�������K�v���Ȃ�
     * </pre>
     */
    public String getValue(String key){
        return data.getProperty(key);
    }

    /**
     * �f�[�^�̃T�C�Y���擾����.<br>
     *
     * <pre>
     * ���̋@�\�́AIXmlData�C���^�t�F�[�X�ɒ�`����āA�����Ɏ�������K�v���Ȃ�
     * </pre>
     */
    public int count(){
        // �_�~�[�l
        return 0;
    }

    /**
     * ������̒l���擾����.<br>
     *
     * <pre>
     * ���̋@�\�AIXmlData�C���^�t�F�[�X�ɒ�`����āA�����Ɏ�������K�v���Ȃ��B
     * </pre>
     */
    public String getValue(int idx, String key){
        // �_�~�[�l
        return "";
    }
}
