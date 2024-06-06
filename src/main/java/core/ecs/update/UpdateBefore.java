package core.ecs.update;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE_USE)
public @interface UpdateBefore 
{
	Class<? extends Updatable>[] than();
}
