package com.tentinet.weixin.util;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;


public class MyX509TrustManager implements X509TrustManager {

	final static char[] keyPassword = "hello".toCharArray();
	private static int aliascounter = 0;
	protected KeyStore trustedKeys = null;

	public MyX509TrustManager(KeyStore trustedKeys) {
		this.trustedKeys = trustedKeys;
	}

	public MyX509TrustManager() {
	}

	public boolean isClientTrusted(X509Certificate[] chain) {
		System.out.println("DEBUG: MyX509TrustManager.isClientTrusted called");
		return true;
	}

	public boolean isServerTrusted(X509Certificate[] chain) {
		System.out.println("DEBUG: MyX509TrustManager.isServerTrusted called");
		for (int i = 0; i < chain.length; i++) {
			try {
				trustedKeys.setKeyEntry(String.valueOf(aliascounter++),
						chain[i].getPublicKey(), keyPassword, null);
			} catch (KeyStoreException kse) {
			} catch (NullPointerException npe) {
			}
			;
		}
		return true;
	}

	public X509Certificate[] getAcceptedIssuers() {
		X509Certificate[] c = {};
		System.out.println("DEBUG: X509TrustManager.getAcceptedIssuers called");
		return c;
	}

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
		// TODO Auto-generated method stub
		
	}

}
