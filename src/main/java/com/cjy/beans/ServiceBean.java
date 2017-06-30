/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.cjy.beans;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cjy.annotation.Service;

/**
 * Created by jychai on 17/6/28.
 */
public class ServiceBean<T> implements InitializingBean, ApplicationContextAware {

    private Service service;

    private ApplicationContext applicationContext;

    public ServiceBean(Service service) {
        this.service = service;
    }

    private T ref;

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println(service.content());
        Class clazz = ref.getClass();
        Method method = clazz.getDeclaredMethod("show");
        method.invoke(ref);

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
