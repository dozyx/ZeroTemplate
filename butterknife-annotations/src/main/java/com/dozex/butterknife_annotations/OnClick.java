package com.dozex.butterknife_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IdRes;

/**
 * @author dozyx
 * @date 2020-02-04
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface OnClick {
    @IdRes int value();
}
