package com.abcd.wifimanager.routerlogin.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Enumeration;

public class Wireless {
    private Context context;

    public Wireless(Context context) {
        this.context = context;
    }

    public String getMacAddress() throws UnknownHostException, SocketException {
        String macAddress = getWifiInfo().getMacAddress();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        byte[] hardwareAddress = NetworkInterface.getByInetAddress(getWifiInetAddress()).getHardwareAddress();
        StringBuilder stringBuilder = new StringBuilder();
        int length = hardwareAddress.length;
        for (int i = 0; i < length; i++) {
            stringBuilder.append(String.format("%02x:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private InetAddress getWifiInetAddress() throws UnknownHostException {
        return InetAddress.getByName((String) getInternalWifiIpAddress(String.class));
    }

    public int getSignalStrength() {
        return getWifiInfo().getRssi();
    }

    public String getBSSID() {
        return getWifiInfo().getBSSID();
    }

    public String getGetWay() {
        return String.valueOf(Formatter.formatIpAddress(getWifiManager().getDhcpInfo().gateway));
    }

    public String getSSID() {
        String ssid = getWifiInfo().getSSID();
        return (ssid.startsWith("\"") && ssid.endsWith("\"")) ? ssid.substring(1, ssid.length() - 1) : ssid;
    }

    public <T> T getInternalWifiIpAddress(Class<T> cls) throws UnknownHostException {
        int ipAddress = getWifiInfo().getIpAddress();
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }
        byte[] toByteArray = BigInteger.valueOf((long) ipAddress).toByteArray();
        if (cls.isInstance("")) {
            return cls.cast(InetAddress.getByAddress(toByteArray).getHostAddress());
        }
        return cls.cast(Integer.valueOf(new BigInteger(InetAddress.getByAddress(toByteArray).getAddress()).intValue()));
    }

    public int getInternalWifiSubnet() {
        WifiManager wifiManager = getWifiManager();
        if (wifiManager == null) {
            return 0;
        }
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if (dhcpInfo == null) {
            return 0;
        }
        wifiManager.getConfiguredNetworks();
        int bitCount = Integer.bitCount(dhcpInfo.netmask);
        if (bitCount < 4 || bitCount > 32) {
            try {
                InetAddress wifiInetAddress = null;
                try {
                    wifiInetAddress = getWifiInetAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(wifiInetAddress);
                if (byInetAddress == null) {
                    return 0;
                }
                for (InterfaceAddress interfaceAddress : byInetAddress.getInterfaceAddresses()) {
                    if (wifiInetAddress != null && wifiInetAddress.equals(interfaceAddress.getAddress())) {
                        return interfaceAddress.getNetworkPrefixLength();
                    }
                }
            } catch (SocketException unused) {
            }
        }
        return bitCount;
    }

    public int getNumberOfHostsInWifiSubnet() {
        return (int) (Math.pow(2.0d, 32.0d - Double.valueOf((double) getInternalWifiSubnet()).doubleValue()) - 2.0d);
    }

    public String getInternalMobileIpAddress() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces != null && networkInterfaces.hasMoreElements()) {
                Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
            return "Unknown";
        } catch (SocketException unused) {
            return "Unknown";
        }
    }

    public int getLinkSpeed() {
        return getWifiInfo().getLinkSpeed();
    }

    public boolean isConnectedWifi() {
        NetworkInfo networkInfo = getNetworkInfo(1);
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

    public boolean isEnabled() {
        return getWifiManager().isWifiEnabled();
    }

    @SuppressLint("WrongConstant")
    public WifiManager getWifiManager() {
        return (WifiManager) this.context.getApplicationContext().getSystemService("wifi");
    }

    public WifiInfo getWifiInfo() {
        return getWifiManager().getConnectionInfo();
    }

    @SuppressLint("WrongConstant")
    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) this.context.getSystemService("connectivity");
    }

    private NetworkInfo getNetworkInfo(int i) {
        ConnectivityManager connectivityManager = getConnectivityManager();
        return connectivityManager != null ? connectivityManager.getNetworkInfo(i) : null;
    }
}
