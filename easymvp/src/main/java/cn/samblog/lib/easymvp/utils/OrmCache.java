package cn.samblog.lib.easymvp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cn.samblog.lib.easymvp.annotation.CacheField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * SharedPreferences ORM缓存工具
 * @author Sam
 */

public  final class OrmCache {

    private  static OrmCache instance;
    private SharedPreferences sharedPreferences;

    private OrmCache()
    {

    }

    public static OrmCache getInstance()
    {
        if(null == instance)
        {
            synchronized (OrmCache.class)
            {
                if (null == instance)
                {
                    return instance = new OrmCache();
                }

            }
        }
        return  instance;
    }

    private Context context;

    /**
     * 初始化
     * @param appContext
     */
    public void  init(Context appContext)
    {
        this.context = appContext;
    }

    /**
     * 保存实例
     */
    public OrmCache save(Object cacheObject)
    {
        if (null == context) {
            throw new RuntimeException("you must be  initialize  before  call this method! ");
        }
        if(null == cacheObject)
            return this;
        synchronized (this)
        {
            sharedPreferences = context.getSharedPreferences(cacheObject.getClass().getName(), Context.MODE_PRIVATE);
            Field[] fields = cacheObject.getClass().getDeclaredFields();
            if(null != fields && fields.length >0) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                boolean hasCacheField = false;
                for (Field field : fields) {
                    if (field.isAnnotationPresent(CacheField.class)) {
                        CacheField fieldAnnotation =  field.getAnnotation(CacheField.class);
                        String fieldName = fieldAnnotation.fieldName();
                        if(CacheField.DEFUALT.equals(fieldName))
                            fieldName = field.getName();
                        Class fieldType =  field.getType();
                        if(int.class.isAssignableFrom(fieldType))
                        {
                            try {
                                field.setAccessible(true);
                                editor.putInt(fieldName,field.getInt(cacheObject));
                                hasCacheField = true;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        else  if (boolean.class.isAssignableFrom(fieldType))
                        {
                            try {
                                field.setAccessible(true);
                                editor.putBoolean(fieldName,field.getBoolean(cacheObject));
                                hasCacheField = true;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        else  if (float.class.isAssignableFrom(fieldType))
                        {
                            try {
                                field.setAccessible(true);
                                editor.putFloat(fieldName,field.getFloat(cacheObject));
                                hasCacheField = true;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        else  if (long.class.isAssignableFrom(fieldType))
                        {
                            try {
                                field.setAccessible(true);
                                editor.putLong(fieldName,field.getLong(cacheObject));
                                hasCacheField = true;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        else  if (String.class.isAssignableFrom(fieldType))
                        {
                            try {
                                field.setAccessible(true);
                                editor.putString(fieldName,(String)field.get(cacheObject));
                                hasCacheField = true;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if(hasCacheField)
                    editor.commit();
            }
        }
            return this;

    }


    /**
     * 获取该类缓存，如果为空，返回一个默认属性的对象
     */
    public <T> T  getCache(Class<? extends T> tClass)
    {
        if (null == context) {
            throw new RuntimeException("you must be  initialize  before  call this method! ");
        }
        synchronized (this)
        {
            sharedPreferences = context.getSharedPreferences(tClass.getName(), Context.MODE_PRIVATE);
            T t = null;
            try {
                t = (T) ClassUtil.newObjectByContructorWithoutParams(tClass);
                Field[] fields = t.getClass().getDeclaredFields();
                if(null != fields && fields.length >0) {

                    for (Field field : fields) {
                        if (field.isAnnotationPresent(CacheField.class)) {
                            CacheField fieldAnnotation =  field.getAnnotation(CacheField.class);
                            String fieldName = fieldAnnotation.fieldName();
                            if(CacheField.DEFUALT.equals(fieldName))
                                fieldName = field.getName();
                            field.setAccessible(true);
                            Class fieldType =  field.getType();
                            if(int.class.isAssignableFrom(fieldType))
                            {
                                try {
                                    field.set(t,sharedPreferences.getInt(fieldName, field.getInt(t) ) );
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                            else  if (boolean.class.isAssignableFrom(fieldType))
                            {
                                try {
                                    field.set(t,sharedPreferences.getBoolean(fieldName, field.getBoolean(t) ) );
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                            else  if (float.class.isAssignableFrom(fieldType))
                            {
                                try {
                                    field.set(t,sharedPreferences.getFloat(fieldName, field.getFloat(t) ) );
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                            else  if (long.class.isAssignableFrom(fieldType))
                            {
                                try {
                                    field.set(t,sharedPreferences.getLong(fieldName, field.getLong(t) ) );
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                            else  if (String.class.isAssignableFrom(fieldType))
                            {
                                try {
                                    field.set(t,sharedPreferences.getString(fieldName, (String) field.get(t)) );
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return (T)t;
        }
    }


    /**
     * 清楚缓存
     */
    public OrmCache clearCache(Class<? extends Object> tClass)
    {
        if (null == context) {
            throw new RuntimeException("you must be  initialize  before  call this method! ");
        }
        synchronized (this)
        {
            sharedPreferences = context.getSharedPreferences(tClass.getName(), Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();
        }
        return this;
    }


    public OrmCache clearCache(Class<? extends Object> tClass, String cacheFieldName)
    {
        if(null == cacheFieldName)
        return this;
        synchronized (this)
        {
            if (null == context) {
                throw new RuntimeException("you must be  initialize  before  call this method! ");
            }
            sharedPreferences = context.getSharedPreferences(tClass.getName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=  sharedPreferences.edit();
            editor.remove(cacheFieldName);
            editor.commit();
        }
        return this;
    }


    /**
     * 针对class 某个字段设置值，其他值不变
     */
    public OrmCache update(Class<? extends Object> tClass, String cacheFieldName, Object value)
    {
        if(null == value||null == cacheFieldName)
            return this;
        if (null == context) {
            throw new RuntimeException("you must be  initialize  before  call this method! ");
        }
        synchronized (this)
        {
            sharedPreferences = context.getSharedPreferences(tClass.getName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=  sharedPreferences.edit();
            if(null == value)
            {
                editor.remove(cacheFieldName);
                editor.commit();
                return this;
            }
            Class fieldType = null;
            try {
                Field field  = tClass.getDeclaredField(cacheFieldName);
                fieldType = field.getType();
                if(int.class.isAssignableFrom(fieldType))
                {
                    editor.putInt(cacheFieldName,(int)value);
                    editor.commit();
                }
                else  if (boolean.class.isAssignableFrom(fieldType))
                {
                    editor.putBoolean(cacheFieldName,(boolean)value);
                    editor.commit();
                }
                else  if (float.class.isAssignableFrom(fieldType))
                {
                    editor.putFloat(cacheFieldName,(float)value);
                    editor.commit();
                }
                else  if (long.class.isAssignableFrom(fieldType))
                {
                    editor.putLong(cacheFieldName,(long)value);
                    editor.commit();
                }
                else  if (String.class.isAssignableFrom(fieldType))
                {
                    editor.putString(cacheFieldName,(String) value);
                    editor.commit();

                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return this;
    }



}
