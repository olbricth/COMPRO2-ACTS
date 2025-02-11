package gradeCal;

import java.io.*;
import java.util.Scanner;

public class GradeCalculator {

    //declare variables
    public static final String FILE_DIR = "target/records";
    public static final int NUMTERMS = 3;
    public static final double MIN_GRADE = 50;
    public static final int NUMSUBS = 64;

    public static void main(String[] args) {
        String name;

        //2d array and array for the subjects and grades
        String [] subjects = new String[NUMSUBS];
        double[][] grades = new double[NUMSUBS][NUMTERMS];

        String [] terms = {"Prelim", "Midterm", "Finals"};
        Scanner gaby = new Scanner(System.in);

        //prompt the user to enter name
        System.out.print("Enter name: ");
        name = gaby.nextLine();

        //to format the output
        StringBuilder gab = new StringBuilder();
        gab.append("Name: ").append(name);
        gab.append("\n").append(String.format(
                        "%-20s%-10s%-10s%-10s%-15s\n",
                        "SUBJECTS", "PRELIM", "MIDTERM", "FINAL", "FINAL RATING"
                )
        );

        //to input subjects and grades
        for (int i = 0; i < NUMSUBS; i++) {
            System.out.print("Enter subject: ");
            subjects[i] = gaby.nextLine();
            gab.append(String.format("%-20s", subjects[i]));

            for (int j = 0; j < NUMTERMS; j++) {
                System.out.print("\t" + terms[j] + ": ");
                try {
                    //to check if the entered grade is over 50.
                    grades[i][j] = Double.parseDouble(gaby.nextLine());
                    if (grades[i][j] < MIN_GRADE) {
                        throw new Exception("Error! entered grade is invalid.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\tError! Invalid number.");
                    --j;
                    continue;
                } catch (Exception e) {
                    System.out.println("\t" + e.getMessage());
                    --j;
                    continue;
                }
                //to append the grade to the output
                gab.append(String.format("%-10.2f", grades[i][j]));
            }

            //to calculate and append the final rating
            gab.append(String.format("%-15.2f\n", getFinalRating(grades[i])));
        }

        //display the output to the console and save
        System.out.println(gab);
        writeToFile(name, gab.toString());
    }

    //to calculate the final rating based on term grades
    public static double getFinalRating(double[] termGrades) {
        double finalRating = termGrades[0] * .3 + termGrades[1] * .3 + termGrades[2] * .4;
        return finalRating;
    }

    //to write data to a file
    public static void writeToFile(String fileName, String data) {
        File folder = new File(FILE_DIR);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, fileName + ".txt");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(data);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    //to read all files
    public static void readAllFiles() {
        File folder = new File(FILE_DIR);
        File[] studentGradeFiles = folder.listFiles();
        if (studentGradeFiles != null) {
            for (File studentGradeFile : studentGradeFiles) {
                if (studentGradeFile.isFile()) {
                    readFile(studentGradeFile);
                }
            }
        } else {
            System.out.println("No files found.");
        }
    }

    // to read a single file and print
    public static void readFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            System.out.println("--------------------------");
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

