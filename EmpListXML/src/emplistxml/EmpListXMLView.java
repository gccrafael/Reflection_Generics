/*
 * EmpListXMLView.java
 */

package emplistxml;

import business.EmpIO;
import business.Employee;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The application's main frame.
 */
public class EmpListXMLView extends FrameView {
    Map<Long,Employee> emps;
    Map<String,Employee> empsbyname;
    Map<String, JTextField> screenmap;
    String[] getmethods = {"getEmpno", "getPaycd", "getPhone", "getLastnm", 
                           "getFirstnm", "getMidnm", "getSuffix", "getAddr1",
                            "getAddr2", "getCity", "getState", "getZip", "getGender",
                            "getStatus", "getHiredt", "getTerminatedt"};
    JTextField[] fields;
    boolean loading = false;
    public EmpListXMLView(SingleFrameApplication app) {
        super(app);

        initComponents();
        
        JTextField[] fld = {jtxtEmpNo, jtxtPayCd, jtxtPhone, jtxtLastNm, jtxtFirstNm,
                             jtxtMidNm, jtxtSuffix, jtxtAddr1, jtxtAddr2, jtxtCity, 
                            jtxtState, jtxtZip, jtxtGender, jtxtStatus, jtxtHireDt,
                             jtxtTermDt};
        fields = fld;
        screenmap = new HashMap<>();
        for (int i = 0; i < getmethods.length; i++) {
            screenmap.put(getmethods[i], fields[i]);
        }
        
        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = EmpListXMLApp.getApplication().getMainFrame();
            aboutBox = new EmpListXMLAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        EmpListXMLApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jradHashMap = new javax.swing.JRadioButton();
        jradTreeMap = new javax.swing.JRadioButton();
        jradNameKey = new javax.swing.JRadioButton();
        cmbKeys = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jtxtEmpNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxtLastNm = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtFirstNm = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtMidNm = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtxtSuffix = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtAddr1 = new javax.swing.JTextField();
        jtxtAddr2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtxtCity = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtxtState = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtxtZip = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxtPhone = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtxtGender = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtxtStatus = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtxtHireDt = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtxtTermDt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jtxtPayCd = new javax.swing.JTextField();
        jbtnDelete = new javax.swing.JButton();
        jbtnClear = new javax.swing.JButton();
        jbtnPrev = new javax.swing.JButton();
        jbtnNext = new javax.swing.JButton();
        jbtnUpdate = new javax.swing.JButton();
        jbtnAdd = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jmnuLoad = new javax.swing.JMenuItem();
        jmnuSaveCSV = new javax.swing.JMenuItem();
        jmnuLoadXML = new javax.swing.JMenuItem();
        jmnuSaveXML = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        buttonGroup1 = new javax.swing.ButtonGroup();

        mainPanel.setName("mainPanel"); // NOI18N

        buttonGroup1.add(jradHashMap);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(emplistxml.EmpListXMLApp.class).getContext().getResourceMap(EmpListXMLView.class);
        jradHashMap.setText(resourceMap.getString("jradHashMap.text")); // NOI18N
        jradHashMap.setName("jradHashMap"); // NOI18N
        jradHashMap.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jradHashMapItemStateChanged(evt);
            }
        });
        jradHashMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jradHashMapActionPerformed(evt);
            }
        });

        buttonGroup1.add(jradTreeMap);
        jradTreeMap.setText(resourceMap.getString("jradTreeMap.text")); // NOI18N
        jradTreeMap.setToolTipText(resourceMap.getString("jradTreeMap.toolTipText")); // NOI18N
        jradTreeMap.setName("jradTreeMap"); // NOI18N
        jradTreeMap.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jradTreeMapItemStateChanged(evt);
            }
        });
        jradTreeMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jradTreeMapActionPerformed(evt);
            }
        });

        buttonGroup1.add(jradNameKey);
        jradNameKey.setText(resourceMap.getString("jradNameKey.text")); // NOI18N
        jradNameKey.setName("jradNameKey"); // NOI18N
        jradNameKey.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jradNameKeyItemStateChanged(evt);
            }
        });

        cmbKeys.setFont(resourceMap.getFont("cmbKeys.font")); // NOI18N
        cmbKeys.setName("cmbKeys"); // NOI18N
        cmbKeys.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbKeysItemStateChanged(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setToolTipText(resourceMap.getString("jLabel1.toolTipText")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setName("jPanel1"); // NOI18N

        jtxtEmpNo.setName("jtxtEmpNo"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setToolTipText(resourceMap.getString("jLabel3.toolTipText")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jtxtLastNm.setName("jtxtLastNm"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setToolTipText(resourceMap.getString("jLabel4.toolTipText")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jtxtFirstNm.setName("jtxtFirstNm"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setToolTipText(resourceMap.getString("jLabel5.toolTipText")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jtxtMidNm.setName("jtxtMidNm"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setToolTipText(resourceMap.getString("jLabel6.toolTipText")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jtxtSuffix.setName("jtxtSuffix"); // NOI18N

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setToolTipText(resourceMap.getString("jLabel7.toolTipText")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jtxtAddr1.setName("jtxtAddr1"); // NOI18N

        jtxtAddr2.setName("jtxtAddr2"); // NOI18N

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setToolTipText(resourceMap.getString("jLabel8.toolTipText")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jtxtCity.setName("jtxtCity"); // NOI18N

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setToolTipText(resourceMap.getString("jLabel9.toolTipText")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jtxtState.setName("jtxtState"); // NOI18N

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setToolTipText(resourceMap.getString("jLabel10.toolTipText")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jtxtZip.setName("jtxtZip"); // NOI18N

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setToolTipText(resourceMap.getString("jLabel11.toolTipText")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jtxtPhone.setName("jtxtPhone"); // NOI18N

        jLabel12.setFont(resourceMap.getFont("jLabel12.font")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setToolTipText(resourceMap.getString("jLabel12.toolTipText")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jtxtGender.setName("jtxtGender"); // NOI18N

        jLabel13.setFont(resourceMap.getFont("jLabel13.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setToolTipText(resourceMap.getString("jLabel13.toolTipText")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jtxtStatus.setName("jtxtStatus"); // NOI18N

        jLabel14.setFont(resourceMap.getFont("jLabel14.font")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setToolTipText(resourceMap.getString("jLabel14.toolTipText")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jtxtHireDt.setName("jtxtHireDt"); // NOI18N

        jLabel15.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setToolTipText(resourceMap.getString("jLabel15.toolTipText")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jtxtTermDt.setName("jtxtTermDt"); // NOI18N

        jLabel16.setFont(resourceMap.getFont("jLabel16.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setToolTipText(resourceMap.getString("jLabel16.toolTipText")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jtxtPayCd.setName("jtxtPayCd"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jtxtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtState, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtZip, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel11)
                                        .addGap(12, 12, 12)
                                        .addComponent(jtxtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 4, Short.MAX_VALUE)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtxtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel14)
                                        .addGap(12, 12, 12)
                                        .addComponent(jtxtHireDt, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtTermDt, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(183, 183, 183)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtGender, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtPayCd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jtxtEmpNo, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtLastNm, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtFirstNm, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jtxtMidNm, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jtxtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtAddr2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtxtAddr1))))
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(3, 3, 3)
                        .addComponent(jtxtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(3, 3, 3)
                        .addComponent(jtxtMidNm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(3, 3, 3)
                        .addComponent(jtxtFirstNm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtEmpNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtLastNm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtxtAddr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtAddr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jtxtState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jtxtZip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jtxtGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jtxtPayCd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jtxtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel15)
                        .addComponent(jtxtTermDt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtHireDt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jbtnDelete.setText(resourceMap.getString("jbtnDelete.text")); // NOI18N
        jbtnDelete.setName("jbtnDelete"); // NOI18N
        jbtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteActionPerformed(evt);
            }
        });

        jbtnClear.setText(resourceMap.getString("jbtnClear.text")); // NOI18N
        jbtnClear.setName("jbtnClear"); // NOI18N
        jbtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnClearActionPerformed(evt);
            }
        });

        jbtnPrev.setText(resourceMap.getString("jbtnPrev.text")); // NOI18N
        jbtnPrev.setName("jbtnPrev"); // NOI18N
        jbtnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPrevActionPerformed(evt);
            }
        });

        jbtnNext.setText(resourceMap.getString("jbtnNext.text")); // NOI18N
        jbtnNext.setName("jbtnNext"); // NOI18N
        jbtnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNextActionPerformed(evt);
            }
        });

        jbtnUpdate.setText(resourceMap.getString("jbtnUpdate.text")); // NOI18N
        jbtnUpdate.setName("jbtnUpdate"); // NOI18N
        jbtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateActionPerformed(evt);
            }
        });

        jbtnAdd.setText(resourceMap.getString("jbtnAdd.text")); // NOI18N
        jbtnAdd.setName("jbtnAdd"); // NOI18N
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jradHashMap)
                .addGap(54, 54, 54)
                .addComponent(jradTreeMap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jradNameKey)
                .addGap(45, 45, 45))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jbtnPrev)
                .addGap(93, 93, 93)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbKeys, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnNext))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jbtnDelete)
                        .addGap(41, 41, 41)
                        .addComponent(jbtnUpdate)
                        .addGap(48, 48, 48)
                        .addComponent(jbtnClear)
                        .addGap(57, 57, 57)
                        .addComponent(jbtnAdd))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jradHashMap)
                    .addComponent(jradTreeMap)
                    .addComponent(jradNameKey))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbKeys, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnPrev)
                    .addComponent(jbtnNext))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnDelete)
                    .addComponent(jbtnClear)
                    .addComponent(jbtnUpdate)
                    .addComponent(jbtnAdd))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N
        fileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuActionPerformed(evt);
            }
        });

        jmnuLoad.setText(resourceMap.getString("jmnuLoad.text")); // NOI18N
        jmnuLoad.setName("jmnuLoad"); // NOI18N
        jmnuLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuLoadActionPerformed(evt);
            }
        });
        fileMenu.add(jmnuLoad);

        jmnuSaveCSV.setText(resourceMap.getString("jmnuSaveCSV.text")); // NOI18N
        jmnuSaveCSV.setName("jmnuSaveCSV"); // NOI18N
        jmnuSaveCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuSaveCSVActionPerformed(evt);
            }
        });
        fileMenu.add(jmnuSaveCSV);

        jmnuLoadXML.setText(resourceMap.getString("jmnuLoadXML.text")); // NOI18N
        jmnuLoadXML.setName("jmnuLoadXML"); // NOI18N
        jmnuLoadXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuLoadXMLActionPerformed(evt);
            }
        });
        fileMenu.add(jmnuLoadXML);

        jmnuSaveXML.setText(resourceMap.getString("jmnuSaveXML.text")); // NOI18N
        jmnuSaveXML.setName("jmnuSaveXML"); // NOI18N
        jmnuSaveXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuSaveXMLActionPerformed(evt);
            }
        });
        fileMenu.add(jmnuSaveXML);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(emplistxml.EmpListXMLApp.class).getContext().getActionMap(EmpListXMLView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 433, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jradHashMapItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jradHashMapItemStateChanged
        clearForm();
        try {
            if (jradHashMap.isSelected()) {
                cmbKeys_build();
            }
        } catch (Exception e) {
            statusMessageLabel.setText("Load file on Menu");
        }
    }//GEN-LAST:event_jradHashMapItemStateChanged

    private void jradHashMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jradHashMapActionPerformed
        
    }//GEN-LAST:event_jradHashMapActionPerformed

    private void jradTreeMapItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jradTreeMapItemStateChanged
        clearForm();
        try {
            if (jradTreeMap.isSelected()) {
                cmbKeys_build();
            }
        } catch (Exception e) {
            statusMessageLabel.setText("Load file on Menu");
        }    
    }//GEN-LAST:event_jradTreeMapItemStateChanged

    private void jradTreeMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jradTreeMapActionPerformed

    }//GEN-LAST:event_jradTreeMapActionPerformed

    private void jradNameKeyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jradNameKeyItemStateChanged
        clearForm();
        try {
            if (jradNameKey.isSelected()) {
                empsbyname = new TreeMap<>();            
                for (Map.Entry<Long,Employee> entry : emps.entrySet()) {
                    Employee emp = entry.getValue();
                    String nm = emp.getLastnm() + ", " + emp.getFirstnm() + " " +
                                emp.getMidnm();
                    empsbyname.put(nm, emp);
                }
                cmbKeys_build();
            }
        } catch (Exception e) {
            statusMessageLabel.setText("Load file on Menu");
        }           
    }//GEN-LAST:event_jradNameKeyItemStateChanged

    private void cmbKeysItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbKeysItemStateChanged
        Employee emp = new Employee();
        if (cmbKeys.getSelectedIndex() != -1) {
            statusMessageLabel.setText("");             
        }
        if (loading) {
            return;
        }          
        if (cmbKeys.getSelectedIndex() == -1) {
            statusMessageLabel.setText("No employee selected.");
            return;
        }        
        if (jradNameKey.isSelected()) {
            emp = (Employee)empsbyname.get((String)cmbKeys.getSelectedItem());
        } else {
            emp = (Employee)emps.get((Long)cmbKeys.getSelectedItem());            
        } 
        DisplayValues(emp);
        
    }//GEN-LAST:event_cmbKeysItemStateChanged

    private void jmnuLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuLoadActionPerformed
        clearForm();
        buttonGroup1.clearSelection();
        cmbKeys.removeAllItems();
        statusMessageLabel.setText(" ");
        JFileChooser f = new JFileChooser(".");
        f.setDialogTitle("Select Employee File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
        f.setFileFilter(filter);
        JDialog dg = new JDialog();
        int rval = f.showOpenDialog(dg);
        if (rval == JFileChooser.CANCEL_OPTION) {
            statusMessageLabel.setText("Open cancelled.");
        } else {
            emps = EmpIO.getEmps(f.getSelectedFile().getAbsolutePath());
            statusMessageLabel.setText("Size of emps: " + emps.size());
        }
    }//GEN-LAST:event_jmnuLoadActionPerformed

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed
        if (cmbKeys.getSelectedIndex() == -1) {
            statusMessageLabel.setText("No employee selected.");
        } else {
            if (jradNameKey.isSelected()) {
                try {
                    long key = Long.parseLong(jtxtEmpNo.getText());
                    emps.remove(key);
                    empsbyname.remove((String)cmbKeys.getSelectedItem());
                    
                } catch (Exception e) {
                    statusMessageLabel.setText("Item not deleted.");
                }
            } else {
                try {
                    emps.remove((Long)cmbKeys.getSelectedItem());
                    
                } catch (Exception e) {
                    statusMessageLabel.setText("Item not deleted.");
                }
            }
            cmbKeys_build();
        }
        
       
    }//GEN-LAST:event_jbtnDeleteActionPerformed

    private void jbtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnClearActionPerformed
        clearForm();           
        buttonGroup1.clearSelection();
        cmbKeys.removeAllItems();
    }//GEN-LAST:event_jbtnClearActionPerformed

    private void jbtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdateActionPerformed
        Employee emp;
        statusMessageLabel.setText("");
        if (cmbKeys.getSelectedIndex() == -1) {
            statusMessageLabel.setText("No employee selected.");
            return;            
        }
        if (jradNameKey.isSelected()) {
            emp = (Employee)empsbyname.get((String)cmbKeys.getSelectedItem());
            UpdateValues(emp);
            empsbyname.put((String)cmbKeys.getSelectedItem(), emp);
            
        } else {
            emp = (Employee) emps.get((Long)cmbKeys.getSelectedItem());
            UpdateValues(emp);
            emps.put((Long)cmbKeys.getSelectedItem(), emp);
            
        }
        cmbKeys_build();
        clearForm();
    }//GEN-LAST:event_jbtnUpdateActionPerformed

    private void jbtnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPrevActionPerformed
        int index = cmbKeys.getSelectedIndex();
        if (index == -1) {
            statusMessageLabel.setText("No employee selected.");
        } else if (index == 0) {
            clearForm();
            cmbKeys.setSelectedIndex( index - 1);
            statusMessageLabel.setText("No employee selected.");            
        } else {                                        
            cmbKeys.setSelectedIndex( index - 1);
            statusMessageLabel.setText("");
        }
        
    }//GEN-LAST:event_jbtnPrevActionPerformed

    private void jbtnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNextActionPerformed
        int index = cmbKeys.getSelectedIndex();
        int lastindex = cmbKeys.getItemCount();
        if (index != (lastindex - 1)) {
            cmbKeys.setSelectedIndex( index + 1);
            statusMessageLabel.setText("");
        } else {
            statusMessageLabel.setText("Last item selected.");
        }
    }//GEN-LAST:event_jbtnNextActionPerformed

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed
        long eno = 0;
        try {
            eno = Long.parseLong(jtxtEmpNo.getText());
            if (eno <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            statusMessageLabel.setText("Illegal employee no.");
            return;
        }
        
        Employee emp = new Employee();
        UpdateValues(emp);
        if (jradNameKey.isSelected()) {
            if (emp.getLastnm().isEmpty()) {
                statusMessageLabel.setText("No last name.");
                return;
            }
            String nm = emp.getLastnm() + ", " + emp.getFirstnm() + " " +
                        emp.getMidnm();
            empsbyname.put(nm, emp);
            emps.put(eno, emp);
            
        } else {
            emps.put(eno, emp);
        }
        cmbKeys_build();
        clearForm();
    }//GEN-LAST:event_jbtnAddActionPerformed

    private void jmnuSaveXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuSaveXMLActionPerformed
        statusMessageLabel.setText(" ");
        JFileChooser f = new JFileChooser(".");
        f.setDialogTitle("Select Output File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files (*.xml)", "xml");
        f.setFileFilter(filter);
        JDialog dg = new JDialog();
        int rval = f.showSaveDialog(dg);
        if (rval == JFileChooser.CANCEL_OPTION) {
            statusMessageLabel.setText("XML Save cancelled.");
        } else {
            String status = EmpIO.setEmpsXML(f.getSelectedFile().getAbsolutePath(), emps);
            statusMessageLabel.setText(status);
        }
    }//GEN-LAST:event_jmnuSaveXMLActionPerformed

    private void jmnuLoadXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuLoadXMLActionPerformed
        clearForm();
        buttonGroup1.clearSelection();
        cmbKeys.removeAllItems();
        statusMessageLabel.setText(" ");
        JFileChooser f = new JFileChooser(".");
        f.setDialogTitle("Select Employee File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files (*.xml)", "xml");
        f.setFileFilter(filter);
        JDialog dg = new JDialog();
        int rval = f.showOpenDialog(dg);
        if (rval == JFileChooser.CANCEL_OPTION) {
            statusMessageLabel.setText("Open cancelled.");
        } else {
            emps = EmpIO.getEmpsXML(f.getSelectedFile().getAbsolutePath());
            statusMessageLabel.setText("Size of emps: " + emps.size());
        }    
    }//GEN-LAST:event_jmnuLoadXMLActionPerformed

    private void fileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuActionPerformed
        
    }//GEN-LAST:event_fileMenuActionPerformed

    private void jmnuSaveCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuSaveCSVActionPerformed
        statusMessageLabel.setText(" ");
        JFileChooser f = new JFileChooser(".");
        f.setDialogTitle("Select Output File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
        f.setFileFilter(filter);
        JDialog dg = new JDialog();
        int rval = f.showSaveDialog(dg);
        if (rval == JFileChooser.CANCEL_OPTION) {
            statusMessageLabel.setText("Save cancelled.");
        } else {
            String status = EmpIO.setEmps(f.getSelectedFile().getAbsolutePath(), emps);
            statusMessageLabel.setText(status);
        }
    }//GEN-LAST:event_jmnuSaveCSVActionPerformed
    private void cmbKeys_build() {
        loading = true;
        cmbKeys.removeAllItems();
        if (jradHashMap.isSelected()) {
            Set<Long> keys = emps.keySet();
            ArrayList<Long> akeys = new ArrayList<>(keys);
            //or: ArrayList<Long> akeys = new ArrayList<>(emps.keySet());
            for (Long k : akeys) {
                cmbKeys.addItem(k);
            }
            cmbKeys.setSelectedIndex(-1);
                
        } else if (jradTreeMap.isSelected()) {
            TreeMap<Long,Employee> tmp = new TreeMap<>(emps);
            for (Map.Entry<Long,Employee> entry : tmp.entrySet()) {
                Long k = entry.getKey();
                cmbKeys.addItem(k);
            }        
        } else if (jradNameKey.isSelected()) {
            for (Map.Entry<String,Employee> entry : empsbyname.entrySet()) {
                String nm = entry.getKey();
                cmbKeys.addItem(nm);
            }
        } else {
            statusMessageLabel.setText("Unknown Operaton.");
        }
        cmbKeys.setSelectedIndex(-1);
        loading = false;
    }
    private void DisplayValues(Employee emp) {
        clearForm();
        Class empclass = emp.getClass();
        Method[] methods = empclass.getMethods();
        
        try {
            for (Method m : methods) {
                if (screenmap.containsKey(m.getName())) {
                    JTextField f = screenmap.get(m.getName());
                    switch (m.getName()) {
                        case "getEmpno":
                            long x = (long)m.invoke(emp, null);
                            f.setText(String.valueOf(x));
                            break;
                        case "getPhone":
                        case "getPaycd":
                            int y = (int)m.invoke(emp, null);
                            f.setText(String.valueOf(y));
                            break;
                        default:
                            f.setText((String)m.invoke(emp, null));
                            break;
                    } //end of switch
                    
                }
            } 
        } catch (Exception e) {
                    statusMessageLabel.setText(e.getMessage());
                    }
    } // end of display values
    private void clearForm() {
        for(JTextField f : fields) {  //clear all displayed fields on the fomr
            f.setText("");
        }
    }
    private void UpdateValues(Employee emp) {
        Class empclass = emp.getClass();
        Method m;
        
        try {
            for (String nm : getmethods) {
                String setmethod = "set" + nm.substring(3);
                JTextField f = screenmap.get(nm);
                switch (setmethod) {
                    case "setEmpno":
                        m = empclass.getMethod(setmethod, new Class[] {Long.class});
                        Long x = Long.parseLong(f.getText());
                        m.invoke(emp, x);
                        break;
                    case "setPhone":
                    case "setPaycd":
                        m = empclass.getMethod(setmethod, new Class[] {Integer.class});
                        try {
                            Integer y = Integer.parseInt(f.getText());
                            m.invoke(emp, y);                            
                        } catch (NumberFormatException e){
                            statusMessageLabel.setText("Illegal phone or pay code "
                                    + "entry: skipped.");
                        }
                        break;
                    default:
                        m = empclass.getMethod(setmethod, new Class[] {String.class});
                        m.invoke(emp, f.getText());
                        break;
                        
                }//end of switch
            }//end of loop    
        } catch (Exception e) {
            statusMessageLabel.setText(e.getMessage());
        }
    }//end of updatevalues
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbKeys;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnAdd;
    private javax.swing.JButton jbtnClear;
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JButton jbtnNext;
    private javax.swing.JButton jbtnPrev;
    private javax.swing.JButton jbtnUpdate;
    private javax.swing.JMenuItem jmnuLoad;
    private javax.swing.JMenuItem jmnuLoadXML;
    private javax.swing.JMenuItem jmnuSaveCSV;
    private javax.swing.JMenuItem jmnuSaveXML;
    private javax.swing.JRadioButton jradHashMap;
    private javax.swing.JRadioButton jradNameKey;
    private javax.swing.JRadioButton jradTreeMap;
    private javax.swing.JTextField jtxtAddr1;
    private javax.swing.JTextField jtxtAddr2;
    private javax.swing.JTextField jtxtCity;
    private javax.swing.JTextField jtxtEmpNo;
    private javax.swing.JTextField jtxtFirstNm;
    private javax.swing.JTextField jtxtGender;
    private javax.swing.JTextField jtxtHireDt;
    private javax.swing.JTextField jtxtLastNm;
    private javax.swing.JTextField jtxtMidNm;
    private javax.swing.JTextField jtxtPayCd;
    private javax.swing.JTextField jtxtPhone;
    private javax.swing.JTextField jtxtState;
    private javax.swing.JTextField jtxtStatus;
    private javax.swing.JTextField jtxtSuffix;
    private javax.swing.JTextField jtxtTermDt;
    private javax.swing.JTextField jtxtZip;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
