package com.minis.spring.core.env;

/**
 * 所有的 ApplicationContext 都实现了 Environment 接口
 *
 * @author chen
 * @date 2023/08/30
 */
public interface Environment extends PropertyResolver {
	String[] getActiveProfiles();

	String[] getDefaultProfiles();

	boolean acceptsProfiles(String... profiles);

}

