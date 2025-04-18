package haw.hof.shiftplanning.exception.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String entity, Integer id) {
        super("There is already a " + entity + " with id " + id);
    }

    public EntityAlreadyExistsException(String entity, String name) {
        super("There is already a " + entity + " with name " + name);
    }

    public EntityAlreadyExistsException(Integer id) {
        super("Id " + id + " is already present");
    }
}