package org.sharechina.pay.pufa.protocal;

import java.security.SignatureException;

import org.sharechina.pay.pufa.common.XmlUtil;

import com.csii.payment.client.core.MerchantSignVerify;

/**
 * �ַ����صı�׼��ʽ���ݣ���װ��ȷ�ʹ������ָ�ʽ
 * @author pc
 *
 */
public class ResponseModel<T> {
	
	private boolean success;
	
	/**
	 * �Ƿ��ǳɹ�����
	 * if true, ���Ի�ȡtransName,Plain,Signature,data
	 * if false, ��ȡErrorCode,ErrorMsg
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}
	
	/**������Ϣ����ʱ����*/
	private String ErrorCode;
	private String ErrorMsg;
	
	/**������Ϣ��ȷʱ����*/
	private TransName transName;
	private String Plain;
	private String Signature;
	private T data;
	
	/**
	 * �ӷ���xml�����н������ؽ��
	 * @param body
	 * @return
	 */
	public static <T> ResponseModel<T> fromXml(String xml, Class<T> tClass) throws SignatureException {
		@SuppressWarnings("unchecked")
		ResponseModel<T> response=XmlUtil.getObjectFromXml(xml, ResponseModel.class, "packet", "GBK");
		if(response.getErrorCode()==null) {
			response.success(tClass);
			return response;
		}
		response.fail();
		return response;
	}
	
	private ResponseModel(){}
	
	private void success(Class<T> tClass) throws SignatureException {
		this.success=true;
		boolean isSignSuccess=MerchantSignVerify.merchantVerifyPayGate_ABA(Signature, Plain);
		if(!isSignSuccess)throw new SignatureException();
		data=PlainData.fromPlain(tClass, Plain);		
	}
	
	private void fail() {
		this.success=false;
	}
	
	public String getErrorCode() {
		return ErrorCode;
	}
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public TransName getTransName() {
		return transName;
	}
	public String getPlain() {
		return Plain;
	}
	public String getSignature() {
		return Signature;
	}
	public T getData() {
		return data;
	}
}
