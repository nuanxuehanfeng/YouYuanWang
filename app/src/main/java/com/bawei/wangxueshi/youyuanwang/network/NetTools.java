package com.bawei.wangxueshi.youyuanwang.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetTools {

    //是否有可用网络
    public static boolean isNetworkAvailable(Context mActivity) {
//获取管理者
        ConnectivityManager connectivity = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
//若管理者不为空，则获取所有网络信息
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
//遍历网络信息，只要有一个处于连接状态，就是有网络
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


//网络类型
    public static String GetNetype(Context context)
    {
//初始化，变量 为不知道
        String strNetworkType = "unknown";
//获取 连接管理器
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//获取可用的网络信息
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//若可用网络不为空，并且已经连接
        if (networkInfo != null && networkInfo.isConnected())
        {
//判断可用网络的类型，是否为wifi
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {//若是，则 将类型设为wifi
                strNetworkType = "WIFI";
            }
//当前链接的是手机网络
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
//获取手机网络的子类型名
                String _strSubTypeName = networkInfo.getSubtypeName();


// TD-SCDMA networkType is 17

//获取手机网络的子类型
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
//若类型为这几个，则为2g网络
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
//若类型为这几个，则为3g网络
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD: //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP: //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
//若类型为这几个，则为4g网络
                    case TelephonyManager.NETWORK_TYPE_LTE: //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
// http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
//若手机网络的子类型名 为下面三种的一个 则为3G网
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000"))
                        {
                            strNetworkType = "3G";
                        }
                        else
                        {
//否则 是什么样，就是什么样了
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }
            }
        }
        return strNetworkType;
    }



    public static void toSystemSetting(Context context){
//隐式调转，到网络设置界面
        Intent intent=new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        context.startActivity(intent);

    }
}