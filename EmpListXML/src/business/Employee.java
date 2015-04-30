
package business;

/**
 *
 * @author Rafael Garcia
 */
public class Employee {
    public static final String[] getmethods = {"getEmpno", "getPaycd", "getPhone", "getLastnm", 
                           "getFirstnm", "getMidnm", "getSuffix", "getAddr1",
                            "getAddr2", "getCity", "getState", "getZip", "getGender",
                            "getStatus", "getHiredt", "getTerminatedt"};
    private String Firstnm;
    private String Midnm;
    private String Lastnm;
    private String Suffix;
    private String Addr1;
    private String Addr2;
    private String City;
    private String State;
    private String Zip;
    private String Gender;
    private String Status;
    private String Hiredt;
    private String Terminatedt;
    private long Empno;
    private int Phone;
    private int Paycd;
    
    public Employee() {
        this.Empno = 0;
        this.Phone = 0;
        this.Paycd = 0;
        this.Firstnm = "";
        this.Midnm = "";
        this.Lastnm = "";
        this.Suffix = "";
        this.Addr1 = "";
        this.Addr2 = "";
        this.City = "";
        this.State = "";
        this.Zip = "";
        this.Gender = "";
        this.Status = "";
        this.Hiredt = "";
        this.Terminatedt = "";
        
    }

    public String getFirstnm() {
        return Firstnm;
    }

    public void setFirstnm(String Firstnm) {
        this.Firstnm = Firstnm;
    }

    public String getMidnm() {
        return Midnm;
    }

    public void setMidnm(String Midnm) {
        this.Midnm = Midnm;
    }

    public String getLastnm() {
        return Lastnm;
    }

    public void setLastnm(String Lastnm) {
        this.Lastnm = Lastnm;
    }

    public String getSuffix() {
        return Suffix;
    }

    public void setSuffix(String Suffix) {
        this.Suffix = Suffix;
    }

    public String getAddr1() {
        return Addr1;
    }

    public void setAddr1(String Addr1) {
        this.Addr1 = Addr1;
    }

    public String getAddr2() {
        return Addr2;
    }

    public void setAddr2(String Addr2) {
        this.Addr2 = Addr2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String Zip) {
        this.Zip = Zip;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getHiredt() {
        return Hiredt;
    }

    public void setHiredt(String Hiredt) {
        this.Hiredt = Hiredt;
    }

    public String getTerminatedt() {
        return Terminatedt;
    }

    public void setTerminatedt(String Terminatedt) {
        this.Terminatedt = Terminatedt;
    }

    public long getEmpno() {
        return Empno;
    }

    public void setEmpno(Long Empno) {
        this.Empno = Empno;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(Integer Phone) {
        this.Phone = Phone;
    }

    public int getPaycd() {
        return Paycd;
    }

    public void setPaycd(Integer Paycd) {
        this.Paycd = Paycd;
    }

    @Override
    public String toString()
    {        
        return this.Empno + "," +
               this.Firstnm + "," +
               this.Lastnm + "," +
               this.Midnm + "," +
               this.Suffix + "," +
               this.Addr1 + "," +
               this.Addr2 + "," +
               this.City + "," +
               this.State + "," +
               this.Zip + "," +
               this.Phone + "," +
               this.Gender + "," +
               this.Status + "," +
               this.Hiredt + "," +
               this.Terminatedt + "," +
               this.Paycd;         
    }
    
}
