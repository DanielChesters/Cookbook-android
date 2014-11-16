package fr.oni.cookbook.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import fr.oni.cookbook.adapter.MainRecipeAdapter;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;

public class TaskUtility {
    private static List<Recipe> stringToData(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Recipe>>() {
            // Nothing
        }.getType();
        return gson.fromJson(json, collectionType);
    }

    public static String readFromFile(InputStream inputStream) throws IOException {

        String ret = "";

        if (inputStream != null) {
            int sizeFile = inputStream.available();
            byte[] buffer = new byte[sizeFile];
            inputStream.read(buffer);
            inputStream.close();
            ret = new String(buffer, Charset.defaultCharset());
        }

        return ret;
    }

    public static void readJsonAndUpdateData(String json, Data data, MainRecipeAdapter recipeAdapter) {
        List<Recipe> recipes = TaskUtility.stringToData(json);
        if (recipes != null && !recipes.isEmpty()) {
            data.getRecipes().clear();
            data.getRecipes().addAll(recipes);
        }
        recipeAdapter.notifyDataSetChanged();
    }

    public static void writeToFile(OutputStream outputStream, Data data) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write(dataToString(data));
        outputStreamWriter.close();
        outputStream.close();
    }

    private static String dataToString(Data data) {
        Gson gson = new Gson();
        return gson.toJson(data.getRecipes());
    }
}
