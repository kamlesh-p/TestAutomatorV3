package test.automator.common;

/**
 * This class throws exception
 * 
 * @author kamalesh.p
 * 
 */
public class ExceptionHandler extends RuntimeException {

    /**
     * throw exception
     */
    private static final long serialVersionUID = 1L;

    public ExceptionHandler(final Exception e) {
        super(e);
    }

}
