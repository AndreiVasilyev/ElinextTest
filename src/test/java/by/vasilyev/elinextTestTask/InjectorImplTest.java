package by.vasilyev.elinextTestTask;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.Test;

import by.vasilyev.elinextTestTask.diContainer.Injector;
import by.vasilyev.elinextTestTask.diContainer.InjectorImpl;
import by.vasilyev.elinextTestTask.diContainer.Provider;
import by.vasilyev.elinextTestTask.exception.BindingNotFoundException;
import by.vasilyev.elinextTestTask.exception.ConstructorNotFoundException;
import by.vasilyev.elinextTestTask.exception.TooManyConstructorsException;
import by.vasilyev.elinextTestTask.testClass.CommandClass;
import by.vasilyev.elinextTestTask.testClass.CommandClassImpl;
import by.vasilyev.elinextTestTask.testClass.DAOClass;
import by.vasilyev.elinextTestTask.testClass.DAOClassImpl;
import by.vasilyev.elinextTestTask.testClass.Service;
import by.vasilyev.elinextTestTask.testClass.ServiceImpl;
import by.vasilyev.elinextTestTask.testClass.ServiceImplWithImplemetationTypeAsParametr;
import by.vasilyev.elinextTestTask.testClass.ServiceWithAbsenceConstructor;
import by.vasilyev.elinextTestTask.testClass.ServiceWithDefaultConstructor;
import by.vasilyev.elinextTestTask.testClass.ServiceWithMultiInjectConstructor;
import by.vasilyev.elinextTestTask.testClass.SimpleClass;
import by.vasilyev.elinextTestTask.testClass.SimpleClassImpl;

public class InjectorImplTest {

