package com.textilechat.ingenious.textilechat.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser
{
    static List<CategoryClass> categoryClassList;

    public static List<CategoryClass> parse_category(String content)
    {
        JSONArray jsonArray = null;
        CategoryClass myCategoryClass = null;
        try
        {
            jsonArray = new JSONArray(content);
            categoryClassList = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                myCategoryClass = new CategoryClass();

                myCategoryClass.setC_id(jsonObject.getString("c_id"));
                myCategoryClass.setC_name(jsonObject.getString("c_name"));
                myCategoryClass.setC_image(jsonObject.getString("c_icon"));

                categoryClassList.add(myCategoryClass);
            }
            return categoryClassList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
