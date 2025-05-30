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
        if (scopeName == null) {
            throw new IllegalArgumentException("Scope name must not be null");
        }

        String trimmedScope = scopeName.trim();
        this.scope = trimmedScope;
        this.singleton = "singleton".equals(trimmedScope);
        this.prototype = "prototype".equals(trimmedScope);
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

}
