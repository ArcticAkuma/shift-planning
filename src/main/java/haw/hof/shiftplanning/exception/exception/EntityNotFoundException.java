package haw.hof.shiftplanning.exception.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String name, Integer id) {
        super("Could not find " + name + " with id " + id);
    }

    public EntityNotFoundException(Integer id) {
        super("Id " + id + " not found");
    }
}