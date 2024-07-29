package com.app.mouse.utils;

import com.app.mouse.Main;
import com.app.mouse.annocations.Inject;
import com.app.mouse.annocations.Injectable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Container {
    private final Map<Class<?>, Object> services = new HashMap<>();

    /**
     * 扫描包中的所有类，保存带有Injectable注解的类
     */
    public void scan() {
        try {
            var classes = getClasses();
            for (Class<?> aClass : classes) {
                Injectable injectable = aClass.getAnnotation(Injectable.class);
                if (injectable != null) { // 创建实例并放入map中
                    Constructor<?> constructor = aClass.getConstructor();
                    Object service = constructor.newInstance();
                    services.put(aClass, service);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前软件包下所有的类
     *
     * @return 类的集合
     * @throws IOException            读取文件失败
     * @throws ClassNotFoundException 未找到类
     */
    private Set<Class<?>> getClasses() throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String packageName = Main.class.getPackageName();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        Set<Class<?>> classes = new HashSet<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();
            File dir = new File(URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8));
            if ("file".equals(protocol)) {
                findClasses(dir, packageName, classes);
            }
        }
        return classes;
    }

    /**
     * 扫描文件夹下的类
     *
     * @param dir         文件夹
     * @param packageName 包名
     * @param classes     所有的类（返回结果保存在这里）
     * @throws ClassNotFoundException 如果类未找到，会抛出的异常
     */
    private static void findClasses(File dir, String packageName, Set<Class<?>> classes) throws ClassNotFoundException {
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                findClasses(file, packageName + "." + file.getName(), classes); // 递归调用
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
    }

    /**
     * 注入所有服务
     */
    public void injectServices() {
        try {
            for (Class<?> aClass : services.keySet()) {
                Object target = services.get(aClass);
                Field[] fields = aClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        Class<?> fieldType = field.getType();
                        var service = services.get(fieldType);
                        if (service == null) {
                            throw new ClassNotFoundException(String.format("Service %s not found", fieldType.getName()));
                        }
                        field.setAccessible(true);
                        field.set(target, service);
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取服务实例
     * @param serviceClass 服务的类信息
     * @return 获取到的服务
     * @param <T> 服务的类型
     */
    public<T> T getService(Class<T> serviceClass) {
        return (T) services.get(serviceClass);
    }
}
