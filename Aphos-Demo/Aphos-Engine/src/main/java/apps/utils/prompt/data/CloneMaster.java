package apps.utils.prompt.data;

import java.io.*;

/**
 * Created by Alexander on 10/26/2023
 */
public class CloneMaster {
    public static Object deepCopy(Object object) {
        FileOutputStream fos = null;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream("somefilename");
            out = new ObjectOutputStream(fos);
            out.writeObject(object);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
        }

        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = new FileInputStream("somefilename");
            in = new ObjectInputStream(fis);
            Object deepCopy = in.readObject();
            in.close();
            return deepCopy;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
