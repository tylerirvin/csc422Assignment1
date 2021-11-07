import java.util.ArrayList;
import java.util.Scanner;

public class CSC422Assignment1
{
    private static ArrayList<Pet> ALLPETS;
  public static void main(String[] args)
  {
      System.out.println("Pet Database Program.");
      menu();
    
  } /* End main() */
  
  private static void menu()
  {
      Scanner scIn = new Scanner(System.in);
      ALLPETS = new ArrayList<>();
      
      int opt = 0;
      while (opt != 7)
      {
          try
          {
              printMenu();
              System.out.print("Your choice: ");
              opt = Integer.parseInt(scIn.nextLine());
              
              switch (opt)
              {
                  case 1 -> /* View all */
                  {
                      viewPets();
                  }
                  case 2 -> /* Add */
                  {
                      addPets();
                  }
                  case 3 -> /* Update */
                  {
                      underDev();
                  }
                  case 4 -> /* Remove */
                  {
                      underDev();
                  }
                  case 5 -> /* Search name */
                  {
                      searchName();
                  }
                  case 6 -> /* Search age */
                  {
                      searchAge();
                  }
                  case 7 -> System.out.println("\nGoodbye!");
                  default -> System.out.println("Please enter an integer between 1 & 7.");
              }
              
          }
          catch (NumberFormatException nfe)
          {
              System.out.println("Please enter an integer between 1 & 7.");
          }
      }
  }  /* End menu() */
  
  private static void printMenu()
  {
      System.out.println("\nWhat would you like to do?");
      System.out.println("1) View all pets");
      System.out.println("2) Add more pets");
      System.out.println("3) Update an existing pet");
      System.out.println("4) Remove an existing pet");
      System.out.println("5) Search pets by name");
      System.out.println("6) Search pets by age");
      System.out.println("7) Exit program");
  }  /* End printMenu() */
  
  
  private static void addPets()
  {
      Scanner scAdd = new Scanner(System.in);
      System.out.println("\nAdd pets. Enter \"done\" to return to menu.");    /* User instructions (once only) */
      int addIncrement = 0;                   /* Int for pets added */
      
      while(true)
      {
          System.out.print("Add pet (name age): ");               /* Prompt then read line */
          String userIn = scAdd.nextLine();
          if (userIn.toLowerCase().startsWith("done ") || userIn.equalsIgnoreCase("done"))     /* If user enters done or 'done ', print summary of action then return to menu */
          {
              String output;
              output = (addIncrement == 1 ? "1 pet added" : addIncrement + " pets added");     /* Grammer adjust for single adds */
              System.out.println(output);
              break;
          }
          else                                                  /* CASE: user didn't enter done */
          {
              try
              {
                int breakPoint = userIn.indexOf(" ");
                if (breakPoint < 1)                               /* CASE: user entered space as the first character or no space was entered (not including done scenario) */
                {
                    System.out.println("Pet not added. Enter pet name followed by space followed by age as an integer.");
                }
                else
                {
                    String name = userIn.substring(0, userIn.indexOf(" "));                 /* Everything before space is name */
                    int age = Integer.parseInt(userIn.substring(userIn.indexOf(" ") + 1));  /* Try and parse after the space for the age */
                    if (age >= 0)                                                           /* CASE: non-negative age parsed correctly, add the pet to the arraylist */
                    {
                        ALLPETS.add(new Pet(name, age));
                        addIncrement++;
                    }
                    else                                                                    /* CASE: negative age parsed correctly, don't add and warn user */
                        System.out.println("Pet not added. Age must be non-negative.");
                    }
               
              }
              catch(NumberFormatException nfe)                                          /* CASE: entry after space doesn't parse to Integer */
              {
                  System.out.println("Pet not added. Enter age as a non-negative integer.");
              }
          }
      }
  }  /* End addPets() */
  

  private static void printPet(Pet pet)
  {
      System.out.printf("| %3d | %-10s | %4d |%n", pet.getId(), pet.getName(), pet.getAge());
      
  }   /* End printPet() */

  
  private static void printResultsHeader()
  {
      System.out.print("+------");       /* ID column plus left and right borders */
      System.out.print("------------");  /* Name column, no borders */
      System.out.println("-------+");    /* Age column plus both borders */
      
      System.out.println("|  ID | NAME       | AGE  |");
      
      System.out.print("+------");       /* ID column plus left and right borders */
      System.out.print("------------");  /* Name column, no borders */
      System.out.println("-------+");    /* Age column plus both borders */
  }   /* End printREsultsHeader() */
  
  private static void printResultsFooter(int results)
  {
      System.out.print("+------");       /* ID column plus left and right borders */
      System.out.print("------------");  /* Name column, no borders */
      System.out.println("-------+");    /* Age column plus both borders */
      
      String output;
      output = (results == 1 ? "1 row in set." : results + " rows in set.");
      System.out.println(output);
  }   /* End printResultsFooter */
  
  
  private static void searchAge()
  {
      Scanner ageIn = new Scanner(System.in);
      int results = 0;                  /* Accumulator for matches */
      
      System.out.print("Enter an age to search: ");
      
      try
      {
          int age = Integer.parseInt(ageIn.nextLine());
          
          printResultsHeader();
          for(Pet pet: ALLPETS)
              if (pet.getAge() == age)
              {
                  printPet(pet);
                  results++;
              }
          printResultsFooter(results);
      }
      catch (NumberFormatException nfe)     /* CASE: user input doesn't parse to an Integer. */
      {
          System.out.println("Age not recognized.");
      }
  }  /* End searchAge() */
  
  
  private static void searchName()
  {
      Scanner useIn = new Scanner(System.in);
      int results = 0;
      
      System.out.print("\nEnter a name to search: ");
      String name = useIn.nextLine();
      
      
      printResultsHeader();
      for(Pet pet: ALLPETS)
      {
          if (pet.getName().equalsIgnoreCase(name))    /* Compare regardless of capitalization */
          {
              printPet(pet);
              results++;
          }
      }
      printResultsFooter(results);
  }  /* End searchName() */
  
  
  private static void underDev()
  {
      System.out.println("This function is currently under development");
  }  /* End underDev() */
  
  
  private static void viewPets()
  {
      printResultsHeader();
      ALLPETS.forEach(pet ->
        {
          printPet(pet);
        });
      printResultsFooter(ALLPETS.size());
  }  /* End viewPets() */
}