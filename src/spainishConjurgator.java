
import java.awt.EventQueue;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class spainishConjurgator {

	private JFrame frame;
	private JTextField input;
	private JTextField Meaning;
	private JTextField yo;
	private JTextField Nos;
	private JTextField tu;
	JComboBox stemChange;
	private boolean preteriteTense = false;
	boolean printing = true;
	private JTextField ElElla;
	private JTextField uds;
	ArrayList<String> temp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					spainishConjurgator window = new spainishConjurgator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public spainishConjurgator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	ArrayList<String> getHtml(String url) {
		try {
			URL website = new URL(url);
			URLConnection connection = website.openConnection();

			connection.setDoInput(true);
			InputStream inStream = connection.getInputStream();
			BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
			ArrayList<String> html = new ArrayList<String>();
			String line = "";
			while ((line = input.readLine()) != null) {
				System.out.println(line);
				html.add(line);
			}
			return html;

			// Now you can do what you please with
			// the HTML content (save it locally, parse, etc...)
		} catch (Exception e) {
			// Error handling
		}
		return null;
	}

	ArrayList<String> preteriteConurgation(ArrayList<String> html) {
		int i = 0;
		ArrayList<String> Conjurgations = new ArrayList<String>();
		while (i < html.size()) {
			if (html.get(i).indexOf("Preterite") != -1 && html.get(i).indexOf("a title") != -1) {
				// System.out.println("hit");
				break;
			} else {
				i++;
			}
		}
		int y = 0;
		while (y <= 15) {

			if ((html.get(y + i).indexOf("english") != -1 || html.get(y + i).indexOf("conjugation") != -1) && y != 12) {
				Conjurgations.add(parseVerb(html.get(i + y)));
			}
			y++;
		}
		return Conjurgations;
	}

	String parseVerb(String rawLine) {
		String verb = rawLine.substring(rawLine.indexOf(">") + 1);
		if (verb.indexOf("Ã¡") != -1) {
			verb = verb.replaceAll("Ã¡", "á");
		}
		if (verb.indexOf("Ã±") != -1) {
			verb = verb.replace("Ã±", "ñ");
		}
		if (verb.indexOf("Ã­") != -1) {
			verb = verb.replace("Ã­", "í");
		}
		if (verb.indexOf("eÂ") != -1) {
			verb = verb.replace("eÂ", "e");
		}
		if (verb.indexOf("Ã³") != -1) {
			verb = verb.replace("Ã³", "ó");
		}
		if (verb.indexOf("Ã©") != -1) {
			verb = verb.replace("Ã©", "é");
		}
		return verb;

	}

	ArrayList<String> getConjurgations(ArrayList<String> html) {
		int i = 0;
		ArrayList<String> Conjurgations = new ArrayList<String>();
		while (i < html.size()) {

			if (html.get(i).indexOf("English Translation:</b>  ") != -1) {
				String line = "English Translation:</b>  ";
				Conjurgations.add(html.get(i).substring(line.length() + 4));
			}
			if (html.get(i).indexOf("Present Tense Indicative") != -1) {
				break;
			} else {
				i++;
			}
		}
		int y = 0;
		while (y <= 15) {
			if ((html.get(y + i).indexOf("english") != -1 || html.get(y + i).indexOf("conjugation") != -1) && y != 12) {
				Conjurgations.add(parseVerb(html.get(i + y)));
			}
			y++;
		}
		return Conjurgations;
	}

	ArrayList<String> display() {
		if (!input.getText().equals("")) {
			ArrayList<String> info = null;
			if (!preteriteTense) {
				info = getConjurgations(
						getHtml("https://www.123teachme.com/spanish_verb_conjugation/" + input.getText()));
			} else {
				info = preteriteConurgation(
						getHtml("https://www.123teachme.com/spanish_verb_conjugation/" + input.getText()));
			}
			if (preteriteTense) {
				Meaning.setText(info.get(0));
				yo.setText(info.get(1));
				tu.setText(info.get(2));

				stemChange.setSelectedItem(stemChangers.None);
				ElElla.setText(info.get(3));
				Nos.setText(info.get(4));
				uds.setText(info.get(5));
			} else {
				Meaning.setText(info.get(0));
				yo.setText(info.get(2));
				tu.setText(info.get(3));

				stemChange.setSelectedItem(stemChangers.None);
				ElElla.setText(info.get(4));
				Nos.setText(info.get(5));
				uds.setText(info.get(6));
			}
			return info;
		}
		return null;
	}

	void printVerb(ArrayList<String> info) {
		if (printing&&info!=null) {
			try {
				if (!preteriteTense) {
					PrintWriter writer = new PrintWriter(new FileWriter(System.getProperty("user.home") + File.separator
							+ "Desktop" + File.separator + "quizlet.txt", true));

					if (stemChange.getSelectedItem() != stemChangers.None) {
						stemChangers temp = (stemChangers) stemChange.getSelectedItem();
						writer.println(input.getText() + " " + temp.stemChange + " :" + Meaning.getText());
					} else {
						writer.println(input.getText() + ":" + Meaning.getText());
					}
					writer.close();
				} else {
					PrintWriter writer = new PrintWriter(new FileWriter(System.getProperty("user.home") + File.separator
							+ "Desktop" + File.separator + "quizlet.txt", true));
					String[] nouns = { "yo", "tu", "el/ella/Ud./", "Nosotros(as)", "Uds./Ellos/Ellas" };
					for (int i = 0; i < 5; i++) {
						writer.println(info.get(i + 1) + ":" + info.get(0) + "(" + nouns[i] + ")");
						System.out.println(i + ")" + nouns[i] + "," + info.get(i + 1));
					}
					writer.close();
				}
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initialize() {
		frame = new JFrame();

		frame.setBounds(100, 100, 450, 316);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		input = new JTextField();
		input.setBounds(10, 11, 86, 20);
		frame.getContentPane().add(input);
		input.setColumns(10);
		JButton btnEnter = new JButton("Enter");
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {

					temp = display();
					printVerb(temp);
				}
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE) {
					input.setText("");
				}
				int increment = 0;
				if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					try {
						stemChange.setSelectedIndex(stemChange.getSelectedIndex() + 1);
					} catch (IllegalArgumentException e) {
						stemChange.setSelectedIndex(0);
					}
				}
				if (arg0.getKeyCode() == KeyEvent.VK_UP) {

					try {
						stemChange.setSelectedIndex(stemChange.getSelectedIndex() - 1);
					} catch (IllegalArgumentException e) {
						stemChange.setSelectedIndex(stemChange.getItemCount() - 1);
					}
				}
			}
		});
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				temp = display();
				printVerb(temp);
			}
		});
		btnEnter.setBounds(109, 10, 89, 23);
		frame.getContentPane().add(btnEnter);

		Meaning = new JTextField();
		Meaning.setEditable(false);
		Meaning.setBounds(71, 99, 249, 20);
		frame.getContentPane().add(Meaning);
		Meaning.setColumns(10);

		yo = new JTextField();
		yo.setEditable(false);
		yo.setColumns(10);
		yo.setBounds(71, 130, 127, 20);
		frame.getContentPane().add(yo);

		Nos = new JTextField();
		Nos.setEditable(false);
		Nos.setColumns(10);
		Nos.setBounds(208, 130, 107, 20);
		frame.getContentPane().add(Nos);

		tu = new JTextField();
		tu.setEditable(false);
		tu.setColumns(10);
		tu.setBounds(71, 161, 127, 20);
		frame.getContentPane().add(tu);

		ElElla = new JTextField();
		ElElla.setEditable(false);
		ElElla.setColumns(10);
		ElElla.setBounds(71, 192, 127, 20);
		frame.getContentPane().add(ElElla);

		uds = new JTextField();
		uds.setEditable(false);
		uds.setColumns(10);
		uds.setBounds(208, 192, 107, 20);
		frame.getContentPane().add(uds);

		stemChange = new JComboBox();
		stemChange.setModel(new DefaultComboBoxModel(stemChangers.values()));
		stemChange.setSelectedItem(stemChangers.None);
		stemChange.setBounds(213, 8, 107, 26);
		frame.getContentPane().add(stemChange);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		//
		JCheckBoxMenuItem preteriteChcBx = new JCheckBoxMenuItem("Preterite");
		preteriteChcBx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					preteriteTense = !preteriteTense;
					if (preteriteTense) {
						stemChange.setModel(new DefaultComboBoxModel(stemChangePast.values()));
						stemChange.setSelectedItem(stemChangePast.None);
					} else {
						stemChange.setModel(new DefaultComboBoxModel(stemChangers.values()));
					}
					display();
				} catch (IndexOutOfBoundsException e) {
					System.out.println(
							"Eh, you got an error but it is probbably fine.\nIf not check the action listener for the check box");
				}
			}
		});
		menuBar.add(preteriteChcBx);
		JCheckBoxMenuItem Printing = new JCheckBoxMenuItem("Printing");
		Printing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printing = !printing;
			}
		});
		Printing.setSelected(true);
		menuBar.add(Printing);
	}
}
