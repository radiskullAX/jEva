package eu.animegame.jeva.poc;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import eu.animegame.jeva.core.IrcBaseEvent;

/**
 *
 * @author radiskull
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
public @interface IrcEvent {
	String type() default "";

    Class<IrcBaseEvent> clazz() default IrcBaseEvent.class;
}
