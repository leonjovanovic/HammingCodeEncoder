import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Main {
	
	String result;
	int n,k;
	
	public static void main(String[] args) {
		new Main();
	}
	public Main()
	{
		//-----------------------------FRAME-----------------------------------
		JFrame guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Kodiranje Hemingovim kodovima");
		guiFrame.setSize(700,500);
		guiFrame.setLocationRelativeTo(null);
		//----------------------------------------------------------------------

		//---------------------------FRAME PANEL--------------------------------
		GridLayout frameLayout = new GridLayout(9,0);
		final JPanel framePanel = new JPanel(frameLayout);
		//----------------------------------------------------------------------

		//--------------------------HEMMING CODE--------------------------------
		String[] origHemCode = {"(3,1)", "(5,2)", "(6,3)", "(7,4)",
				"(9,5)", "(10,6)", "(11,7)", "(12,8)", "(13,9)", "(14,10)", "(15,11)",
				"(17,12)", "(18,13)", "(19,14)", "(20,15)", "(21,16)", "(22,17)", "(23,18)", "(24,19)", "(25,20)", "(26,21)", "(27,22)", "(28,23)", "(29,24)", "(30,25)", "(31,26)",
				"(33,27)", "(34,28)", "(35,29)", "(36,30)", "(37,31)", "(38,32)", "(39,33)", "(40,34)", "(41,35)", "(42,36)", "(43,37)", "(44,38)", "(45,39)", "(46,40)", "(47,41)", "(48,42)", "(49,43)", "(50,44)", "(51,45)", "(52,46)", "(53,47)", "(54,48)", "(55,49)", "(56,50)", "(57,51)", "(58,52)", "(59,53)", "(60,54)", "(61,55)", "(62,56)", "(63,57)"};
		final JPanel codePanel = new JPanel();
		JLabel comboLbl = new JLabel("Hemingov kod:");
		JComboBox codes = new JComboBox(origHemCode);	
		codes.setSelectedIndex(3);

		comboLbl.setFont(new Font("Serif", Font.PLAIN, 25));
		codes.setFont(new Font("Serif", Font.PLAIN, 25));
		codePanel.add(comboLbl);
		codePanel.add(codes);
		//----------------------------------------------------------------------		

		//------------------------INFORMATION BITS------------------------------
		final JPanel infPanel = new JPanel();
		JLabel listLbl = new JLabel("Informacioni bitovi:");
		JTextField field = new JTextField(10);
		
		listLbl.setFont(new Font("Serif", Font.PLAIN, 25));
		field.setFont(new Font("Serif", Font.PLAIN, 25));
		infPanel.add(listLbl);
		infPanel.add(field);
		//----------------------------------------------------------------------

		//-----------------------------RESULT-----------------------------------
		final JPanel resultPanel = new JPanel();
		fillResult(resultPanel);//u funkciju jer emo zvati opet po pritisku dugmeta
		//----------------------------------------------------------------------

		//------------------------CALCULATE BUTTON------------------------------
		GridLayout buttonLayout = new GridLayout(1,5);
		final JPanel calcFrame = new JPanel(buttonLayout);
		JButton calc = new JButton("Izracunaj");
		calc.setFont(new Font("Serif", Font.PLAIN, 25));
		calc.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event)
			{
				resultPanel.removeAll();
				result = calculate(codes.getSelectedItem().toString(), field.getText());
				fillResult(resultPanel);				
				resultPanel.validate();
				resultPanel.repaint();
			}
		});
		calcFrame.add(new JLabel(""));
		calcFrame.add(new JLabel(""));
		calcFrame.add(calc);
		calcFrame.add(new JLabel(""));
		calcFrame.add(new JLabel(""));
		//----------------------------------------------------------------------
		
		//---------------------------FRAME PANEL--------------------------------
		framePanel.add(new JLabel(""));
		framePanel.add(codePanel);
		framePanel.add(new JLabel(""));
		framePanel.add(infPanel);
		framePanel.add(new JLabel(""));
		framePanel.add(calcFrame);
		framePanel.add(new JLabel(""));
		framePanel.add(resultPanel);
		//----------------------------------------------------------------------
		
		guiFrame.add(framePanel);
		guiFrame.setVisible(true);
	}
	
	private void fillResult(JPanel resultPanel) {
		JLabel resultLbl = new JLabel("Kodirana sekvenca: ");
		JLabel result2Lbl = new JLabel(result);
		resultLbl.setFont(new Font("Serif", Font.PLAIN, 25));
		result2Lbl.setFont(new Font("Serif", Font.PLAIN, 25));
		if(result2Lbl.getText() != null && (result2Lbl.getText().length()>41 || n>38))result2Lbl.setFont(new Font("Serif", Font.PLAIN, 20));
		if(result2Lbl.getText() != null && n>47)result2Lbl.setFont(new Font("Serif", Font.PLAIN, 15));
		resultPanel.add(resultLbl);
		resultPanel.add(result2Lbl);
		
	}
	
	protected String calculate(String code, String text) {
		try{
			Long.parseLong(text, 2);
			
			n = Integer.parseInt(code.split(",")[0].substring(1));
			k = Integer.parseInt(code.split(",")[1].substring(0,code.split(",")[1].length()-1));
			if(k != text.length()) return "Ne poklapa se broj bitova! Potrebni broj bitova je "+k+".";
			int[] i = new int[text.length()];
			for(int j = 0; j<text.length();j++) {
				i[j] = Character.getNumericValue(text.charAt(j));
			}
			int[] arr = new int[n+1];
			createArray(arr, i);
			findZ(arr, n, k, i);//z1,z2,...	
			
			String res = "";
			for(int j = 1; j<arr.length; j++) res += arr[j];
			return res;
		}
		catch(NumberFormatException e) {
			return "Informaciona sekvenca nije bitni zapis!";
		}
	}
	public boolean isPowerOfTwo(int number) {
	    return 
	        ( (number & (-number)) == number ) && (number > 0);
	}
	
	private void createArray(int[] arr, int[] i) {
		int t = 0;
		for(int j = 2; j<arr.length; j++) {
			if(isPowerOfTwo(j))continue;//z
			arr[j] = i[t++];
		}
		
	}
	
	private void findZ(int[] arr, int n, int k, int[] i) {
		int t = 1;
		for(int j = 1; j<arr.length; j++) {
			if(!isPowerOfTwo(j) && j!=1)continue;//nije z
			arr[j] = 0;
			for(int s = 2; s<arr.length; s++) {//xorujemo sve i bitove
				if(isPowerOfTwo(s))continue;//z
				if((s&t) == 0) continue;
				arr[j] = arr[j]^arr[s];
			}
			t *=2;
		}
	}

}
