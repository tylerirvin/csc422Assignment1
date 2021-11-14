import java.io.Serializable;

public class Pet implements Serializable {
    private static final long serialVersionUID = 6529685098266757691L;   /* Serial match point */
    
    private String name;
    private int age;
    
    public Pet (String name, int age)
    {
        this.name = name;
        this.age = age;
    } /* End constructor */
    
/* SETTERS */    
    public void setName(String name)
    {
        this.name = name;
    } /* End setName(String) */
    
    
    public void setAge(int age)
    {
        this.age = age;
    } /*End setAge(int) */
    
    
    public void updatePet(String name, int age)
    {
        this.name = name;
        this.age = age;
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