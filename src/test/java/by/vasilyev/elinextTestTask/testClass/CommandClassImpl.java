package by.vasilyev.elinextTestTask.testClass;

import by.vasilyev.elinextTestTask.annotation.Inject;

public class CommandClassImpl implements CommandClass {

	private SimpleClass simpleClass;
	
	@Inject
	public CommandClassImpl(SimpleClass simpleClass) {
		this.simpleClass=simpleClass;
	}
	
	public SimpleClass getSimpleClass() {
		return simpleClass;
	}

	public void execute() {
		System.out.println("Run execute from CommandClassImpl");
		simpleClass.execute();
	}

}
