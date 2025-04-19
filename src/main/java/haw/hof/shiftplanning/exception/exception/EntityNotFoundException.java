package haw.hof.shiftplanning.exception.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String module, Integer id) {
        super("Could not find " + module + " with id " + id);
    }

    public EntityNotFoundException(String module, String name) {
        super("Could not find " + module + " with name " + name);
    }

    public EntityNotFoundException(Integer id) {
        super("Id " + id + " not found");
    }

    public EntityNotFoundException(String name) {
        super("No " + name + " found");
    }
}