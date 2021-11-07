public class Pet {
    private int id;
    private String name;
    private int age;
    
    private static int currId = 0;
    
    public Pet (String name, int age)
    {
        this.name = name;
        this.age = age;
        setId(currId);
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setAge(int age)
    {
        this.age = age;
    }
    
    private void setId(int id)
    {
        this.id = id;
        currId++;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getAge()
    {
        return age;
    }
    
    public int getId()
    {
        return id;
    }
    
    public static int getCurrId()
    {
        return currId;
    }
}
