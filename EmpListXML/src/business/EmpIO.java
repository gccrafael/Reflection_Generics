
package business;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author Rafael Garcia
 */
public class EmpIO {
    public static Map<Long,Employee> getEmps(String path) {
        Map<Long, Employee> elist = new HashMap<>();
        
        try {
            BufferedReader in = new BufferedReader(
                               new FileReader(path));
            in.readLine(); //discard column headings
            String e = in.readLine();
            while (e != null) {
                String[] edata = e.split(",");
                Employee emp = new Employee();
                long empno = Long.parseLong(edata[0]);
                emp.setEmpno(empno);
                if (!edata[1].isEmpty()) {emp.setFirstnm(edata[1]);}
                if (!edata[2].isEmpty()) {emp.setLastnm(edata[2]);}
                if (!edata[3].isEmpty()) {emp.setMidnm(edata[3]);}
                if (!edata[4].isEmpty()) {emp.setSuffix(edata[4]);}
                if (!edata[5].isEmpty()) {emp.setAddr1(edata[5]);}
                if (!edata[6].isEmpty()) {emp.setAddr2(edata[6]);}
                if (!edata[7].isEmpty()) {emp.setCity(edata[7]);}
                if (!edata[8].isEmpty()) {emp.setState(edata[8]);}
                if (!edata[9].isEmpty()) {emp.setZip(edata[9]);}
                if (!edata[10].isEmpty()) {emp.setPhone(Integer.parseInt(edata[10]));}
                if (!edata[11].isEmpty()) {emp.setGender(edata[11]);}
                if (!edata[12].isEmpty()) {emp.setStatus(edata[12]);}
                if (!edata[13].isEmpty()) {emp.setHiredt(edata[13]);}
                if (!edata[14].isEmpty()) {emp.setTerminatedt(edata[14]);}
                if (!edata[15].isEmpty()) {emp.setPaycd(Integer.parseInt(edata[15]));}
                elist.put(empno, emp);
                e = in.readLine();                
            }//end of while
            in.close();
        } catch (Exception e) {
            //no action: just return map as is
        }
        return elist;
    }
    public static String setEmps(String path, Map<Long,Employee> mp) {
        String status = "";
        
        
        try {
            Iterator<Map.Entry<Long,Employee>> it = mp.entrySet().iterator();
            PrintWriter out = new PrintWriter(new FileWriter(path));
            boolean index = true;
            while(it.hasNext()) {
                Map.Entry<Long,Employee> empentry = it.next();                
                if (index) {
                out.println("Empno" + "," + "Firstname" + "," +
                        "Lastname" + "," + "MiddleInit" + "," +
                        "Suffix" + "," + "Address1" + "," +
                        "Address2" + "," + "City" + "," +
                        "State" + "," + "Zip" + "," +
                        "Phone" + "," + "Gender" + "," +
                        "Status" + "," + "HireDate" + "," +
                        "TerminateDt" + "," + "PayCd");
                index = false;
                }
                out.println(empentry.getValue().toString());
            }
            out.close();
            status = "Employees saved as CSV data.";
        } catch (Exception e) {
            
            status = "Error = " + e.getMessage();
        }
        return status;
    }   
    public static String setEmpsXML(String path, Map<Long,Employee> emps) {
        String status = "Employees saved as XML data.";
        XMLOutputFactory outFactory = XMLOutputFactory.newFactory();
        try {
            Iterator<Map.Entry<Long,Employee>> it = emps.entrySet().iterator();
            FileWriter fileWriter = new FileWriter(path);
            XMLStreamWriter writer = outFactory.createXMLStreamWriter(fileWriter);
        
            writer.writeStartDocument("1.0");
            writer.writeStartElement("Employees");
            
            while (it.hasNext()) {
            Map.Entry<Long,Employee> empentry = it.next();
            Employee emp = empentry.getValue();
            
            //have an employee from map to be written as an XML record
            //using reflection to process each emp field
            Class empclass = emp.getClass();
            Method m;
            
            writer.writeStartElement("Employee");
            writer.writeAttribute("Empno", String.valueOf(emp.getEmpno()));
            //all emp fields follow
            for (String getmethod : Employee.getmethods) {
                int y;
                String val = "";
                switch (getmethod) {
                    case "getEmpno":
                        //no action: attribute on Employee tag
                        break;
                    case "getPhone":
                    case "getPaycd":
                        m = empclass.getMethod(getmethod, null);
                        try {
                            y = (int)(m.invoke(emp, null));
                            val = String.valueOf(y);
                        } catch (NumberFormatException e) {
                            val = "";
                        }
                        break;
                    default:
                        m = empclass.getMethod(getmethod, null);
                        val = (String)(m.invoke(emp, null));
                        break;
                }//end of switch
                if (!getmethod.equalsIgnoreCase("getEmpno")) {
                    writer.writeStartElement(getmethod.substring(3));
                    writer.writeCharacters(val);
                    writer.writeEndElement();
                }
            }//end of for each...
            
            writer.writeEndElement();//end of Employee
            
        }//end of map iterator loop
        writer.writeEndElement(); //end for Employees tag
        writer.flush();
        writer.close();
        } catch (IOException e) {
            status = "FileWriter IO exception: " + e.getMessage();
        } catch (XMLStreamException e) {
            status = "XML Stream error: " + e.getMessage();
        } catch (Exception e) {
            status = "General exception: " + e.getMessage();
        } 
        
        return status;
    }// end of setEmpsXML
    public static Map<Long,Employee> getEmpsXML(String path) {
        Map<Long,Employee> emps = new HashMap<>();
        Employee emp = null;
        Class empclass = null;
        Method m;
        long empno = 0;
        String elementName = "";
        ArrayList<String> empfields = new ArrayList<>();
        for (String field : Employee.getmethods) {
            empfields.add(field.substring(3));
        }
        XMLInputFactory inFactory = XMLInputFactory.newFactory();
        try {
            FileReader fileReader = new FileReader(path);
            XMLStreamReader reader = inFactory.createXMLStreamReader(fileReader);
            while (reader.hasNext()) {
                int eventType = reader.getEventType();
                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementName.equals("Employee")) {
                            emp = new Employee();
                            empclass = emp.getClass();
                            empno = Long.parseLong(reader.getAttributeValue(0));
                            emp.setEmpno(empno);
                        } else if (empfields.indexOf(elementName)>0) {
                            String val = reader.getElementText();
                            String setmethod = "set" + elementName;
                            switch (setmethod) {
                                case "setPhone":
                                case "setPaycd":
                                    m = empclass.getMethod(setmethod, 
                                                   new Class[] {Integer.class});
                                    try {
                                        int y = Integer.parseInt(val);
                                        m.invoke(emp, y);                                        
                                    } catch (Exception e) {
                                        //skip: bad value
                                    }
                                    break;
                                default:
                                    m = empclass.getMethod(setmethod, 
                                                new Class[] {String.class});
                                    try {
                                        m.invoke(emp, val);
                                    } catch (Exception e) {
                                        //skip...
                                    }
                                    break;
                            }// end of switch for field value
                        }// end of else for start element
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementName.equals("Employee")) {
                            emps.put(empno, emp);
                        }
                        break;
                    default:  
                        break;
                }// end of event type switch
                reader.next();
            }//end of while for reader
        } catch (FileNotFoundException | XMLStreamException 
                 | NoSuchMethodException e) {
            return null;
        }
        return emps;
    }
}
