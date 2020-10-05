package com.example.recipes.User;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipes.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends Activity {
    ImageView iv;
    Uri photo_uri;
    EditText register_username, register_password, register_repassword, register_name, register_phone;
    Button btn_cancal, btn_reg;
    String id, password, repassword, name, phone;
    TextView span_phone, span_psd, span_repsd, span_id;
    RadioGroup sexGroup;
//    private byte[] img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏窗口title
        setContentView(R.layout.activity_register);

        photo_uri = null;
        init(); //初始化

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.reg:
                        //得到性别
                        RadioButton register_sex = (RadioButton) findViewById(sexGroup.getCheckedRadioButtonId());
                        String sex = "";
                        if (register_sex != null)
                            sex = register_sex.getText().toString();

                        if (check()) {

                            //检查账号是否已存在
                            UserDAO userDAO = new UserDAO(getApplicationContext());
                            User user = userDAO.find(id);

//                            UserPhotoDAO userPhotoDAO = new UserPhotoDAO(getApplicationContext());
//                            UserPhoto userPhoto = userPhotoDAO.find(id);


                            if (user != null) {
                                Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                            } else {
                                user = new User(id, password, name, sex, phone, photo_uri.toString());
                                userDAO.add(user);

                                //                                img=bitmabToBytes();
                                //
                                //                                userPhoto=new UserPhoto(id,img);
                                //                                userPhotoDAO.add(userPhoto);

                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        break;
                    case R.id.cancel:
                        finish();
                        break;
                    case R.id.photo:
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x1);
                        break;
                }
            }
        };
        btn_cancal.setOnClickListener(clickListener);
        btn_reg.setOnClickListener(clickListener);
        iv.setOnClickListener(clickListener);

        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()) {
                    case R.id.register_username:
                        if (hasFocus) {
                            span_id.setText("必填");
                            span_id.setTextColor(Color.BLACK);
                        } else {
                            checkId();
                        }
                        break;
                    case R.id.register_password:
                        if (hasFocus) {
                            span_psd.setText("必填");
                            span_psd.setTextColor(Color.BLACK);
                        } else {
                            checkPassword();
                        }
                        break;
                    case R.id.register_repassword:
                        if (hasFocus) {
                            span_repsd.setText("必填");
                            span_repsd.setTextColor(Color.BLACK);
                        } else {
                            checkRepassword();
                        }
                        break;
                    case R.id.register_name:
                        if (hasFocus) {
                        } else {
                            checkName();
                        }
                        break;
                    case R.id.register_phone:
                        if (hasFocus) {
                            span_phone.setText("");
                        } else {
                            checkPhone();
                        }
                        break;
                }
            }
        };

        register_username.setOnFocusChangeListener(focusChangeListener);
        register_password.setOnFocusChangeListener(focusChangeListener);
        register_repassword.setOnFocusChangeListener(focusChangeListener);
        register_name.setOnFocusChangeListener(focusChangeListener);
        register_phone.setOnFocusChangeListener(focusChangeListener);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                photo_uri = data.getData();
                iv.setImageURI(photo_uri);
                //                img=bitmabToBytes();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        register_username = (EditText) findViewById(R.id.register_username);
        register_password = (EditText) findViewById(R.id.register_password);
        register_repassword = (EditText) findViewById(R.id.register_repassword);
        register_name = (EditText) findViewById(R.id.register_name);
        register_phone = (EditText) findViewById(R.id.register_phone);
        btn_cancal = (Button) findViewById(R.id.cancel);
        btn_reg = (Button) findViewById(R.id.reg);
        iv = (ImageView) findViewById(R.id.photo);
        span_phone = (TextView) findViewById(R.id.reg_span_phone);
        span_phone.setText("");
        span_psd = (TextView) findViewById(R.id.reg_span_psd);
        span_psd.setTextColor(Color.BLACK);
        span_repsd = (TextView) findViewById(R.id.reg_span_repsd);
        span_repsd.setTextColor(Color.BLACK);
        span_id = (TextView) findViewById(R.id.reg_span_id);
        span_id.setTextColor(Color.BLACK);
        sexGroup = (RadioGroup) findViewById(R.id.sex);
        sexGroup.check(R.id.reg_man);
    }

    private boolean check() {
        if (checkId() && checkPassword() && checkRepassword() && checkName() && checkPhone()) {
            return true;
        }
        return false;
    }

    private boolean checkId() {
        //检查账号
        id = register_username.getText().toString();
        String regex = "^[a-zA-Z0-9]{1,10}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        boolean isMatch = m.matches();
        if (!isMatch) {
            span_id.setText("请输入10位以内字母或数字");
            span_id.setTextColor(Color.RED);
            return false;
        }
        if(id.equals("123456")){
            Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
            return false;
        }
        span_id.setText("OK");
        span_id.setTextColor(Color.BLACK);
        return true;
    }

    private boolean checkPassword() {
        //检查密码
        password = register_password.getText().toString();
        String regex = "^[a-zA-Z0-9]{8,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        boolean isMatch = m.matches();
        if (!isMatch) {
            span_psd.setText("请输入8-16位字母或数字");
            span_psd.setTextColor(Color.RED);
            return false;
        }
        span_psd.setText("OK");
        span_psd.setTextColor(Color.BLACK);
        return true;
    }

    private boolean checkRepassword() {
        //检查重复输入的密码
        repassword = register_repassword.getText().toString();
        password = register_password.getText().toString();
        if (!password.equals(repassword)) {
            span_repsd.setText("两次输入的密码不一致");
            span_repsd.setTextColor(Color.RED);
            return false;
        }
        span_repsd.setText("OK");
        span_repsd.setTextColor(Color.BLACK);
        return true;
    }

    private boolean checkName() {
        //检查昵称
        name = register_name.getText().toString();
        if (register_name.getText() == null) {
            name = "zhangsan";
        }
        return true;
    }

    private boolean checkPhone() {
        //检查电话号码
        phone = register_phone.getText().toString();
        if (phone.length() != 11) {
            span_phone.setText("请输入11位数字");
            span_phone.setTextColor(Color.RED);
            return false;
        } else {
            String regex = "^1[3|4|5|6|7|8][0-9]\\d{8}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                span_phone.setText("请输入正确的11位手机号");
                span_phone.setTextColor(Color.RED);
                return false;
            }
            span_phone.setText("OK");
            span_phone.setTextColor(Color.BLACK);
            return true;
        }
    }

//    //图片转为二进制数据
    //    public byte[] bitmabToBytes() {
    //        iv.setDrawingCacheEnabled(true);
    //        Bitmap bitmap = iv.getDrawingCache();
    //        iv.setDrawingCacheEnabled(false);
    //        //将图片转化为位图
    //        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
    //        //创建一个字节数组输出流,流的大小为size
    //        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
    //        try {
    //            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
    //            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    //            //将字节数组输出流转化为字节数组byte[]
    //            byte[] imagedata = baos.toByteArray();
    //            return imagedata;
    //        } catch (Exception e) {
    //        } finally {
    //            try {
    //                bitmap.recycle();
    //                baos.close();
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //        return new byte[0];
    //    }


}
