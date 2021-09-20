package by.vasilyev.elinextTestTask.diContainer;

import java.lang.reflect.InvocationTargetException;

import by.vasilyev.elinextTestTask.exception.BindingNotFoundException;
import by.vasilyev.elinextTestTask.exception.ConstructorNotFoundException;
import by.vasilyev.elinextTestTask.exception.TooManyConstructorsException;

public interface Provider<T> {

	T getInstance() throws TooManyConstructorsException, ConstructorNotFoundException, InstantiationException,
	IllegalAccessException, IllegalArgumentException, InvocationTargetException, BindingNotFoundException;
}
