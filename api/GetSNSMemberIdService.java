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
 * Create API�iReturn SNS customer or not base on mail address
 * </pre>
 * 
 * @author NhuNTQ
 * @version 1.0 
 */
public class GetSNSMemberIdService extends APIBaseService {
	
	/** �V���A���o�[�W����UID */
    private static final long serialVersionUID = -6954809116335805767L;
    
    /** �^�C���A�E�g */
    private  String timeOut = null;

    /** MD5�̃G���R�[�h�p�R�[�h */
    private  String codeKey = null;

    /**
     * �f�t�H���g�R���X�g���N�^�[.<br>
     */
    public GetSNSMemberIdService() {
        // �������Ȃ�
    }

    /**
     * <pre>
     *  DB����SNSMemberId���擾���A�p�����[�^����Â��Axml��������쐬����
     * </pre>
     *
     * @param apiCode�F�����R�[�h
     * @param email�F���[���A�h���X
     * @param sendTime �F�f�[�^���M���鎞��
     * @param hashCode �F�Z�L�����e�B�[�R�[�h
     * @return �FSNSMemberId��XML�t�H�[�}�b�g�ŋL�q�����B
     */
    protected IXmlData process() throws APIException {

        String apiCode = request.getParameter(APIConstants.Request.API_CODE);
        String email = request.getParameter(APIConstants.Request.EMAIL_ADDRESS);
        String sendTime = request.getParameter(APIConstants.Request.SEND_TIME);
        String hashCode = request.getParameter(APIConstants.Request.HASH_CODE);
        
        // �p�����[�^���`�F�b�N���A�G���[�X�e�[�^�X���擾����B*/        
        String status = validateParameters(apiCode, email, sendTime, hashCode);
        XMLContent getSNSMemberData = null;
        String snsId = APIConstants.EMPTY_STRING;

        // �p�����[�^�ɃG���[�����݂��Ă��Ȃ��ꍇ�ADB���烁�[���̏����擾����B
        if (APIConstants.Status.NORMAL_CD.equals(status)) {
            // ���[���̏����擾����B
            SNSDb snsDb = new SNSDb();
            snsId = snsDb.getSNSMemberId(email);
            snsDb.close();
            // DB����f�[�^�擾�����̓f�[�^�������Ȃ��ꍇ�ɃG���[����������B
            if (snsId == null) {
            	
            	// �V�X�e���G���[���Z�b�g�����B
                status = APIConstants.Status.ERR_SYSTEM;
                SNSLog log = new SNSLog();
            	// ���O���������ށB
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
     * �p�����[�^���`�F�b�N����.<br>
     *
     * <pre>
     * </pre>
     *
     * @param apiCode�F�����R�[�h
     * @param email �F���[���A�h���X 
     * @param sendTime�F���M����
     * @param hashCode �F�Z�L�����e�B�[�R�[�h
     * @return �F�G���[�X�e�[�^�X
     */
    private String validateParameters(String apiCode, String email,
            String sendTime, String hashCode) {
        // ������
        SearchUtil objSearch = new SearchUtil(APIConstants.PROPERTIES_FILE);
        timeOut = objSearch.runSearch(APIConstants.PARAM_TIME_OUT);
        if (APIConstants.EMPTY_STRING.equals(timeOut)) {
               return APIConstants.Status.ERR_SYSTEM;
        }

        // �p�����[�^�̐����`�F�b�N����.
        if (apiCode == null || APIConstants.EMPTY_STRING.equals(apiCode)
                || sendTime == null
                || APIConstants.EMPTY_STRING.equals(sendTime) || email == null
                || APIConstants.EMPTY_STRING.equals(email) || hashCode == null
                || APIConstants.EMPTY_STRING.equals(hashCode)) {
            return APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER;
        }

        // �L�[���[�h���`�F�b�N����
        KeyWordCheck kwc = new KeyWordCheck();
        if (!kwc.chkKeyWord(apiCode) || !kwc.chkKeyWord(email)
                || !kwc.chkKeyWord(sendTime) || !kwc.chkKeyWord(hashCode)) {
            return APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER;
        }

        // �������Ԃ��`�F�b�N���A���Ԃ�5��������ꍇ�AstrErrorCode = E001�Ƃ���.
        if (CommonDate.getMinutesBetween(sendTime, CommonDate
                .getSysDate(APIConstants.DATE_TIME_FORMATE)) > CommonString
                ._parseInt(timeOut)) {
            return APIConstants.Status.ERR_TIME_OUT;
        }

        codeKey = objSearch.runSearch(APIConstants.PARAM_CODE_KEY);

        if (APIConstants.EMPTY_STRING.equals(codeKey)) {
               return APIConstants.Status.ERR_SYSTEM;
        }

        // �p�����[�^�̒l���`�F�b�N���A�ύX�����ꍇ�AErrorCode = E002�Ƃ���
        if (!hashCode.equals(MD5.MD5Encode(apiCode + sendTime + codeKey))) {
            return APIConstants.Status.ERR_CHANGED_VALUE;
        }

        return APIConstants.Status.NORMAL_CD;
    }
}
