package com.ruanbanhai.springboot.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBootUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBootUtils.applicationContext == null) {
            SpringBootUtils.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取对象
    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    //通过class获取对象
    public static <T> Object getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name和class获取对象
    public static <T> Object getBean(String beanName, Class<T> clazz) {
        return getApplicationContext().getBean(beanName, clazz);
    }
}
