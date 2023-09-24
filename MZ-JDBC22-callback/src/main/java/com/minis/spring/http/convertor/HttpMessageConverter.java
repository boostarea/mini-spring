package com.minis.spring.http.convertor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpMessageConverter {
	void write(Object obj, HttpServletResponse response) throws IOException;
}
