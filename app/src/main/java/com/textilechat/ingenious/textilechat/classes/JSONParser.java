package com.textilechat.ingenious.textilechat.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser
{
    static List<CategoryClass> categoryClassList;
    static List<Sub_category_class> subcategoryClassList;

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

    //edit on it for subcategory
    public static List<Sub_category_class> parse_sub_category(String content)
    {
        JSONArray jsonArray = null;
        Sub_category_class sub_category_class = null;
        try
        {
            jsonArray = new JSONArray(content);
            subcategoryClassList = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                sub_category_class = new Sub_category_class();


                subcategoryClassList.add(sub_category_class);
            }
            return subcategoryClassList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
