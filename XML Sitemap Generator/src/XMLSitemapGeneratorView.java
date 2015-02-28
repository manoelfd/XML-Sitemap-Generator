import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JDateChooser;

import javax.swing.SwingConstants;
import javax.swing.JProgressBar;


@SuppressWarnings("serial")
public class XMLSitemapGeneratorView extends JFrame {

  private JPanel contentPane;
  private JTextField sourcePathField;
  private JTextField destinationPathField;
  private JTextField websiteField;
  private JPanel websiteBorder;
  private JComboBox<String> numSitemapsBox;
  private JLabel lblnumberOfUrls;
  private JSpinner numURLsSpinner;
  private JButton generateButton;
  private JButton destinationExploreButton;
  private JDateChooser dateChooser;
  private JComboBox<String> frequencyBox;
  private JProgressBar progressBar;
  private JComboBox<String> priorityBox;
  private JButton sourceExploreButton;
  private JLabel lblNumberOfTrailing;
  private JComboBox<String> numberFromBox;
  JButton cancelButton;
  
  public JPanel getFrame() {
    return contentPane;
  }
  
  public void setSourcePathFieldText(String s) {
    sourcePathField.setText(s);
  }
  public void setDestinationPathFieldText(String s) {
    destinationPathField.setText(s);
  }
  public void setProgressBarValue(int n) {
    progressBar.setValue(n);
    if(n == 0)
      progressBar.setStringPainted(false);
    else
      progressBar.setStringPainted(true);
  }
  public void setProgressBarString(String s) {
    progressBar.setString(s);
  }
  public void setProgressBarIndeterminateMode(boolean b) {
    progressBar.setIndeterminate(b);
  }
  public String getSourcePathFieldText() {
    return sourcePathField.getText();
  }
  public String getDestinationPathFieldText() {
    return destinationPathField.getText();
  }  
  public String getWebsiteFieldText() {
    return websiteField.getText();
  }
  public String getNumOfSitemaps() {
    return (String)this.numSitemapsBox.getSelectedItem();
  }
  public int getNumUrls() {
    return (int)numURLsSpinner.getValue();
  }
  public String getFirstNumber() {
    return (String)numberFromBox.getSelectedItem();
  }
  public String getPriority() {
    return (String)priorityBox.getSelectedItem();
  }
  public String getFrequency() {
    return (String)frequencyBox.getSelectedItem();
  }
  public Date getDate() {
    return (Date)dateChooser.getDate();
  }
  public void toggleMultipleSitemapOptions(boolean b) {
    numURLsSpinner.setEnabled(b);
    lblnumberOfUrls.setEnabled(b);
    numberFromBox.setEnabled(b);
    lblNumberOfTrailing.setEnabled(b);
  }
  public void toggleControlButtons() {
    generateButton.setEnabled(!generateButton.isEnabled());
    cancelButton.setEnabled(!cancelButton.isEnabled());
  }
  public void addGenerateButtonActionListener(ActionListener l) {
    generateButton.addActionListener(l);
  }
  public void addCancelButtonActionListener(ActionListener l) {
    cancelButton.addActionListener(l);
  }
  public void addNumSitemapsBoxListener(ActionListener l) {
    numSitemapsBox.addActionListener(l);
  }
  public void addSourceExploreButtonActionListener(ActionListener l) {
    sourceExploreButton.addActionListener(l);
  }
  public void addDestinationExploreButtonActionListener(ActionListener l) {
    destinationExploreButton.addActionListener(l);
  }
  /**
   * Create the frame.
   */
  public XMLSitemapGeneratorView() {
    setResizable(false);
    setTitle("XML Sitemap Generator");
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setBounds(100, 100, 495, 482);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    websiteBorder = new JPanel();
    websiteBorder.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Website", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    websiteBorder.setBounds(10, 189, 465, 78);
    contentPane.add(websiteBorder);
    websiteBorder.setLayout(null);
    
    JPanel panel = new JPanel();
    panel.setBounds(6, 16, 407, 55);
    websiteBorder.add(panel);
    panel.setLayout(null);
    
    websiteField = new JTextField();
    websiteField.setText("http://www.");
    websiteField.setBounds(10, 21, 337, 20);
    panel.add(websiteField);
    websiteField.setColumns(10);
    
    JLabel lblPleaseSpecifyThe = new JLabel("Please specify the website URL:");
    lblPleaseSpecifyThe.setBounds(10, 4, 321, 14);
    panel.add(lblPleaseSpecifyThe);
    
    JPanel sourceBorder = new JPanel();
    sourceBorder.setLayout(null);
    sourceBorder.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Source Folder", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    sourceBorder.setBounds(10, 11, 465, 78);
    contentPane.add(sourceBorder);
    
    JPanel panel_3 = new JPanel();
    panel_3.setLayout(null);
    panel_3.setBounds(6, 16, 449, 55);
    sourceBorder.add(panel_3);
    
    sourcePathField = new JTextField();
    sourcePathField.setColumns(10);
    sourcePathField.setBounds(10, 21, 337, 20);
    panel_3.add(sourcePathField);
    
    JLabel lblPleaseSpecifyThe_1 = new JLabel("Please specify the path to the source folder:");
    lblPleaseSpecifyThe_1.setBounds(10, 4, 321, 14);
    panel_3.add(lblPleaseSpecifyThe_1);
    
    sourceExploreButton = new JButton("Browse...");
    sourceExploreButton.setBounds(350, 20, 99, 23);
    panel_3.add(sourceExploreButton);
    
    JPanel optionsBorder = new JPanel();
    optionsBorder.setLayout(null);
    optionsBorder.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    optionsBorder.setBounds(10, 282, 465, 129);
    contentPane.add(optionsBorder);
    
    JPanel panel_5 = new JPanel();
    panel_5.setBounds(6, 16, 449, 25);
    optionsBorder.add(panel_5);
    panel_5.setLayout(null);
    
    JLabel lblNumberOfSitemaps = new JLabel("Number of Sitemaps: ");
    lblNumberOfSitemaps.setBounds(10, 5, 103, 14);
    panel_5.add(lblNumberOfSitemaps);
    
    numSitemapsBox = new JComboBox<String>();
    numSitemapsBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Single", "Multiple"}));
    numSitemapsBox.setBounds(123, 3, 73, 18);
    panel_5.add(numSitemapsBox);
    
    lblnumberOfUrls = new JLabel("Number of URLS per sitemap:");
    lblnumberOfUrls.setEnabled(false);
    lblnumberOfUrls.setBounds(206, 5, 145, 14);
    panel_5.add(lblnumberOfUrls);
    
    numURLsSpinner = new JSpinner();
    numURLsSpinner.setEnabled(false);
    numURLsSpinner.setModel(new SpinnerNumberModel(1, 1, 50000, 1));
    numURLsSpinner.setBounds(361, 2, 61, 20);
    panel_5.add(numURLsSpinner);
    
    JPanel panel_2 = new JPanel();
    panel_2.setLayout(null);
    panel_2.setBounds(6, 47, 196, 25);
    optionsBorder.add(panel_2);
    
    lblNumberOfTrailing = new JLabel("First sitemap file no:");
    lblNumberOfTrailing.setEnabled(false);
    lblNumberOfTrailing.setBounds(10, 5, 106, 14);
    panel_2.add(lblNumberOfTrailing);
    
    numberFromBox = new JComboBox<String>();
    numberFromBox.setMaximumRowCount(5);
    numberFromBox.setEnabled(false);
    numberFromBox.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "01", "001", "0001", "00001"}));
    numberFromBox.setBounds(123, 2, 73, 20);
    panel_2.add(numberFromBox);
    
    JPanel panel_8 = new JPanel();
    panel_8.setLayout(null);
    panel_8.setBounds(284, 50, 171, 25);
    optionsBorder.add(panel_8);
    
    JLabel lblPriority = new JLabel("Priority:");
    lblPriority.setBounds(31, 5, 38, 14);
    panel_8.add(lblPriority);

    priorityBox = new JComboBox<String>();
    priorityBox.setModel(new DefaultComboBoxModel<String>(new String[] {"0.5", "0.6", "0.7", "0.8", "0.9", "1.0"}));
    priorityBox.setBounds(82, 3, 63, 18);
    panel_8.add(priorityBox);
    
    JPanel panel_9 = new JPanel();
    panel_9.setLayout(null);
    panel_9.setBounds(6, 83, 196, 25);
    optionsBorder.add(panel_9);
    
    JLabel lblChangeFrequency = new JLabel("Change Frequency:");
    lblChangeFrequency.setBounds(11, 5, 104, 14);
    panel_9.add(lblChangeFrequency);
    
    frequencyBox = new JComboBox<String>();
    frequencyBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Daily", "Weekly", "Monthly"}));
    frequencyBox.setBounds(123, 3, 73, 18);
    panel_9.add(frequencyBox);
    
    JPanel panel_11 = new JPanel();
    panel_11.setLayout(null);
    panel_11.setBounds(234, 86, 221, 32);
    optionsBorder.add(panel_11);
    
    JLabel lblDate = new JLabel("Creation Date:");
    lblDate.setHorizontalAlignment(SwingConstants.TRAILING);
    lblDate.setToolTipText("");
    lblDate.setBounds(0, 5, 81, 20);
    panel_11.add(lblDate);
    
    dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("yyyy-MM-dd");
    dateChooser.setBounds(92, 5, 104, 20);
    dateChooser.setDate((Calendar.getInstance().getTime()));
    panel_11.add(dateChooser);
    
    JPanel destinationBorder = new JPanel();
    destinationBorder.setLayout(null);
    destinationBorder.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Destination Folder", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    destinationBorder.setBounds(10, 100, 465, 78);
    contentPane.add(destinationBorder);
    
    JPanel panel_7 = new JPanel();
    panel_7.setLayout(null);
    panel_7.setBounds(6, 16, 449, 55);
    destinationBorder.add(panel_7);
    
    destinationPathField = new JTextField();
    destinationPathField.setColumns(10);
    destinationPathField.setBounds(10, 21, 337, 20);
    panel_7.add(destinationPathField);
    
    JLabel lblPleaseSpecifyThe_2 = new JLabel("Please specify the path to the destination folder:");
    lblPleaseSpecifyThe_2.setBounds(10, 4, 321, 14);
    panel_7.add(lblPleaseSpecifyThe_2);
    
    destinationExploreButton = new JButton("Browse...");
    destinationExploreButton.setBounds(350, 20, 99, 23);
    panel_7.add(destinationExploreButton);
    
    generateButton = new JButton("Generate ");
    generateButton.setBounds(10, 422, 89, 23);
    contentPane.add(generateButton);
    
    progressBar = new JProgressBar();
    progressBar.setBounds(109, 422, 267, 23);
    contentPane.add(progressBar);
    
    cancelButton = new JButton("Cancel");
    cancelButton.setEnabled(false);
    cancelButton.setBounds(386, 422, 89, 23);
    contentPane.add(cancelButton);
    
  }


}
