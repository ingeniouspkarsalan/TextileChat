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
    static List<News_Class> news_classList;
    static List<chat_messages> chat_messages;

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
                myCategoryClass.setIs_sub_cat(jsonObject.getString("sub_cat"));

                categoryClassList.add(myCategoryClass);
            }
            return categoryClassList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }


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
                sub_category_class.setSc_id(jsonObject.getString("sc_id"));
                sub_category_class.setSc_name(jsonObject.getString("sc_name"));
                sub_category_class.setSc_image(jsonObject.getString("sc_icon"));


                subcategoryClassList.add(sub_category_class);
            }
            return subcategoryClassList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }


    public static List<News_Class> parse_news(String content)
    {
        JSONArray jsonArray = null;
        News_Class news_class = null;
        try
        {
            jsonArray = new JSONArray(content);
            news_classList = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                news_class = new News_Class();
                news_class.setNews_image(jsonObject.getString(""));
                news_class.setNews_title(jsonObject.getString(""));
                news_class.setNews_des(jsonObject.getString(""));



                news_classList.add(news_class);
            }
            return news_classList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }


    public static List<chat_messages> parse_chatmessages(String content)
    {
        JSONArray jsonArray = null;
        chat_messages chat_messagess = null;
        try
        {
            jsonArray = new JSONArray(content);
            chat_messages = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                chat_messagess = new chat_messages();
                chat_messagess.setIds(jsonObject.getString("u_id"));
                chat_messagess.setUser_name(jsonObject.getString("u_name"));
                chat_messagess.setMessages(jsonObject.getString("message"));
                chat_messagess.setTimestamp(jsonObject.getString("created_at"));
                chat_messagess.setU_status(jsonObject.getString("status"));
                chat_messagess.setU_image(jsonObject.getString("u_image"));




                chat_messages.add(chat_messagess);
            }
            return chat_messages;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
