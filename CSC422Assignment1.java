import java.util.ArrayList;
import java.util.Scanner;

public class CSC422Assignment1
{
    private static ArrayList<Pet> ALLPETS;
  public static void main(String[] args)
  {
      System.out.println("Pet Database Program.");
      menu();  /* something */
    
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
                      updatePet();
                  }
                  case 4 -> /* Remove */
                  {
                      deletePet();
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
  
/* -----------------------------------------
    Running functions sorted alphabetically
   -----------------------------------------
 */
  
  
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
                if (breakPoint < 1 || breakPoint > userIn.length() - 2)                               /* CASE: Valid index but either first character is a space, no space entered to separate out age */
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
  
  
  private static void deletePet()
  {
      Scanner delIn = new Scanner(System.in);
      viewPets();
      System.out.print("Enter the pet ID to remove: ");
      
      try
      {
          int index = Integer.parseInt(delIn.nextLine());
          if (index >= 0 && index < ALLPETS.size())
          {
              String petDetails = ALLPETS.get(index).getName() + " " + ALLPETS.get(index).getAge();
              ALLPETS.remove(index);
              System.out.println(petDetails + " is removed.");
          }
          else
              System.out.println("Pet ID not found.");   /* CASE: Index parses but outside range of arraylist */
      }
      catch (NumberFormatException nfe)         /* CASE: Index entered doesn't parse to int */
      {
          System.out.println("Pet ID not found.");
      }
  }  /* End deletePet() */
  

  private static void printPet(int index, Pet pet)
  {
      System.out.printf("| %3d | %-10s | %4d |%n", index, pet.getName(), pet.getAge());
      
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
          for (int i = 0; i < ALLPETS.size(); i++)
              if (ALLPETS.get(i).getAge() == age)
              {
                  printPet(i, ALLPETS.get(i));
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
      for (int i = 0; i < ALLPETS.size(); i++)
      {
          if (ALLPETS.get(i).getName().equalsIgnoreCase(name))    /* Compare regardless of capitalization */
          {
              printPet(i, ALLPETS.get(i));
              results++;
          }
      }
      printResultsFooter(results);
  }  /* End searchName() */
  
  
  private static void underDev()          /* Should be defunct */
  {
      System.out.println("This function is currently under development");
  }  /* End underDev() */
  
  
  private static void updatePet()
  {
      Scanner upIn = new Scanner(System.in);
      viewPets();
      System.out.print("\nEnter the pet ID to update: ");
      
      try
      {
          int index = Integer.parseInt(upIn.nextLine());
          if (index >= 0 && index < ALLPETS.size())
          {
              String oldName = ALLPETS.get(index).getName();
              int oldAge = ALLPETS.get(index).getAge();
              
              System.out.print("Enter new name and age: ");
              String userInput = upIn.nextLine();
              int breakpoint = userInput.indexOf(" ");
              if (breakpoint < 1 || breakpoint > userInput.length() - 2)                     /* CASE: Valid index but either first character is a space, no space entered to separate out age */
                  System.out.println("1.Pet not updated. Data must be name followed by space followed by non-negative integer.");
              else
              {
                  try
                  {
                      String newName = userInput.substring(0, breakpoint);
                      int newAge = Integer.parseInt(userInput.substring(breakpoint + 1));
                      if (newAge >= 0)
                      {
                          ALLPETS.get(index).updatePet(newName, newAge);
                          System.out.println(oldName + " " + oldAge + " changed to " + newName + " " + newAge + ".");
                      }
                      else                             /* CASE: Valid index, age parses, but age is negative */
                          System.out.println("2.Pet not updated. Data must be name followed by space followed by non-negative integer.");
                  }
                  catch (NumberFormatException nfe2)   /* CASE: Valid index but age doesn't parse to int */
                  {
                      System.out.println("3.Pet not updated. Data must be name followed by space followed by non-negative integer.");
                  }
              }
          }
          else                                /* CASE: Index provided is an int but outside range of arraylist */
              System.out.println("Not a valid ID.");
      }
      catch (NumberFormatException nfe)       /* CASE: index provided doesn't parse to int */
      {
          System.out.println("Not a valid ID.");
      }
  }  /* End updatePet() */
  
  
  private static void viewPets()
  {
      printResultsHeader();
      for (int i = 0; i < ALLPETS.size(); i++)
          printPet(i, ALLPETS.get(i));
      printResultsFooter(ALLPETS.size());
  }  /* End viewPets() */
}