package cn.samblog.lib.easymvp.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具
 * @author  Sam
 */
public class ClassUtil {

    /**
     * 获取父类第index个泛型class实例
     */
    public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 根据class Name创建公开不带参数构造方法的实例
     */
    public static <T> T newSimpleInstance(Class classType) {
        try {
            return   (T)classType.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    };



    /**
     * 根据对象class建引用
     * @param classType 对象class
     * @return 新引用
     */
    public static Object newObjectForFieldWithoutParams(Class<?> classType) throws ClassNotFoundException, IllegalAccessException, InstantiationException {


        return  Class.forName( classType.getName()).newInstance();
    }


    public static Object newObjectByContructorWithoutParams(Class<?> classType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor declaredConstructor = classType.getDeclaredConstructor(new Class[]{});
        declaredConstructor.setAccessible(true);
        return  declaredConstructor.newInstance(new Object[]{});
    }

    public static Object newObjectByContructorWithParams(Class<?> classType, Class<?>[] paramClassTypes, Object[] paramValues) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor declaredConstructor = classType.getDeclaredConstructor(paramClassTypes);
        declaredConstructor.setAccessible(true);
        return  declaredConstructor.newInstance(paramValues);
    }



    /**
     * 根据对象名字创建引用
     * @param className 对象名字
     * @return 新引用
     */
    public static Object newObjectForFieldWithoutParams(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return  Class.forName( className).newInstance();
    }

    /**
     * 给对象引用instance的Field赋值value
     * @param instance 对象引用
     * @param field 对象引用field
     * @param value 要进行的操作的值
     */
    public static void setValueForField(Object instance, Field field, Object value) throws IllegalAccessException {
        if(null == instance || null  == instance || null == value)
            return;
        field.setAccessible(true);
        field.set(instance, value);
    }



    public static  void injectObjectByAnnitation(Object object, Class<? extends  Annotation> ...annotations)
    {
        Field[] fields = object.getClass().getDeclaredFields();
        if(null != fields && fields.length >0)
        {
            for(Field field : fields)
            {
                for(Class<? extends  Annotation> classType : annotations)
                {
                    if(field.isAnnotationPresent(classType))
                    {
                        try {
                            ClassUtil.setValueForField(object, field, ClassUtil.newObjectByContructorWithoutParams(field.getType()));
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
                }

            }
        }
    }

    /**
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。

     */
    public static Method getMethod(Class clazz, String methodName,
                                   final Class[] classes) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName,
                            classes);
                }
            }
        }
        return method;
    }


    public static Object invokeMethod(final Object obj, final String methodName,
                                final Class[] classes, final Object[] objects) {
        try {
            Method method = getMethod(obj.getClass(), methodName, classes);
            method.setAccessible(true);// 调用private方法的关键一句话
            return method.invoke(obj, objects);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
