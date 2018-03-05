package comp3350.iPuP.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amanjyot on 2018-03-04.
 */

public class User implements Parcelable
{
    private long userID;
    private String username;

    public User(Long userID, String username)
    {
        this.userID = userID;
        this.username = username;
    }

    /**
     * Use when reconstructing User object from parcel
     * This will be used only by the 'CREATOR'
     * @param in a parcel to read this object
     */
    public User(Parcel in)
    {
        this.userID = in.readLong();
        this.username = in.readString();
    }

    /**
     * Define the kind of object that you gonna parcel,
     * You can use hashCode() here
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Actual object serialization happens here, Write object content
     * to parcel one by one, reading should be done according to this write order
     * @param dest parcel
     * @param flags Additional flags about how the object should be written
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(userID);
        dest.writeString(username);
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     *
     * If you don’t do that, Android framework will through exception
     * Parcelable protocol requires a Parcelable.Creator object called CREATOR
     */
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getUserID()
    {
        return this.userID;
    }

    public String getUsername()
    {
        return this.username;
    }

    @Override
    public String toString()
    {
        return this.userID + "\n" + this.username;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null && other.getClass() == User.class)
        {
            User otherUser = (User) other;
            if (this.userID == otherUser.userID && this.username.equals(otherUser.username))
            {
                return true;
            }
        }

        return false;
    }
}
