import java.io.Serializable;
import java.util.ArrayList;

public class PetList implements Serializable
{
    private static final long serialVersionUID = 6529685098266757690L;    /* Serial key */
    private final ArrayList<Pet> pets;
    
    public PetList()
    {
        pets = new ArrayList<>();
    } /* End constructor */
    
    
    public int addPet(Pet pet)   /* Add pet. Returns index of added pet. */
    {
        pets.add(pet);
        return pets.indexOf(pet);  /* Return index of newly added pet */
    } /* End addPet(Pet) */
    
    
    public boolean deletePet(int index)  /* Delete pet at location. Returns success. */
    {
        if (index >= 0 && index < pets.size())
        {
            pets.remove(index);
            return true;
        }
        else                     /* CASE: attempting to delete outside of the arrayList size */
            return false;
    } /* End deletePet(index) */
    
    
    public Pet getPet(int index)  /* Find pet at index of PetList. Returns Pet and methods */
    {
        if (index >= 0 && index < pets.size())
            return pets.get(index);
        else
            return null;
    } /* End getPet(index) */
    
    
    public int updatePet(int index, Pet newInfo) throws AgeException   /* Update exist pet. Return index of updated Pet. */
    {
        if (index >= 0 && index < pets.size())   /* Ensure index is in range */
        {
            try
            {
                pets.get(index).updatePet(newInfo.getName(), newInfo.getAge());
                return index;
            }
            catch (Exception e)     /* CASE: couldn't update the Pet object */
            {
                return -1;
            }
        }
        else
            return -1;
    } /* End updatePet(index, new Pet) */
    
    
    public int size()   /* Returns size of PetList */
    {
        return pets.size();
    } /* End size() */
    
    
    public ArrayList<Pet> getAll()  /* Returns entirety of PetList */
    {
        return pets;
    } /* End getAll() */
}