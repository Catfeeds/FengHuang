package org.sharechina.pay.pufa.service;

import java.net.URL;
import java.security.SignatureException;

import org.sharechina.pay.pufa.protocal.RequestModel;
import org.sharechina.pay.pufa.protocal.ResponseModel;
import org.sharechina.pay.pufa.protocal.pay.AccountType;
import org.sharechina.pay.pufa.protocal.pay.KhzfRequestData;
import org.sharechina.pay.pufa.protocal.pay.KhzfResponseData;
import org.sharechina.pay.pufa.protocal.pay.PayBank;
import org.sharechina.pay.pufa.protocal.pay.PayType;

public class KhzfService {

	/**
	 * ����Ҫ����ת��Ϊ��ֱ��ʹ�õ�֧������
	 * @param MasterId		��ҵ�ͻ��ţ���ҵ�ͻ�֧��ʱ�����ṩ�����˿ͻ�֧��ʱ����Ҫ
	 * @param TermSsn		�����ţ������ظ�
	 * @param MercCode		�̻��ţ�����ʱ���
	 * @param TermCode		�ն˺ţ�����ȫΪ0
	 * @param TranAmt		���׽���λΪԪ
	 * @param PayBank		֧������
	 * @param AccountType	�˻�����
	 * @param PayType		֧������
	 * @param Subject		��Ʒ���ƣ���Ʒ�ı���/���ױ���/��������/�����ؼ��ֵȡ��ò����Ϊ128�����֡�
	 * @param Notice		��Ʒ��������һ�ʽ��׵ľ���������Ϣ������Ƕ�����Ʒ���뽫��Ʒ�����ַ����ۼӴ���body��
	 * @param Remark1		���ױ�ע1�����ױ�עһ�����ֶν��ڶ����ļ����ṩ
	 * @param Remark2		���ױ�ע2�����ױ�ע�������ֶν��ڶ����ļ����ṩ
	 * @param MercUrl		֧�������У����ս��׽����url;�������Ϊ�գ����׽�����͵��̻��ڹ���ǩԼ��ʱ����д�Ľ��ս����url������ͻ��͵��õ�ַ�С�
	 * @param Ip			��������ʱ��IP��ַ���������Ϊ�գ��򲻽���IP��ַ��顣
	 */
	public static RequestModel getKhzfRequestData(String MasterId, String TermSsn, String MercCode, String TermCode, Double TranAmt,
			PayBank PayBank, AccountType AccountType, PayType PayType, String Subject, String Notice,
			String Remark1, String Remark2, URL MercUrl, String Ip) {
		KhzfRequestData data=new KhzfRequestData(MasterId, TermSsn, MercCode, TermCode, TranAmt, PayBank, 
				AccountType, PayType, Subject, Notice, Remark1, Remark2, MercUrl, Ip);
		RequestModel model=new RequestModel(data);
		return model;
	}
	
	public static ResponseModel<KhzfResponseData> resolveFromXml(String xml) throws SignatureException {
		return ResponseModel.fromXml(xml, KhzfResponseData.class);
	}
}
