package com.example.shenhaichen.mobileassistant.dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Identifies a type that the injector only instantiates once. Not inherited.
 * 仅实例化一次，没有继承
 * Created by shenhaichen on 03/01/2018.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface FragmentScope {
}
