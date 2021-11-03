package base.simplebd;

import java.io.*;
import java.util.*;

public class Main {
    public static void create () {
        try {
            File file = new File("simpledb.db");

            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Done");
            } else { System.out.println("Arquivo ja existe."); }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void add () {
        File fArquivo = null;
        try {
            fArquivo = new File("simpledb.db");
            FileWriter fwArquivo = null;

            if (fArquivo.exists() == true) {
                fwArquivo = new FileWriter(fArquivo, true);
            } else {
                fwArquivo = new FileWriter(fArquivo);
            }

            Scanner sc2 = new Scanner(System.in);
            BufferedWriter bw = new BufferedWriter(fwArquivo);
            System.out.println("Add line: ");
            String registro = sc2.nextLine();

            bw.write(registro + "\n");

            System.out.println("Done");

            bw.close();
            fwArquivo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read () {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("simpledb.db"));
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update (){
        Scanner sc3 = new Scanner(System.in);
        File fileToBeModified = new File("simpledb.db");
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;

        System.out.println("Linha a ser alterada:");
        String oldString = sc3.next();
        System.out.println("Nova linha: ");
        String newString = sc3.next();

        try{
            reader = new BufferedReader(new FileReader(fileToBeModified));
            String line = reader.readLine();
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
            String newContent = oldContent.replaceAll(oldString,newString);
            writer = new FileWriter((fileToBeModified));
            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                reader.close();
                writer.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void delete () {
        try {
            File input_file = new File("simpledb.db");
            File temp_file = new File("simpledbTemp.db");
            BufferedReader my_reader = new BufferedReader(new FileReader(input_file));
            BufferedWriter my_writer = new BufferedWriter(new FileWriter(temp_file));
            Scanner sc4 = new Scanner(System.in);
            System.out.println("Delete: ");
            String lineToRemove = sc4.nextLine();
            String current_line;
            while((current_line = my_reader.readLine()) != null) {
                String trimmedLine = current_line.trim();
                if(trimmedLine.equals(lineToRemove)) continue;
                my_writer.write(current_line + System.getProperty("line.separator"));
            }
            my_writer.close();
            my_reader.close();
            input_file.delete();
            temp_file.renameTo(input_file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        int option;
        boolean flag = true;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Opcao 1: Create");
        System.out.println("Opcao 2: Add");
        System.out.println("Opcao 3: Read");
        System.out.println("Opcao 4: Update");
        System.out.println("Opcao 5: Delete");
        System.out.println("Opcao 0: Exit");

        while (flag == true) {
            System.out.println("Digite sua opcao:");
            option = entrada.nextInt();

            switch (option) {
                case 1: //Create
                    create();
                    break;
                case 2:
                    add();
                    break;
                case 3: //Read
                    read();
                    break;
                case 4: //Update
                    update();
                    break;
                case 5: //Delete
                    delete();
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    System.out.println("Utilize uma opcao valida.");
            }
        }
    }
}