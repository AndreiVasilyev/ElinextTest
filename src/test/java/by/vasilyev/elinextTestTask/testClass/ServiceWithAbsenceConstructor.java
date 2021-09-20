package by.vasilyev.elinextTestTask.testClass;

public class ServiceWithAbsenceConstructor implements Service {

	private DAOClass daoClass;
	private CommandClass commandClass;

	public ServiceWithAbsenceConstructor(DAOClass daoClass, CommandClass commandClass) {
		this.daoClass = daoClass;
		this.commandClass = commandClass;
	}

	@Override
	public void method() {
		System.out.println("Run method from ServiceWithAbsenceConstructor object...");
		daoClass.execute();
		commandClass.execute();
	}
}
