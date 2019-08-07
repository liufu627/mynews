package alfredfliu.app.mynews.net;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

import alfredfliu.app.mynews.base.Promise;
import alfredfliu.app.mynews.util.Cache;
import alfredfliu.app.mynews.util.Config;
import alfredfliu.app.mynews.util.MyLog;
import lombok.var;

public class NetHelper {

    public static String[] GetCatgories(final Promise<String, String> promise)
    {
        final var context = Cache.getContext();
        final var url =Config.Url;
        String objtemp = Cache.StringGlobal.get(url);
        if(objtemp!=null){
            promise.pass(url,objtemp);
        }else {
            var request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String result) {
                    MyLog.D("使用Volley联网请求成功==", result);
                    //缓存数据
                    Cache.putString(context, url, result);

                    promise.pass(url, result);

                    //processData(result);
                    //设置适配器
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    promise.fail(url, volleyError.getMessage());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String parsed = new String(response.data, "UTF-8");
                        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return super.parseNetworkResponse(response);
                }
            };
            //添加到队列
            VolleyManager.getRequestQueue().add(request);
        }
        return null;

    }
}
