package org.sharechina.pay.pufa.protocal.pay;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.sharechina.pay.pufa.protocal.PlainData;
import org.sharechina.pay.pufa.protocal.RequestData;
import org.sharechina.pay.pufa.protocal.TransName;

public class KhzfRequestData implements RequestData {

	private KhzfPlainData plainData;

	public TransName getTransName() {
		return TransName.KHZF;
	}
	public KhzfPlainData getPlainData() {
		return plainData;
	}
	
	/**
	 * 
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
	public KhzfRequestData(String MasterId, String TermSsn, String MercCode, String TermCode, Double TranAmt,
				PayBank PayBank, AccountType AccountType, PayType PayType, String Subject, String Notice,
				String Remark1, String Remark2, URL MercUrl, String Ip) {
		KhzfPlainData data=new KhzfPlainData(MasterId, TermSsn, MercCode, TermCode, TranAmt, PayBank, 
				AccountType, PayType, Subject, Notice, Remark1, Remark2, MercUrl, Ip);
		this.plainData=data;
	}

	public static class KhzfPlainData implements PlainData {		
		/**
		 * 
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
		public KhzfPlainData(String MasterId, String TermSsn, String MercCode, String TermCode, Double TranAmt,
				PayBank PayBank, AccountType AccountType, PayType PayType, String Subject, String Notice,
				String Remark1, String Remark2, URL MercUrl, String Ip) {
			this.MasterId=MasterId;
			this.TermSsn=TermSsn;
			this.MercCode=MercCode;
			this.TermCode=TermCode;
			if(StringUtils.isBlank(TermCode)) {
				this.TermCode="00000000";
			}
			this.TranAmt=TranAmt;
			this.PayBank=PayBank;
			this.AccountType=AccountType;
			this.PayType=PayType;
			this.Subject=Subject;
			this.Notice=Notice;
			this.Remark1=Remark1;
			this.Remark2=Remark2;
			this.MercUrl=MercUrl;
			this.Ip=Ip;
		}
		
		
		/**������д*/
		private TransName TranAbbr=TransName.KHZF;
		/**�̻�����ʱ�䣬yyyyMMddhhMMss*/
		private LocalDateTime MercDtTm=LocalDateTime.now();
		/**��ҵ�ͻ��ţ���ҵ�ͻ�֧��ʱ�����ṩ�����˿ͻ�֧��ʱ����Ҫ*/
		private String MasterId;		
		/**�����ţ������ظ�*/
		private String TermSsn;
		/**�̻��ţ�����ʱ���*/
		private String MercCode;
		/**�ն˺ţ�����ȫΪ0*/
		private String TermCode;
		/**���׽���λΪԪ*/
		private Double TranAmt;
		/**֧������*/
		private PayBank PayBank;
		/**�˻�����*/
		private AccountType AccountType;
		/**֧������*/
		private PayType PayType;
		/**��Ʒ���ƣ���Ʒ�ı���/���ױ���/��������/�����ؼ��ֵȡ��ò����Ϊ128�����֡�*/
		private String Subject;
		/**��Ʒ��������һ�ʽ��׵ľ���������Ϣ������Ƕ�����Ʒ���뽫��Ʒ�����ַ����ۼӴ���body��*/
		private String Notice;
		/**���ױ�ע1�����ױ�עһ�����ֶν��ڶ����ļ����ṩ*/
		private String Remark1;
		/**���ױ�ע2�����ױ�ע�������ֶν��ڶ����ļ����ṩ*/
		private String Remark2;
		/**֧�������У����ս��׽����url;�������Ϊ�գ����׽�����͵��̻��ڹ���ǩԼ��ʱ����д�Ľ��ս����url������ͻ��͵��õ�ַ�С�*/
		private URL MercUrl;
		/**��������ʱ��IP��ַ���������Ϊ�գ��򲻽���IP��ַ��顣*/
		private String Ip;
		
		public TransName getTranAbbr() {
			return TranAbbr;
		}		
		public String getMercDtTm() {
			return MercDtTm.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss"));
		}
		public String getMasterId() {
			return MasterId;
		}
		public String getTermSsn() {
			return TermSsn;
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
		public PayBank getPayBank() {
			return PayBank;
		}
		public AccountType getAccountType() {
			return AccountType;
		}
		public PayType getPayType() {
			return PayType;
		}
		public String getSubject() {
			return Subject;
		}
		public String getNotice() {
			return Notice;
		}
		public String getRemark1() {
			return Remark1;
		}
		public String getRemark2() {
			return Remark2;
		}
		public URL getMercUrl() {
			return MercUrl;
		}
		public String getIp() {
			return Ip;
		}
	}
}
