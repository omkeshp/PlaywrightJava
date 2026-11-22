
package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class DataProvider {

    public static Object[][] JsonFileDataProvider(String filePath)
            throws IOException {

        String jsonContent = Files.readString(
                Paths.get(System.getProperty("user.dir"), filePath)
        );

        Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();

        List<HashMap<String, String>> dataList =
                new Gson().fromJson(jsonContent, type);

        Object[][] table = new Object[dataList.size()][1];

        for (int i = 0; i < dataList.size(); i++) {

            // Each row contains ONE HashMap
            table[i][0] = dataList.get(i);

            System.out.println("DataProvider row " + i + ": "
                    + table[i][0].getClass().getName());
        }

        return table;
    }
}
