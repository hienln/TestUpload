package co.jp.nissen.api;

import javax.servlet.http.HttpServletRequest;

import co.jp.nissen.api.APIException;

public interface IAPIService {

	public String run() throws APIException;

	public void setRequest(HttpServletRequest request);

    public void setXmlRespFile(String xmlFileName);

}
