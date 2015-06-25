package org.sharechina.pay.pufa.protocal.refund;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.sharechina.pay.pufa.protocal.PlainData;
import org.sharechina.pay.pufa.protocal.RequestData;
import org.sharechina.pay.pufa.protocal.TransName;

/**�����˻��������*/
public class RefundRequestData implements RequestData {
	
	private KhthPlainData plainData;

	@Override
	public TransName getTransName() {
		return TransName.KHTH;
	}

	@Override
	public PlainData getPlainData() {
		return plainData;
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
	public RefundRequestData(String masterId, String termSsn, String osttDate, String oacqSsn, String mercCode,
				String termCode, Double tranAmt, String remark1, String remark2) {
		KhthPlainData data=new KhthPlainData(masterId, termSsn, osttDate, oacqSsn, mercCode, termCode, tranAmt,
				remark1, remark2);
		this.plainData=data;
	}
	
	public static class KhthPlainData implements PlainData {
		
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
		public KhthPlainData(String masterId, String termSsn, String osttDate, String oacqSsn, String mercCode,
				String termCode, Double tranAmt, String remark1, String remark2) {
			this.MasterId=masterId;
			this.TermSsn=termSsn;
			this.OSttDate=osttDate;
			this.OAcqSsn=oacqSsn;
			this.MercCode=mercCode;
			if(termCode!=null) {
				this.TermCode=termCode;
			}			
			this.TranAmt=tranAmt;
			this.Remark1=remark1;
			this.Remark2=remark2;
		}
		
		/**������д*/
		private final TransName TranAbbr=TransName.KHTH;
		/**��ҵ�ͻ��ţ���ҵ�ͻ�֧��ʱ�����ṩ�����˿ͻ�֧��ʱ����Ҫ*/
		private String MasterId;
		/**�̻�����ʱ�䣬yyyyMMddhhMMss*/
		private LocalDateTime MercDtTm=LocalDateTime.now();
		/**�����ţ������ظ�*/
		private String TermSsn;
		/**ԭ���׵��������ڣ���֧���ص�����м�¼*/
		private String OSttDate;
		/**ԭ���׵�������ˮ����֧���ص�����м�¼*/
		private String OAcqSsn;
		/**�̻��ţ�����ʱ���*/
		private String MercCode;		
		/**�ն˺ţ�����ȫΪ0*/
		private String TermCode="00000000";
		/**���׽���λΪԪ*/
		private Double TranAmt;
		/**���ױ�ע1�����ױ�עһ�����ֶν��ڶ����ļ����ṩ*/
		private String Remark1;
		/**���ױ�ע2�����ױ�ע�������ֶν��ڶ����ļ����ṩ*/
		private String Remark2;
		
		public TransName getTranAbbr() {
			return TranAbbr;
		}
		public String getMasterId() {
			return MasterId;
		}
		public String getMercDtTm() {
			return MercDtTm.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss"));
		}
		public String getTermSsn() {
			return TermSsn;
		}
		public String getOSttDate() {
			return OSttDate;
		}
		public String getOAcqSsn() {
			return OAcqSsn;
		}
		public String getMercCode() {
			return MercCode;
		}
		public String getTermCode() {
			return TermCode;
		}
		public Double getTranAmt() {
			return TranAmt;
		}
		public String getRemark1() {
			return Remark1;
		}
		public String getRemark2() {
			return Remark2;
		}		
	}

}
