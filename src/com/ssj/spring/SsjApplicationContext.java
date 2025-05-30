package com.ssj.spring;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class SsjApplicationContext {
    private Class<?> configClass;
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public SsjApplicationContext(Class<?> configClass) {
        this.configClass = configClass;

        // 扫描
        if (this.configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
            // 通过ComponentScan的扫描路径拿到所有的带有Component注解的类
            String path = componentScan.value();
            Class<?>[] componentClasses = scan(path);
            System.out.println(Arrays.toString(componentClasses));
            // 判断类上是否有Component注解
            for (Class<?> componentClass : componentClasses) {
                doScan(componentClass);
            }
        }
    }

    private void doScan(Class<?> componentClass) {
        if (componentClass.isAnnotationPresent(Component.class)) {
            Component component = componentClass.getAnnotation(Component.class);
            String beanName = component.value();
            // 如果没有指定bean名称, 使用类名首字母小写
            if ("".equals(beanName)) {
                String className = componentClass.getSimpleName();
                // 首字母转小写 + 后续名字截取除了第一个字母之外的字符串
                beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
            }

            // 创建 BeanDefinition对象
            BeanDefinition beanDefinition = new BeanDefinition(componentClass);
            beanDefinition.setBeanName(beanName);

            // 判断Scope
            if (componentClass.isAnnotationPresent(Scope.class)) {
                Scope scopeAnnotation = componentClass.getAnnotation(Scope.class);
                beanDefinition.setScope(scopeAnnotation.value());
            }
            // 把BeanDefinition放入容器中
            beanDefinitionMap.put(beanName, beanDefinition);
            // beanDefinitionMap.forEach((key, value) -> {
            // System.out.println(key + ":" + value);
            // });
        }
    }

    /**
     * 扫描指定的包路径来查找和加载所有类。
     * 
     * 实现过程：
     * 1. 将包路径转换为文件系统路径
     * 2. 获取包目录的绝对路径, 通过classloader获取
     * 3. 在目录及其子目录中查找所有.class文件
     * 4. 将文件路径转换为完全限定类名
     * 5. 使用Class.forName()加载每个类
     * 6. 创建并返回已加载类的数组
     * 
     * @param path 要扫描的包路径（例如："com.ssj.spring"）
     * @return 在指定包路径中找到的Class对象数组
     * @throws RuntimeException 如果在扫描或类加载过程中出现错误
     */
    private Class<?>[] scan(String path) {
        try {
            // 1. 将包路径转换为文件系统路径
            String filePath = path.replace(".", "/");
            URL resource = SsjApplicationContext.class.getClassLoader().getResource(filePath);
            if (resource == null) {
                return new Class[0];
            }
            // 2. 通过resource 找到下面的所有文件, 后缀带.class的文件
            File[] listFiles = new File(resource.getFile()).listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(".class");
                }
            });
            // 3. Convert file paths to class names and load them
            // // 流式写法
            // return Arrays.stream(listFiles).map(
            // file -> {
            // String classname = path + "." + file.getName().replace(".class", "");
            // try {
            // return Class.forName(classname);
            // } catch (Exception e) {
            // // TODO: handle exception
            // throw new RuntimeException("Failed to load class: " + classname, e);
            // }
            // }).toArray(Class<?>[]::new);
            Class<?>[] classes = new Class[listFiles.length];
            // 遍历
            int i = 0;
            for (File file : listFiles) {
                String classname = path + "." + file.getName().replace(".class", "");
                try {
                    classes[i] = Class.forName(classname);
                    i++;
                } catch (Exception e) {
                    // TODO: handle exception
                    throw new RuntimeException("Failed to load class: " + classname, e);
                }
            }
            return classes;

        } catch (Exception e) {
            throw new RuntimeException("Error scanning package: " + path, e);
        }

    }

    public Class<?> getConfigClass() {
        return configClass;
    }

    public Object getBean(String beanName) {
        return null;
    }
}
