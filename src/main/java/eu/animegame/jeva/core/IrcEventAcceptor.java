package eu.animegame.jeva.core;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author radiskull
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
public @interface IrcEventAcceptor {
	String command() default "";

    Class<? extends IrcBaseEvent> clazz() default IrcBaseEvent.class;
}
