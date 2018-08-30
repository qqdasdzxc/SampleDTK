package com.dmitrijkuzmin.sampledtk.di.singleuser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface SingleUserScope {
}
