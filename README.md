# Pocket Translator

A program that automatically recognizes words in English or Japanese and translates them into Chinese in real time.

## Background
I have to read a lot of English or Japanese documents both in working and study. When I meet some unfamiliar words, I have to go Baidu (Chinese Google) for a translation. I find it is really trouble to copy&paste the words, open the browser and switch the languages between English and Japanese. So, I created this tiny program to help me reading the documents in a fast speed with just knowing the basic meaning of the words.

## Overview
For this program, you only need to “Ctrl + C” the words when reading the documents(online or offline). The program will automatically recognize the words in  English or Japanese and translated into Chinese in real time. It saves the trouble of switching languages or opening a browser.

Please confirm the pics below:
### Dialog layout
![Dialog Layout](https://github.com/koshiryo/PocketTranslator/blob/master/img/Pt.png)

### pic showing English translation
![English Translation](https://github.com/koshiryo/PocketTranslator/blob/master/img/English.png)

### pic showing Japanese translation
![Japanese Translation](https://github.com/koshiryo/PocketTranslator/blob/master/img/Japanese.png)


## Basic Idea
1. Get the contents from the system clipboard
2. Send the contents to the web page, then get the source code of the web page and find the corresponding Chinese explanation.
3. Display Chinese translation

## Project Map
    - src/translate/
      + utils                       … Provide utils
       - ClipboardUtil.java         … Provide util for getting contents from system clipboard
       - EnglishTransUtil.java      … Provide util for English-->Chinese translation
       - JanpansesTransUtil.java    … Provide util for Japanese-->Chinese translation
      - TranslateDialog.java        … Defined dialog layout
      - TranslateTool.java          … Entry Point


## Details
####  Get the contents from the system clipboard
    public static String getClipboardText() throws Exception {
            Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
            // Get the contents from Clipboard
            Transferable clipT = clip.getContents(null);
            if (clipT != null) {
                // Check the contents whether it is String 
                if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor))
                    return (String) clipT.getTransferData(DataFlavor.stringFlavor);
            }
            return null;
        }

#### Get translation from Baidu
    public class EnglishTransUtil {
        private final static String Url = "http://www.baidu.com/s?wd="; //Baidu searching URL
        private final static String TransStart = "<span class=\"op_dict_text2\">"; //Start flag of translation
        private final static String TransEnd = "</span>"; //End flag of translation
    
        public static String getTranslation(String urlString) throws Exception { 
            URL url = new URL(Url + urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // Get web contents
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String preLine = "";
            String line;
            int flag = 1;
            String content = ""; //translation
            while ((line = reader.readLine()) != null) { 
                if (preLine.indexOf(TransStart) != -1 && line.indexOf(TransEnd) == -1) {
                    content += line.replaceAll("　| ", "") + "\n";
                    flag = 0;
                }
                if (line.indexOf(TransEnd) != -1) {
                    flag = 1;
                }
                if (flag == 1) {
                    preLine = line;
                }
            }
            return content;//Get translation
        }


## Acknowledgement
* **Baidu** 
* **[百度翻译](https://fanyi.baidu.com/ "百度翻译")**



## Authors

* **Koshi Ryo**


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

