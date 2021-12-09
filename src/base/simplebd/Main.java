package base.simplebd;

import java.io.IOException;
import java.util.*;

import static base.simplebd.Huffman.*;
import static base.simplebd.Lzw.*;


public class Main {
    public static void menu() {
        System.out.println("Opcao 1: Insert");
        System.out.println("Opcao 2: Remove");
        System.out.println("Opcao 3: Search");
        System.out.println("Opcao 4: Update");
        System.out.println("Opcao 5: SelectionSort");
        System.out.println("Opcao 0: Exit");
    }

    public static void test() {
        int option, k, sK;
        long pos;
        boolean flag = true, menuTeste = true;
        String value;

        Scanner entrada = new Scanner(System.in);
        HashExtensivel hash = new HashExtensivel();
        CRUD crud = new CRUD();
        selectionSort select = new selectionSort();

        hash.insert();

        if (menuTeste) {
            menu();
        }

        while (flag == true) {
            System.out.println("Digite sua opcao:");
            option = entrada.nextInt();

            switch (option) {
                case 1:
                    crud.insert(sK = entrada.nextInt(), value = entrada.next());
                    //hash.insert();
                    break;
                case 2:
                    hash.removeHash(k = entrada.nextInt());
                    crud.remove(k);
                    break;
                case 3:
                    crud.search(hash.searchHash(k = entrada.nextInt()));
                    break;
                case 4:
                    crud.update(k = entrada.nextInt(), sK = entrada.nextInt(), value = entrada.next());
                    break;
                case 5:
                    select.lerArquivo();
                    System.out.println("\nBucket List \n" + hash.bucket);
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    System.out.println("Utilize uma opcao valida.");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //int k, sK;
        //long pos;
        //boolean flag = true, menuTeste = true;
        //String value;

        //Scanner entrada = new Scanner(System.in);
        HashExtensivel hash = new HashExtensivel();
        CRUD crud = new CRUD();
        selectionSort select = new selectionSort();

        int teste = 0;

        if (teste == 1) {
            test();
        } else {

            if (args.length == 0 || args[0].equals("--help")) {
                System.out.println("simpledb [cmd]\n" +
                        "  --insert=<sort-key,value> \n      Insere um objeto no banco de dados.\n" +
                        "  --remove=<key>\n      Remove do banco de dados o objeto identificado pela chave key.\n" +
                        "  --search=<key>\n      Busca no banco de dados objeto identificado pela chave key.\n" +
                        "  --update=<key,sort-key,value>\n      Atualiza o objeto que e identificado pela chave key. \n" +
                        "  --list=<expr>\n      Lista em ordem crescente todos os objetos que possam uma chave de ordenação que atenda aos critérios especificados pela expressão 'expr'.\n" +
                        "  --reverse-list=<expr>\n      Lista em ordem decrescente  todos os objetos que possam uma chave de ordenação que atenda aos critérios especificados pela expressão 'expr'.\n" +
                        "  * A expressão 'expr' deve aceitar qualquer operação logica simples envolvendo a chave:\n" +
                        "      key>X: objetos que possuem chave de ordenação maior que X.\n" +
                        "      key<X: objetos que possuem chave de ordenação menor que X.\n" +
                        "      key=X: objetos que possuem chave ordenação igual a X.\n" +
                        "      key>=X: objetos que possuem chave de ordenação maior ou igual que X.\n" +
                        "      key<=X: objetos que possuem chave de ordenação menor ou igual que X.\n" +
                        "  --compress=[huffman|lzw]\n      Compacta os registros do banco de dados usando o algoritmo de Codificação de Huffman ou o Algoritmo de Compressão LZW. \n" +
                        "  --decompress=[huffman|lzw]\n      Descompacta os registros do banco de dados usando o algoritmo de Codificação de Huffman ou o Algoritmo de Compressão LZW. \n");
            } else {
                String[] arg = args[0].split("=");
                if (arg.length == 1) {
                    System.out.println("Faltando argumentos para as operações. \nPara mais informações de uso, utilize a opção --help");
                }
                hash.insert();
                switch (arg[0]) {
                    case "--insert":
                        String[] insertArg = arg[1].split(",");
                        if (insertArg.length == 2) {
                            crud.insert(Integer.valueOf(insertArg[0]),insertArg[1]); // sort-key,value
                            hash.insert();
                        } else {
                            System.out.println("Número de argumentos incorreto para a operação. \nPara mais informações de uso, utilize a opção --help");
                        }
                        break;
                    case "--remove":
                        crud.remove(Integer.valueOf(arg[1])); // arg[1] -> key
                        hash.removeHash(Integer.valueOf(arg[1]));
                        break;
                    case "--search":
                        crud.search(hash.searchHash(Integer.valueOf(arg[1]))); // arg[1] -> key
                        break;
                    case "--update":
                        String[] updateArg = arg[1].split(",");
                        if (updateArg.length == 3) {
                            crud.update(Integer.valueOf(updateArg[0]),Integer.valueOf(updateArg[1]),updateArg[2]); //key,sort-key,value
                            //crud.update(k = entrada.nextInt(), sK = entrada.nextInt(), value = entrada.next());
                        } else {
                            System.out.println("Número de argumentos incorreto para a operação. \nPara mais informações de uso, utilize a opção --help");
                        }

                        break;
                    case "--list":
                        String[] n;
                        if (arg[1].matches("key<[0-9]+")) {
                            n = arg[1].split("<");
                            hash.listIdxMenor(Integer.valueOf(n[1]),1);
                            // listmenorque(n[1]);
                        } else if (arg[1].matches("key>[0-9]+")) {
                            n = arg[1].split(">");
                            hash.listIdxMaior(Integer.valueOf(n[1]), 1);
                            // listmaiorque(n[1]);
                         } else if (arg.length == 3 && arg[1].matches("key") && arg[2].matches("[0-9]+")) { //lembrar que o '=' foi "comido" no split
                            // listigual(arg[2]);
                            hash.listIdxEqual(Integer.valueOf(arg[2]),1);
                        } else if (arg.length == 3 && arg[1].matches("key<") && arg[2].matches("[0-9]+")) { //lembrar que o '=' foi "comido" no split
                            //listmenorigual(arg[2]);
                            hash.listIdxMenorIgual(Integer.valueOf(arg[2]),1);
                        } else if (arg.length == 3 && arg[1].matches("key>") && arg[2].matches("[0-9]+")) { //lembrar que o '=' foi "comido" no split
                            //listmaiorigual(arg[2]);
                            hash.listIdxMaiorIgual(Integer.valueOf(arg[2]),1);
                        } else {
                            System.out.println("Opção não contemplada no simpledb\nPara mais informações utilize a opção --help");
                        }
                        break;
                    case "--reverse-list":
                        if (arg[1].matches("key<[0-9]+")) {
                            n = arg[1].split("<");
                            hash.listIdxMenor(Integer.valueOf(n[1]),2);
                            // reverselistmenorque(n[1]);
                        } else if (arg[1].matches("key+>[0-9]+")) {
                            n = arg[1].split(">");
                            // reverselistmaiorque(n[1]);
                            hash.listIdxMaior(Integer.valueOf(n[1]), 2);
                        } else if (arg[1].matches("key") && arg[2].matches("[0-9]+")) { //lembrar que o '=' foi "comido" no split
                            // reverselistigual(arg[2]);
                            hash.listIdxEqual(Integer.valueOf(arg[2]),2);
                        } else if (arg.length == 3 && arg[1].matches("key<") && arg[2].matches("[0-9]+")) { //lembrar que o '=' foi "comido" no split
                            //reverselistmenorigual(arg[2]);
                            hash.listIdxMenorIgual(Integer.valueOf(arg[2]),2);
                        } else if (arg.length == 3 && arg[1].matches("key+>") && arg[2].matches("[0-9]+")) { //lembrar que o '=' foi "comido" no split
                            //reverselistmaiorigual(arg[2]);
                            hash.listIdxMaiorIgual(Integer.valueOf(arg[2]),2);
                        } else {
                            System.out.println("Opção não contemplada no simpledb\nPara mais informações utilize a opção --help");
                        }

                        break;
                    case "--compress":
                        if (arg[1].equals("lzw")) {
                            compressLZW();
                        } else if (arg[1].equals("huffman")) {
                            compresshuffman();
                        } else {
                            System.out.println("Opção não contemplada no simpledb\nPara mais informações utilize a opção --help");
                        }
                        break;
                    case "--decompress":
                        if (arg[1].equals("lzw")) {
                            decompressLZW();
                        } else if (arg[1].equals("huffman")) {
                            decompresshuffman();
                        } else {
                            System.out.println("Opção não contemplada no simpledb\nPara mais informações utilize a opção --help");
                        }
                        break;
                    default:
                        System.out.println("Opção não contemplada no simpledb\nPara mais informações utilize a opção --help");
                        break;
                }
            }
        }
    }
}
