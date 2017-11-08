package cn.samblog.lib.easymvp.utils;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.Inject;
import cn.samblog.lib.easymvp.annotation.Model;
import cn.samblog.lib.easymvp.annotation.OnClicked;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.annotation.Single;
import cn.samblog.lib.easymvp.model.ContextModel;
import cn.samblog.lib.easymvp.presenter.IPresenter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * 反射注解辅助工具
 * @author Sam
 */

public final  class EasyHelper {

    private  final static  Manager manager = new Manager();

    /**
     * 如果是单例，那么用包名做区分
     * 如果不是， 那么用hashcode做区分
     */
    private  final static class Manager
    {
        final Map<Object, IPresenter> presenters =new Hashtable<Object, IPresenter>();

        final Map<Object, ContextModel> models =new Hashtable<Object, ContextModel>();

    }

    public static <T>  T getSinglePresenter(Class<? extends T> classType  )
    {
        return (T)manager.presenters.get(classType.getName());
    }

    public <T>  T getSingleModel(Class<? extends T> classType  )
    {
        return (T)manager.models.get(getSingleKey(classType));
    }


    private static String getSingleKey(Class<?> classType)
    {
        return classType.getClass().getName();
    }

    private static String getKey(Object object, Class<?> parentClass)
    {
        return object.getClass().getName()+":"+parentClass.getName();
    }

    public  static   void addPresenter(IPresenter presenter , Class<?> parentClass)
    {
        manager.presenters.put(getKey(presenter, parentClass), presenter);
    }

    public  static   void addModel(ContextModel model , Class<?> parentClass)
    {
        manager.models.put(getKey(model, parentClass), model);
    }

    public static  void removeModel(ContextModel model , Class<?> parentClass)
    {
        manager.presenters.remove(getKey(model, parentClass));
    }

    public static  void removePresenter(ContextModel model , Class<?> parentClass)
    {
        manager.models.remove(getKey(model, parentClass));
    }


    /**
     * 根据资源文件获取View
     */
    public static View getLayoutView(Context context, Object containerObject)
    {
        Class<?extends Object> classtype = containerObject.getClass();
        Annotation[] annatations =  classtype.getDeclaredAnnotations();
        for(Annotation anntation : annatations)
        {
            if(Resource.class.isAssignableFrom(anntation.annotationType()))
            {
                Resource resource = (Resource) anntation;
                return LayoutInflater.from(context).inflate(resource.layoutResource(), null);
            }
        }
        return null;
    }


    public static int getResource( Object containerObject)
    {
        Class<?extends Object> classtype = containerObject.getClass();
        Annotation[] annatations =  classtype.getDeclaredAnnotations();
        for(Annotation anntation : annatations)
        {
            if(Resource.class.isAssignableFrom(anntation.annotationType()))
            {
                Resource resource = (Resource) anntation;
                return  resource.layoutResource();
            }
        }
        return 0;
    }

