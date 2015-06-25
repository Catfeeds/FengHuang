package org.sharechina.pay.pufa.service;

import java.security.SignatureException;

import org.sharechina.pay.pufa.common.HttpsRequest;
import org.sharechina.pay.pufa.common.HttpsService;
import org.sharechina.pay.pufa.protocal.RequestModel;
import org.sharechina.pay.pufa.protocal.ResponseModel;
import org.sharechina.pay.pufa.protocal.refund.RefundRequestData;
import org.sharechina.pay.pufa.protocal.refund.RefundResponseData;

/**
 * �����˻�����
 * @author pc
 *
 */
public class KhthService {

	private HttpsService httpsService=new HttpsRequest();

	public void setHttpsService(HttpsService httpsService) {
		this.httpsService = httpsService;
	}
	
	/**
	 * 
	 * @param masterId	��ҵ�ͻ��ţ���ҵ�ͻ�֧��ʱ�����ṩ�����˿ͻ�֧��ʱ����Ҫ
	 * @param termSsn	�����ţ������ظ�
	 * @param osttDate	ԭ���׵��������ڣ���֧���ص�����м�¼
	 * @param oacqSsn	ԭ���׵�������ˮ����֧���ص�����м�¼
	 * @param mercCode	�̻��ţ�����ʱ���
	 * @param termCode	�ն˺ţ�����ȫΪ0���Ѿ��ṩ��ȫΪ0��Ĭ��ֵ
	 * @param tranAmt	���׽���λΪԪ
	 * @param remark1	���ױ�ע1�����ױ�עһ�����ֶν��ڶ����ļ����ṩ
	 * @param remark2	���ױ�ע2�����ױ�ע�������ֶν��ڶ����ļ����ṩ
	 */
	public ResponseModel<RefundResponseData> sendKhthRequest(String masterId, String termSsn, String osttDate, String oacqSsn, 
			String mercCode, String termCode, Double tranAmt, String remark1, String remark2)
			throws SignatureException {
		RefundRequestData data=new RefundRequestData(masterId, termSsn, osttDate, oacqSsn, mercCode, termCode,
				tranAmt, remark1, remark2);
		RequestModel model=new RequestModel(data);
		String result=httpsService.postXml(RequestModel.TEST_URL, model.toXml());
		ResponseModel<RefundResponseData> response=ResponseModel.fromXml(result, RefundResponseData.class);
		return response;
	}
}
