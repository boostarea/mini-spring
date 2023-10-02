package com.minis.spring.ioc_test;

import com.minis.spring.web.WebBindingInitializer;
import com.minis.spring.web.WebDataBinder;

import java.util.Date;

public class DateInitializer implements WebBindingInitializer {
    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class,"yyyy-MM-dd", false));
    }
}
