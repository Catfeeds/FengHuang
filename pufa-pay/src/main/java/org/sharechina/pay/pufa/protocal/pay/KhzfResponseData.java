package org.sharechina.pay.pufa.protocal.pay;

import org.sharechina.pay.pufa.protocal.PlainData;

public class KhzfResponseData {
	
	public static KhzfResponseData fromPlain(String plain) {
		return PlainData.fromPlain(KhzfResponseData.class, plain);
	}

	/**������д*/
	private String TranAbbr;
	/**������ˮ��Ҫ���¼��ֵ���������˻��Ƚ�����Ҫ���ֶ�*/
	private String AcqSsn;
	/**�̻�����ʱ��*/
	private String MercDtTm;
	/**������*/
	private String TermSsn;
	/**��Ӧ��*/
	private String RespCode;
	/**�ն˺�*/
	private String TermCode;
	/**�̻���*/
	private String MercCode;
	/**���׽��(Ԫ)*/
	private Double TranAmt;
	/**�������ڣ�Ҫ���¼��ֵ���������˻��Ƚ�����Ҫ���ֶ�*/
	private String ClearDate;
	
	public String getTranAbbr() {
		return TranAbbr;
	}
	public void setTranAbbr(String tranAbbr) {
		TranAbbr = tranAbbr;
	}
	public String getAcqSsn() {
		return AcqSsn;
	}
	public void setAcqSsn(String acqSsn) {
		AcqSsn = acqSsn;
	}
	public String getMercDtTm() {
		return MercDtTm;
	}
	public void setMercDtTm(String mercDtTm) {
		MercDtTm = mercDtTm;
	}
	public String getTermSsn() {
		return TermSsn;
	}
	public void setTermSsn(String termSsn) {
		TermSsn = termSsn;
	}
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public String getTermCode() {
		return TermCode;
	}
	public void setTermCode(String termCode) {
		TermCode = termCode;
	}
	public String getMercCode() {
		return MercCode;
	}
	public void setMercCode(String mercCode) {
		MercCode = mercCode;
	}
	public Double getTranAmt() {
		return TranAmt;
	}
	public void setTranAmt(Double tranAmt) {
		TranAmt = tranAmt;
	}
	public String getClearDate() {
		return ClearDate;
	}
	public void setClearDate(String clearDate) {
		ClearDate = clearDate;
	}
}
