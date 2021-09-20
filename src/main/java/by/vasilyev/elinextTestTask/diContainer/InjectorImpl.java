package by.vasilyev.elinextTestTask.diContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import by.vasilyev.elinextTestTask.annotation.Inject;
import by.vasilyev.elinextTestTask.exception.BindingNotFoundException;
import by.vasilyev.elinextTestTask.exception.ConstructorNotFoundException;
import by.vasilyev.elinextTestTask.exception.TooManyConstructorsException;

public class InjectorImpl implements Injector {

	private Map<Class<?>, Class<?>> bindsMap = new HashMap<Class<?>, Class<?>>();
	private Map<Class<?>, Object> singletoneCacheMap = new HashMap<Class<?>, Object>();

	public synchronized <T> Provider<T> getProvider(Class<T> type)
			throws TooManyConstructorsException, ConstructorNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, BindingNotFoundException {

		return findImplementationType(type) == null ? null : () -> {
			return createInstance(type);
		};
	}

	public <T> void bind(Class<T> intf, Class<? extends T> impl) {
		bindsMap.put(intf, impl);
		if (singletoneCacheMap.containsKey(impl)) {
			singletoneCacheMap.remove(impl);
		}
	}

	public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
		bindsMap.put(intf, impl);
		singletoneCacheMap.put(impl, null);
	}

	@SuppressWarnings("unchecked")
	private <T> T createInstance(Class<T> interfaceType)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			ConstructorNotFoundException, TooManyConstructorsException, BindingNotFoundException {
		T instance = null;
		Class<?> implementationType = findImplementationType(interfaceType);
		if (implementationType != null) {
			instance = (T) constructInstanceWithInjection(implementationType);
		}
		return instance;
	}

	private Class<?> findImplementationType(Class<?> interfaceType) {
		Class<?> implementationType = bindsMap.get(interfaceType);
		if (implementationType == null && bindsMap.containsValue(interfaceType)) {
			implementationType = interfaceType;
		}
		
		return implementationType;
	}

	private Object constructInstanceWithInjection(Class<?> implementationType)
			throws ConstructorNotFoundException, TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, BindingNotFoundException {

		Object instanceWithInjection = null;
		boolean isSingletone = singletoneCacheMap.containsKey(implementationType);
		if (isSingletone) {
			instanceWithInjection = singletoneCacheMap.get(implementationType);
			if (instanceWithInjection != null) {
				return instanceWithInjection;
			}
		}
		Constructor<?> constructorToInvoke = getConstructorToInvoke(implementationType);
		int constructorParametrCount = constructorToInvoke.getParameterCount();
		Object[] injections = new Object[constructorParametrCount];
		int counter = 0;
		for (Class<?> constructorParametrType : constructorToInvoke.getParameterTypes()) {
			Class<?> constructorParametrImplementationType = findImplementationType(constructorParametrType);
			if (constructorParametrImplementationType == null) {
				throw new BindingNotFoundException(
						"There is no implementation types for " + constructorParametrType.getSimpleName());
			}
			Object injectionInstance = constructInstanceWithInjection(constructorParametrImplementationType);
			injections[counter++] = injectionInstance;
		}
		instanceWithInjection = constructorToInvoke.newInstance(injections);
		if (isSingletone) {
			singletoneCacheMap.put(implementationType, instanceWithInjection);
		}
		return instanceWithInjection;
	}

	private Constructor<?> getConstructorToInvoke(Class<?> implementationType)
			throws ConstructorNotFoundException, TooManyConstructorsException {
		Constructor<?>[] allConstructors = implementationType.getDeclaredConstructors();
		Constructor<?> injectedConstructor = null;
		Constructor<?> defaultConstructor = null;
		for (Constructor<?> constructor : allConstructors) {
			boolean isAnnotationPresent = constructor.isAnnotationPresent(Inject.class);
			if (isAnnotationPresent) {
				if (injectedConstructor == null) {
					injectedConstructor = constructor;
					continue;
				} else {
					throw new TooManyConstructorsException(
							"There is more then one constructor with annotation 'Inject'");
				}
			}
			if (constructor.getParameterCount() == 0) {
				defaultConstructor = constructor;
			}
		}
		if (injectedConstructor == null && defaultConstructor == null) {
			throw new ConstructorNotFoundException("There are no constructors to create instance");
		}
		return injectedConstructor != null ? injectedConstructor : defaultConstructor;
	}

}
