/*
 * GetSNSMemberIdService.java
 *
 * Version 1.0
 *
 * Copyright 2008 FPT-Software, All rights reserved
 *
 * Modification Logs:
 *  DATE         AUTHOR            DESCRIPTION
 *  --------------------------------------------------
 *  14-01-2009   NhuNTQ        First created
 */
package co.jp.nissen.api;

import co.jp.nissen.com.CommonDate;
import co.jp.nissen.com.CommonString;
import co.jp.nissen.com.KeyWordCheck;
import co.jp.nissen.com.MD5;
import co.jp.nissen.com.SearchUtil;
import co.jp.nissen.com.xml.IXmlData;
import co.jp.nissen.sns.SNSDb;
import co.jp.nissen.sns.SNSLog;

/**  
 * <pre>
 * Create API（Return SNS customer or not base on mail address
 * </pre>
 * 
 * @author NhuNTQ
 * @version 1.0 
 */
public class GetSNSMemberIdService extends APIBaseService {
	
	/** シリアルバージョンUID */
    private static final long serialVersionUID = -6954809116335805767L;
    
    /** タイムアウト */
    private  String timeOut = null;

    /** MD5のエンコード用コード */
    private  String codeKey = null;

    /**
     * デフォルトコンストラクター.<br>
     */
    public GetSNSMemberIdService() {
        // 何もしない
    }

    /**
     * <pre>
     *  DBからSNSMemberIdを取得し、パラメータを基づき、xml文字列を作成する
     * </pre>
     *
     * @param apiCode：処理コード
     * @param email：メールアドレス
     * @param sendTime ：データ送信する時刻
     * @param hashCode ：セキュリティーコード
     * @return ：SNSMemberIdがXMLフォーマットで記述される。
     */
    protected IXmlData process() throws APIException {

        String apiCode = request.getParameter(APIConstants.Request.API_CODE);
        String email = request.getParameter(APIConstants.Request.EMAIL_ADDRESS);
        String sendTime = request.getParameter(APIConstants.Request.SEND_TIME);
        String hashCode = request.getParameter(APIConstants.Request.HASH_CODE);
        
        // パラメータをチェックし、エラーステータスを取得する。*/        
        String status = validateParameters(apiCode, email, sendTime, hashCode);
        XMLContent getSNSMemberData = null;
        String snsId = APIConstants.EMPTY_STRING;

        // パラメータにエラーが存在していない場合、DBからメールの情報を取得する。
        if (APIConstants.Status.NORMAL_CD.equals(status)) {
            // メールの情報を取得する。
            SNSDb snsDb = new SNSDb();
            snsId = snsDb.getSNSMemberId(email);
            snsDb.close();
            // DBからデータ取得中又はデータが見つけない場合にエラーが発生する。
            if (snsId == null) {
            	
            	// システムエラーがセットされる。
                status = APIConstants.Status.ERR_SYSTEM;
                SNSLog log = new SNSLog();
            	// ログを書き込む。
                log.open();
                log
                        .write("XMLSNSMemberId"
                                + ": System Error !!! Can't find SNSMember of email: "
                                + email);
                log.close();
                
                throw new APIException(apiCode, "", status, "System Error");
                
            } else  {
            	getSNSMemberData = new XMLContent(APIConstants.XML.MAIL_INFO_DATA_ROOT_NAME);
                getSNSMemberData.setValue(APIConstants.XML.API_CODE_KEY, apiCode);
                getSNSMemberData.setValue(APIConstants.XML.SNS_ID_KEY, snsId);
                getSNSMemberData.setValue(APIConstants.XML.STATUS_KEY, status);
            }
        } else {
            throw new APIException(apiCode, snsId, status,
                    "Parameters is not correct");
        }

        return getSNSMemberData;
    }

    /**
     * パラメータをチェックする.<br>
     *
     * <pre>
     * </pre>
     *
     * @param apiCode：処理コード
     * @param email ：メールアドレス 
     * @param sendTime：送信時刻
     * @param hashCode ：セキュリティーコード
     * @return ：エラーステータス
     */
    private String validateParameters(String apiCode, String email,
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
                || APIConstants.EMPTY_STRING.equals(sendTime) || email == null
                || APIConstants.EMPTY_STRING.equals(email) || hashCode == null
                || APIConstants.EMPTY_STRING.equals(hashCode)) {
            return APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER;
        }

        // キーワードをチェックする
        KeyWordCheck kwc = new KeyWordCheck();
        if (!kwc.chkKeyWord(apiCode) || !kwc.chkKeyWord(email)
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
