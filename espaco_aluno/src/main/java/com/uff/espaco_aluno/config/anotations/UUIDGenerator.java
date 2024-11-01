package com.uff.espaco_aluno.config.anotations;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
public @interface UUIDGenerator {
}
