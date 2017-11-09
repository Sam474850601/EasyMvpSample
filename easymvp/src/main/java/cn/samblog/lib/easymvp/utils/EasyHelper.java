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
import cn.samblog.lib.easymvp.annotation.SingleInstance;
import cn.samblog.lib.easymvp.model.ContextModel;
import cn.samblog.lib.easymvp.presenter.IPresenter;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/**
 * 反射注解辅助工具
 * @author Sam
 */

public final  class EasyHelper {

    private  final static  Manager manager = new Manager();

    /**
     * 如果是单例，那么用包名做区
     */
    private  final static class Manager
    {

        final Map<String, ContextModel> models =new Hashtable<String, ContextModel>();
        final Map<String, Object> others =new Hashtable<String, Object>();
    }

    public static  <T>  T getSingleModel(Class<? extends T> classType  )
    {
        return (T)manager.models.get(getSingleKey(classType));
    }

    public static void addModel(ContextModel contextModel)
    {
        manager.models.put(getSingleKey(contextModel.getClass()), contextModel);
    }


    public static  <T>  T getSingleObject(Class<? extends T> classType  )
    {
        return (T)manager.others.get(getSingleKey(classType));
    }

    private static void addObject(Object object)
    {
        manager.others.put(getSingleKey(object.getClass()), object);
    }


    private static String getSingleKey(Class<?> classType)
    {
        return classType.getClass().getName();
    }

    private static String getKey(Object object, Class<?> parentClass)
    {
        return object.getClass().getName()+":"+parentClass.getName();
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
                int layout =  resource.layoutResource();
                return LayoutInflater.from(context).inflate(layout, null);
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
        Object object  = null;
        if(field.isAnnotationPresent(SingleInstance.class))
        {
            object = getSingleObject(classType);
            if(null == object)
            {
                injectObject(needContextInstance,classType, field,  appContext, containerObject);
                addObject(object);
            }
        }
        if(null == object)
        {
            injectObject(needContextInstance,classType, field,  appContext, containerObject);
        }
    }

    public static  Object injectObject( boolean needContextInstance,  Class<?> classType, Field field , Context appContext,Object containerObject )
    {

        classType =  Object.class == classType ? field.getType() : classType;
        Object object = null;
        try {
            if (needContextInstance) {
                object =  ClassUtil.newObjectByContructorWithParams(classType, new Class[]{Context.class}, new Object[]{appContext});
                ClassUtil.setValueForField(containerObject, field, object);
            } else {
                object = ClassUtil.newObjectByContructorWithoutParams(classType);
                ClassUtil.setValueForField(containerObject, field,object );
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
        return object;
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
    public static void inject( Object containerObject,  View parentView, Context context,List<IPresenter> presenters)
    {

        SparseArray<View> viewSparseArray = new  SparseArray<View>();
        injectFields(containerObject,parentView, context, viewSparseArray,presenters);
        injectMethods(containerObject,  parentView, viewSparseArray);

    }





    private static void injectMethods(final Object containerObject, View parentView, SparseArray<View> viewSparseArray) {
        if(null == parentView)
            return;
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
                    if(null != parentView)
                    {
                        EasyHelper.injectViews(containerObject, field, parentView, viewSparseArray);
                    }
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
                    if(null != presenters)
                    {
                        IPresenter presenter =  EasyHelper.injectPresenter(containerObject, field);
                        if(null != presenter)
                        {
                            presenters.add(presenter);
                        }
                    }

                }

            }
        }

    }

    public static IPresenter injectPresenter(Object containerObject, Field field)
    {
        Presenter presenterAnn = field.getAnnotation(Presenter.class);
        Class<? extends IPresenter> modelClass =  presenterAnn.value();
        IPresenter presenter = null;
        try {
            presenter  = (IPresenter) ClassUtil.newObjectByContructorWithoutParams(modelClass);
            ClassUtil.setValueForField(containerObject, field, presenter);

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
        return   presenter;
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
        ContextModel contextModel   = null;
        Class<? extends ContextModel> modelClass =  modelAnn.value();
        if(field.isAnnotationPresent(SingleInstance.class))
        {
            contextModel =  EasyHelper.getSingleModel(modelClass);
            if(null == contextModel)
            {
                contextModel =  injectModel(modelClass,field, applicationContext,   containerObject);
                EasyHelper.addModel(contextModel);
            }
            else
            {
                try {
                    ClassUtil.setValueForField(containerObject, field, contextModel);
                    ClassUtil.invokeMethod(contextModel,"_onCreate" , new Class[]{Context.class}, new Object[]{applicationContext});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if(null == contextModel)
        {
            injectModel(modelClass,field, applicationContext,   containerObject);
        }

    }

    private static  ContextModel injectModel( Class<? extends ContextModel> modelClass , Field field, Context applicationContext , Object containerObject)
    {
        ContextModel contextModel   = null;
        try {
            contextModel   =  (ContextModel)ClassUtil.newObjectByContructorWithoutParams(modelClass);
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
        return contextModel;
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

    public static  void showView(boolean isShow, View...views) {
        if(isShow)
            EasyHelper.showView(views);
        else
            EasyHelper.hideView(views);
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

    public static void  release(final Object container)
    {
        final Field[] fields  = container.getClass().getDeclaredFields();
        for(Field field : fields)
        {
           if( (field.isAnnotationPresent(Model.class) || field.isAnnotationPresent(Inject.class))&&(field.isAnnotationPresent(SingleInstance.class)))
           {
               field.setAccessible(true);
               try {
                   field.set(container, null);
               } catch (IllegalAccessException e) {
                   e.printStackTrace();
               }
           }
        }

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
