### 2021.11.26

## Native 启动RN

rn-cli创建的android项目在添加新Activity后，提示targetVersion和compileVersion，将它们的版本都设置成31后，需要将Launch Activity的exported属性设为true，否则无法打开

#### Android创建可以打开RN的Activity有2种方法：
1. 直接用cli模板生成的MainActivity和MainApplication
2. 根据官网教程创建OfficialReactActivity，但是不知道为什么无法返回

#### 启动RN
```
Intent intent = new Intent(NativeActivity, )
```

#### 启动RN，并传递数据给RN Activity
1. **Native Activity传递** 
 * 可以通过Intent，然后在useEffect里通过Native Module拿数据。没办法在LifeCycle里通知RN去拿，原因如下：
 * Native吊起RN的过程，RN可以监听Pause、Destroy，没办法监听Resume
 * RN吊起Native的过程，RN可以监听Resume、Pause，因为React Activity没有销毁，因此不会监听到Destroy。
2. **Native Module传递** 
* 如果数据不是在Native Activity上的，通过在RN的useEffect里调用Native Module方法主动获取数据也是可以的。
3. **例如，启动RN，并打开一个指定的Screen**
也就是在Native Module里传路由数据给RN，RN在useEffect里拿到路由数据进行重定向。

#### 退出RN

4. **backPress直接退出RN**
禁用react-navigation的的回退事件，调用rn activity的回退事件
