package co.jp.nissen.api;

import javax.servlet.http.HttpServletRequest;
import co.jp.nissen.com.xml.IXmlData;
import co.jp.nissen.api.IXmlResponse;

public abstract class  APIBaseService implements IAPIService{

    /** リクエストオブジェクト */
    protected HttpServletRequest request = null;

    /** 応答XMLファイル */
    private String xmlRespFile = null;

    /**
     * デフォルトコンストラクター.<br>
     */
    public APIBaseService() {
        // 何もしない
    }

    /**
     * ビジネスメソッド.<br>
     *
     * <pre>
     * このメソッドがクライエントから要求を処理し、応答XMLを作成するのに使用される。 
     * </pre>
     *
     * @実行中にエラーが発生した場合、APIExceptionを投げる。
     */
    public String run() throws APIException{
        IXmlData xmlData = process();
        IXmlResponse xmlResponse = new XmlResponse(xmlRespFile);
        xmlResponse.addXmlData(xmlData);
        return xmlResponse.getXmlResponse();
    }

    /**
     * リクエストセット.<br>
     *
     * @param request：リクエストオブジェクト
     */
    public void setRequest(HttpServletRequest request ){
        this.request = request;
    }

    /**
     * 応答XMLファイルをセットする.<br>
     *
     * @param xmlFile：XMLフォーマットファイル
     */
    public void setXmlRespFile(String xmlFile){
        this.xmlRespFile = xmlFile;
    }

    /**
     * このメソッドがサブクラス内で実装される.<br>
     *
     * @XMLデータを返却する
     * @APIExceptionを投げる。
     */
    protected abstract IXmlData process() throws APIException;
}
