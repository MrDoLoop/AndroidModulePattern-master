package com.netease.pineapple.common.http;

import com.netease.pineapple.common.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhaonan on 17/10/18.
 */

public class NetMonitorInterceptor implements Interceptor {

    private static final String TAG = "NetMonitorInterceptor";
    public static final String HEADER_REQUEST_TRACE_ID = "X-NR-Trace-Id";
    @Override
    public Response intercept(Chain chain) throws IOException {
        long sendTime = System.currentTimeMillis();
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
            if(!response.isSuccessful() && NetworkUtils.isConnected()) {
                StringBuilder sBuilder = new StringBuilder(request.url().toString());
                sBuilder.append("\n").append(Thread.currentThread()).append("\n");
                sBuilder.append(request.toString());
                // traceid
                String traceId = request.header(HEADER_REQUEST_TRACE_ID);
                sBuilder.append("\n").append(traceId).append("\n");
                //相应时间
                long responseTime = System.currentTimeMillis();
                sBuilder.append(responseTime).append("\n");
                // 网络类型
//                int netType = NetUtil.getNetworkType(LiveApplication.getInstance());
//                switch (netType){
//                    case NetUtil.NETWORK_TYPE_NONE:
//                        sBuilder.append("netType:").append("unconnected").append("\n");
//                        break;
//                    case NetUtil.NETWORK_TYPE_WIFI:
//                        sBuilder.append("netType:").append("wifi").append("\n");
//                        break;
//                    case NetUtil.NETWORK_TYPE_2G:
//                        sBuilder.append("netType:").append("2g").append("\n");
//                        break;
//                    case NetUtil.NETWORK_TYPE_3G:
//                        sBuilder.append("netType:").append("3g").append("\n");
//                        break;
//                    case NetUtil.NETWORK_TYPE_4G:
//                        sBuilder.append("netType:").append("4g").append("\n");
//                        break;
//                    case NetUtil.NETWORK_TYPE_UNKNOWN:
//                        sBuilder.append("netType:").append("unknown").append("\n");
//                        break;
//                }
//                // 平台
//                sBuilder.append("android").append("\n").
//                        append(DeviceUtil.getAppBuildInfo(LiveApplication.getInstance()))// 系统版本号
//                        .append("\n")
//                        .append(Build.BRAND)
//                        .append("\n")
//                        .append(Build.MODEL);// 机型
//                DebugLog.w(TAG, sBuilder.toString());
            }
        } catch (Exception e) {
            throw e;
        }

        return response;
    }
}
