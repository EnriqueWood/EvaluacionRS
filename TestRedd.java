
/**
 *
 *
 *
 * @author EnriqueWood Contact: ewoodg@hotmail.com
 * @date Wednesday, May 31, 2017
 *
 *
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestRedd {

    public static final String[] legalArgs = {"-i", "-o", "-r", "-p"};

    static int results = 10;
    static String inputPath = "";
    static String outputPath = "";
    static String productName = "";
    //Arrays to store JSON information
    static String[] attrNameArray = null;
    static String[] itemNameArray = null;
    static String[][] itemArray = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Process arguments
        processArgs(args);

        //Parse JSON from file
        JSONObject jsonGlobal = getJSONObjectFromFile(inputPath);

        //Fill arrays using JSON file data
        fillArrays(jsonGlobal);

        //Search for results
        SearchEngine engine = new SearchEngine(itemArray, itemNameArray);
        engine.setMaxResults(results);

        int index = getProductName(args);

        int[] resList = engine.search(itemNameArray[index]);

        System.out.println("Articulo a buscar: ");
        System.out.println(toJSONFormat(index));
        String out = "";
        System.out.println("Resultados (ordenados de mayor a menor semejanza):\n");
        for (int i = 0; i < resList.length; i++) {
            System.out.println((i + 1) + ". " + toJSONFormat(resList[i]));
            out +=  toJSONFormat(resList[i]);
        }
	
        outputToFile(out);
    }

    public static String toJSONFormat(int index) {
        StringBuilder s = new StringBuilder("{\"" + itemNameArray[index] + "\":{");
        String[] items = itemArray[index];
        for (int i = 0; i < items.length; i++) {
            if (i != 0) {
                s.append(", ");
            }
            String item = items[i];
            s.append("\"" + attrNameArray[i] + "\" : \"" + item + "\"");
        }
        s.append("}}");
        return s.toString();
    }

    public static void processArgs(String[] args) {
        inputPath = new File("json/").getAbsolutePath() + File.separator + "redd-test-data.json";
        // System.out.println(inputPath);
        if (args.length > 0) {
            if (args.length % 2 != 0) {
                System.out.println("Error: Cantidad incorrecta de argumentos");
                System.exit(1);
            }

            System.out.println(inputPath);
            for (int i = 0; i < args.length; i += 2) {
                for (String s : legalArgs) {
                    if (s.equals(args[i].toLowerCase())) {
                        switch (s) {
                            //To extend program's capabilities you need to add further args here.
                            case "-i":
                                inputPath = args[i + 1];
                                break;
                            case "-o":
                                outputPath = args[i + 1];
                                break;
                            case "-r":
                                try {
                                    results = Integer.parseInt(args[i + 1]);
                                } catch (NumberFormatException nfe) {
                                    System.out.println("Error: ¡Argumento '-n' debe estar seguido de un numero valido!");
                                }
                                break;
                            case "-p":
                                productName = args[i + 1];
                            default:
                                System.out.println("Error: ¡Argumento '" + s + "' no se encuentra definido!");
                        }
                    }
                }
            }
        }
    }

    public static JSONObject getJSONObjectFromFile(String filename) {
        //Reading file
        String jsonText = null;
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            jsonText = new String(data, "UTF-8");
        } catch (Exception e) {
            System.out.println("Error: ¡Archivo JSON no accesible!");
            System.exit(1);
        }

        //Parse JSON File
        JSONObject jsonGlobal = null;
        try {
            //Get the container element (Hashmap)
            jsonGlobal = (JSONObject) new JSONParser().parse(jsonText);
        } catch (ParseException ex) {
            System.out.println("Error: ¡No se puede procesar archivo JSON!");
            System.exit(1);
        }
        return jsonGlobal;
    }

    public static void printInfo() {
        for (int i = 0; i < itemArray.length; i++) {
            System.out.println(itemNameArray[i]);
            for (String a : itemArray[i]) {
                System.out.print(a);
            }
            System.out.println();
        }
    }

    //Fill data arrays from JSONObject
    private static void fillArrays(JSONObject jsonGlobal) {
        itemNameArray = new String[jsonGlobal.size()];
        new ArrayList(jsonGlobal.keySet()).toArray(itemNameArray);

        //Array to store every element 
        itemArray = new String[jsonGlobal.size()][];

        //Get sorted attribute array from the first element
        List<String> l = new ArrayList(
                ((JSONObject) jsonGlobal.get(itemNameArray[0])).keySet());
        Collections.sort(l);
        attrNameArray = new String[l.size()];
        l.toArray(attrNameArray);

        //Going through every JSON Element (item in the list)
        for (int i = 0; i < jsonGlobal.size(); i++) {
            JSONObject item = (JSONObject) jsonGlobal.get(itemNameArray[i]);
            String[] element = new String[attrNameArray.length];
            for (int j = 0; j < attrNameArray.length; j++) {
                element[j] = (String) item.get(attrNameArray[j]);
            }
            itemArray[i] = element;
        }
    }

    private static int getProductName(String[] args) {
        int index = -1;
        while (productName.isEmpty()) {
            if (args.length == 0 || productName.isEmpty()) {

                Scanner s = new Scanner(System.in);

                System.out.print("\nIngrese nombre del articulo a buscar: ");
                productName = s.nextLine().trim();

            }

            if (productName.isEmpty()) {
                System.out.println("Error: No se ha ingresado un nombre de articulo a buscar.");
                System.exit(1);
            }

            index = Arrays.asList(itemNameArray).indexOf(productName);

            if (index == -1) {
                productName = "";
                System.out.println("Error: El articulo no se encuentra en el listado.");

            }
        }
        return index;
    }

    private static void outputToFile(String out) {

        System.out.print("\nDesea guardar la salida en un archivo?: (Y/N): ");
        Scanner s = new Scanner(System.in);
        if (s.nextLine().trim().toLowerCase().equals("y")) {
            System.out.print("\nIngrese nombre del archivo: ");

            outputPath = s.nextLine().trim();

        }

        if (!outputPath.isEmpty()) {
            try {
                PrintWriter pw = new PrintWriter(outputPath + ".txt");
                pw.printf(out);
                pw.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error al guardar el archivo.");
            } finally {
            }
        }
        System.out.println("Ejecucion finalizada.");

    }
}
