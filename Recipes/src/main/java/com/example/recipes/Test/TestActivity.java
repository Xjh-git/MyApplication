package com.example.recipes.Test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.recipes.R;

public class TestActivity extends Activity {
    ImageView img;
    private TestDAO dbOperate;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        dbOperate=new TestDAO(this);


        Button save, read;
        img = (ImageView) findViewById(R.id.img_test);
        save = (Button) findViewById(R.id.save_test);
        read = (Button) findViewById(R.id.read_test);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.save_test:
//                        img.setDrawingCacheEnabled(true);
//                        Bitmap bitmap = img.getDrawingCache();
//                        img.setDrawingCacheEnabled(false);
//
//                        byte[] imagedata;
//                        //将图片转化为位图
//                        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
//                        //创建一个字节数组输出流,流的大小为size
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
//
//                        //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                        //将字节数组输出流转化为字节数组byte[]
//                        imagedata = baos.toByteArray();
//
//
//                        TestDB testDB = new TestDB(TestActivity.this, "test.db", null, 1);
//                        SQLiteDatabase db = testDB.getWritableDatabase();
//
//                        db.execSQL("insert into img VALUES( ? )", new Object[]{imagedata});
//                        db.close();
                        saveImage(url);

                        break;
                    case R.id.read_test:
//                        TestDB testDB1 = new TestDB(TestActivity.this, "test.db", null, 1);
//                        SQLiteDatabase db1 = testDB1.getWritableDatabase();
//
//                        Cursor cursor=db1.rawQuery("select * from img",new String[]{});
//
//                        byte[] imgdata=cursor.getBlob(cursor.getColumnIndex("img"));
//
//                        cursor.close();
//
//                        db1.close();
//
//                        Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length);
//
//                        img.setImageBitmap(imagebitmap);
//
                        readImage();

                        break;
                    case R.id.img_test:
                        //前往图库获得图片
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x1);
                        break;
                }
            }
        };

        save.setOnClickListener(listener);
        read.setOnClickListener(listener);
        img.setOnClickListener(listener);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0x1 && resultCode == -1) {
            if (data != null) {
                //得到图片的uri
                Uri photo_uri = data.getData();
                //更新图片
                img.setImageURI(photo_uri);

            }
        }

    }

    private void readImage(){

        byte[] imgData=dbOperate.readImage();
        if (imgData!=null) {
            //将字节数组转化为位图
            Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            //将位图显示为图片
            img.setImageBitmap(imagebitmap);
        }else {
            img.setBackgroundResource(android.R.drawable.menuitem_background);
        }
    }

    private void saveImage(String url){
        dbOperate.saveImage();
    }

}
