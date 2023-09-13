package com.minis.spring.web;


import com.minis.spring.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//核心在于利用反射对 Bean 属性值进行读写，具体是通过 setter 和 getter 方法
public class BeanWrapperImpl extends AbstractPropertyAccessor {
	Object wrappedObject;
	Class<?> clz;
	
	public BeanWrapperImpl(Object object) {
		super();	
		this.wrappedObject = object;
		this.clz = object.getClass();
	}

	@Override
	public void setPropertyValue(PropertyValue pv) {
		BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
		//最初我们直接获取 DefaultEditor 的代码，改为先获取 CustomEditor ，如果它不存在，再获取 DefaultEditor
		//就能支持用户自定义的 CustomEditor ，增强了扩展性。同样的类型，如果既有用户 自定义的实现，又有框架默认的实现，那用户自定义的优先。
		PropertyEditor pe = this.getCustomEditor(propertyHandler.getPropertyClz());
		if (pe == null) {
			pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
			
		}
		if (pe != null) {
			pe.setAsText((String) pv.getValue());
			propertyHandler.setValue(pe.getValue());
		}
		else {
			propertyHandler.setValue(pv.getValue());			
		}

	}

	class BeanPropertyHandler {
		Method writeMethod = null;
		Method readMethod = null;
		Class<?> propertyClz = null;
		
		public Class<?> getPropertyClz() {
			return propertyClz;
		}

		public BeanPropertyHandler(String propertyName) {
			try {
				Field field = clz.getDeclaredField(propertyName);
				propertyClz = field.getType();
				this.writeMethod = clz.getDeclaredMethod("set"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1),propertyClz);
				this.readMethod = clz.getDeclaredMethod("get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		public Object getValue() {
			Object result = null;
			writeMethod.setAccessible(true);
			
			try {
				result =  readMethod.invoke(wrappedObject);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return result;

		}

		public void setValue(Object value) {
				writeMethod.setAccessible(true);
				try {
					writeMethod.invoke(wrappedObject, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
		}

	}

}
