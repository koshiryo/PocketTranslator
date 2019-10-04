package translate;

import javax.swing.JFrame;

public class TranslateTool {
    public static void main(String[] args) {

    	TranslateDialog td = new TranslateDialog();
    	td.setTitle("Pocket Translator");		//设置界面标题
        td.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        td.setBounds(300, 200, 400, 300);
        td.setVisible(true);
        td.setAlwaysOnTop(true);//设置在最顶层
        td.setLayout();

        Thread t = new Thread(td);
        t.start(); //开启线程
    }
}
