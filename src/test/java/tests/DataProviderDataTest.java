package tests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class DataProviderDataTest {

    // Creating the test data and accessing it

    @DataProvider(name = "basicDataProvider") // Multi Dimensional Array oBject
    public Object[][] BasicDataProviderTest() {
        return new Object[][]{
                {"user1@yahoo.com", "password1"},
                {"user2@gmail.com", "password2"},
                {"user3@outlook.com", "password}"}
        };
    }

    @Test(dataProvider = "basicDataProvider")
    public void testDataProvider(String username, String password)// catchingh the data from the data provider and using it in the test method)
    {
        // it will print all the 3 sets of data in the console and it is due to testng data provider which will run the test method 3 times with different sets of data

        System.out.println("Username is " + username + " and password is " + password);


    }


    // Using the Hasmap to Provide data
    @DataProvider(name = "hashMapDataProvider") // Multi Dimensional Array oBject
    public Object[][] HashMapDataProviderTest() {

        HashMap<String, String> data1 = new HashMap<>();
        data1.put("username", "user1@yahoo.com");
        data1.put("password", "password1");


        HashMap<String, String> data2 = new HashMap<>();
        data2.put("username", "user2@gmail.com");
        data2.put("password", "password2");

        return new Object[][]{
                {data1},
                {data2}
        };
    }
@Test(dataProvider = "hashMapDataProvider")
    public void withHashMapDataProvider(HashMap<String, String> data) {

    System.out.println("Hashmap data");
    System.out.println(data.get("username"));
    System.out.println(data.get("password"));

    //data.get("password");
       // data.forEach((k, v) -> System.out.println(k + " : " + v));

    }



    // With External Json File
    @DataProvider(name = "jsonFileDataProvider")
    public static Object[][] JsonFileDataProvider(String filePath) throws IOException {
        Path dataFile = Paths.get(System.getProperty("user.dir")).resolve(filePath).normalize();
        JsonElement jsonData = JsonParser.parseString(Files.readString(dataFile));
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        Gson gson = new Gson();
        List<HashMap<String, String>> dataList;

        if (jsonData.isJsonArray()) {
            JsonArray jsonArray = jsonData.getAsJsonArray();
            dataList = new java.util.ArrayList<>(jsonArray.size());
            for (JsonElement element : jsonArray) {
                if (!element.isJsonObject()) {
                    throw new IOException("Expected each data entry in " + dataFile + " to be a JSON object");
                }
                dataList.add(gson.fromJson(element, type));
            }
        } else if (jsonData.isJsonObject()) {
            dataList = List.of(gson.fromJson(jsonData, type));
        } else {
            throw new IOException("Expected a JSON object or array in " + dataFile);
        }

        Object [][] table = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size() ; i++) {
            table[i][0] = dataList.get(i);
            
        }

        return table;
    }


@Test(dataProvider = "JsonFileDataProvider")
    public void withJsonFileDataProvider(HashMap<String, String> data) {
        System.out.println("Json file data");
        System.out.println(data.get("username"));
        System.out.println(data.get("password"));
    }
}
