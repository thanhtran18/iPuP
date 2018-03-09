package comp3350.iPuP.objects;

/**
 * Created by Amanjyot on 2018-03-08.
 */

public class DAOException extends Exception{

    public DAOException() {
        super();
    }

    public DAOException(String message)
    {
        super(message);
    }

    public DAOException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DAOException(Throwable cause)
    {
        super(cause);
    }
}
