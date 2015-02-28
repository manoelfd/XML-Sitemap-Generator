import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;


public class XMLSitemapGeneratorController {

  /**
   * Launch the application.
   */
  private XMLSitemapGeneratorView view;
  private XMLSitemapGeneratorTask task;
  boolean taskInProgress = false;

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    new XMLSitemapGeneratorController(new XMLSitemapGeneratorView());

  }

  public XMLSitemapGeneratorController(XMLSitemapGeneratorView frame) {
    this.view = frame;
    view.addWindowListener(new WindowListener() {

      @Override
      public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        int answer = 0;
        if (taskInProgress) {
          Toolkit.getDefaultToolkit().beep();
          answer = JOptionPane.showConfirmDialog(view.getFrame(), "Are you sure you want to quit?", "Confirm", 0);
        }
        if (answer == 0)
          System.exit(0);
      }

      @Override
      public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

      }
    });
    view.addGenerateButtonActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          task =
              new XMLSitemapGeneratorTask(view.getSourcePathFieldText(), view.getDestinationPathFieldText(), view.getWebsiteFieldText(), view.getNumOfSitemaps(), view.getNumUrls(), view
                  .getFirstNumber(), view.getPriority(), view.getFrequency(), view.getDate());
          task.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
              String propertyName = evt.getPropertyName();
              if ("state".equals(propertyName)) {
                if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                  System.out.println("Done!");
                  try {
                    Integer[] results = task.get();
                    view.setProgressBarValue(100);
                    JOptionPane.showMessageDialog(view.getFrame(), results[0] + " URLs were written in " + results[1] + " sitemap(s).", "Complete", 1);
                    taskInProgress = false;
                    view.toggleControlButtons();
                  } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                  } catch (CancellationException e2) {
                    System.out.println("Caught the exception in cancel");
                    view.setProgressBarIndeterminateMode(false);
                    view.setProgressBarValue(0);
                    view.toggleControlButtons();
                    taskInProgress = false;

                  }
                }
              } else if ("progress".equals(propertyName)) {
                if (task.isCancelled())
                  return;
                view.setProgressBarIndeterminateMode(false);
                view.setProgressBarValue((int) evt.getNewValue());
              }
            }
          });
          view.toggleControlButtons();
          taskInProgress = true;
          view.setProgressBarIndeterminateMode(true);
          task.execute();
        } catch (IllegalArgumentException e1) {
          System.out.println(e1.getMessage());
          Toolkit.getDefaultToolkit().beep();
          JOptionPane.showMessageDialog(view.getFrame(), e1.getMessage(), "Error", 0);

        }

      }

    });
    view.addCancelButtonActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().beep();
        int answer = JOptionPane.showConfirmDialog(view.getFrame(), "Are you sure you want to cancel?", "Confirm", 0);
        if (answer == 0) { // cancel the task
          task.cancel(true);
        }
      }
    });


    view.addSourceExploreButtonActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser choose = new JFileChooser();
        choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnval = choose.showOpenDialog(view.getContentPane());
        if (returnval == JFileChooser.APPROVE_OPTION)
          view.setSourcePathFieldText(choose.getSelectedFile().toString());
      }

    });
    view.addDestinationExploreButtonActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser choose = new JFileChooser();
        choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnval = choose.showOpenDialog(view.getContentPane());
        if (returnval == JFileChooser.APPROVE_OPTION)
          view.setDestinationPathFieldText(choose.getSelectedFile().toString());
      }



    });
    view.addNumSitemapsBoxListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String num = view.getNumOfSitemaps();
        if (num.equalsIgnoreCase("Multiple")) {
          view.toggleMultipleSitemapOptions(true);
        } else
          view.toggleMultipleSitemapOptions(false);
      }

    });

    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          view.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

}
