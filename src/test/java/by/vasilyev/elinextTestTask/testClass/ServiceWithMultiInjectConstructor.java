package by.vasilyev.elinextTestTask.testClass;

import by.vasilyev.elinextTestTask.annotation.Inject;

public class ServiceWithMultiInjectConstructor implements Service {

	private DAOClass daoClass;
	private CommandClass commandClass;

	public ServiceWithMultiInjectConstructor() {
	}

	@Inject
	public ServiceWithMultiInjectConstructor(DAOClass daoClass) {
		this.daoClass = daoClass;

	}

	@Inject
	public ServiceWithMultiInjectConstructor(DAOClass daoClass, CommandClass commandClass) {
		this.daoClass = daoClass;
		this.commandClass = commandClass;
	}

	public void method() {
		System.out.println("Run method from ServiceWithMultiInjectConstructor object...");
		daoClass.execute();
		commandClass.execute();
	}

}
