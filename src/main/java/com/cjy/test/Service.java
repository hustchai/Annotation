/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.cjy.test;

/**
 * Created by jychai on 17/6/28.
 */
@com.cjy.annotation.Service(content = "Hello World")
public class Service {

    public void show() {
        System.out.println("Hello Spring");
    }
}
