package com.zerofate.template;

/**
 * @author timon
 * @date 2018/10/18
 */
public abstract class ThisPattern<T extends ThisPattern<T>> {

    public T foo1() {
        return getThis();
    }

    protected abstract T getThis();

    public static class SubThisPattern extends ThisPattern<SubThisPattern> {

        public SubThisPattern foo2() {
            return getThis();
        }

        @Override
        protected SubThisPattern getThis() {
            return this;
        }
    }
}
