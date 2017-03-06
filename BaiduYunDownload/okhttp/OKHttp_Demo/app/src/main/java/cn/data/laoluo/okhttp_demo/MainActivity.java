package cn.data.laoluo.okhttp_demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends ActionBarActivity {

    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;
    private final static int SUCCESS_STATUS = 1;

    private final static int FAIL_STATUS = 0;

    private final static String TAG = MainActivity.class.getSimpleName();

    private String image_path = "http://img.popo.cn/uploadfile/2017/0216/1487228830613789.jpg";

    private String json_path = "http://api2.hichao.com/stars?category=%E5%85%A8%E9%83%A8&pin=&ga=%2Fstars&flag=&gv=63&access_token=&gi=862949022047018&gos=5.2.3&p=2013022&gc=xiaomi&gn=mxyc_adr&gs=720x1280&gf=android&page=2";

    private OkHttpClient client;

    private OKManager manager;//工具类


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_STATUS:
//                    byte[] result = (byte[]) msg.obj;
//                    // Bitmap bitmap = BitmapFactory.decodeByteArray(result,0,result.length);
//                    Bitmap bitmap = new CropSquareTrans().transform(BitmapFactory.decodeByteArray(result, 0, result.length));
//                    imageView.setImageBitmap(bitmap);
//                    break;
//                case FAIL_STATUS:
//                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        button = (Button) this.findViewById(R.id.button);
        button2 = (Button) this.findViewById(R.id.button2);
        button3 = (Button) this.findViewById(R.id.button3);
        imageView = (ImageView) this.findViewById(R.id. iv1);

        client = new OkHttpClient();
        //使用的是get请求
        final Request request = new Request.Builder().get().url(image_path).build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(MainActivity.this).load(image_path).into(imageView);

//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Message message = handler.obtainMessage();
//                        if (response.isSuccessful()) {
//                            message.what = SUCCESS_STATUS;
//                            message.obj = response.body().bytes();
//                            handler.sendMessage(message);
//                        } else {
//                            handler.sendEmptyMessage(FAIL_STATUS);
//                        }
//                    }
//                });
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        manager = OKManager.getInstance();
        button2.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {

                manager.asyncJsonStringByURL(json_path, new OKManager.Func1() {
                    @Override
                    public void onResponse(String result) {
                        Log.i(TAG,result);//获取json字符串
                    }
                });
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                sendBigNotification();

            }
        });
    }

    private void sendBigNotification() {
        Intent dismissIntent = new Intent(this, MainActivity.class);
//        dismissIntent.setAction(CommonConstants.ACTION_DISMISS);
        PendingIntent piDismiss = PendingIntent.getActivity(
                this, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(this, MainActivity.class);
//        snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);
        PendingIntent piSnooze =PendingIntent.getActivity(this, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.little_animal_01)
                        .setContentTitle("标题")
                        .setContentText("内容")
                        .setDefaults(Notification.DEFAULT_ALL)
                        // 该方法在Android 4.1之前会被忽略
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("我是大通知我是大通知我是大通知我是大通知我是大通知我是大通知我是大通知我是大通知"))

//                        添加Action Button
                        .addAction (R.drawable.little_animal_02,
                                "按钮1", piDismiss)
                        .addAction (R.drawable.little_animal_03,
                                "按钮2", piSnooze).setAutoCancel(true);
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = builder.build();

        mNotifyMgr.notify(1, notification);
        Log.i("shurun","fatongzhile");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
