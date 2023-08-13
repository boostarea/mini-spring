package com.minis.spring.context;

import java.util.EventObject;

//ApplicationEvent 继承了 Java 工具包内的 EventObject，我们是在 Java 的事件 监听的基础上进行了简单的封装。
public class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
