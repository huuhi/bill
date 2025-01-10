package ZhiJianHu;

import javax.swing.*;
import ZhiJianHu.GUI.CheckoutGUI;
/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/10
 * 说明:
 */
public class App {
     public static void main(String[] args) {
          SwingUtilities.invokeLater(() -> {
            CheckoutGUI gui = new CheckoutGUI();
            gui.setVisible(true);
        });
     }
}
