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
    static List<Single_user_msg_list> single_chat_messages;
    static List<Ads_class> ads_classList;
    static List<pc_class> pc_classList;
    static List<blog_class> blog_classList;

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
                news_class.setNews_image(jsonObject.getString("n_image"));
                news_class.setNews_title(jsonObject.getString("n_title"));
                news_class.setNews_des(jsonObject.getString("n_desc"));
                news_class.setNews_id(jsonObject.getString("n_id"));
                news_class.setNews_date(jsonObject.getString("n_date"));

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
                chat_messagess.setM_id(jsonObject.getString("message_id"));
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


    public static List<Single_user_msg_list> parse_single_chatmessages(String content)
    {
        JSONArray jsonArray = null;
        Single_user_msg_list chat_messagess = null;
        try
        {
            jsonArray = new JSONArray(content);
            single_chat_messages= new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                chat_messagess = new Single_user_msg_list();
                chat_messagess.setFrom_user_id(jsonObject.getString("from_user_id"));
                chat_messagess.setTo_user_id(jsonObject.getString("to_user_id"));
                chat_messagess.setMessages(jsonObject.getString("message"));
                chat_messagess.setTimestamp(jsonObject.getString("msg_created_at"));




                single_chat_messages.add(chat_messagess);
            }
            return single_chat_messages;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static List<Ads_class> parse_ads(String content)
    {
        JSONArray jsonArray = null;
        Ads_class ads_class = null;
        try
        {
            jsonArray = new JSONArray(content);
            ads_classList= new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ads_class = new Ads_class();
                ads_class.setAd_id(jsonObject.getString("ad_id"));
                ads_class.setAd_image(jsonObject.getString("ad_image"));
                ads_class.setAd_display_time(jsonObject.getInt("ad_display_time"));
                ads_class.setC_id(jsonObject.getString("c_id"));
                ads_class.setSc_id(jsonObject.getString("sc_id"));
                ads_class.setCat_name(jsonObject.getString("c_name"));
                ads_class.setSub_name(jsonObject.getString("sc_name")+"");




                ads_classList.add(ads_class);
            }
            return ads_classList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }


    public static List<pc_class> parse_pc(String content)
    {
        JSONArray jsonArray = null;
        pc_class pc_class = null;
        try
        {
            jsonArray = new JSONArray(content);
            pc_classList= new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                pc_class = new pc_class();
                pc_class.setFrom_user_id(jsonObject.getString("from_user_id"));
                pc_class.setTo_user_id(jsonObject.getString("to_user_id"));
                pc_class.setFrom_u_name(jsonObject.getString("from_user"));
                pc_class.setFrom_u_image(jsonObject.getString("from_user_image"));
                pc_class.setTo_u_name(jsonObject.getString("to_user"));
                pc_class.setTo_u_image(jsonObject.getString("to_user_image"));
                pc_class.setMessage(jsonObject.getString("message"));
                pc_class.setDate(jsonObject.getString("date"));



                pc_classList.add(pc_class);
            }
            return pc_classList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }}

    public static List<blog_class> parse_blogs(String content)
    {
        JSONArray jsonArray = null;
        blog_class blogClass = null;
        try
        {
            jsonArray = new JSONArray(content);
            blog_classList = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                blogClass = new blog_class();
                blogClass.setB_id(jsonObject.getString("b_id"));
                blogClass.setB_title(jsonObject.getString("b_title"));
                blogClass.setB_status(jsonObject.getString("b_status"));
                blogClass.setB_description(jsonObject.getString("b_description"));
                blogClass.setB_image(jsonObject.getString("b_image"));
                blogClass.setB_date(jsonObject.getString("b_date"));

                blog_classList.add(blogClass);
            }
            return blog_classList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
