package org.sharechina.pay.pufa.protocal.query;

public class QueryResponseData {

	/**������*/
	private String TermSsn;
	/**���׽��(Ԫ)*/
	private Double TranAmt;
	/**
	 * 00-���׳ɹ�
	 * 01-����ʧ��
	 * 02-�����ɹ�
	 * 03-�����˻�
	 * 04-ȫ���˻�
	 * 05-�˻�������
	 * 06-�˻�����ɹ�
	 * 09-֧����
	 * 99-���׳�ʱ
	 */
	private String TransStatus;
	/**��Ӧ��*/
	private String RespCode;
	/**��������*/
	private String ClearDate;
	/**������ˮ*/
	private String AcqSsn;
	
	public String getTermSsn() {
		return TermSsn;
	}
	public void setTermSsn(String termSsn) {
		TermSsn = termSsn;
	}
	public Double getTranAmt() {
		return TranAmt;
	}
	public void setTranAmt(Double tranAmt) {
		TranAmt = tranAmt;
	}
	public String getTransStatus() {
		return TransStatus;
	}
	public void setTransStatus(String transStatus) {
		TransStatus = transStatus;
	}
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public String getClearDate() {
		return ClearDate;
	}
	public void setClearDate(String clearDate) {
		ClearDate = clearDate;
	}
	public String getAcqSsn() {
		return AcqSsn;
	}
	public void setAcqSsn(String acqSsn) {
		AcqSsn = acqSsn;
	}
}
