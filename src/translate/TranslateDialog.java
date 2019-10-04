package translate;
/*
java.awt
Abstract Window ToolKit (抽象窗口工具包)
java.awt包提供了基本的java程序的GUI设计工具。主要包括下述三个概念:
组件--Component
容器--Container
布局管理器--LayoutManager

javax.swing
Swing 是一个为Java设计的GUI工具包。Swing包括了图形用户界面（GUI）器件如：文本框，按钮，分隔窗格和表。
Swing提供许多比AWT更好的屏幕显示元素。它们用纯Java写成，所以同Java本身一样可以跨平台运行，这一点不像AWT。
*/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import translate.utils.*;

public class TranslateDialog extends JFrame implements Runnable {
	//top组件
	private JPanel jp1;//定义top面板
    private JTextField contentField; //显示待翻译的单词
    //翻译组件
    private JPanel jp2;//定义trans面板
    private JSplitPane jsp;	//定义拆分窗格
    private JScrollPane jspane1;//定义滚动窗格
    private JTextArea JapaneseTransField; //显示日文翻译结果
    private JScrollPane jspane2;//定义滚动窗格
    private JTextArea EnglishTransField; //显示英文翻译结果

    public TranslateDialog() {
    	jp1=new JPanel();
        contentField = new JTextField(10);
        jp2=new JPanel();
        JapaneseTransField = new JTextArea();
        JapaneseTransField.setLineWrap(true);	//设置多行文本框自动换行
        jspane1=new JScrollPane(JapaneseTransField);
        EnglishTransField = new JTextArea();
        EnglishTransField.setLineWrap(true);	
        jspane2=new JScrollPane(EnglishTransField);
        jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,jspane1,jspane2); //创建拆分窗格
		jsp.setDividerLocation(120);	//设置拆分窗格分频器初始位置
		jsp.setDividerSize(1);			//设置分频器大小  
    }

    public void setLayout() {
    	//设置布局管理
    	jp1.setLayout(new FlowLayout(FlowLayout.CENTER));
    	jp2.setLayout(new BorderLayout());
    	
        //布局设置
      	jp1.add(contentField);
      	jp2.add(jsp);
      		
      	this.add(jp1,BorderLayout.NORTH);
      	this.add(jp2,BorderLayout.CENTER);
        this.setResizable(false);

        //监听JTextField里面内容改变的事件
        contentField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {

            }

            @Override
            public void insertUpdate(DocumentEvent arg0) { //内容改变

                try {
                    //获取日文翻译结果
                    String resultJa = JapaneseTransUtil.getTranslation(contentField.getText());
                    if (resultJa == "")
                        resultJa = "選択した単語は英語です。";
                    JapaneseTransField.setText(resultJa);//显示日文翻译结果

                    //获取英文翻译结果
                    String resultEn = EnglishTransUtil.getTranslation(contentField.getText());
                    if (resultEn == "")
                        resultEn = "The selected word is in Japanses.";
                    EnglishTransField.setText(resultEn);//显示英文翻译结果
                } catch (Exception e) {
                    JapaneseTransField.setText("ごめんね、見つけなかった。");
                    EnglishTransField.setText("Sorry, I didn't find a translation.");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {

            }

        });

        this.validate();//确保组件的有效布局
    }

    @Override
    public void run() {
        while (true) {
            try {
                String content = ClipboardUtil.getClipboardText();
                contentField.setText(getSimpleWord(content));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getSimpleWord(String content) {//去掉切板里面的一些特殊字符
        return content.replace(".", "").replace(",", "")
                .replace("'", "").replace(":", "")
                .replace(";", "").trim();
    }
}
