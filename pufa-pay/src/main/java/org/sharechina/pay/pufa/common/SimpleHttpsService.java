package org.sharechina.pay.pufa.common;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.X509TrustManager;

/**
 * ����post����
 * @author pc
 *
 */
public class SimpleHttpsService implements HttpsService {
	
	private static final CloseableHttpClient HTTPS_CLIENT;
	static {
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			//����https֤�����
			//TODO ����֤����֤����
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
		         public X509Certificate[] getAcceptedIssuers() {
		                 return null;
		         }
		
		         public void checkClientTrusted(X509Certificate[] certs,
		                         String authType) {
		         }
		
		         public void checkServerTrusted(X509Certificate[] certs,
		                         String authType) {
		         }
			}},new SecureRandom());
			SSLConnectionSocketFactory socketFactory=new SSLConnectionSocketFactory(sslContext);
			HTTPS_CLIENT = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����xml post����
	 * @param url
	 * @param xml
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postXml(String url, String xml) {
		try {
			HttpPost httpPost = new HttpPost(url);
			//��ָ��ʹ��GBK���룬����API������XML�����Ĳ��ܱ��ɹ�ʶ��
		    StringEntity postEntity = new StringEntity(xml, "GBK");
	        httpPost.addHeader("Content-Type", "text/xml");
	        httpPost.setEntity(postEntity);
	        HttpResponse response = HTTPS_CLIENT.execute(httpPost);
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "GBK");
	        return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
