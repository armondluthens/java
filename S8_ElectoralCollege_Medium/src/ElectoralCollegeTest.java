import javax.swing.JFrame;

public class ElectoralCollegeTest {
	public static void main(String[] args) {
        ElectoralCollegeFrame electoral = new ElectoralCollegeFrame();
        electoral.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        electoral.setSize(2000, 800);
        electoral.setResizable(true); //user allowed to resize calculator window
        electoral.setLocationRelativeTo(null); //makes GUI pop up in center of screen
        electoral.setVisible(true);
    }
}
