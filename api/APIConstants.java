/*
 * APIConstants.java
 *
 * バージョン1.0
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
 * APIConstantsクラス.<br>
 *
 * <pre>
 *  このクラスは、SNS APIの全てのコンスタントがある。
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class APIConstants {

    public class XML {

        /** API処理コード */
        public static final String API_CODE_KEY = "cd";

        /** SNS会員ID */
        public static final String SNS_ID_KEY = "snsid";

        /** プロセスステータス */
        public static final String STATUS_KEY = "status";

        /** メール送信先フラグ */
        public static final String SEND_FLG_KEY = "send_flg";

        /** PCメール */
        public static final String PC_EMAIL_KEY = "pc_email";

        /** 携帯メール　*/
        public static final String MB_EMAIL_KEY = "mobile_email";

        /** エラーデータのルート名 */
        public static final String ERR_DATA_ROOT_NAME = "Err";

        /** メールの情報のルート名 */
        public static final String MAIL_INFO_DATA_ROOT_NAME = "cust";
    }

    public class Request {

        /** APIコード */
        public static final String API_CODE = "cd";

        /** SNS会員ID */
        public static final String SNS_ID = "snsid";

        /** 送信時刻 */
        public static final String SEND_TIME = "time";

        /** セキュリティーコード */
        public static final String HASH_CODE = "k";
        
        /** メールアドレス　*/
        public static final String EMAIL_ADDRESS = "email";        
    }

    public class Status {

        /** タイムアウトエラーコード */
        public static final String ERR_TIME_OUT = "E001";

        /** パラメータの値が変更された */
        public static final String ERR_CHANGED_VALUE = "E002";

        /** パラメータの数が正しくない */
        public static final String ERR_WRONG_NUMBER_OF_PARMETER = "E003";

        /** SNS会員が存在しない */
        public static final String ERR_MEMBER_NOT_EXIST = "E101";

        /** システムエラー */
        public static final String ERR_SYSTEM = "E999";

        /** 成功 */
        public static final String NORMAL_CD = "N001";
    }    

    /** プロパテーぃファイル名 */
    public static final String PROPERTIES_FILE = "sns.properties";

    /** APIコード */
    public static final String PROP_API_CODE = "ApiCode.";

    /** コードキーパラメータ */
    public static final String PARAM_CODE_KEY = "Public.APICodeKey";

    /** タイムアウトパラメータ */
    public static final String PARAM_TIME_OUT = "Public.APITimeOut";

    /** メール情報の応答xmlファイル */
    public static final String PROP_XML_RESPONSE_FILE = "XMLResponseFile.";

    /** エラー応答xmlファイル */
    public static final String PROP_XML_ERR_RESPONSE_FILE = "XmlErrResponseFile";

    /** 空白文字列 */
    public static final String EMPTY_STRING = "";
    
    /** PCメールのタイプ */
    public static final String PC_MAIL_TYPE = "0";
    
    /** 携帯のメールのタイプ */
    public static final String MOBILE_MAIL_TYPE = "1";
    
    /** 日付フォーマット */
    public static final String DATE_TIME_FORMATE = "yyyyMMddHHmmss";
}
