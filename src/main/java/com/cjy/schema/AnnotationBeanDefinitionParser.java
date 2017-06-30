/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.cjy.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by jychai on 17/6/28.
 */
public class AnnotationBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    private final boolean required;


    public AnnotationBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }



    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass);
    }

    private BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(beanClass);
        rootBeanDefinition.setLazyInit(false);

        String annotationPackage = element.getAttribute("package");
        rootBeanDefinition.getPropertyValues().addPropertyValue("annotationPackage", annotationPackage);
        String generatedBeanName = beanClass.getName();
        parserContext.getRegistry().registerBeanDefinition(generatedBeanName, rootBeanDefinition);
        return rootBeanDefinition;
    }


}
