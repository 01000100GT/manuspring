package com.ssj.spring;

public class BeanDefinition {
    private Class<?> type;
    private String scope;
    private String beanName;

    // 默认单例
    private boolean singleton = true;
    private boolean prototype = false;

    public BeanDefinition(Class<?> type) {
        this.type = type;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public void setPrototype(boolean prototype) {
        this.prototype = prototype;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scopeName) {
        // 如果 scopeName 为 null 或空字符串，使用默认的单例模式
        if (scopeName == null || scopeName.trim().isEmpty()) {
            this.scope = "singleton";
            this.singleton = true;
            this.prototype = false;
            return;
        }

        String trimmedScope = scopeName.trim();
        this.scope = trimmedScope;
        // 只有明确指定为 "prototype" 时才设置为原型模式
        if ("prototype".equals(trimmedScope)) {
            this.singleton = false;
            this.prototype = true;
        } else {
            // 其他所有情况都默认为单例模式
            this.singleton = true;
            this.prototype = false;
        }
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

}
