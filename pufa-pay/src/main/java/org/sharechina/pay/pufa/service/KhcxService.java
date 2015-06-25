package org.sharechina.pay.pufa.service;

import java.security.SignatureException;

import org.sharechina.pay.pufa.common.HttpsRequest;
import org.sharechina.pay.pufa.common.HttpsService;
import org.sharechina.pay.pufa.protocal.RequestModel;
import org.sharechina.pay.pufa.protocal.ResponseModel;
import org.sharechina.pay.pufa.protocal.TransName;
import org.sharechina.pay.pufa.protocal.query.QueryRequestData;

/**
 * ���ʲ�ѯ����
 * @author pc
 *
 */
public class KhcxService {

	private HttpsService httpsService=new HttpsRequest();

	public void setHttpsService(HttpsService httpsService) {
		this.httpsService = httpsService;
	}
	
	/**
	 * 
	 * @param mercCode	�̻��ţ�����ʱ���
	 * @param OTranAbbr	ԭ������д
	 * @param termSsn	�����ţ������ظ�
	 */
	public ResponseModel<QueryRequestData> sendKhcxRequest(String mercCode, TransName OTranAbbr, String termSsn)
		throws SignatureException {
		QueryRequestData data=new QueryRequestData(mercCode, OTranAbbr, termSsn);
		RequestModel model=new RequestModel(data);
		String result=httpsService.postXml(RequestModel.PRODUCTION_URL, model.toXml());
		return ResponseModel.fromXml(result, QueryRequestData.class);
	}
}