	@Test
	public void testExistingBinding() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(SimpleClass.class, SimpleClassImpl.class);
		Provider<SimpleClass> serviceProvider = injector.getProvider(SimpleClass.class);
		assertNotNull(serviceProvider);
		assertNotNull(serviceProvider.getInstance());
		assertSame(SimpleClassImpl.class, serviceProvider.getInstance().getClass());
	}

	@Test(expected = TooManyConstructorsException.class)
	public void testMultiConstructorWithInjectAnnotation() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(Service.class, ServiceWithMultiInjectConstructor.class);
		Provider<Service> serviceProvider = injector.getProvider(Service.class);
		serviceProvider.getInstance();
	}

	@Test
	public void testUseDefaultConstructor() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(Service.class, ServiceWithDefaultConstructor.class);
		Provider<Service> serviceProvider = injector.getProvider(Service.class);
		Service service = serviceProvider.getInstance();
		assertNotNull(serviceProvider);
		assertNotNull(service);
		assertSame(ServiceWithDefaultConstructor.class, service.getClass());
	}

	@Test(expected = ConstructorNotFoundException.class)
	public void testWithAbsenceConstructor() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(Service.class, ServiceWithAbsenceConstructor.class);
		Provider<Service> serviceProvider = injector.getProvider(Service.class);
		serviceProvider.getInstance().method();
	}

	@Test(expected = BindingNotFoundException.class)
	public void testAbsenceBindingForParametr() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(Service.class, ServiceImpl.class);
		injector.bind(DAOClass.class, DAOClassImpl.class);
		Provider<Service> serviceProvider = injector.getProvider(Service.class);
		serviceProvider.getInstance().method();
	}

	@Test
	public void testAbsenceBindingForSourceClass() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		Provider<Service> serviceProvider = injector.getProvider(Service.class);
		assertNull(serviceProvider);
	}

	@Test
	public void testExistingInjection() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(Service.class, ServiceImpl.class);
		injector.bind(DAOClass.class, DAOClassImpl.class);
		injector.bind(CommandClass.class, CommandClassImpl.class);
		injector.bind(SimpleClass.class, SimpleClassImpl.class);
		Provider<Service> serviceProvider = injector.getProvider(Service.class);
		ServiceImpl service = (ServiceImpl) serviceProvider.getInstance();
		assertNotNull(serviceProvider);
		assertNotNull(service);
		assertNotNull(service.getCommandClass());
		assertNotNull(service.getDaoClass());
		assertNotNull(((CommandClassImpl) service.getCommandClass()).getSimpleClass());
	}

	@Test
	public void testUsingImplemetationClassAsParametrTypeInConstructor() throws TooManyConstructorsException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			ConstructorNotFoundException, BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(Service.class, ServiceImplWithImplemetationTypeAsParametr.class);
		injector.bind(DAOClass.class, DAOClassImpl.class);
		injector.bind(CommandClass.class, CommandClassImpl.class);
		injector.bind(SimpleClass.class, SimpleClassImpl.class);
		Provider<Service> serviceProvider = injector.getProvider(Service.class);
		Service service = serviceProvider.getInstance();
		assertNotNull(serviceProvider);
		assertNotNull(service);
		assertSame(ServiceImplWithImplemetationTypeAsParametr.class, service.getClass());
		assertNotNull(((ServiceImplWithImplemetationTypeAsParametr) service).getCommandClass());
		assertSame(CommandClassImpl.class,
				((ServiceImplWithImplemetationTypeAsParametr) service).getCommandClass().getClass());
		assertNotNull(((ServiceImplWithImplemetationTypeAsParametr) service).getDaoClass());
		assertSame(DAOClassImpl.class, ((ServiceImplWithImplemetationTypeAsParametr) service).getDaoClass().getClass());
	}

	@Test
	public void testCreatingPrototypeBean() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(SimpleClass.class, SimpleClassImpl.class);
		Provider<SimpleClass> serviceProvider = injector.getProvider(SimpleClass.class);
		SimpleClass serviceFirst = serviceProvider.getInstance();
		SimpleClass serviceSecond = serviceProvider.getInstance();
		assertNotSame(serviceFirst, serviceSecond);
	}

	@Test
	public void testCreatingInjectedPrototypeBean() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(Service.class, ServiceImpl.class);
		injector.bind(DAOClass.class, DAOClassImpl.class);
		injector.bind(CommandClass.class, CommandClassImpl.class);
		injector.bind(SimpleClass.class, SimpleClassImpl.class);
		Provider<Service> serviceProvider = injector.getProvider(Service.class);
		ServiceImpl serviceFirst = (ServiceImpl) serviceProvider.getInstance();
		ServiceImpl serviceSecond = (ServiceImpl) serviceProvider.getInstance();
		DAOClass daoClassInstanceFromFirstService = serviceFirst.getDaoClass();
		DAOClass daoClassInstanceFromScondService = serviceSecond.getDaoClass();
		assertNotSame(daoClassInstanceFromFirstService, daoClassInstanceFromScondService);
	}

	@Test
	public void testCreatingSingletoneBean() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bindSingleton(SimpleClass.class, SimpleClassImpl.class);
		Provider<SimpleClass> serviceProvider = injector.getProvider(SimpleClass.class);
		SimpleClass serviceFirst = serviceProvider.getInstance();
		SimpleClass serviceSecond = serviceProvider.getInstance();
		assertSame(serviceFirst, serviceSecond);
	}

	@Test
	public void testCreatingInjectedSingletoneBean() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bind(Service.class, ServiceImpl.class);
		injector.bind(DAOClass.class, DAOClassImpl.class);
		injector.bind(CommandClass.class, CommandClassImpl.class);
		injector.bindSingleton(SimpleClass.class, SimpleClassImpl.class);
		Provider<Service> serviceProvider = injector.getProvider(Service.class);
		ServiceImpl serviceFirst = (ServiceImpl) serviceProvider.getInstance();
		ServiceImpl serviceSecond = (ServiceImpl) serviceProvider.getInstance();
		SimpleClass simpleClassInstanceFromFirstService = ((CommandClassImpl) serviceFirst.getCommandClass())
				.getSimpleClass();
		SimpleClass simpleClassInstanceFromScondService = ((CommandClassImpl) serviceSecond.getCommandClass())
				.getSimpleClass();
		assertSame(simpleClassInstanceFromFirstService, simpleClassInstanceFromScondService);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testLazyCreatingSingletoneBean() throws TooManyConstructorsException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException,
			BindingNotFoundException {
		Injector injector = new InjectorImpl();
		injector.bindSingleton(SimpleClass.class, SimpleClassImpl.class);
		Provider<SimpleClass> serviceProvider = injector.getProvider(SimpleClass.class);
		Field singletoneCache = Stream.of(injector.getClass().getDeclaredFields())
				.filter((field) -> "singletoneCacheMap".equals(field.getName())).findFirst().get();
		singletoneCache.setAccessible(true);
		Object singletoneBean = ((HashMap<Class<?>, Object>) singletoneCache.get(injector)).get(SimpleClassImpl.class);
		assertNull(singletoneBean);
		SimpleClass serviceFirst = serviceProvider.getInstance();
		singletoneBean = ((HashMap<Class<?>, Object>) singletoneCache.get(injector)).get(SimpleClassImpl.class);
		assertNotNull(singletoneBean);
		assertSame(serviceFirst, singletoneBean);
	}
	
}
