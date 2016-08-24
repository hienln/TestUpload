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
 * XMLMailInfo�N���X.<br>
 *
 * <pre>
 *   ���̃N���X�́A�ڋq�̃��[�����ixml�t�H�[�}�b�g�j���쐬����̂Ɏg�p�����B
 * </pre>
 *
 * @author FPT Mai Ba Duy
 * @version 1.0
 */
public class MailInfoService extends APIBaseService {

    /** �V���A���o�[�W����UID */
    private static final long serialVersionUID = -6954809116335805767L;

    /** �^�C���A�E�g */
    private  String timeOut = null;

    /** MD5�̃G���R�[�h�p�R�[�h */
    private  String codeKey = null;

    /**
     * �f�t�H���g�R���X�g���N�^�[.<br>
     */
    public MailInfoService() {
        // �������Ȃ�
    }

    /**
     * xml���[���̏����쐬����.<br>
     *
     * <pre>
     *  DB����ڋq�̃��[�����擾���A�p�����[�^����Â��Axml��������쐬����
     * </pre>
     *
     * @param apiCode�F�����R�[�h
     * @param snsId�FSNS���ID
     * @param sendTime �F�f�[�^���M���鎞��
     * @param hashCode �F�Z�L�����e�B�[�R�[�h
     * @return �F���[���̏��XML�t�H�[�}�b�g�ŋL�q�����B
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
        
        // �p�����[�^���`�F�b�N���A�G���[�X�e�[�^�X���擾����B*/        
        String status = validateParameters(apiCode, snsId, sendTime, hashCode);
        XMLContent mailInfoData = null;

        // �p�����[�^�ɃG���[�����݂��Ă��Ȃ��ꍇ�ADB���烁�[���̏����擾����B
        if (APIConstants.Status.NORMAL_CD.equals(status)) {
            // ���[���̏����擾����B
            SNSDb snsDb = new SNSDb();
            MailInfo mailInfo = snsDb.getMailInfo(snsId);

            // DB����f�[�^�擾�����̓f�[�^�������Ȃ��ꍇ�ɃG���[����������B
            if (mailInfo == null) {
                // �V�X�e���G���[���Z�b�g�����B

                status = APIConstants.Status.ERR_SYSTEM;

                // ���O���������ށB
                log.open();
                log
                        .write("XMLMailInfo"
                                + ": System Error !!! Can't find MailInfo of SNSMemberID: "
                                + snsId);
                log.close();
            } else if (!mailInfo.exists()) {
                // SNS������`�F�b�N����B��������݂��Ȃ��ꍇ�AErrorCode = E101�Ƃ���.
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
     * �p�����[�^���`�F�b�N����.<br>
     *
     * <pre>
     * </pre>
     *
     * @param apiCode�F�����R�[�h
     * @param snsId �FSNS���ID
     * @param sendTime�F���M����
     * @param hashCode �F�Z�L�����e�B�[�R�[�h
     * @return �F�G���[�X�e�[�^�X
     */
    private String validateParameters(String apiCode, String snsId,
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
                || APIConstants.EMPTY_STRING.equals(sendTime) || snsId == null
                || APIConstants.EMPTY_STRING.equals(snsId) || hashCode == null
                || APIConstants.EMPTY_STRING.equals(hashCode)) {
            return APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER;
        }

        // �L�[���[�h���`�F�b�N����
        KeyWordCheck kwc = new KeyWordCheck();
        if (!kwc.chkKeyWord(apiCode) || !kwc.chkKeyWord(snsId)
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
