/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.cjy.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.cjy.beans.AnnotationBean;

/**
 * Created by jychai on 17/6/28.
 */
public class AnnotationNamespaceHandler extends NamespaceHandlerSupport {


    public void init() {
        registerBeanDefinitionParser("annotation", new AnnotationBeanDefinitionParser(AnnotationBean.class, true));
    }
}
