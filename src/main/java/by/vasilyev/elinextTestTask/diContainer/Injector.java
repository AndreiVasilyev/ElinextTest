package by.vasilyev.elinextTestTask.diContainer;

import java.lang.reflect.InvocationTargetException;

import by.vasilyev.elinextTestTask.exception.BindingNotFoundException;
import by.vasilyev.elinextTestTask.exception.ConstructorNotFoundException;
import by.vasilyev.elinextTestTask.exception.TooManyConstructorsException;

public interface Injector {

	<T> Provider<T> getProvider(Class<T> type) throws TooManyConstructorsException, ConstructorNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, BindingNotFoundException;

	<T> void bind(Class<T> intf, Class<? extends T> impl);

	<T> void bindSingleton(Class<T> intf, Class<? extends T> impl);
}
