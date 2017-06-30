/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.cjy.utils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 * ReflectUtils
 * 
 * @author qian.lei
 */
public final class ReflectUtils {
    
	/**
	 * void(V).
	 */
	public static final char JVM_VOID = 'V';

	/**
	 * boolean(Z).
	 */
	public static final char JVM_BOOLEAN = 'Z';

	/**
	 * byte(B).
	 */
	public static final char JVM_BYTE = 'B';

	/**
	 * char(C).
	 */
	public static final char JVM_CHAR = 'C';

	/**
	 * double(D).
	 */
	public static final char JVM_DOUBLE = 'D';

	/**
	 * float(F).
	 */
	public static final char JVM_FLOAT = 'F';

	/**
	 * int(I).
	 */
	public static final char JVM_INT = 'I';

	/**
	 * long(J).
	 */
	public static final char JVM_LONG = 'J';

	/**
	 * short(S).
	 */
	public static final char JVM_SHORT = 'S';

	public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

	public static final String JAVA_IDENT_REGEX = "(?:[_$a-zA-Z][_$a-zA-Z0-9]*)";

	public static final String JAVA_NAME_REGEX = "(?:" + JAVA_IDENT_REGEX + "(?:\\." + JAVA_IDENT_REGEX + ")*)";

	public static final String CLASS_DESC = "(?:L" + JAVA_IDENT_REGEX   + "(?:\\/" + JAVA_IDENT_REGEX + ")*;)";

	public static final String ARRAY_DESC  = "(?:\\[+(?:(?:[VZBCDFIJS])|" + CLASS_DESC + "))";

	public static final String DESC_REGEX = "(?:(?:[VZBCDFIJS])|" + CLASS_DESC + "|" + ARRAY_DESC + ")";

	public static final Pattern DESC_PATTERN = Pattern.compile(DESC_REGEX);

	public static final String METHOD_DESC_REGEX = "(?:("+JAVA_IDENT_REGEX+")?\\(("+DESC_REGEX+"*)\\)("+DESC_REGEX+")?)";

	public static final Pattern METHOD_DESC_PATTERN = Pattern.compile(METHOD_DESC_REGEX);

	public static final Pattern GETTER_METHOD_DESC_PATTERN = Pattern.compile("get([A-Z][_a-zA-Z0-9]*)\\(\\)(" + DESC_REGEX + ")");

	public static final Pattern SETTER_METHOD_DESC_PATTERN = Pattern.compile("set([A-Z][_a-zA-Z0-9]*)\\((" + DESC_REGEX + ")\\)V");

	public static final Pattern IS_HAS_CAN_METHOD_DESC_PATTERN = Pattern.compile("(?:is|has|can)([A-Z][_a-zA-Z0-9]*)\\(\\)Z");
	
	private static final ConcurrentMap<String, Class<?>>  DESC_CLASS_CACHE = new ConcurrentHashMap<String, Class<?>>();
    
	private static final ConcurrentMap<String, Class<?>>  NAME_CLASS_CACHE = new ConcurrentHashMap<String, Class<?>>();
	    
	private static final ConcurrentMap<String, Method>  Signature_METHODS_CACHE = new ConcurrentHashMap<String, Method>();
	

	public static Class<?> forName(String name) {
		try {
			return name2class(name);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Not found class " + name + ", cause: " + e.getMessage(), e);
		}
	}

	/**
	 * name to class.
	 * "boolean" => boolean.class
	 * "java.util.Map[][]" => java.util.Map[][].class
	 * 
	 * @param name name.
	 * @return Class instance.
	 */
	public static Class<?> name2class(String name) throws ClassNotFoundException
	{
		return name2class(ClassHelper.getClassLoader(), name);
	}

	/**
	 * name to class.
	 * "boolean" => boolean.class
	 * "java.util.Map[][]" => java.util.Map[][].class
	 * 
	 * @param cl ClassLoader instance.
	 * @param name name.
	 * @return Class instance.
	 */
	private static Class<?> name2class(ClassLoader cl, String name) throws ClassNotFoundException
	{
		int c = 0, index = name.indexOf('[');
		if( index > 0 )
		{
			c = ( name.length() - index ) / 2;
			name = name.substring(0, index);
		}
		if( c > 0 )
		{
			StringBuilder sb = new StringBuilder();
			while( c-- > 0 )
				sb.append("[");

			if( "void".equals(name) ) sb.append(JVM_VOID);
			else if( "boolean".equals(name) ) sb.append(JVM_BOOLEAN);
			else if( "byte".equals(name) ) sb.append(JVM_BYTE);
			else if( "char".equals(name) ) sb.append(JVM_CHAR);
			else if( "double".equals(name) ) sb.append(JVM_DOUBLE);
			else if( "float".equals(name) ) sb.append(JVM_FLOAT);
			else if( "int".equals(name) ) sb.append(JVM_INT);
			else if( "long".equals(name) ) sb.append(JVM_LONG);
			else if( "short".equals(name) ) sb.append(JVM_SHORT);
			else sb.append('L').append(name).append(';'); // "java.lang.Object" ==> "Ljava.lang.Object;"
			name = sb.toString();
		}
		else
		{
			if( "void".equals(name) ) return void.class;
			else if( "boolean".equals(name) ) return boolean.class;
			else if( "byte".equals(name) ) return byte.class;
			else if( "char".equals(name) ) return char.class;
			else if( "double".equals(name) ) return double.class;
			else if( "float".equals(name) ) return float.class;
			else if( "int".equals(name) ) return int.class;
			else if( "long".equals(name) ) return long.class;
			else if( "short".equals(name) ) return short.class;
		}

		if( cl == null )
			cl = ClassHelper.getClassLoader();
		Class<?> clazz = NAME_CLASS_CACHE.get(name);
        if(clazz == null){
            clazz = Class.forName(name, true, cl);
            NAME_CLASS_CACHE.put(name, clazz);
        }
        return clazz;
	}


	private ReflectUtils(){}
}