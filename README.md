### 2021.11.26

## Native 启动RN

rn-cli创建的android项目在添加新Activity后，提示targetVersion和compileVersion，将它们的版本都设置成31后，需要将Launch Activity的exported属性设为true，否则无法打开

#### Android创建可以打开RN的Activity有2种方法：
1. 直接用cli模板生成的MainReactActivity和MainApplication
2. 用MyReactActivity创建了另一个入口，验证OK，不携带数据
2. 根据官网教程创建OfficialReactActivity，但是不知道为什么无法返回

#### 启动RN

##### 启动RN，并传递数据给RN Activity
1. **Native Activity传递** 
 * 可以通过Intent，然后在useEffect里通过Native Module拿数据。没办法在LifeCycle里通知RN去拿，原因如下：
 * Native吊起RN的过程，RN可以监听Pause、Destroy，没办法监听Resume
 * RN吊起Native的过程，RN可以监听Resume、Pause，因为React Activity没有销毁，因此不会监听到Destroy。
2. **Native Module传递** 
* 如果数据不是在Native Activity上的，通过在RN的useEffect里调用Native Module方法主动获取数据也是可以的。
3. **例如，启动RN，并打开一个指定的Screen**
也就是在Native Module里传路由数据给RN，RN在useEffect里拿到路由数据进行重定向。

#### 退出RN

1. **backPress左滑直接退出RN**
* 禁用react-navigation的的回退事件，调用rn activity的回退事件。当然也可以通过setResult回传数据给Native
* onBackPressed要先获取RN需要回传的数据，RN端可以通过EventEmitter监听onBackPressed的调用，进行数据的回传给Native Activity。 
* Pause、Stop、Destroy，当这些生命周期函数调用时，虽然可以执行JS方法回传数据给Native Module，再处理Native Module返回的数据，但是会造成React Activity退出延迟。就算是不处理Promise返回的数据，也会造成延迟。不知道有什么影响？？？

2. 通过一个rn方法，rn方法调用native方法关闭rn activity 如 ReactWithResult
例如，homeActivityResultLauncher.launch吊起RN，JS可以调用Native方法通过finish退出RN和setResult回传数据给吊起RN的Native Activity。
