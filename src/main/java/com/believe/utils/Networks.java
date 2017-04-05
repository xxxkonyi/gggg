package com.believe.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public final class Networks {

  private Networks() {
  }

  public static String getHostName() {
    String name = null;

    try {
      Enumeration e = NetworkInterface.getNetworkInterfaces();

      while (true) {
        while (true) {
          NetworkInterface net;
          do {
            if (!e.hasMoreElements() || name != null) {
              return name;
            }

            net = (NetworkInterface) e.nextElement();
          } while (net.isLoopback());

          Enumeration addr = net.getInetAddresses();

          while (addr.hasMoreElements()) {
            InetAddress inet = (InetAddress) addr.nextElement();
            if (inet.isSiteLocalAddress()) {
              name = inet.getHostAddress();
            }

            if (!inet.getCanonicalHostName().equalsIgnoreCase(inet.getHostAddress())) {
              name = inet.getCanonicalHostName();
              break;
            }
          }
        }
      }
    } catch (SocketException var5) {
      name = "localhost";
      return name;
    }
  }

  public static String getSiteIp() {
    try {
      Enumeration e = NetworkInterface.getNetworkInterfaces();

      while (e.hasMoreElements()) {
        NetworkInterface network = (NetworkInterface) e.nextElement();
        Enumeration addresses = network.getInetAddresses();

        while (addresses.hasMoreElements()) {
          InetAddress address = (InetAddress) addresses.nextElement();
          if (address.isSiteLocalAddress()) {
            return address.getHostAddress();
          }
        }
      }
    } catch (Exception var4) {
      var4.printStackTrace();
    }

    return "127.0.0.1";
  }

  public static Integer ip2Num(String ipStr) {
    if (ipStr != null && !"".equals(ipStr)) {
      if (ipStr.contains(":")) {
        ipStr = "127.0.0.1";
      }

      String[] ips = ipStr.split("\\.");
      return Integer.valueOf((Integer.parseInt(ips[0]) << 24) + (Integer.parseInt(ips[1]) << 16) + (Integer.parseInt(ips[2]) << 8) + Integer.parseInt(ips[3]));
    } else {
      return Integer.valueOf(-1);
    }
  }

  public static String num2Ip(int ipNum) {
    return (ipNum >> 24 & 255) + "." + (ipNum >> 16 & 255) + "." + (ipNum >> 8 & 255) + "." + (ipNum & 255);
  }
}
