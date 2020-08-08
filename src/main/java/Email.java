import java.io.*;
import java.lang.String;
import java.lang.Exception;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class Email extends HttpServlet {
    int accesses = 0;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.print("<html>");
        out.print("<head>");
        out.print("<meta charset='utf-8'>");
        out.print("<meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no'>");
        //Bootstrap library
        out.print("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css' integrity='sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO' crossorigin='anonymous'>");
        //jQuery library
        out.print("<script type='text/javascript' src='https://code.jquery.com/jquery-3.5.1.min.js'></script>");
        //ChartJS library
        out.print("<script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.bundle.min.js'></script>");
        out.print("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.css'>");

        //Project files
        out.print("<script type='text/javascript' src='scripts/scripts.js'></script>");
        out.print("<link rel='stylesheet' href='styles/styles.css'>");
        out.print("<title>XML example</title>");
        out.print("</head>");
        out.print("<body><h1>Result</h1>");
        out.print("<canvas id='myChart' width='400' height='400'></canvas>");

        try{
            File inputFile = File.createTempFile(this.getClass().getSimpleName(), null);
            inputFile.deleteOnExit();
            ServletContext ctx = getServletContext();
            FileUtils.copyInputStreamToFile(ctx.getResourceAsStream("/files/Roll-In-soapui-project.xml"), inputFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("item");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String mName = eElement.getElementsByTagName("mName").item(0).getTextContent();
                    String mType = eElement.getElementsByTagName("mType").item(0).getTextContent();
                    String mValue;

                    if (mName.equalsIgnoreCase("FreeVirtualMemory")){
                        mValue = eElement.getElementsByTagName("mValue").item(0).getTextContent();
                        out.print("<input type='hidden' id='FreeVirtualMemory' name='FreeVirtualMemory' value='"+mValue+"'></input>");
                    }
                    if (mName.equalsIgnoreCase("FreePhysicalMemory")){
                        mValue = eElement.getElementsByTagName("mValue").item(0).getTextContent();
                        out.print("<input type='hidden' id='FreePhysicalMemory' name='FreePhysicalMemory' value='"+mValue+"'></input>");
                    }
                }
            }
        } catch (Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            out.print("<h6>"+sw.toString()+"</h6>");
        }
        out.print("</body></html>");
    }
} 