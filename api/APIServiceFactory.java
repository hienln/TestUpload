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
 * APIServiceFactorykurasuクラス.<br>
 *
 * <pre>
 *   顧客のリクエストを基づき、APIサービスを作成する。
 * </pre>
 *
 * @author FPT Dinh Hong Nam
 * @version 1.0
 */
public class APIServiceFactory {

	/** リクエストオブジェクト */
    private HttpServletRequest request = null;

	/**
     * コンストラクター.<br>
     *
     * @param request：リクエストオブジェクト
	 */
    public APIServiceFactory(HttpServletRequest request){
		this.request = request;
	}

	/**
     * 顧客のリクエストを基づき、APIサービスを作成する。.<br>
     *
     * @return：APIサービス
     * @APIExceptionを投げる
	 */
	public IAPIService createAPIService() throws APIException {
		// リクエストからプロセスコード及びSNS顧客IDを取得する。
		String apiCode = request.getParameter(APIConstants.Request.API_CODE);
        String snsId = request.getParameter(APIConstants.Request.SNS_ID);

        // データをチェックする。
        if (apiCode == null){
            apiCode = APIConstants.EMPTY_STRING;
        }

        if (snsId == null){
            snsId = APIConstants.EMPTY_STRING;
        }

		// データが正しくない場合、Exceptionを投げる。
        if (APIConstants.EMPTY_STRING.equals(apiCode)) {
			throw new APIException(apiCode, snsId, APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER, "API code not exists in request");
		}
        /*
        if (APIConstants.EMPTY_STRING.equals(snsId)) {
            throw new APIException(apiCode, snsId, APIConstants.Status.ERR_WRONG_NUMBER_OF_PARMETER, "SNS id not exists in request");
        }*/

		// プロパティーファイルからサービスクラスを検索する。
        SearchUtil objSearch = new SearchUtil(APIConstants.PROPERTIES_FILE);
		String serviceName = objSearch.runSearch(APIConstants.PROP_API_CODE + apiCode);

        // サービス名が存在しない場合、Exceptionを投げる。
        if (APIConstants.EMPTY_STRING.equals(serviceName)) {
			throw new APIException(apiCode, snsId, APIConstants.Status.ERR_SYSTEM, "API service have code" + apiCode +  " is not exists in properties file");
		}

        // プロパティーファイルから応答XMLファイルを取得する。
        String xmlRespFile = objSearch.runSearch(APIConstants.PROP_XML_RESPONSE_FILE + apiCode);

		try {
			// サービスのインスタンスを作成する。
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
