import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class PetDatabase {
    private static PetDatabase petDb = null;
    private static PetList pets;
    
    private PetDatabase()       /* Private constructor for singleton class */
    {
        pets = new PetList();
    } /* End constructor */
    
    
    public static PetDatabase getInstance()    /* Singleton creation or return for class */
    {
        if (petDb == null)
            petDb = new PetDatabase();
        
        return petDb;
    } /* End getInstance() */
    
    
    public PetList load(String filename) throws IOException   /* Load file (if present) CASE: not present: return an empty database */
    {
        try (FileInputStream fileIn = new FileInputStream(filename))   /* Input stream of filename */
        {
            try (ObjectInputStream objIn = new ObjectInputStream(fileIn))  /* Object parser for input stream */
            {
                Object obj = objIn.readObject();               /* Should only be a single object (a PetList) so just read the first */
                if (obj instanceof PetList)
                    pets = (PetList) obj;

                objIn.close();                           /* Close parser and file */
                fileIn.close();

            }
            catch (ClassNotFoundException cnfe){}   /* Don't really care; just create new */
        }
        catch (FileNotFoundException fnfe){}    /* Don't really care; just create new */
        
        return pets;
    } /* End load(filename) */
    
    
    public boolean save(String filename)  /* Save PetList to a file at filename location */
    {
        try (FileOutputStream fileOut = new FileOutputStream(filename))   /* Open output stream */
        {
            try (ObjectOutputStream objOut = new ObjectOutputStream(fileOut))   /* Parser into object */
            {
                objOut.writeObject(pets);     /* Write PetList as serialized object */
                objOut.close();               /* Close */
                fileOut.close();
                return true;                  /* CASE: Successful save */
            }
            catch (Exception e)               /* CASE: couldn't parse. Return false */
            {
                e.printStackTrace();
                return false;
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
    } /* End save(filename) */
    
    
    public PetList Pets()   /* Access to PetList object */
    {
        return pets;
    } /* End Pets() */
}