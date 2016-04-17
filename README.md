#二维码扫描库的使用
##看了BGA的Zxing封装库后对齐做了一些精简，满足基本的使用
##BGA的封装库地址：[BGAQRCode-Android](https://github.com/bingoogolapple/BGAQRCode-Android.git)

##使用效果：
![这里写图片描述](http://img.blog.csdn.net/20160417125646070)

###封装的`module`名称为`zxing`,导入项目添加依赖
###打开二维码扫描界面
```
 //启动扫描的Activity 成功的返回码为 Result.OK
        startActivityForResult(new Intent(MainActivity.this, QRScanActivity.class), 0);
```
###使用`onActivityResult`方法获取返回的数据
```
if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
	        ...数据操作
        }
```

###生成二维码
```
//参数分别为 数据源 ， 图片宽度 ， 二维码颜色 ， 要插入的logo ， 监听器
 QRCodeEncoder.encodeQRCode(mEt.getText().toString(), mWidth, getResources().getColor(R.color.colorAccent), BitmapFactory.decodeResource(getResources(), R.drawable.logo), new QRCodeEncoder.Delegate() {
                @Override
                public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                    mImage.setImageBitmap(bitmap);
                }

                @Override
                public void onEncodeQRCodeFailure() {
                    Toast.makeText(MainActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                }
            });
```

###能满足大多数需求

