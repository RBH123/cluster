package com.ruanbanhai.springboot.demo.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    //存储爬取到的文件的路径
    public static String path = "F:\\Crawler";
    //已经爬取过的url
    private static List<String> alreadyCrawler = new ArrayList<>();
    //可以爬取的url
    private static List<String> allowCrawler = new ArrayList<>();

    public static void crawlerUrl(String url) throws IOException {
        //创建url对象
        URL crawUrl = new URL(url);
        //创建链接
        URLConnection connection = crawUrl.openConnection();
        //获取输入流
        InputStream inputStream = connection.getInputStream();
        //创建输入流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        //创建输出流
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path,System.currentTimeMillis()+".txt"))));
        //一次读取一行
        String line = null;
        //读取网页中的链接
//        Pattern p=Pattern.compile("<a .*href=.+</a>");
        Pattern p = Pattern.compile("<img .*src=.");
        //读取数据
        while((line = bufferedReader.readLine()) != null){
            //匹配正则
            Matcher matcher = p.matcher(line);
            //匹配成功
            while(matcher.find()){
                String group = matcher.group();
                System.out.println(group);
                //截取
                int i = line.indexOf(group);
                String lines = line.substring(i+group.length());
                System.out.println(lines);
                if("/".equals(lines.charAt(0)+"") && "/".equals(lines.charAt(1)+"")){
                    lines = lines.substring(2);
                }
                int index = lines.indexOf("\" ");
                System.out.println(index);
                if(index != -1) {
                    String str = lines.substring(0,index-1);
//                    if("\"".equals(str.charAt(str.length()-1)+"")){
//                        str = str.substring(0,str.length()-1);
//                    }
                    System.out.println(str);
                    String format = str.substring(str.lastIndexOf(".") + 1);
                    if("jpg".equals(format) || "png".equals(format) || "gif".equals(format) || "jpng".equals(format)){
                        //写入文件
                        bufferedWriter.write(str,0,str.length());
                        bufferedWriter.newLine();
                    }
                }
            }
        }
        //关闭io流
        bufferedWriter.close();
        bufferedReader.close();
    }
}
