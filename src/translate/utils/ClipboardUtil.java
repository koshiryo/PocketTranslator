package translate.utils;

/*
java.awt.Toolkit
ToolKit是一个抽象类，ToolKit作为AWT工具箱，提供了GUI 最底层的Java访问.
Toolkit是个非常有用类的，提供许多修改窗口默认行为的方法。

java.awt.datatransfer
提供在应用程序之间和在应用程序内部传输数据的接口和类。它定义了"transferable"对象的概念，该对象可以在应用程序之间或应用程序内部传输。它是一种通过实现Transferable接口标识自身为可传输的对象。

Toolkit.getDefaultToolkit()
在java中可以通过Toolkit类的getSystemClipboard方法来获得剪贴板的数据因为剪贴板上存放的可能不是文本，所以在使用剪贴板返回的数据之前我们需要先检测剪贴板上的内容是不是文本。
*/

import java.awt.Toolkit;
import java.awt.datatransfer.*;

public class ClipboardUtil {

    public static String getClipboardText() throws Exception {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪贴板
        // 获取剪切板中的内容
        Transferable clipT = clip.getContents(null);
        if (clipT != null) {
            // 检查内容是否是文本类型
            if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor))
                return (String) clipT.getTransferData(DataFlavor.stringFlavor);
        }
        return null;
    }
}
