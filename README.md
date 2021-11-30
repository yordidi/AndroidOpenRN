## Native invoke RN
*2021.11.26*

rn-cli创建的android项目在添加新Activity后，提示targetVersion和compileVersion，将它们的版本都设置成31后，需要将Launch Activity的exported属性设为true，否则无法打开

### 一、创建RN Activity
 Android创建可以打开RN的Activity有2种方法：
1. 直接用cli模板生成的MainReactActivity和MainApplication
2. 用MyReactActivity创建了另一个入口，验证OK，不携带数据
2. 根据官网教程创建OfficialReactActivity，但是不知道为什么无法返回

### 二、启动RN，并传递数据给RN Activity
1. **Native Activity传递** 
 * 可以通过Intent，然后在useEffect里通过Native Module拿数据。没办法在LifeCycle里通知RN去拿，原因如下：
 * Native吊起RN的过程，RN可以监听Pause、Destroy，没办法监听Resume
 * RN吊起Native的过程，RN可以监听Resume、Pause，因为React Activity没有销毁，因此不会监听到Destroy。
2. **Native Module传递** 
* 如果数据不是在Native Activity上的，通过在RN的useEffect里调用Native Module方法主动获取数据也是可以的。
3. **例如，启动RN，并打开一个指定的Screen**
也就是在Native Module里传路由数据给RN，RN在useEffect里拿到路由数据进行重定向。
4. 纯启动RN，不传递任何数据
### 三、退出RN

1. **backPress左滑直接退出RN**
* 禁用react-navigation的的回退事件，调用rn activity的回退事件。当然也可以通过setResult回传数据给Native
* onBackPressed要先获取RN需要回传的数据，RN端可以通过EventEmitter监听onBackPressed的调用，进行数据的回传给Native Activity。 
* Pause、Stop、Destroy，当这些生命周期函数调用时，虽然可以执行JS方法回传数据给Native Module，再处理Native Module返回的数据，但是会造成React Activity退出延迟。就算是不处理Promise返回的数据，也会造成延迟。不知道有什么影响？？？参考：calledAfterReactDestroy方法


2. **JS主动关闭RN并回传数据**
* 通过一个rn方法，rn方法调用native方法关闭rn activity 如 ReactWithResult
* 例如，homeActivityResultLauncher.launch吊起RN，JS可以调用Native方法通过finish退出RN和setResult回传数据给吊起RN的Native Activity。
* ActivityResultLauncher比startIntentForResult更加解耦，而且提供了一些API
[OnActivityResult method is deprecated, what is the alternative?](https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative)
[再见！onActivityResult！你好，Activity Results API！](https://jishuin.proginn.com/p/763bfbd30374)

### 四、订阅Native发布的事件
比如监听生命周期函数调用，参见MainApp.js


### 五、RN集成到现有Android项目
两种方式：
1. 官网+ git subModule。这种方式需要修改Android项目的文件夹名称，这个倒无所谓。
2. 通过软链接。软链接也许可以解决文件夹名称的问题，但也许带来一些路径引用的问题。
综合考虑，还是用第一种方案

3. 我晕~ cli复制的android项目可以运行成功，自己创建的android项目怎么都运行不成功？  这个问题卡了我半天，原来是有个debug文件夹，这个文件夹是专门给react native调试用的
4. 不过我把signingConfigs 注释了，不知道这个配置项是干什么用的。
5. 现在总结一下，RN集成到现有的Android项目
1）可以参照官网新建一个MyReactActivity文件，不可以只用一个Activity继承ReactActivity就完了
2）不过我猜想，如果实现了MainApplication估计也是可以的。
3）其实官网的MyReactActivity类倒也好，更简洁。


### TODOS
- [ ] 官网教程虽然简洁，不能与react-navigation兼容，以后研究。MainActivity更简单，但多出来一个文件，且不知道MainApplication会不会影响客户端
- [ ] native打开指定rn页面有闪动
- [ ] native可以发布事件给rn，但是rn没有订阅该怎么处理呢？
- [ ] native 引用一个 rn组件，应该是没必要的



