/*
 * MailInfoService.java
 *
 * Version 1.0
 *
 * Copyright 2008 FPT-Software, All rights resevered
 *
 * Modification Logs:
 *  DATE         AUTHOR            DESCRIPTION
 *  --------------------------------------------------
 *  14-01-2009   Mai Ba Duy        First created
 */
package co.jp.nissen.api;

import co.jp.nissen.com.CommonDate;
import co.jp.nissen.com.CommonString;
import co.jp.nissen.com.KeyWordCheck;
import co.jp.nissen.com.MD5;
import co.jp.nissen.com.SearchUtil;
import co.jp.nissen.sns.MailInfo;
import co.jp.nissen.sns.SNSDb;
import co.jp.nissen.sns.SNSLog;
import co.jp.nissen.api.APIConstants;
import co.jp.nissen.com.xml.IXmlData;

/**
 * XMLMailInfoクラス.<br>
 *
 * <pre>
 *   このクラスは、顧客のメール情報（xmlフォーマット）を作成するのに使用される。
 * </pre>
 *
 * @author FPT Mai Ba Duy
 * @version 1.0
 */
public class MailInfoService extends APIBaseService {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = -6954809116335805767L;

    /** タイムアウト */
    private  String timeOut = null;

    /** MD5のエンコード用コード */
    private  String codeKey = null;

    /**
     * デフォルトコンストラクター.<br>
     */
    public MailInfoService() {
        // 何もしない
    }

    /**
     * xmlメールの情報を作成する.<br>
     *
     * <pre>
     *  DBから顧客のメールを取得し、パラメータを基づき、xml文字列を作成する
     * </pre>
     *
     * @param apiCode：処理コード
     * @param snsId：SNS会員ID
     * @param sendTime ：データ送信する時刻
     * @param hashCode ：セキュリティーコード
     * @return ：メールの情報がXMLフォーマットで記述される。
     */
    protected IXmlData process() throws APIException {

        String pcMail = APIConstants.EMPTY_STRING;
        String mobileMail = APIConstants.EMPTY_STRING;
        String mailDestination = APIConstants.EMPTY_STRING;

        String apiCode = request.getParameter(APIConstants.Request.API_CODE);
        String snsId = request.getParameter(APIConstants.Request.SNS_ID);
        String sendTime = request.getParameter(APIConstants.Request.SEND_TIME);
        String hashCode = request.getParameter(APIConstants.Request.HASH_CODE);

        SNSLog log = new SNSLog();
        
        // パラメータをチェックし、エラーステータスを取得する。*/        
        String status = validateParameters(apiCode, snsId, sendTime, hashCode);
        XMLContent mailInfoData = null;

        // パラメータにエラーが存在していない場合、DBからメールの情報を取得する。
        if (APIConstants.Status.NORMAL_CD.equals(status)) {
            // メールの情報を取得する。
            SNSDb snsDb = new SNSDb();
            MailInfo mailInfo = snsDb.getMailInfo(snsId);

            // DBからデータ取得中又はデータが見つけない場合にエラーが発生する。
            if (mailInfo == null) {
                // システムエラーがセットされる。

                status = APIConstants.Status.ERR_SYSTEM;

                // ログを書き込む。
                log.open();
                log
                        .write("XMLMailInfo"
                                + ": System Error !!! Can't find MailInfo of SNSMemberID: "
                                + snsId);
                log.close();
            } else if (!mailInfo.exists()) {
                // SNS会員をチェックする。会員が存在しない場合、ErrorCode = E101とする.
                status = APIConstants.Status.ERR_MEMBER_NOT_EXIST;

            }

            if (!APIConstants.Status.NORMAL_CD.equals(status)) {
                throw new APIException(apiCode, snsId, status,
                        "Parameters is not correct");
            }

            pcMail = mailInfo.getPCMail();
            mobileMail = mailInfo.getMBMail();                       
            mailDestination = String.valueOf(mailInfo.getMailDestination());              
            
            snsDb.close();

            mailInfoData = new XMLContent(APIConstants.XML.MAIL_INFO_DATA_ROOT_NAME);
            mailInfoData.setValue(APIConstants.XML.API_CODE_KEY, apiCode);
            mailInfoData.setValue(APIConstants.XML.SNS_ID_KEY, snsId);
            mailInfoData.setValue(APIConstants.XML.STATUS_KEY, status);
            mailInfoData.setValue(APIConstants.XML.PC_EMAIL_KEY, pcMail);
            mailInfoData.setValue(APIConstants.XML.MB_EMAIL_KEY, mobileMail);
            mailInfoData.setValue(APIConstants.XML.SEND_FLG_KEY,
                                  mailDestination);

        } else {
            throw new APIException(apiCode, snsId, status,
                    "Parameters is not correct");
        }

        return mailInfoData;
    }

    /**
     * パラメータをチェックする.<br>
     *
     * <pre>
     * </pre>
     *
     * @param apiCode：処理コード
     * @param snsId ：SNS会員ID
     * @param sendTime：送信時刻
     * @param hashCode ：セキュリティーコード
     * @return ：エラーステータス
     */
    private String validateParameters(String apiCode, String snsId,
            String sendTime, String hashCode) {
        // 初期化
        SearchUtil objSearch = new SearchUtil(APIConstants.PROPERTIES_FILE);
        timeOut = objSearch.runSearch(APIConstants.PARAM_TIME_OUT);
        if (APIConstants.EMPTY_STRING.equals(timeOut)) {
               return APIConstants.Status.ERR_SYSTEM;
        }

        // パラメータの数をチェックする.
        if (apiCode == null || APIConstants.EMPTY_STRING.equals(apiCode)
                || sendTime == null
                || APIConstants.EMPTY_STRING.equals(sendTime) || snsId == null
                || APIConstants.EMPTY_STRING.equals(snsId) || hashCode == null
                || APIConstants.EMPTY_STRING.equals(hashCode)) {
            return APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER;
        }

        // キーワードをチェックする
        KeyWordCheck kwc = new KeyWordCheck();
        if (!kwc.chkKeyWord(apiCode) || !kwc.chkKeyWord(snsId)
                || !kwc.chkKeyWord(sendTime) || !kwc.chkKeyWord(hashCode)) {
            return APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER;
        }

        // 応答時間をチェックし、時間が5分超える場合、strErrorCode = E001とする.
        if (CommonDate.getMinutesBetween(sendTime, CommonDate
                .getSysDate(APIConstants.DATE_TIME_FORMATE)) > CommonString
                ._parseInt(timeOut)) {
            return APIConstants.Status.ERR_TIME_OUT;
        }

        codeKey = objSearch.runSearch(APIConstants.PARAM_CODE_KEY);

        if (APIConstants.EMPTY_STRING.equals(codeKey)) {
               return APIConstants.Status.ERR_SYSTEM;
        }

        // パラメータの値をチェックし、変更した場合、ErrorCode = E002とする
        if (!hashCode.equals(MD5.MD5Encode(apiCode + sendTime + codeKey))) {
            return APIConstants.Status.ERR_CHANGED_VALUE;
        }

        return APIConstants.Status.NORMAL_CD;
    }
}
