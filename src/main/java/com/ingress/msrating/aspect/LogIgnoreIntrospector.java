package com.ingress.msrating.aspect;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.ingress.msrating.annotation.LogIgnore;

public class LogIgnoreIntrospector extends JacksonAnnotationIntrospector {

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember m) {
        var logIgnore = m.getAnnotation(LogIgnore.class);
        if (nonNull(logIgnore)) return true;
        return super.hasIgnoreMarker(m);
    }
}
