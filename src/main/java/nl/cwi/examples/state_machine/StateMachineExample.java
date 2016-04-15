package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.*;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
import org.apache.log4j.PropertyConfigurator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StateMachineExample {

    public final static String OPEN_STATE       = "Open";
    public final static String CLOSED_STATE     = "Closed";
    public final static String OPEN_TRANSITION  = "open";
    public final static String CLOSE_TRANSITION = "close";

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/main/resources/logger.properties");

        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema =
                SchemaLoader.load(schemaFactory, schemaSchema, Machine.class, State.class, Transition.class);
        final BasicFactory basicFactoryForStateMachines = new BasicFactory(StateMachineFactory.class, stateMachineSchema);
        final StateMachineFactory stateMachineFactory = basicFactoryForStateMachines.make();

        // ========================================================
        // Door State Machine definition
        final Machine doorStateMachine = stateMachineFactory.Machine();

        // Open State definition
        final State openState = stateMachineFactory.State(OPEN_STATE);
        openState.machine(doorStateMachine);

        // Closed State definition
        final State closedState = stateMachineFactory.State(CLOSED_STATE);
        closedState.machine(doorStateMachine);

        // Close Transition
        final Transition closeTransition = stateMachineFactory.Transition(CLOSE_TRANSITION);
        closeTransition.from(openState);
        closeTransition.to(closedState);

        // Open Transition
        final Transition openTransition = stateMachineFactory.Transition(OPEN_TRANSITION);
        openTransition.from(closedState);
        openTransition.to(openState);

        // State machine start
        doorStateMachine.start(openState);

        final List<String> commands = new LinkedList<>(Arrays.asList(
                CLOSE_TRANSITION,
                OPEN_TRANSITION));

        interpretStateMachine(doorStateMachine, commands);
    }

    private static void interpretStateMachine(Machine stateMachine, List<String> commands) {

        stateMachine.current(stateMachine.start());

        for (String event : commands) {
            for (Transition trans : stateMachine.current().out()) {
                if (trans.event().equals(event)) {
                    stateMachine.current(trans.to());
                    break;
                }
            }
        }
    }
}