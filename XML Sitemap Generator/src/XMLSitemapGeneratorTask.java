import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingWorker;

public class XMLSitemapGeneratorTask extends SwingWorker<Integer[], String> {
  private File sourceFolder;
  private File destinationFolder;
  private String website;
  private String numSitemaps;
  private String priority;
  private String changeFrequency;
  private int numURLsPerSitemap;
  private int numLeadingZeros;
  private Date date;
  private static final String NEW_LINE = "\r\n";
  private static final int PERCENT_OF_FINDING_FILES = 90;
  private static final int PERCENT_OF_WRITING_FILES = 10;

  public XMLSitemapGeneratorTask(String source, String destination, String website, String numSitemaps, int numURLsPerSitemap, String firstNumber, String priority, String changeFrequency, Date date)
      throws IllegalArgumentException {
    if (!new File(source).isDirectory())
      throw new IllegalArgumentException("Invalid source path!");
    if (!new File(destination).isDirectory())
      throw new IllegalArgumentException("Invalid destination path!");
    if (!website.matches("http://www.[\\w-]*[.][\\w-]+"))
      throw new IllegalArgumentException("Websites must be of the form http://www.name.com");
    if (date == null)
      throw new IllegalArgumentException("Date must be of the form yyyy-mm-dd!");

    switch (firstNumber) {
      case "1":
        this.numLeadingZeros = 1;
        break;
      case "01":
        this.numLeadingZeros = 2;
        break;
      case "001":
        this.numLeadingZeros = 3;
        break;
      case "0001":
        this.numLeadingZeros = 4;
        break;
      case "00001":
        this.numLeadingZeros = 5;
        break;
      default: // can't happen
    }
    // all is good
    this.sourceFolder = new File(source);
    this.destinationFolder = new File(destination);
    this.website = website;
    this.numSitemaps = numSitemaps;
    this.numURLsPerSitemap = numURLsPerSitemap;
    this.priority = priority;
    this.changeFrequency = changeFrequency;
    this.date = date;
  }

  @Override
  protected Integer[] doInBackground() throws Exception {
    int totalFiles = 0, siteMaps = 0;
    ArrayList<String> htmlFileNames = new ArrayList<String>();
    String fileName;
    totalFiles = sourceFolder.listFiles().length;
    int processed = 0;
    for (File file : sourceFolder.listFiles()) {
      if (isCancelled()) {
        return null;
      }
      if (file.isDirectory()) {
        processed++;
        setProgress(Math.min(PERCENT_OF_FINDING_FILES, (int) ((((double) processed) / totalFiles) * PERCENT_OF_FINDING_FILES)));
        continue;
      }
      fileName = file.getName();
      if (fileName.endsWith(".html")) {
        if (fileName.toLowerCase().contains("google") || fileName.toLowerCase().contains("twitter")) {
          processed++;
          setProgress(Math.min(PERCENT_OF_FINDING_FILES, (int) ((((double) processed) / totalFiles) * PERCENT_OF_FINDING_FILES)));
          continue;
        }
        htmlFileNames.add(fileName);
      }
      processed++;
      setProgress(Math.min(PERCENT_OF_FINDING_FILES, (int) ((((double) processed) / totalFiles) * PERCENT_OF_FINDING_FILES)));
    }
    setProgress(PERCENT_OF_FINDING_FILES);
    totalFiles = htmlFileNames.size();
    if (totalFiles == 0)
      return new Integer[] {0, 0};

    // part 2. write the files!
    String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
    FileWriter out;
    siteMaps = 1; // files written
    processed = 0;
    if (isCancelled()) {
      return null;
    }
    try {
      if (numSitemaps.equalsIgnoreCase("single")) {
        out = new FileWriter(new File(destinationFolder + "/sitemap.xml"));
      } else
        out = new FileWriter(new File(destinationFolder + "/sitemap" + String.format("%0" + numLeadingZeros + "d", 1) + ".xml"));
      writeHeader(out);

      for (String htmlFile : htmlFileNames) {
        if (isCancelled()) {
          return null;
        }
        if (numSitemaps.equalsIgnoreCase("multiple")) {
          if (processed % numURLsPerSitemap == 0 && processed > 0) {
            siteMaps++;
            writeTrailer(out);
            out.flush();
            out.close();
            out = new FileWriter(new File(destinationFolder + "/sitemap" + String.format("%0" + numLeadingZeros + "d", siteMaps) + ".xml"));
            writeHeader(out);
          }
        }
        out.write("<url><loc>" + website + "/" + htmlFile + "</loc><lastmod>" + dateString + "</lastmod><changefreq>" + changeFrequency + "</changefreq><priority>" + priority + "</priority></url>");
        out.write(NEW_LINE);
        processed++;
        setProgress(Math.min(PERCENT_OF_FINDING_FILES + PERCENT_OF_WRITING_FILES, PERCENT_OF_FINDING_FILES + (int) ((((double) processed) / totalFiles) * PERCENT_OF_WRITING_FILES)));
      }
      writeTrailer(out);
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    htmlFileNames.clear();
    return new Integer[] {totalFiles, siteMaps};
  }

  private void writeHeader(FileWriter out) {
    try {
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
      out.write("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\r\n");
      out.write(NEW_LINE);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void writeTrailer(FileWriter out) {
    try {
      out.write(NEW_LINE);
      out.write("</urlset>");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
