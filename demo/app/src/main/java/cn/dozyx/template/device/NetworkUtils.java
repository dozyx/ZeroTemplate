package cn.dozyx.template.device;

import static android.text.format.DateUtils.SECOND_IN_MILLIS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public final class NetworkUtils {

   public static final int NETWORK_TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
   public static final int NETWORK_TYPE_WIFI = ConnectivityManager.TYPE_WIFI;
   private static boolean reverseProxyOn = false;

   private static final String ANDROID_HOTSPOT_IP_ADDRESS = "192.168.43.1";
   private static final String IOS_HOTSPOT_IP_ADDRESS = "172.20.10.1";

   //refer: com.wandoujia.mtdownload.impl.Consts # DEFAULT_TIMEOUT
   private static final int SOCKET_TIMEOUT_MS = (int) (20 * SECOND_IN_MILLIS);

   @SuppressLint("MissingPermission")
   public static boolean isNetworkConnected(Context context) {
      if (context == null) {
         return false;
      }
      if (reverseProxyOn) {
         return true;
      }
      ConnectivityManager connManager = getConnectivityManagerSafely(context);
      if (connManager == null) {
         return false;
      }
      NetworkInfo activeNetworkInfo = null;
      try {
         activeNetworkInfo = connManager.getActiveNetworkInfo();
      } catch (Exception e) {
         // in some roms, here maybe throw a exception(like nullpoint).
         e.printStackTrace();
      }
      return activeNetworkInfo != null && activeNetworkInfo.isConnected();
   }

   /**
    * time consuming, only can call on non-ui thread
    *
    * @param context
    * @return true if has internet access
    */
   public static boolean isInternetAccessible(Context context) {
      if (!isNetworkConnected(context)) {
         return false;
      }
      return true;
//    if (Looper.myLooper() == Looper.getMainLooper()) {
//      throw new IllegalStateException("must run on non-ui thread");
//    }
//    try {
//      Socket sock = new Socket();
//      ensureNetCheckHost(context);
//      SocketAddress address = new InetSocketAddress(sNetCheckHost, sNetCheckPort);
//      sock.connect(address, SOCKET_TIMEOUT_MS);
//      sock.close();
//      return true;
//    } catch (IOException e) {
//      return false;
//    }
//      detectNetworkUseHttp(context);
//      return false;
//    }
   }

   public static boolean isMobileNetworkConnected(Context context) {
      ConnectivityManager connManager = getConnectivityManagerSafely(context);
      if(connManager == null) {
         return false;
      }
      NetworkInfo networkInfo = null;
      try{
         // maybe throw exception : Neither user 10113 nor current process has android.permission.ACCESS_NETWORK_STATE.
         networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
      } catch (Exception e) {
         e.printStackTrace();
      }

      return networkInfo != null && networkInfo.isConnected();
   }

   public static boolean isWifiConnected(Context context) {
      if(context == null){
         return false;
      }
      ConnectivityManager connManager = getConnectivityManagerSafely(context);
      if (connManager == null) {
         return false;
      }
      NetworkInfo networkInfo = null;
      try {
         // maybe throw exception in android framework
         networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      } catch (Exception e) {
         e.printStackTrace();
      }

      // can not use pingSupplicant (), on cm9 or some other roms it will
      // block whole wifi network!
      return (networkInfo != null && networkInfo.isConnected());
   }

   @Nullable
   private static ConnectivityManager getConnectivityManagerSafely(Context context) {
      // Missing ConnectivityManager in some framework, such as 5.1.1
      try {
         return (ConnectivityManager) context
                 .getSystemService(Context.CONNECTIVITY_SERVICE);
      } catch (Exception e) {
         // ignore
      }
      return null;
   }

   /**
    * Convert a IPv4 address from an integer to an InetAddress.
    *
    * @param hostAddress is an Int corresponding to the IPv4 address in network byte order
    * @return the IP address as an {@code InetAddress}, returns null if
    * unable to convert or if the int is an invalid address.
    */
   private static InetAddress intToInetAddress(int hostAddress) {
      InetAddress inetAddress = null;
      byte[] addressBytes = {(byte) (0xff & hostAddress),
              (byte) (0xff & (hostAddress >> 8)),
              (byte) (0xff & (hostAddress >> 16)),
              (byte) (0xff & (hostAddress >> 24))};

      try {
         inetAddress = InetAddress.getByAddress(addressBytes);
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return inetAddress;
   }

   /**
    * Check wifi is hotSpot or not.
    *
    * @return whether wifi is hotSpot or not.
    */
   public static boolean checkWifiIsHotSpot(Context context) {
      WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
      if (wifiManager == null) {
         return false;
      }
      DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
      if (dhcpInfo == null) {
         return false;
      }
      InetAddress address = intToInetAddress(dhcpInfo.gateway);
      if (address == null) {
         return false;
      }
      String currentGateway = address.getHostAddress();
      return TextUtils.equals(currentGateway, ANDROID_HOTSPOT_IP_ADDRESS)
              || TextUtils.equals(currentGateway, IOS_HOTSPOT_IP_ADDRESS);
   }

   /**
    * Get the network type name. If currently connected to a mobile network, the detail mobile
    * network type name will be returned.
    *
    * @param context
    * @param networkInfo
    * @return the network type name.
    */
   public static String getNetworkTypeName(Context context, NetworkInfo networkInfo) {
      if (networkInfo == null) {
         if (reverseProxyOn) {
            return "PC";
         }
      } else if (networkInfo.getType() == NETWORK_TYPE_MOBILE) {
         TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                 Context.TELEPHONY_SERVICE);
         return getNetworkTypeName(telephonyManager.getNetworkType());
      } else {
         return "WIFI";
      }
      return "NONE"; // no network connected. change null to "NONE", so it can be known.
   }

   private static String getNetworkTypeName(int type) {
      switch (type) {
         case TelephonyManager.NETWORK_TYPE_GPRS:
            return "GPRS";
         case TelephonyManager.NETWORK_TYPE_EDGE:
            return "EDGE";
         case TelephonyManager.NETWORK_TYPE_UMTS:
            return "UMTS";
         case TelephonyManager.NETWORK_TYPE_HSDPA:
            return "HSDPA";
         case TelephonyManager.NETWORK_TYPE_HSUPA:
            return "HSUPA";
         case TelephonyManager.NETWORK_TYPE_HSPA:
            return "HSPA";
         case TelephonyManager.NETWORK_TYPE_CDMA:
            return "CDMA";
         case TelephonyManager.NETWORK_TYPE_EVDO_0:
            return "CDMA - EvDo rev. 0";
         case TelephonyManager.NETWORK_TYPE_EVDO_A:
            return "CDMA - EvDo rev. A";
         case TelephonyManager.NETWORK_TYPE_EVDO_B:
            return "CDMA - EvDo rev. B";
         case TelephonyManager.NETWORK_TYPE_1xRTT:
            return "CDMA - 1xRTT";
         case TelephonyManager.NETWORK_TYPE_LTE:
            return "LTE";
         case TelephonyManager.NETWORK_TYPE_EHRPD:
            return "CDMA - eHRPD";
         case TelephonyManager.NETWORK_TYPE_IDEN:
            return "iDEN";
         case TelephonyManager.NETWORK_TYPE_HSPAP:
            return "HSPA+";
         default:
            return "UNKNOWN";
      }
   }

   public static String getNetworkCategoryName(Context context) {
      ConnectivityManager connManager = getConnectivityManagerSafely(context);
      String category = "INVALID";
      if (connManager == null) {
         return category;
      }
      try {
         final NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
         if (networkInfo.getType() == NETWORK_TYPE_WIFI) {
            return "WiFi";
         } else if (networkInfo.getType() == NETWORK_TYPE_MOBILE) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                    Context.TELEPHONY_SERVICE);
            int mobileType = telephonyManager.getNetworkType();
            return getNetworkCategoryName(mobileType);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return category;
   }

   private static String getNetworkCategoryName(int type) {
      switch (type) {
         case TelephonyManager.NETWORK_TYPE_GPRS:
         case TelephonyManager.NETWORK_TYPE_EDGE:
         case TelephonyManager.NETWORK_TYPE_CDMA:
         case TelephonyManager.NETWORK_TYPE_1xRTT:
         case TelephonyManager.NETWORK_TYPE_IDEN:
            return "2G";
         case TelephonyManager.NETWORK_TYPE_UMTS:
         case TelephonyManager.NETWORK_TYPE_HSDPA:
         case TelephonyManager.NETWORK_TYPE_HSUPA:
         case TelephonyManager.NETWORK_TYPE_HSPAP:
         case TelephonyManager.NETWORK_TYPE_HSPA:
         case TelephonyManager.NETWORK_TYPE_EVDO_0:
         case TelephonyManager.NETWORK_TYPE_EVDO_A:
         case TelephonyManager.NETWORK_TYPE_EVDO_B:
         case TelephonyManager.NETWORK_TYPE_EHRPD:
            return "3G";
         case TelephonyManager.NETWORK_TYPE_LTE:
            return "4G";
         case TelephonyManager.NETWORK_TYPE_UNKNOWN:
         default:
            return "UNKNOWN";
      }
   }

   public static boolean isFastMobileNetwork(Context context) {
      TelephonyManager telephonyManager = (TelephonyManager) context
              .getSystemService(Context.TELEPHONY_SERVICE);
      switch (telephonyManager.getNetworkType()) {
         case TelephonyManager.NETWORK_TYPE_EVDO_0:// ~ 400-1000 kbps
         case TelephonyManager.NETWORK_TYPE_EVDO_A:// ~ 600-1400 kbps
         case TelephonyManager.NETWORK_TYPE_HSDPA:// ~ 2-14 Mbps
         case TelephonyManager.NETWORK_TYPE_HSPA:// ~ 700-1700 kbps
         case TelephonyManager.NETWORK_TYPE_HSUPA:// ~ 1-23 Mbps
         case TelephonyManager.NETWORK_TYPE_UMTS:// ~ 400-7000 kbps
         case TelephonyManager.NETWORK_TYPE_EHRPD:// ~ 1-2 Mbps
         case TelephonyManager.NETWORK_TYPE_EVDO_B:// ~ 5 Mbps
         case TelephonyManager.NETWORK_TYPE_HSPAP:// ~ 10-20 Mbps
         case TelephonyManager.NETWORK_TYPE_LTE:// ~ 10+ Mbps
            return true;
         case TelephonyManager.NETWORK_TYPE_1xRTT:// ~ 50-100 kbps
         case TelephonyManager.NETWORK_TYPE_CDMA:// ~ 14-64 kbps
         case TelephonyManager.NETWORK_TYPE_GPRS: // ~ 100 kbps
         case TelephonyManager.NETWORK_TYPE_EDGE:// ~ 50-100 kbps
         case TelephonyManager.NETWORK_TYPE_IDEN: // ~25 kbps
         case TelephonyManager.NETWORK_TYPE_UNKNOWN:
         default:
            return false;
      }
   }

   public static String getWifiIPAddress(Context context) {
      try {
         WifiManager mgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
         if (mgr == null) {
            return null;
         }

         WifiInfo info = mgr.getConnectionInfo();
         if (info == null) {
            return null;
         }

         int ipAddress = info.getIpAddress();
         if (ipAddress == 0) {
            return null;
         }

         return intToInetAddress(ipAddress).getHostAddress();
      } catch (Exception e) {
         return null;
      }
   }

   public static String getMobileIpAddress() {
      try {
         Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
         while (en != null && en.hasMoreElements()) {
            NetworkInterface netInterface = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddress = netInterface.getInetAddresses();
                 enumIpAddress.hasMoreElements(); ) {
               InetAddress inetAddress = enumIpAddress.nextElement();
               if (!inetAddress.isLoopbackAddress()) {
                  return inetAddress.getHostAddress();
               }
            }
         }
      } catch (Exception ex) {
         // do nothing
      }
      return null;
   }

   public static String getLocalIpAddress(Context context) {
      if (isWifiConnected(context)) {
         return getWifiIPAddress(context);
      }

      if (isMobileNetworkConnected(context)) {
         return getMobileIpAddress();
      }

      return null;
   }

   public static boolean isNetworkStateEquals(NetworkInfo info1, NetworkInfo info2) {
      if (info1 == info2) {
         return true;
      }

      if (info1 == null || info2 == null) {
         return false;
      }

      return info1.isConnected() == info2.isConnected()
              && info1.getType() == info2.getType()
              && info1.getSubtype() == info2.getSubtype()
              && TextUtils.equals(info1.getExtraInfo(), info2.getExtraInfo());
   }

   public static boolean isWifiEnabled(Context context) {
      boolean openWLAN = false;
      WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
      if (wifiManager != null) {
         openWLAN = wifiManager.isWifiEnabled();
      }
      return openWLAN;
   }

   @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
   public static boolean isVPNOpen(Context context) {
      boolean hasVPN = false;
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connectivityManager != null) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
               NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
               if (networkCapabilities != null) {
                  hasVPN = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
               }
            }
         } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN);
            if (networkInfo != null) {
               hasVPN = networkInfo.isConnected();
            }
         }
      }
      return !hasVPN;
   }
}
