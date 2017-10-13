package com.example.fi.rinkkasatiainen.util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class Struct {

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class ForClass {
        private Object object;

        public ForClass(Object object) {
            this.object = object;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode( object );
        }

        @Override
        public boolean equals(Object that) {
            return EqualsBuilder.reflectionEquals(this.object, that);
        }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this.object, ToStringStyle.SHORT_PREFIX_STYLE);
        }

        // usage
        /*
    @Override
    public boolean equals(Object o) {
        return new Struct.ForClass(this).equals( o );
    }

    @Override
    public int hashCode() {
        return new Struct.ForClass(this).hashCode();
    }

    @Override
    public String toString() {
        return new Struct.ForClass(this).toString();
    }
        * */
    }

}
