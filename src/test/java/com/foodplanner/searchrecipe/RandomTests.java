package com.foodplanner.searchrecipe;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class RandomTests {

    @Test
    public void jsonArrayTest() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        // jsonArray.put(new Recipe("Gazpacho", "Comida o bebida refrescante"));
        // jsonArray.put(new Recipe("Ensaladilla rusa",
        // "Ensalada de papatas con mayonesa con un gran abanico de posibilidades"));

        JSONObject jsonObject = new JSONObject();
        System.out.println(jsonObject.has("asdf"));
        jsonObject.put("name", "Gazpacho");
        jsonObject.put("description", "Comida o bebida refrescante");
        jsonObject.put("description", "Comida o bebida refrescante");
        jsonArray.put(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "Ensaladilla rusa");
        jsonObject.put("description",
                "Ensalada de papatas con mayonesa con un gran abanico de posibilidades");
        jsonArray.put(jsonObject);

        System.out.println(jsonArray);

        for (int i = 0; i < jsonArray.length(); i++) {
            // Recipe recipe = (Recipe) jsonArray.get(i);
            // System.out.println(recipe);
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            System.out.println(jsonObj);
            Iterator iter = jsonObject.keys();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                System.out.println(jsonObj.get(key));
            }
        }
    }
}

class Recipe {
    String name;

    String description;

    public Recipe(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Recipe [name=" + name + ", description=" + description + "]";
    }

}
