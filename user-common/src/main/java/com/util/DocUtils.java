package com.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Class Name:DocUtils Description: 获取设备本身的参数<br>
 * Create Date: 2015-08-17 10:18:18 <br/>
 * 
 * @author Chnjie He
 * @version 1.0
 * @since 1.7
 */
public class DocUtils {

	/**
	 * 获取主机名称
	 * @return
	 */
	public static String getHostName() {
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return address.getHostName();
	}

	/**
	 * 获取MAC地址
	 * 
	 * @return
	 */
	public static String getMacAddress() {

		try {
			Enumeration<NetworkInterface> network = NetworkInterface.getNetworkInterfaces();
			while (network.hasMoreElements()) {
				NetworkInterface netinter = (NetworkInterface) network.nextElement();
				if (netinter.isLoopback() || netinter.isVirtual() || !netinter.isUp()) {
					continue;
				} else {
					byte[] mac = netinter.getHardwareAddress();
					if (mac != null) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < mac.length; i++) {
							sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
						}

						if (sb.length() > 0) {
							return sb.toString();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 获取IP地址
	 * 
	 * @return
	 */
	public static String getIpAddress() {

		try {

			Enumeration<NetworkInterface> network = NetworkInterface.getNetworkInterfaces();
			while (network.hasMoreElements()) {
				NetworkInterface netinter = (NetworkInterface) network.nextElement();
				if (netinter.isLoopback() || netinter.isVirtual() || !netinter.isUp()) {
					continue;
				} else {
					Enumeration<InetAddress> addresses = netinter.getInetAddresses();
					while (addresses.hasMoreElements()) {
						InetAddress ip = addresses.nextElement();
						if (ip != null && ip instanceof Inet4Address) {
							return ip.getHostAddress();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
