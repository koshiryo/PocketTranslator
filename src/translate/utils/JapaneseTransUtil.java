package translate.utils;

import java.io.*;
import java.net.*;

public class JapaneseTransUtil {
    private final static String Url = "http://www.baidu.com/s?wd="; //百度搜索URL
    private final static String TransStart = " <p class=\"op_sp_fanyi_line_two\">";
    //翻译开始标签
    private final static String TransEnd = "</p>"; //翻译结束标签

    public static String getTranslation(String urlString) throws Exception { //传入要搜索的单词
        URL url = new URL(Url + urlString); //生成完整的URL
        // 打开URL
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        // 得到输入流，即获得了网页的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String preLine = "";//预留空白
        String line;
        int flag = 1;//定义符号状态变量flag
        // 读取输入流的数据，并显示
        String content = ""; //翻译结果
        while ((line = reader.readLine()) != null) { //获取翻译结果的算法
            if (preLine.indexOf(TransStart) != -1 && line.indexOf(TransEnd) == -1) {
                content += line.replaceAll("　| ", "") + "\n"; //去电源代码上面的半角以及全角字符
                flag = 0;
            }
            if (line.indexOf(TransEnd) != -1) {
                flag = 1;
            }
            if (flag == 1) {
                preLine = line;
            }
        }
        return content;//返回翻译结果
    }

}
