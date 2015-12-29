package com.tengyun.lesson2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tengyun.lesson2.adapters.ItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Callback {

    private Call call;
    private TextView mTextView;
    private ListView listView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.main_text);
        listView = (ListView) findViewById(R.id.main_list);

        adapter =new ItemAdapter(this);
        listView.setAdapter(adapter);

        //OkHttp使用,内部使用HttpConnection
        OkHttpClient client = new OkHttpClient();
        //获取请求
        Request request = new Request.Builder().url("http://m2.qiushibaike.com/article/list/suggest?page=").get().build();
        call = client.newCall(request);
        //同步请求在当前线程执行,返回值为Response,使用较少
        //Response response = call.execute();
        //请求加入调度,异步请求
        call.enqueue(this);
    }

    //异步请求的回调
    /**
     * 失败（在非Ui线程中执行）
     * @param request 请求对象
     * @param e       异常,一定要打印异常信息
     */
    @Override
    public void onFailure(Request request, IOException e) {
        e.printStackTrace();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 失败（在非Ui线程中执行，不可以操作UI，不可以Toast）
     * @param response
     * @throws IOException
     */
    @Override
    public void onResponse(Response response) throws IOException {

        final String s = response.body().string();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray array = jsonObject.getJSONArray("items");
            final List<Item> list = new ArrayList<Item>();
            for (int i = 0; i < array.length(); i++) {
                list.add(new Item(array.getJSONObject(i)));
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.addAll(list);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 用户关闭当前页面时候取消请求
     */
    @Override
    protected void onStop() {
        super.onStop();
        //取消请求
        call.cancel();
    }
}
