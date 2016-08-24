/*
 * APIConstants.java
 *
 * �o�[�W����1.0
 *
 * Copyright 2008 FPT-Software, All rights resevered
 *
 * Modification Logs:
 *  DATE         AUTHOR            DESCRIPTION
 *  --------------------------------------------------
 *  22-01-2009   Dinh Hong Nam     First created
 */
package co.jp.nissen.api;

/**
 * APIConstants�N���X.<br>
 *
 * <pre>
 *  ���̃N���X�́ASNS API�̑S�ẴR���X�^���g������B
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class APIConstants {

    public class XML {

        /** API�����R�[�h */
        public static final String API_CODE_KEY = "cd";

        /** SNS���ID */
        public static final String SNS_ID_KEY = "snsid";

        /** �v���Z�X�X�e�[�^�X */
        public static final String STATUS_KEY = "status";

        /** ���[�����M��t���O */
        public static final String SEND_FLG_KEY = "send_flg";

        /** PC���[�� */
        public static final String PC_EMAIL_KEY = "pc_email";

        /** �g�у��[���@*/
        public static final String MB_EMAIL_KEY = "mobile_email";

        /** �G���[�f�[�^�̃��[�g�� */
        public static final String ERR_DATA_ROOT_NAME = "Err";

        /** ���[���̏��̃��[�g�� */
        public static final String MAIL_INFO_DATA_ROOT_NAME = "cust";
    }

    public class Request {

        /** API�R�[�h */
        public static final String API_CODE = "cd";

        /** SNS���ID */
        public static final String SNS_ID = "snsid";

        /** ���M���� */
        public static final String SEND_TIME = "time";

        /** �Z�L�����e�B�[�R�[�h */
        public static final String HASH_CODE = "k";
        
        /** ���[���A�h���X�@*/
        public static final String EMAIL_ADDRESS = "email";        
    }

    public class Status {

        /** �^�C���A�E�g�G���[�R�[�h */
        public static final String ERR_TIME_OUT = "E001";

        /** �p�����[�^�̒l���ύX���ꂽ */
        public static final String ERR_CHANGED_VALUE = "E002";

        /** �p�����[�^�̐����������Ȃ� */
        public static final String ERR_WRONG_NUMBER_OF_PARMETER = "E003";

        /** SNS��������݂��Ȃ� */
        public static final String ERR_MEMBER_NOT_EXIST = "E101";

        /** �V�X�e���G���[ */
        public static final String ERR_SYSTEM = "E999";

        /** ���� */
        public static final String NORMAL_CD = "N001";
    }    

    /** �v���p�e�[���t�@�C���� */
    public static final String PROPERTIES_FILE = "sns.properties";

    /** API�R�[�h */
    public static final String PROP_API_CODE = "ApiCode.";

    /** �R�[�h�L�[�p�����[�^ */
    public static final String PARAM_CODE_KEY = "Public.APICodeKey";

    /** �^�C���A�E�g�p�����[�^ */
    public static final String PARAM_TIME_OUT = "Public.APITimeOut";

    /** ���[�����̉���xml�t�@�C�� */
    public static final String PROP_XML_RESPONSE_FILE = "XMLResponseFile.";

    /** �G���[����xml�t�@�C�� */
    public static final String PROP_XML_ERR_RESPONSE_FILE = "XmlErrResponseFile";

    /** �󔒕����� */
    public static final String EMPTY_STRING = "";
    
    /** PC���[���̃^�C�v */
    public static final String PC_MAIL_TYPE = "0";
    
    /** �g�т̃��[���̃^�C�v */
    public static final String MOBILE_MAIL_TYPE = "1";
    
    /** ���t�t�H�[�}�b�g */
    public static final String DATE_TIME_FORMATE = "yyyyMMddHHmmss";
}
