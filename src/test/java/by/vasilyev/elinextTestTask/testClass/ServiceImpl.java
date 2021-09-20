package by.vasilyev.elinextTestTask.testClass;

import by.vasilyev.elinextTestTask.annotation.Inject;

public class ServiceImpl implements Service {

	private DAOClass daoClass;
	private CommandClass commandClass;

	public ServiceImpl() {
	}
	
	@Inject
	public ServiceImpl(DAOClass daoClass, CommandClass commandClass) {
		this.daoClass = daoClass;
		this.commandClass = commandClass;
	}
	
	public DAOClass getDaoClass() {
		return daoClass;
	}

	public CommandClass getCommandClass() {
		return commandClass;
	}

	public void method() {
		System.out.println("Run method from ServiceImpl object...");
		daoClass.execute();
		commandClass.execute();
	}

}