    /**
     * 给容器需要注入全部需要提供的Object实例（目前只支持无参构造方法和唯一参数context构造方法实例）
     */
    public static void injectObject(Context appContext, Object containerObject  , Field field)
    {
        Inject fieldAnnotation = field.getAnnotation(Inject.class);
        Class<?> classType = fieldAnnotation.classType();
        boolean needContextInstance = fieldAnnotation.hasContextParamConstructor();
        classType =  Object.class == classType ? field.getType() : classType;
        try {
            if (needContextInstance) {
                ClassUtil.setValueForField(containerObject, field, ClassUtil.newObjectByContructorWithParams(classType, new Class[]{Context.class}, new Object[]{appContext}));
            } else {
                ClassUtil.setValueForField(containerObject, field, ClassUtil.newObjectByContructorWithoutParams(classType));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    /**
     * 给容器需要注入全部需要提供的view实例
     */
    public static void injectViews(Object containerObject, Field field, View parentView, SparseArray<View> viewSparseArray)
    {
        Find fieldAnnotation =  field.getAnnotation(Find.class);
        int value = fieldAnnotation.value();
        if(value > 0)
        {
            View view = parentView.findViewById(value);
            if(null != view)
            {
                field.setAccessible(true);
                try {
                    field.set(containerObject,view);
                    viewSparseArray.put(value, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 注入对应实例
     */
    public static void inject( Object containerObject,  View parentView, Context context)
    {
        SparseArray<View> viewSparseArray = new  SparseArray<View>();
        injectFields(containerObject,parentView, context, viewSparseArray);
        injectMethods(containerObject,  parentView, viewSparseArray);
    }





    private static void injectMethods(final Object containerObject, View parentView, SparseArray<View> viewSparseArray) {

        Method[] methods = containerObject.getClass().getDeclaredMethods();
        if (null != methods && methods.length > 0) {
            for (final Method method : methods) {
                if (method.isAnnotationPresent(OnClicked.class)) {
                    OnClicked onClicked = method.getAnnotation(OnClicked.class);
                    int id = onClicked.value();
                    if(id<=0)
                        continue;
                    View view = viewSparseArray.get(id);
                    if (null == view) {
                        view =  parentView.findViewById(id);
                    }
                    method.setAccessible(true);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(containerObject, v);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }


    public static void injectFields(Object containerObject, View parentView, Context context, SparseArray<View> viewSparseArray, List<IPresenter> presenters)
    {
        Field[] fields = containerObject.getClass().getDeclaredFields();
        if(null != fields && fields.length >0)
        {
            for(Field field : fields)
            {
                if(field.isAnnotationPresent(Find.class))
                {
                    EasyHelper.injectViews(containerObject, field, parentView, viewSparseArray);
                }
                else if(field.isAnnotationPresent(Inject.class))
                {
                    EasyHelper.injectObject(context, containerObject, field);
                }
                else if( field.isAnnotationPresent(Model.class))
                {
                    EasyHelper.injectModel(containerObject, field, context);
                }
                else if(field.isAnnotationPresent(Presenter.class))
                {
                    EasyHelper.injectPresenter(containerObject, field, context);
                }

            }
        }


    }

    public static IPresenter injectPresenter(Object containerObject, Field field, Context applicationContext)
    {
        Presenter modelAnn = field.getAnnotation(Presenter.class);

        if(field.isAnnotationPresent(Single.class))
        {
            EasyHelper.getSinglePresenter();
        }

        return
    }




    /**
     * 根据dimen获取对应的分辨率屏幕的px值
     */
    public static  int getPxValue(Context context , int dimen)
    {
        return  context.getResources().getDimensionPixelSize(dimen);
    }

    public static void injectModel(Object containerObject, Field field, Context applicationContext )
    {
        Model modelAnn = field.getAnnotation(Model.class);
        Class<? extends ContextModel> modelClass =  modelAnn.classType();
        try {
            ContextModel contextModel =  (ContextModel)ClassUtil.newObjectByContructorWithoutParams(modelClass);
            ClassUtil.setValueForField(containerObject, field, contextModel);
            ClassUtil.invokeMethod(contextModel,"_onCreate" , new Class[]{Context.class}, new Object[]{applicationContext});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给容器设置显示或隐藏 Set the container to show or hide
     */
    public static  void setVisible(int visibility, View...views)
    {
        if(null == views || 1 >views.length)
            return;
        for(View view : views)
        {
            if(null == view)
                continue;
            if(view.getVisibility() != visibility)
            {
                view.setVisibility(visibility);
            }
        }
    }


    public static  void showView(View...views) {
        setVisible(View.VISIBLE, views);
    }


    public static  void hideView(View...views) {
        setVisible(View.GONE, views);
    }

    public static  void setViewInvisible(View...views) {
        setVisible(View.INVISIBLE, views);
    }


    public static <T> T   getBeanByIntent(Class<T> dataClass, Intent intent)
    {
        T instance  =   ClassUtil.newSimpleInstance(dataClass);
        Field[] fields =   dataClass.getDeclaredFields();
        if(null != fields)
        {
            for(Field field: fields)
            {
                Class fieldType =  field.getType();
                String name = field.getName();
                if(int.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        field.setInt(instance,intent.getIntExtra(name,field.getInt(instance) ) );
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (boolean.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        field.setBoolean(instance,  intent.getBooleanExtra(name, field.getBoolean(instance)) );
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (float.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        field.setFloat(instance,intent.getFloatExtra( name, field.getFloat(instance) ) );
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (double.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        field.setDouble(instance,intent.getDoubleExtra( name, field.getDouble(instance)  ));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (long.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        field.setLong(instance,intent.getLongExtra(name, field.getLong(instance)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (String.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        field.set(instance,intent.getStringExtra(name));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }



    public  static  void setIntentByBean(Intent intent, Object instance)
    {
        Class dataClass = instance.getClass();
        Field[] fields =   dataClass.getDeclaredFields();
        if(null != fields) {
            for (Field field : fields) {
                Class fieldType =  field.getType();
                String name = field.getName();
                if(int.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        intent.putExtra(name, field.getInt(instance));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (boolean.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        intent.putExtra(name, field.getBoolean(instance));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (float.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        intent.putExtra(name, field.getFloat(instance));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (double.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        intent.putExtra(name, field.getDouble(instance));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (long.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        intent.putExtra(name, field.getLong(instance));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else  if (String.class.isAssignableFrom(fieldType))
                {
                    try {
                        field.setAccessible(true);
                        intent.putExtra(name, (String)field.get(instance));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



}
