package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.*;

import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.data_manager.IDataManager;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static nl.cwi.examples.state_machine.SimpleDoors.*;

public class RunStateMachine {

    public static void main(String[] args) {
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema =
                SchemaLoader.load(schemaFactory, Machine.class, State.class, Transition.class);
        final IDataManager dataManager = new BasicDataManager();
        final StateMachineFactory stateMachineFactory =
                dataManager.factory(StateMachineFactory.class, stateMachineSchema);

        final Machine doorStateMachine = doors(stateMachineFactory);

        interpretStateMachine(doorStateMachine, new LinkedList<>(Arrays.asList(
                Doors.LOCK_EVENT,
                Doors.UNLOCK_EVENT,
                Doors.OPEN_EVENT)));
        System.out.println();
        System.out.println();
        printStateMachine(doorStateMachine);
    }

    public static void interpretStateMachine(Machine stateMachine, List<String> commands) {
        State current = stateMachine.start();
        for (String event : commands) {
            for (Transition trans : current.out()) {
                if (trans.event().equals(event)) {
                	System.out.println("event = " + event);
                    current = trans.to();
                    break;
                }
            }
        }
    }
    
    static void printStateMachine(Machine m) {
    	  for (State s : m.states()){
    	    String start = (s.name() == m.start().name())?"(START)":"";
    	    System.out.println("* State " + s.name() + " " + start);
    	    for (Transition t : s.out())
    	      System.out.println(
    	        "** Transition " + t.event() + " to " + t.to().name());
    	  }
    	}
    
    static void printStateMachineFunc(Machine m) {
        System.out.println("start state: " + m.start().name());
        System.out.println("states: " + 
          m.states().stream()
            .map(s -> s.name())
            .collect(Collectors.toSet()));
        System.out.println("transitions: " + 
          m.states().stream()
            .flatMap(s -> s.out().stream().map(t -> t.event()))
            .collect(Collectors.toSet()));
    }
}
