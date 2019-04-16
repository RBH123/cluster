package com.ruanbanhai.springboot.demo.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

public class ObjectToMap {

    public static Map<String, Object> toMap(Object object) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Map<String, Object> map = new TreeMap<>();
        for (PropertyDescriptor property : propertyDescriptors) {
            if (!"class".equalsIgnoreCase(property.getName())) {
                Method method = property.getReadMethod();
                Object value = method.invoke(object);
                if (value != null) {
                    map.put(property.getName(), value);
                }
            }
        }
        return map;
    }
}
