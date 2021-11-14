/*
    Program:        CSC422Assignment1
    Creator:        Tyler Irvin (irvint@csp.edu)
    Created date:   4 Nov 2011
    Last Revision:  14 Nov 2011
    Version history:Stored on GitHub (https://github.com/tylerirvin/csc422Assignment1)
*/

import java.io.IOException;
import java.util.Scanner;

public class CSC422Assignment1
{
    private static PetDatabase petDb;            /* Database of Pets */
    private static final int MAX_SIZE = 5;       /* Arbitrary max size of database */
    private static final int MAX_AGE = 20;       /* Arbitrary max age for pets */
    private static final Scanner scanIn = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        System.out.println("Pet Database Program.");
        initialize();
        menu();
    } /* End main() */
  
  
  private static void initialize()         /* Create database and load file if present */
  {
      petDb = PetDatabase.getInstance();   /* Create singleton of PetDatabase or return existing */
      
      try                                  /* Load saved data. If not present, an empty PetDatabase is returned */
      {
          petDb.load("savedPets.dat");
      }
      catch (IOException ioe){}
  }  /* End initialize() */
  
  
  private static void menu()
  {
      int opt = 0;
      while (opt != 7)                     /* Opt 7 is close program */
      {
          try
          {
              printMenu();
              System.out.print("Your choice: ");
              opt = Integer.parseInt(scanIn.nextLine());
              
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
                  case 7 -> /*Exit program */
                  {
                      closeProgram();
                  }
                  default -> System.out.println("Please enter an integer between 1 & 7.");
              } /* End switch(opt) */
          }
          catch (NumberFormatException nfe)     /* CASE: Non-integer supplied for menu option */
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
      if (petDb.Pets().size() >= MAX_SIZE)    /* Verify there is room to add more records. If not, warn user and return */
      {
          System.out.println("Database full. Delete entries to add more.");
          return;
      }
      
      System.out.println("\nAdd pets. Enter \"done\" to return to menu.");    /* User instructions (once only) */
      int addIncrement = 0;                                                   /* Int for pets added */
      
      while(true && petDb.Pets().size() < MAX_SIZE)                           /* Add until user enters done or database reaches MAX_SIZE */
      {
          System.out.print("Add pet (name age): ");                           /* Prompt then read line */
          String userIn = scanIn.nextLine();
          if (userIn.toLowerCase().startsWith("done ") || userIn.equalsIgnoreCase("done"))     /* If user enters done or 'done ', print summary of action then return to menu */
          {
              break;
          }
          else                                                                /* CASE: user didn't enter done */
          {
              try
              {
                int breakPoint = userIn.indexOf(" ");
                if (breakPoint < 1 || breakPoint > userIn.length() - 2)       /* CASE: Valid index but either first character is a space, no space entered to separate out age, or space at end but no age entered */
                {
                    System.out.println("Pet not added. Enter pet name followed by space followed by age as an integer.");
                }
                else
                {
                    String name = userIn.substring(0, userIn.indexOf(" "));                 /* Everything before space is name */
                    int age = Integer.parseInt(userIn.substring(userIn.indexOf(" ") + 1));  /* Try and parse after the space for the age */
                    if (age <= MAX_AGE)                                                     /* CASE: age parsed correctly and less than MAX_AGE(inclusive), add the pet to the arraylist */
                    {
                        try                                                                 /* Try to create new pet. Pet throws exception if age < 1 */
                        {
                            Pet newPet = new Pet(name, age);
                            petDb.Pets().addPet(newPet);
                            addIncrement++;
                        }
                        catch (AgeException ae)
                        {
                            System.out.println(ae.getMessage());
                        }
                    }
                    else                                                                    /* CASE: age parsed correctly but above MAX_AGE, don't add and warn user */
                        System.out.println("Pet not added. Age must be less than " + (MAX_AGE + 1) + ".");
                    }
               
              }
              catch(NumberFormatException nfe)                                          /* CASE: entry after space doesn't parse to Integer */
              {
                  System.out.println("Pet not added. Enter age as a positive integer below " + (MAX_AGE + 1) + ".");
              }
          }
      }
      
      String output;
      output = (addIncrement == 1 ? "1 pet added." : addIncrement + " pets added.");     /* Grammer adjust for single adds */
      System.out.println(output);
      
      if (petDb.Pets().size() >= MAX_SIZE)                                               /* In case addition results in a full database, warn user before returning to menu() */
          System.out.println("Database is now full.");
      
  }  /* End addPets() */
  
  
  private static void closeProgram()
  {
      boolean saved = petDb.save("savedPets.dat");         /* Save file to location */
      if (saved)
          System.out.println("\nData saved!\nGoodbye!");
      else
      {
          System.out.println("\nERROR: Data not saved!");
          /* TODO: add in prompt for different save location? */
      }
      scanIn.close();
  }  /* End closeProgram() */
  
  
  private static void deletePet()
  {
      viewPets();
      System.out.print("Enter the pet ID to remove: ");
      
      try
      {
          int index = Integer.parseInt(scanIn.nextLine());
          if (index >= 0 && index < petDb.Pets().size())               /* Verify user input is valid for array size */
          {
              String petDetails = petDb.Pets().getPet(index).getName() + " " + petDb.Pets().getPet(index).getAge();
              petDb.Pets().deletePet(index);
              System.out.println(petDetails + " is removed.");
          }
          else
              System.out.println("Pet ID not found.");   /* CASE: Index parses but outside range of arraylist */
      }
      catch (NumberFormatException nfe)                  /* CASE: Index entered doesn't parse to int */
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
  
  
  private static void printResultsFooter(int results)    /* Prints last line of table and number of results */
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
      int results = 0;                  /* Accumulator for matches */
      
      System.out.print("Enter an age to search: ");
      
      try
      {
          int age = Integer.parseInt(scanIn.nextLine());
          
          printResultsHeader();
          for (int i = 0; i < petDb.Pets().size(); i++)
              if (petDb.Pets().getPet(i).getAge() == age)
              {
                  printPet(i, petDb.Pets().getPet(i));
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
      int results = 0;
      
      System.out.print("\nEnter a name to search: ");
      String name = scanIn.nextLine();
      
      printResultsHeader();
      for (int i = 0; i < petDb.Pets().size(); i++)
      {
          if (petDb.Pets().getPet(i).getName().equalsIgnoreCase(name))    /* Compare regardless of capitalization */
          {
              printPet(i, petDb.Pets().getPet(i));
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
      viewPets();
      System.out.print("\nEnter the pet ID to update: ");
      
      try
      {
          int index = Integer.parseInt(scanIn.nextLine());
          if (index >= 0 && index < petDb.Pets().size())          /* Check that user input index is in range of array */
          {
              System.out.print("Enter new name and age: ");
              String userInput = scanIn.nextLine();
              int breakpoint = userInput.indexOf(" ");
              if (breakpoint < 1 || breakpoint > userInput.length() - 2)                     /* CASE: Valid index but either first character is a space, no space entered to separate out age, or space is last character */
                  System.out.println("Pet not updated. Data must be name followed by space followed by positive integer below " + (MAX_AGE + 1) + ".");
              else
              {
                  try
                  {
                      String newName = userInput.substring(0, breakpoint);                  /* Like addPets() */
                      int newAge = Integer.parseInt(userInput.substring(breakpoint + 1));
                      if (newAge <= MAX_AGE)
                      {
                          try                                                               /* Try to update. Pet will throw AgeException for ages under 1 */
                          {
                            String oldName = petDb.Pets().getPet(index).getName();          /* Save old values for printout */
                            int oldAge = petDb.Pets().getPet(index).getAge();

                            petDb.Pets().getPet(index).updatePet(newName, newAge);
                            System.out.println(oldName + " " + oldAge + " changed to " + newName + " " + newAge + ".");
                          }
                          catch (AgeException ae)
                          {
                              System.out.println("Pet not updated.\n" + ae.getMessage());
                          }
                      }
                      else                             /* CASE: Valid index, age parses, but age is above MAX_AGE */
                          System.out.println("Pet not updated. Data must be name followed by space followed by positive integer below " + (MAX_AGE + 1) + ".");
                  }
                  catch (NumberFormatException nfe2)   /* CASE: Valid index but age doesn't parse to int */
                  {
                      System.out.println("Pet not updated. Data must be name followed by space followed by positive integer below " + (MAX_AGE + 1) + ".");
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
  
  
  private static void viewPets()    /* View all pets */
  {
      printResultsHeader();
      for (int i = 0; i < petDb.Pets().size(); i++)
          printPet(i, petDb.Pets().getPet(i));
      printResultsFooter(petDb.Pets().size());
  }  /* End viewPets() */
}