package co.jp.nissen.api;

import javax.servlet.http.HttpServletRequest;
import co.jp.nissen.com.xml.IXmlData;
import co.jp.nissen.api.IXmlResponse;

public abstract class  APIBaseService implements IAPIService{

    /** ���N�G�X�g�I�u�W�F�N�g */
    protected HttpServletRequest request = null;

    /** ����XML�t�@�C�� */
    private String xmlRespFile = null;

    /**
     * �f�t�H���g�R���X�g���N�^�[.<br>
     */
    public APIBaseService() {
        // �������Ȃ�
    }

    /**
     * �r�W�l�X���\�b�h.<br>
     *
     * <pre>
     * ���̃��\�b�h���N���C�G���g����v�����������A����XML���쐬����̂Ɏg�p�����B 
     * </pre>
     *
     * @���s���ɃG���[�����������ꍇ�AAPIException�𓊂���B
     */
    public String run() throws APIException{
        IXmlData xmlData = process();
        IXmlResponse xmlResponse = new XmlResponse(xmlRespFile);
        xmlResponse.addXmlData(xmlData);
        return xmlResponse.getXmlResponse();
    }

    /**
     * ���N�G�X�g�Z�b�g.<br>
     *
     * @param request�F���N�G�X�g�I�u�W�F�N�g
     */
    public void setRequest(HttpServletRequest request ){
        this.request = request;
    }

    /**
     * ����XML�t�@�C�����Z�b�g����.<br>
     *
     * @param xmlFile�FXML�t�H�[�}�b�g�t�@�C��
     */
    public void setXmlRespFile(String xmlFile){
        this.xmlRespFile = xmlFile;
    }

    /**
     * ���̃��\�b�h���T�u�N���X���Ŏ��������.<br>
     *
     * @XML�f�[�^��ԋp����
     * @APIException�𓊂���B
     */
    protected abstract IXmlData process() throws APIException;
}
