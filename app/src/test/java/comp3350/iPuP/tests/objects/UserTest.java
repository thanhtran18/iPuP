package comp3350.iPuP.tests.objects;

import junit.framework.TestCase;

import comp3350.iPuP.objects.User;

/**
 * Created by Amanjyot on 2018-03-04.
 */

public class UserTest extends TestCase{

    public UserTest(String arg0)
    {
        super(arg0);
    }

    public void testUsers()
    {
        User user = new User((long)100,"User1");
        assertEquals(user.getUserID(),(long)100);
        assertEquals(user.getUsername(),"User1");
        assertTrue(user.equals(new User((long)100,"User1")));
        User user2 = new User((long)101,"User2");
        assertEquals(user.getUserID(),(long)101);
        assertEquals(user.getUsername(),"User2");
        assertTrue(user.equals(new User((long)101,"User2")));

        assertFalse(user.equals(user2));

    }
}
