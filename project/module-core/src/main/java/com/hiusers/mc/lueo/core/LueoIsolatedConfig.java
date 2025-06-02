package com.hiusers.mc.lueo.core;

import taboolib.common.classloader.IsolatedClassLoaderConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author iiabc
 * @since 2025/6/2 14:26
 */
public class LueoIsolatedConfig implements IsolatedClassLoaderConfig {
	@Override
	public Set<String> excludedClasses() {
		// 暴露核心 API 类
		return Collections.singleton("com.hiusers.mc.lueo.api.LueoAPI");
	}

	@Override
	public Set<String> excludedPackages() {
		// 暴露包
		return new HashSet<>(Arrays.asList(
				"com.hiusers.mc.lueo.api.",
				"org.jetbrains.exposed."
		));
	}
}
