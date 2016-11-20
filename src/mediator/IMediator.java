package mediator;

/**
 * The class provides functionality for the Mediator design pattern.
 * It is the class from where the Guis and complex gui elements are instantiated
 * and through which they communicate with each other
 * @author Jon
 *
 */
public interface IMediator {
	/**
	 * Sends the user from gui to gui
	 * @param state New state to go to
	 */
	public void handle(Go go);
}
