package android.cst.hqu.edu.cn.chapter6_4;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class MainActivity extends Activity {
    private ConstraintLayout layout;
    private Bitmap bitmap;
    private ImageView imgShow;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0x123){
                imgShow.setImageBitmap(bitmap);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout=findViewById(R.id.mainLayout);
        imgShow=new ImageView(MainActivity.this);
        layout.addView(imgShow);
        new Thread(){
            @Override
            public void run() {
                try{
                    URL url=new URL("http://www.hqu.edu.cn/"+"images/"+"xww.jpg");
                    InputStream is=url.openStream();
                    bitmap= BitmapFactory.decodeStream(is);
                    handler.sendEmptyMessage(0x123);
                    is.close();
                    is=url.openStream();
                    OutputStream os=openFileOutput("mytest.png",MODE_PRIVATE);
                    byte[] buf=new byte[1024];
                    int hasRead=0;
                    while((hasRead=is.read(buf))>0){
                        os.write(buf,0,hasRead);
                    }
                    is.close();
                    os.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
