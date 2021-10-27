package view;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
import thread.*;

public class View extends JFrame {

    private DefaultTableModel logTableModel;
    private List<JpgImageFileThread> jpgImageFileThreads;
    
    public View() {
        initComponents();
        initTable();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        logTable = new javax.swing.JTable();
        inputFileBt = new javax.swing.JButton();
        saveFolderBt = new javax.swing.JButton();
        runBt = new javax.swing.JButton();
        showSaveFolder = new javax.swing.JTextField();
        showInputFolder = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Change jpg image properties");
        setResizable(false);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LOG", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        logTable.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        logTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "File name", "Status", "Note"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        logTable.setToolTipText("");
        logTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(logTable);

        inputFileBt.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inputFileBt.setText("Choose jpg image files ");
        inputFileBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFileBtActionPerformed(evt);
            }
        });

        saveFolderBt.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        saveFolderBt.setText("Choose a save folder");
        saveFolderBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFolderBtActionPerformed(evt);
            }
        });

        runBt.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        runBt.setText("Run");
        runBt.setEnabled(false);
        runBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runBtActionPerformed(evt);
            }
        });

        showSaveFolder.setEditable(false);
        showSaveFolder.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        showInputFolder.setEditable(false);
        showInputFolder.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(saveFolderBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputFileBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(runBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showSaveFolder)
                            .addComponent(showInputFolder, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputFileBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showInputFolder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showSaveFolder)
                    .addComponent(saveFolderBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(runBt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(995, 612));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void inputFileBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputFileBtActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Jpg", "jpg"));
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        int res = fileChooser.showDialog(this, "Choose files");
        if(res == JFileChooser.APPROVE_OPTION) {
            showInputFolder.setText(fileChooser.getCurrentDirectory().toString());
            File[] files = fileChooser.getSelectedFiles();
            logTableModel.setRowCount(0);
            this.jpgImageFileThreads = new ArrayList<>();
            for(int i = 0; i < files.length ; i++) 
                jpgImageFileThreads.add(new JpgImageFileThread(i, files[i], this));
        }
        
        allowRun();
    }//GEN-LAST:event_inputFileBtActionPerformed

    private void allowRun() {
        if(!showInputFolder.getText().isEmpty() && !showSaveFolder.getText().isEmpty()) {
            runBt.setEnabled(true);
        }
        else runBt.setEnabled(false);
    }
    
    public void addRow(Object[] rowData) {
        logTableModel.addRow(rowData);
    }
    
    public void setStatus(int rowIndex, String status) {
        logTableModel.setValueAt(status, rowIndex, 2);
    } 
    
    public void setNote(int rowIndex, String note) {
        logTableModel.setValueAt(note, rowIndex, 3);
    } 
    
    private void initTable() {
        logTableModel = (DefaultTableModel) logTable.getModel();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.LEFT);
        for(int i = 0 ; i < logTable.getColumnCount(); i++)
            logTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);      
    }
    
    public String getSaveFolder() {
        return showSaveFolder.getText();
    }
    
    private void saveFolderBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFolderBtActionPerformed
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setAcceptAllFileFilterUsed(false);
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int res = folderChooser.showDialog(this, "Choose folder");
        if(res == JFileChooser.APPROVE_OPTION) {
            showSaveFolder.setText(folderChooser.getSelectedFile().toString());
        }
        
        allowRun();
    }//GEN-LAST:event_saveFolderBtActionPerformed

    private void runBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runBtActionPerformed
        jpgImageFileThreads.forEach(jpgImageFileThread -> {
            jpgImageFileThread.start();
        });
        showInputFolder.setText("");
        allowRun();
    }//GEN-LAST:event_runBtActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton inputFileBt;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable logTable;
    private javax.swing.JButton runBt;
    private javax.swing.JButton saveFolderBt;
    private javax.swing.JTextField showInputFolder;
    private javax.swing.JTextField showSaveFolder;
    // End of variables declaration//GEN-END:variables
}