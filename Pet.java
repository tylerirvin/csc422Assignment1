import java.io.Serializable;

public class Pet implements Serializable {
    private static final long serialVersionUID = 6529685098266757691L;   /* Serial match point */
    
    private String name;
    private int age;
    
    public Pet (String name, int age) throws AgeException
    {
        if (age < 1)
            throw new AgeException("Age must be a positive integer.");
        this.name = name;
        this.age = age;
    } /* End constructor */
    
/* SETTERS */    
    private void setName(String name)
    {
        this.name = name;
    } /* End setName(String) */
    
    
    private void setAge(int age) throws AgeException
    {
        if (age < 1)
            throw new AgeException("Age must be a positive integer.");
        else
            this.age = age;
    } /*End setAge(int) */
    
    
    public void updatePet(String name, int age) throws AgeException
    {
        if (age < 1)
            throw new AgeException("Age must be a positive integer.");
        else
        {
            this.setName(name);
            this.setAge(age);
        }
    } /* End updatePet (name, age) */
    
/* GETTERS */
    public String getName()
    {
        return name;
    } /* End getName() */
    
    
    public int getAge()
    {
        return age;
    } /* End getAge() */
}