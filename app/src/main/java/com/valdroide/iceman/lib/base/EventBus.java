package com.valdroide.iceman.lib.base;

/**
 * Created by LEO on 1/12/2016.
 */
public interface EventBus {
        void register(Object subscriber);
        void unregister(Object subscriber);
        void post(Object event);
    }
