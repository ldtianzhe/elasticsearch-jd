package com.example.utils;

import com.example.pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ldtianzhe
 * @description 根据商品名爬取京东商品搜索信息
 * @date 2020/7/9
 */
@Component
public class HttpParseUtils {
    public static void main(String[] args) throws Exception {
        new HttpParseUtils().parseJD("java").forEach(System.out::println);
    }
    public List<Content> parseJD(String keyword) throws Exception {
        //获取请求
        String url = "https://search.jd.com/Search?keyword="+keyword;
        //解析网页
        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("J_goodsList");
        System.out.println(element.html());
        Elements elements = element.getElementsByTag("li");
        ArrayList<Content> goodsList = new ArrayList<>();
        for (Element el : elements) {
            System.out.println("================");
            String img = el.getElementsByTag("img").eq(0).attr("src");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            Content content = new Content();
            content.setTitle(title);
            content.setImg(img);
            content.setPrice(price);
            goodsList.add(content);
        }
        return goodsList;
    }
}
