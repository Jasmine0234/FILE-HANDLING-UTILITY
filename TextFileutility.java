import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileUtility {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Text File Handling Utility");

        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Read File");
            System.out.println("2. Write to File (Overwrite)");
            System.out.println("3. Append to File");
            System.out.println("4. Modify File (Replace a line)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    readFile();
                    break;
                case "2":
                    writeFile(false); // Overwrite
                    break;
                case "3":
                    writeFile(true);  // Append
                    break;
                case "4":
                    modifyFile();
                    break;
                case "5":
                    System.out.println("Exiting utility.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Reads and prints the content of a specified text file to the console.
     */
    public static void readFile() {
        System.out.print("Enter the path of the file to read: ");
        String filePath = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("\n--- File Content ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("--- End of File ---");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Writes content to a specified text file.
     * @param append If true, the content will be appended to the file.
     * If false, the file will be overwritten.
     */
    public static void writeFile(boolean append) {
        System.out.print("Enter the path of the file to write to: ");
        String filePath = scanner.nextLine();
        System.out.println("Enter the content to write (type 'END' on a new line to finish):");

        StringBuilder contentToWrite = new StringBuilder();
        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.equalsIgnoreCase("END")) {
                break;
            }
            contentToWrite.append(line).append(System.lineSeparator());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(contentToWrite.toString());
            if (append) {
                System.out.println("Content appended to file successfully.");
            } else {
                System.out.println("Content written to file successfully (overwritten if file existed).");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Modifies a specified text file by replacing a specific line with new content.
     */
    public static void modifyFile() {
        System.out.print("Enter the path of the file to modify: ");
        String filePath = scanner.nextLine();
        System.out.print("Enter the line number to replace (starting from 1): ");
        try {
            int lineNumberToReplace = Integer.parseInt(scanner.nextLine());
            if (lineNumberToReplace <= 0) {
                System.out.println("Invalid line number.");
                return;
            }
            System.out.print("Enter the new content for line " + lineNumberToReplace + ": ");
            String newContent = scanner.nextLine();

            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                System.err.println("Error reading file for modification: " + e.getMessage());
                return;
            }

            if (lineNumberToReplace > lines.size()) {
                System.out.println("Line number exceeds the number of lines in the file.");
                return;
            }

            lines.set(lineNumberToReplace - 1, newContent);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line + System.lineSeparator());
                }
                System.out.println("Line " + lineNumberToReplace + " modified successfully.");
            } catch (IOException e) {
                System.err.println("Error writing modified content to file: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid line number format.");
        }
    }
}
