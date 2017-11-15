
# 前言

由于前年学习MVP架构后，对其有了深刻的认知。离职后到了下家公司决定开发个MVP框架,投入使用。大概花了一周的时间包括设计，编码，自测搞完了这套框架。随着投入开发时，还是有不少问题，不过经过不断的淬炼，使用了一年到了如今的稳定版。最近发现还有耦合性优化的空间，于是提升了质量，开源了分享给大家，共同讨论相互学习



# 欢迎使用EasyMVP

这是个简单易用的mvp轻量级框架。通过注入式来将各层进行分离，层次清晰，易维护.(kotlin项目兼容)

1.提供Model层, View层，Presenter层注解。

2.提供Inject注入无参或只含有Context参数的对象。

3.Model和Inject可以通过注入@SingleInstance来表明作为单例使用。

4.对于Activity,内部提供Find注入View，onCliked注解点击事件。内部还有封装好的RecyclerView的万能适配器等。



# 架构示意图

![架构图](https://github.com/Sam474850601/EasyMvpSample/blob/master/Architecture.png)




# 类图

###  ContextModel <-> IPresneter

![第一部分类图](https://github.com/Sam474850601/EasyMvpSample/blob/master/class2.png)


###  IPresneter <-> IView


![第二部分类图](https://github.com/Sam474850601/EasyMvpSample/blob/master/class.png)


# 引入代码

### 源码地址：https://github.com/Sam474850601/EasyMvpSample

### Maven

```xml

<dependency>
  <groupId>com.yoyonewbie.android.lib</groupId>
  <artifactId>easymvp</artifactId>
  <version>1.0.1</version>
  <type>aar</type>
</dependency>

```


### jcenter

```gradle

 compile 'com.yoyonewbie.android.lib:easymvp:1.0.1'

```
# 第一部分，简单使用演示

## 举个例子：
### 需求：
#####  欢迎界面 
##### 1.提供TextView, 显示 you're welcome to easymvp，要求初始化的时候动态设置
##### 3.提供“进入体验按钮”，点击“进入体验按钮”， 弹窗ProgressDialog，3秒进入登陆界面
#####  相关： 
      Model: ILoadingModel.java , LoadingModel.java， ILoadingCallback.java（回调）
      UI: IWelcomeView.java ,  WelcomeActivity.java , activity_welcome.xml
      Presenter:   IWelcomePresenter.java , WelcomePresenter.java 
##### 
 
## 界面:

1.进入界面

![进入界面](https://github.com/Sam474850601/EasyMvpSample/blob/master/part1.png)


2.加载界面

![进入界面](https://github.com/Sam474850601/EasyMvpSample/blob/master/part2.png)





#### 第一步 添加 activity_welcome.xml

说明: 主题是沉淀式，需要加上 android:fitsSystemWindows="true"， 如果不想要这个效果，重写 BaseActivity的 setLayoutBefore,将其覆盖即可

```xml
<RelativeLayout
android:layout_width="match_parent"
android:layout_height="match_parent"
    android:fitsSystemWindows="true"
xmlns:android="http://schemas.android.com/apk/res/android">



<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text=""
    android:id="@+id/tv_title"
    android:layout_centerInParent="true"
    android:textSize="30dp"
    />


<Button
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="进入体验"
    android:layout_alignParentBottom="true"
    android:layout_marginBottom="100dp"
    android:textSize="30dp"
    android:id="@+id/btn_onStartCliked"
    android:layout_marginLeft="20dp"
    android:layout_marginRight="20dp"
    />


</RelativeLayout>

```

#### 第二步 添加 WelcomeActivity 继承 BaseActivity,使用@Resource注解注入activity_welcome.xml视图，并重写initViews方法（下面会解析这个方法作用）

```java

@Resource(layoutResource = R.layout.acitivity_welcome)
public class WelcomeActivity extends BaseActivity 
{
     @Override
    protected void initViews(Bundle savedInstanceState, View parentView) 
    {
    
    }
    
}

```
运行结果：

![运行结果](https://github.com/Sam474850601/EasyMvpSample/blob/master/run1.png)

#### 第三步 添加IWelcomeView.java，目的给presenter层调用
```java
public interface IWelcomeView extends IView {
    //设置显示或隐藏ProgressDialog
    void showLoadingView(boolean isShow);
    //跳转到用户登录界面
    void forwordUserView();
}

```


#### 第四步 让WelcomeActivity实现IWelcomeView接口

用@Find注入加载视图对象引用
```java
{
    //...
            @Find(R.id.tv_title)
            TextView tvTitle;
    //...
}

```
使用@Inject加载带Context构造方法的对象
```java

{
    //...
            @Inject(hasContextParamConstructor = true)
            ProgressDialog progressDialog;
    //...
}
```


使用@OnClicked添加点击事件
```java

{
    //...
    @OnClicked(R.id.btn_onStartCliked)
    void onStartCliked(View view)
    {
       
    }

    //...
}
```

#### 完整代码
```java
@Resource(layoutResource = R.layout.acitivity_welcome)
public class WelcomeActivity extends BaseActivity  implements IWelcomeView {


    @Find(R.id.tv_title)
    TextView tvTitle;

    @Inject(hasContextParamConstructor = true)
    ProgressDialog progressDialog;

    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {
        progressDialog.setMessage("正在加载中...");
        tvTitle.setText("you're welcome to use easymvp");
    }

    @OnClicked(R.id.btn_onStartCliked)
    void onStartCliked(View view)
    {
        
    }


    @Override
    public void showLoadingView(boolean isShow) {
        if(isShow)
        {
            progressDialog.show();
        }
        else
        {
            progressDialog.dismiss();
        }

    }

    @Override
    public void forwordUserView() {
        //前往用户登录
    }
}

```
运行结果

![运行结果](https://github.com/Sam474850601/EasyMvpSample/blob/master/part1.png)




#### 第五步，添加加载完毕回调

```java
public interface ILoadingCallback
{
    //通知加载完毕
    void onCompleted();
}

```


#### 添加接口 ILoadingModel.java,提供加载方法 
```java
public interface ILoadingModel 
{
    //模拟加载耗时事务，回调
    void loading(ILoadingCallback callback);
        
}

```    
#### 添加LoadingModel.java 继承ContextModel,实现 ILoadingModel

使用@Inject注解注入任意无参数或只含Context构造方法的对象引用

```java


   {
       //...
         @Inject
         Handler handler;
       //...
       
   }

``` 

完整代码

```java

public class LoadingModel extends ContextModel implements ILoadingModel {

    @Inject
    Handler handler;

    @Override
    public void loading(final ILoadingCallback callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onCompleted();
            }
        }, 3000);
    }
}
    
```

 
#### 第六步 添加 IWelcomePresenter.java, 添加 void startReading()加载方法，目的让WelcomeActivity调用
```java

public interface IWelcomePresenter {

    //加载并跳转
    void startReading();

}


```
#### 第七步 添加 WelcomePresenter.java ，实现添加IWelcomePresenter ， 并且重写 initPeresenter方法 （下面会给出解析为什么要实现这个方法）

使用@Model注入ILoadingModel 引用


```java

{
    //..
    @Model(LoadingModel.class)
    ILoadingModel loadingModel;
    //..
    
}

```

完整代码
```java

/**
 * 欢迎界面交互Presenter
 */
public class WelcomePresenter extends BasePresenter<IWelcomeView> implements IWelcomePresenter {


    @Model(LoadingModel.class)
    ILoadingModel loadingModel;



    @Override
    public void initPeresenter(Bundle savedInstanceState, IWelcomeView view) {
        Log.e("WelcomePresenter", "initPeresenter");

    }

    @Override
    public void startReading() {
        //获取欢迎页面视图实例
        IWelcomeView view = getView();
        //如果没有退出界面
        if(null != view)
        {
            //显示加载对话
            view.showLoadingView(true);
            
            //模拟加载后跳转到用户页面
            loadingModel.loading(new ILoadingCallback() {
                @Override
                public void onCompleted() {
                    IWelcomeView view = getView();
                    if(null != view)
                    {
                        view.showLoadingView(false);
                        view.forwordUserView();
                    }
                }
            });
        }
    }


}

```

#### 最后一步 为 WelcomeActivity 引入 IWelcomePresenter 实例

使用@Presenter注入WelcomePresenter实例

```java
{
    
    //..
    
    @Presenter(WelcomePresenter.class)
    IWelcomePresenter welcomePresenter;
    
    //...
    
}

```

实现点击时候加载功能

```java

{
    
    //..
       
    @OnClicked(R.id.btn_onStartCliked)
    void onStartCliked(View view)
    {
        welcomePresenter.startReading();
    }
   //..

}

```

完整代码

```java

/**
 * 欢迎界面
 */
@Resource(layoutResource = R.layout.acitivity_welcome)
public class WelcomeActivity extends BaseActivity  implements IWelcomeView {

    @Presenter(WelcomePresenter.class)
    IWelcomePresenter welcomePresenter;

    @Find(R.id.tv_title)
    TextView tvTitle;

    @Inject(hasContextParamConstructor = true)
    ProgressDialog progressDialog;

    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {
        progressDialog.setMessage("正在加载中...");
        tvTitle.setText("you're welcome to use easymvp");
    }

    @OnClicked(R.id.btn_onStartCliked)
    void onStartCliked(View view)
    {
        welcomePresenter.startReading();
    }


    @Override
    public void showLoadingView(boolean isShow) {
        if(isShow)
        {
            progressDialog.show();
        }
        else
        {
            progressDialog.dismiss();
        }

    }

    @Override
    public void forwordUserView() {
        //伪代码
        //startActivityFromRightToLeft(UserActivity.class);
        //finish();
    }
}


```

点击运行结果


![运行结果](https://github.com/Sam474850601/EasyMvpSample/blob/master/part2.png)


#### 就这样， mvp构建使用完成。



# 第二部分，解析注解使用情况。


### Inject

#### 1.@Inject可以注入任何无参对象,如

```java

{
   //...
   @Inject
   Handler handler;
   
   //...
}

```
#### 2.@Inject可以注入仅带Context构造方法的对象。但是需要注意一点， 如果它在Model或Presenter中使用，那么Context属于ApplicaitonContext,如果在UI层使用，那么它的上下文就是UI的Context

```java
   
class xxx extends BaseActivity 
{
   //...
    @Inject(hasContextParamConstructor = true)
    ProgressDialog progressDialog;
   //...
}

```

### @Model

#### 1.作用：注入ContextModel对象

     ContextModel是个抽象类，通过继承来使用，表示是Model层类， 主要用来分担业务逻辑的代码。可以在ContextModel子类,BasePresenter子类使用（也可以在BaseActivity子类, BaseFragment, BaseChildFragment子类中使用，但是不推荐）
    
#### 2.生命周期

     生命周期只有onCreate(Context context)方法，当被创建时候，会调用 onCreate(Context context)方法， 也就说，可以在这个方法里面初始化代码。

### @Presenter

#### 1.作用：注入IPresneter对象

    IPresneter是个接口，表示是Prenseter层类, 通常通过继承BasePresenter来使用。主要用来分担与Model层，与View层之间交互逻辑的代码.

#### 2.生命周期

##### 相关有onCreate, initPeresenter,  onStart, onResume, onPause, onStop, onDestroy
 
##### 被创建的时候调用onCreate，当视图加载完毕后调用initPeresenter，其他和Activity使用一样，
与Activity的生命周期同步。
      
      另外要注意一点，Presenter与BaseAcitivty的生命周期执行顺序
       IPresneter.onCreate->BaseAcitivty.initViews->IPresneter.initPeresenter


### @Resource 作用：注入视图

### @Find 作用：注入view ，相当于findViewById，可以使用在BaseActivity , BaseFragment, BaseChildFragment.如

```java

   {
        @Find(R.id.tv_title)
        TextView tvTitle;    
    
        //相当于TextView tvTitle = findViewById(R.id.tv_tile);
   }

```

### @OnCliked 作用：提供点击事件, 可以使用在BaseActivity , BaseFragment, BaseChildFragment. 如


```java

   {
        //...
        
        @OnCliked(R.id.btn_login)
        void onLoginCliked(View view)
        {
            
        }
        
        //...
    
   }

```


### @SingleInstance 作用：代表使用或全局的对象，表示单例的对象,通常配合@Inject或@Model使用，如


```java

class  User
{
    

}


class Test1Model extends ContextModel
{
     @SingleInstance
     @Inject(UserModel.class)
     User user; 
}


class Test2Model extends ContextModel
{
     @SingleInstance
     @Inject(UserModel.class)
     User user; 
}


```

那么，这2个对象的是共享的

# 第三部分，自定义Activity


#### 开发过程可能会用到其他类型的Activity, 这里给个简单的例子
```java
@Resource(layoutResource = R.layout.activity_custom)
public class CustomActivity extends Activity implements IView  {

    View rootView;

    Bundle savedInstanceState;


    @Presenter(CustomPresenter.class)
    ICustomPresenter customPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        rootView =  EasyHelper.getLayoutView(this, this);
        setContentView(rootView);
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        EasyHelper.inject(this, rootView, getApplicationContext(), savedInstanceState, new EasyHelper.OnInitViewsCallback() {

            @Override
            public void onInit(View parentView) {
                initViews(parentView);
            }
        });
    }

    private void initViews(View parentView)
    {
        //初始化View
    }
}

```


# 使用Sharepreference工具，居于面向对象设计。如



```java

//需要在Application初始化
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OrmCache.getInstance().init(getApplicationContext());
    }
}



public class User {
    @CacheField
    public String username = "unknown";

    @CacheField
    public  int age = 0;

    @CacheField
    public  int id = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


class UserDao
{
    
        public void saveUser(User user) {
            //持久化保存到手机，只保存@CacheField注解的字段
            OrmCache.getInstance().save(user);
        }
    
        public User getCacheUser() {
            //从手机上拿取
            return OrmCache.getInstance().getCache(User.class);
        }
    
}

//如果复杂多字段，你可以将它转json字符串保存
public class Data {
    
     @CacheField
    String json;
    
}

public class DataDao
{
    //保存 Data
    public void saveData(Data data)
    {
         OrmCache.getInstance().save(data);
    }
    
     //获取 Data
    public User getCacheUser() {
             
       return OrmCache.getInstance().getCache(Data.class);
    }
}


```

# 具体详细使用，看源码演示代码， 感谢你的阅读

