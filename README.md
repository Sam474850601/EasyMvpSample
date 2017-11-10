# EasyMvp 

这是个简单易用的mvp框架。通过注入式来将各层进行分离，层次清晰，易维护.

1.提供Model层, View层，Presenter层注解。

2.提供Inject注入无参或只含有Context参数的对象。

3.Model和Inject可以通过注入@SingleInstance来表明作为单例使用。

4.对于Activity,内部提供Find注入View，onCliked注解点击事件。内部还有封装好的RecyclerView的万能适配器等。


## 举个例子：
### 需求：
#####  欢迎界面 
##### 1.提供TextView, 显示 you're welcome to easymvp，要求初始化的时候动态设置
##### 3.提供“进入体验按钮”，点击“进入体验按钮”， 弹窗ProgressDialog，3秒进入登陆界面
#####  相关： 
```java
     
      Model: LoadingModel
      UI: IWelcomeView ,  WelcomeActivity , activity_welcome.xml
      Presenter:   WelcomePresenter 
```
##### 
 
## 界面:

1.进入界面

![进入界面](https://github.com/Sam474850601/EasyMvpSample/blob/master/part1.png)


2.加载界面

![进入界面](https://github.com/Sam474850601/EasyMvpSample/blob/master/part2.png)


 
 
