package org.sharechina.pay.pufa.protocal.query;

import org.sharechina.pay.pufa.protocal.PlainData;
import org.sharechina.pay.pufa.protocal.RequestData;
import org.sharechina.pay.pufa.protocal.TransName;

/**
 * ���ʲ�ѯ
 * @author pc
 *
 */
public class QueryRequestData implements RequestData {
	
	private KhcxPlainData plainData;

	@Override
	public TransName getTransName() {
		return TransName.KHCX;
	}

	@Override
	public PlainData getPlainData() {
		return plainData;
	}
	
	/**
	 * 
	 * @param mercCode	�̻��ţ�����ʱ���
	 * @param OTranAbbr	ԭ������д
	 * @param termSsn	�����ţ������ظ�
	 */
	public QueryRequestData(String mercCode, TransName OTranAbbr, String termSsn) {
		plainData=new KhcxPlainData(mercCode, OTranAbbr, termSsn);
	}
	
	public static class KhcxPlainData implements PlainData {
		
		/**
		 * 
		 * @param mercCode	�̻��ţ�����ʱ���
		 * @param OTranAbbr	ԭ������д
		 * @param termSsn	�����ţ������ظ�
		 */
		public KhcxPlainData(String mercCode, TransName OTranAbbr, String termSsn) {
			this.MercCode=mercCode;
			this.OTranAbbr=OTranAbbr;
			this.TermSsn=termSsn;
		}
		
		/**�̻��ţ�����ʱ���*/
		private String MercCode;
		/**ԭ������д*/
		private TransName OTranAbbr;
		/**�����ţ������ظ�*/
		private String TermSsn;
		public String getMercCode() {
			return MercCode;
		}
		public TransName getOTranAbbr() {
			return OTranAbbr;
		}
		public String getTermSsn() {
			return TermSsn;
		}
	}
}
