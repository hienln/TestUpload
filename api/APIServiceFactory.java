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

import co.jp.nissen.api.IAPIService;
import co.jp.nissen.api.APIException;
import co.jp.nissen.api.APIConstants;
import co.jp.nissen.com.SearchUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * APIServiceFactorykurasu�N���X.<br>
 *
 * <pre>
 *   �ڋq�̃��N�G�X�g����Â��AAPI�T�[�r�X���쐬����B
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class APIServiceFactory {

	/** ���N�G�X�g�I�u�W�F�N�g */
    private HttpServletRequest request = null;

	/**
     * �R���X�g���N�^�[.<br>
     *
     * @param request�F���N�G�X�g�I�u�W�F�N�g
	 */
    public APIServiceFactory(HttpServletRequest request){
		this.request = request;
	}

	/**
     * �ڋq�̃��N�G�X�g����Â��AAPI�T�[�r�X���쐬����B.<br>
     *
     * @return�FAPI�T�[�r�X
     * @APIException�𓊂���
	 */
	public IAPIService createAPIService() throws APIException {
		// ���N�G�X�g����v���Z�X�R�[�h�y��SNS�ڋqID���擾����B
		String apiCode = request.getParameter(APIConstants.Request.API_CODE);
        String snsId = request.getParameter(APIConstants.Request.SNS_ID);

        // �f�[�^���`�F�b�N����B
        if (apiCode == null){
            apiCode = APIConstants.EMPTY_STRING;
        }

        if (snsId == null){
            snsId = APIConstants.EMPTY_STRING;
        }

		// �f�[�^���������Ȃ��ꍇ�AException�𓊂���B
        if (APIConstants.EMPTY_STRING.equals(apiCode)) {
			throw new APIException(apiCode, snsId, APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER, "API code not exists in request");
		}
        /*
        if (APIConstants.EMPTY_STRING.equals(snsId)) {
            throw new APIException(apiCode, snsId, APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER, "SNS id not exists in request");
        }*/

		// �v���p�e�B�[�t�@�C������T�[�r�X�N���X����������B
        SearchUtil objSearch = new SearchUtil(APIConstants.PROPERTIES_FILE);
		String serviceName = objSearch.runSearch(APIConstants.PROP_API_CODE + apiCode);

        // �T�[�r�X�������݂��Ȃ��ꍇ�AException�𓊂���B
        if (APIConstants.EMPTY_STRING.equals(serviceName)) {
			throw new APIException(apiCode, snsId, APIConstants.Status.ERR_SYSTEM, "API service have code" + apiCode +  " is not exists in properties file");
		}

        // �v���p�e�B�[�t�@�C�����牞��XML�t�@�C�����擾����B
        String xmlRespFile = objSearch.runSearch(APIConstants.PROP_XML_RESPONSE_FILE + apiCode);

		try {
			// �T�[�r�X�̃C���X�^���X���쐬����B
            Class cls = Class.forName(serviceName.trim());
			IAPIService apiService = (IAPIService) cls.newInstance();

			apiService.setRequest(this.request);
            apiService.setXmlRespFile(xmlRespFile);

			return apiService;
		} catch (ClassNotFoundException e) {
			throw new APIException(apiCode, snsId, APIConstants.Status.ERR_SYSTEM, "APIException: Can't find Class :" + serviceName );
		} catch (IllegalAccessException e) {
			throw new APIException(apiCode, snsId, APIConstants.Status.ERR_SYSTEM, "IllegalAccessException: Don't have right access for Class: " + serviceName);
		} catch (InstantiationException e) {
			throw new APIException(apiCode, snsId, APIConstants.Status.ERR_SYSTEM, "InstantiationException: Can't create API service");
		}
	}
}
