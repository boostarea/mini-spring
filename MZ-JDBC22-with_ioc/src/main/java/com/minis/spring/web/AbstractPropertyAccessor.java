package com.minis.spring.web;


import com.minis.spring.beans.PropertyValue;
import com.minis.spring.beans.PropertyValues;

public abstract class AbstractPropertyAccessor extends PropertyEditorRegistrySupport{

	PropertyValues pvs;
	
	public AbstractPropertyAccessor() {
		super();

	}

	
	public void setPropertyValues(PropertyValues pvs) {
		this.pvs = pvs;
		for (PropertyValue pv : this.pvs.getPropertyValues()) {
			setPropertyValue(pv);
		}
	}
	
	public abstract void setPropertyValue(PropertyValue pv) ;

}
