package alfredfliu.app.mynews.net;
import android.os.SystemClock;


//import org.xutils.common.Callback;
//import org.xutils.common.util.LogUtil;
//import org.xutils.http.RequestParams;
//import org.xutils.x;
//import org.xutils.common.util.LogUtil;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.nio.charset.StandardCharsets;

import alfredfliu.app.mynews.base.Promise;
import alfredfliu.app.mynews.util.Cache;
import alfredfliu.app.mynews.util.Config;
import alfredfliu.app.mynews.util.MyLog;
import lombok.var;


public class NetHelper {

    private static long startTime;

    public static void GetCatgories(final Promise<String, String> promise) {
        var url = Config.Url;
        GetRemoteData(url, promise);
    }

    public static void GetRemoteData(final String url, final Promise<String, String> promise) {
        GetRemoteData_Volley(url, promise);
    }

    public static void GetRemoteData_xuntil(final String url, final Promise<String, String> promise) {
        final var context = Cache.getContext();
        String objtemp = Cache.getNetCache(url);
        if (objtemp != null) {
            promise.pass(url, objtemp);
            return;
        }
        startTime = SystemClock.uptimeMillis();
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(url);
        org.xutils.x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                long endTime = SystemClock.uptimeMillis();
                long passTime = endTime - startTime;

                MyLog.D(url, "xUtils3--passTime==" + passTime);

                MyLog.D(url, "使用xUtils3联网请求成功==" + result);

                Cache.setNetCache(url,result);
                promise.pass(url, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyLog.D(url, "使用xUtils3联网请求失败==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                MyLog.D(url, "使用xUtils3-onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                MyLog.D(url, "使用xUtils3-onFinished");
            }
        });
    }


    public static void GetRemoteData_Volley(final String url, final Promise<String, String> promise) {
        final var context = Cache.getContext();
        String objtemp = Cache.getNetCache(url);
        if (objtemp != null) {
            promise.pass(url, objtemp);
            return;
        }

        var request = new com.android.volley.toolbox.StringRequest(com.android.volley.Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                //MyLog.D(url, "使用Volley联网请求成功==", result);
                //缓存数据
                Cache.setNetCache(url, result);

                promise.pass(url, result);

                //processData(result);
                //设置适配器
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                promise.fail(url, volleyError.getMessage());
            }
        }) {
            @Override
            protected com.android.volley.Response<String> parseNetworkResponse(com.android.volley.NetworkResponse response) {
                String parsed = new String(response.data, StandardCharsets.UTF_8);
                return com.android.volley.Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                //return super.parseNetworkResponse(response);
            }
        };
        //添加到队列
        VolleyManager.getRequestQueue().add(request);

        return;
    }
}

