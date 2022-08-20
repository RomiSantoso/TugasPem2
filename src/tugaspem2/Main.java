/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugaspem2;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import koneksi.DB;

/**
 *
 * @author Romi Santoso
 */
public class Main extends javax.swing.JFrame {
    private DefaultTableModel model;
    private DefaultTableModel modelSiswa;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        
        //desain menu siswa
        inpNis.setEnabled(false); //disable input nis
        
        inpKelas.addItem("-- Pilih --");//get data dari database
        try{                       
            Statement stat = (Statement) DB.getKoneksi().createStatement();
            String sql = "SELECT * FROM kelas";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next()){
                inpKelas.addItem(res.getString("id"));
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        
        //desain menu kelas
        getDataKelas();
        tabel_kelas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabel_kelas.getTableHeader().setOpaque(false);
        tabel_kelas.getTableHeader().setBackground(new Color(0,0,0));
        tabel_kelas.getTableHeader().setForeground(new Color(0,0,0));
        tabel_kelas.setRowHeight(25);
        
        
        //menu kelas
        getDataSiswa();
        
    }
    
    //ambil data kelas
    public void getDataKelas(){
        model = new DefaultTableModel();
        
        tabel_kelas.setModel(model);
        model.addColumn("Kode Kelas");
        model.addColumn("Nama Kelas");
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        
        
        try{
            Statement stat = (Statement) DB.getKoneksi().createStatement();
            String sql = "SELECT * FROM kelas";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next()){
                Object[ ] obj = new Object[6];
                obj[0] = res.getString("id");
                obj[1] = res.getString("kelas");
                
                model.addRow(obj);  
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    
    //insert data kelas
    public void insertDataKelas(){
        idAutoKelas();
        String id = inpkls.getText();
        String kls = inputankelas.getText();
         
        try{
            Statement stat = (Statement) DB.getKoneksi().createStatement();
            String sql = "INSERT INTO kelas (id,kelas) VALUES( '"+id+"', '"+kls+"')";
            stat.execute(sql);
    
            JOptionPane.showMessageDialog(null, "Data kelas '"+kls+"' berhasil ditambahkan !");
            
            inputankelas.setText("");
            inpkls.setText("");
            getDataKelas();
            
        } catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    //ambil data kelas
    public void getDataSiswa(){
        modelSiswa = new DefaultTableModel();
        
        tabel_siswa.setModel(modelSiswa);
        modelSiswa.addColumn("NIS");
        modelSiswa.addColumn("Nama");
        modelSiswa.addColumn("Tanggal Lahir");
        modelSiswa.addColumn("Jenis Kelamin");
        modelSiswa.addColumn("Kelas");
        modelSiswa.addColumn("Tindakan");
        modelSiswa.getDataVector().removeAllElements();
        modelSiswa.fireTableDataChanged();
        
        
        
        try{
            Statement stat = (Statement) DB.getKoneksi().createStatement();
            String sql = "SELECT * FROM siswa";
            ResultSet res = stat.executeQuery(sql);
            
            
            
            while(res.next()){
                Object[ ] obj = new Object[6];
                obj[0] = res.getString("nis");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("tanggal_lahir");
                obj[3] = res.getString("jenis_kelamin");
                obj[4] = res.getString("kelas");
                obj[5] = "-";
                
                modelSiswa.addRow(obj);  
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    //make id auto kelas
    public void idAutoKelas(){
        int rowCount;
        try{
            Statement stat = (Statement) DB.getKoneksi().createStatement();
            String sql = "SELECT * FROM kelas";
            ResultSet rs = stat.executeQuery(sql);
            
            if(rs.last()){               
                rowCount = rs.getRow();
                if(rowCount > 0){
                    int i = rowCount + 1;
                    inpkls.setText("KLS-" + String.valueOf(i));
                    }
                } else{
                    inpkls.setText("KLS-1");
            } 
            
        } catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    //insert data siswa
    public void insertDataSiswa(){
        idAutoSiswa();
        String nis = inpNis.getText();
        String nama = inpNama.getText();
        String tempatLahir = inpTmpLahr.getText();
        String tglLahir = inpTglLhr.getText();
        String jenisKelamin = (String) InpJnsKelmn.getSelectedItem();
        String agama = inpAgama.getText();
        String alamat = inpAlamat.getText();
        String kelas = (String) inpKelas.getSelectedItem();
        
        try{
            Statement stat = (Statement) DB.getKoneksi().createStatement();
            String sql = "INSERT INTO siswa"
                    + "(nis, nama, jenis_kelamin, agama, foto, kelas, tempat_lahir, tanggal_lahir, alamat) VALUES"
                    + "( '"+nis+"', '"+nama+"', '"+jenisKelamin+"', '"+agama+"', 'foto.jpg', '"+kelas+"', '"+tempatLahir+"', '"+tglLahir+"', '"+alamat+"')";
            stat.execute(sql);
    
            inpNis.setText("");
            inpNama.setText("");
            inpTmpLahr.setText("");
            inpTglLhr.setText("");
            InpJnsKelmn.setSelectedIndex(0);
            inpAgama.setText("");
            inpAlamat.setText("");
            inpKelas.setSelectedIndex(0);
            
            JOptionPane.showMessageDialog(null, "Data siswa berhasil ditambahkan !");
     
        } catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    //make id auto siswa
    public void idAutoSiswa(){
        int rowCount;
        try{
            Statement stat = (Statement) DB.getKoneksi().createStatement();
            String sql = "SELECT id FROM siswa";
            ResultSet rs = stat.executeQuery(sql);
            
            if(rs.last()){               
                rowCount = rs.getRow();
                if(rowCount > 0){
                    int i = rowCount + 1;
                    inpNis.setText("402022" + String.valueOf(i));
                    }
                } else{
                    inpNis.setText("4020220");
            } 
            
        } catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        sidipane = new javax.swing.JPanel();
        btn_siswa = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnKelas1 = new javax.swing.JLabel();
        btn_kelas = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnKelas = new javax.swing.JLabel();
        btn_pengembang = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnPengembang = new javax.swing.JLabel();
        btn_keluar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnKeluar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        parent_panel = new javax.swing.JPanel();
        panel_siswa = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        parent_siswa = new javax.swing.JPanel();
        list_siswa = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_siswa = new javax.swing.JTable();
        add_siswa = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inpAlamat = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        inpNis = new textfield.TextField();
        inpAgama = new textfield.TextField();
        inpNama = new textfield.TextField();
        inpTmpLahr = new textfield.TextField();
        InpJnsKelmn = new tugaspem2.Combobox();
        jLabel18 = new javax.swing.JLabel();
        inpKelas = new tugaspem2.Combobox();
        inpTglLhr = new textfield.TextField();
        jButton7 = new javax.swing.JButton();
        panel_pengembang = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        panel_kelas = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        parent_kelas = new javax.swing.JPanel();
        list_kelas = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabel_kelas = new javax.swing.JTable();
        add_kelas = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        inputankelas = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        inpkls = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setAlignmentX(0.0F);
        bg.setAlignmentY(0.0F);
        bg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bgMousePressed(evt);
            }
        });
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidipane.setBackground(new java.awt.Color(54, 33, 89));
        sidipane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_siswa.setBackground(new java.awt.Color(54, 43, 100));
        btn_siswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_siswaMousePressed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_member_15px.png"))); // NOI18N

        btnKelas1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnKelas1.setForeground(new java.awt.Color(240, 240, 240));
        btnKelas1.setText("Data Siswa");

        javax.swing.GroupLayout btn_siswaLayout = new javax.swing.GroupLayout(btn_siswa);
        btn_siswa.setLayout(btn_siswaLayout);
        btn_siswaLayout.setHorizontalGroup(
            btn_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_siswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKelas1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        btn_siswaLayout.setVerticalGroup(
            btn_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_siswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKelas1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        sidipane.add(btn_siswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 50));

        btn_kelas.setBackground(new java.awt.Color(54, 33, 89));
        btn_kelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_kelasMousePressed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_home_15px.png"))); // NOI18N

        btnKelas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnKelas.setForeground(new java.awt.Color(240, 240, 240));
        btnKelas.setText("Data Kelas");

        javax.swing.GroupLayout btn_kelasLayout = new javax.swing.GroupLayout(btn_kelas);
        btn_kelas.setLayout(btn_kelasLayout);
        btn_kelasLayout.setHorizontalGroup(
            btn_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_kelasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKelas, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
        );
        btn_kelasLayout.setVerticalGroup(
            btn_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKelas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        sidipane.add(btn_kelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 50));

        btn_pengembang.setBackground(new java.awt.Color(54, 33, 89));
        btn_pengembang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_pengembangMousePressed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Code_15px.png"))); // NOI18N

        btnPengembang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPengembang.setForeground(new java.awt.Color(240, 240, 240));
        btnPengembang.setText("Pengembang");

        javax.swing.GroupLayout btn_pengembangLayout = new javax.swing.GroupLayout(btn_pengembang);
        btn_pengembang.setLayout(btn_pengembangLayout);
        btn_pengembangLayout.setHorizontalGroup(
            btn_pengembangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_pengembangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPengembang, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
        );
        btn_pengembangLayout.setVerticalGroup(
            btn_pengembangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPengembang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        sidipane.add(btn_pengembang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 50));

        btn_keluar.setBackground(new java.awt.Color(54, 33, 89));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_logout_15px_1.png"))); // NOI18N

        btnKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnKeluar.setForeground(new java.awt.Color(240, 240, 240));
        btnKeluar.setText("Keluar");
        btnKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKeluarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_keluarLayout = new javax.swing.GroupLayout(btn_keluar);
        btn_keluar.setLayout(btn_keluarLayout);
        btn_keluarLayout.setHorizontalGroup(
            btn_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_keluarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
        );
        btn_keluarLayout.setVerticalGroup(
            btn_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKeluar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        sidipane.add(btn_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_schoology_100px.png"))); // NOI18N
        sidipane.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 250, 110));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Aplikasi");
        sidipane.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 150, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Manajemen Data Siswa");
        sidipane.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 150, 20));
        sidipane.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 162, 210, 10));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("versi beta.");
        sidipane.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 694, 240, 20));

        bg.add(sidipane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 720));

        jPanel1.setBackground(new java.awt.Color(122, 72, 221));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 1060, 80));

        parent_panel.setBackground(new java.awt.Color(255, 255, 255));
        parent_panel.setLayout(null);

        panel_siswa.setBackground(new java.awt.Color(255, 255, 255));
        panel_siswa.setLayout(null);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_joyent_15px_1.png"))); // NOI18N
        jButton3.setText("Tambah Siswa");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panel_siswa.add(jButton3);
        jButton3.setBounds(10, 100, 144, 40);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_spotted_patterns_15px.png"))); // NOI18N
        jButton4.setText("Daftar Siswa");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton4MousePressed(evt);
            }
        });
        panel_siswa.add(jButton4);
        jButton4.setBounds(10, 54, 144, 40);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel16.setText("DATA SISWA");
        panel_siswa.add(jLabel16);
        jLabel16.setBounds(10, 11, 148, 25);

        parent_siswa.setBackground(new java.awt.Color(255, 255, 255));
        parent_siswa.setLayout(null);

        list_siswa.setBackground(new java.awt.Color(255, 255, 255));
        list_siswa.setPreferredSize(new java.awt.Dimension(780, 600));

        tabel_siswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabel_siswa);

        javax.swing.GroupLayout list_siswaLayout = new javax.swing.GroupLayout(list_siswa);
        list_siswa.setLayout(list_siswaLayout);
        list_siswaLayout.setHorizontalGroup(
            list_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(list_siswaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addContainerGap())
        );
        list_siswaLayout.setVerticalGroup(
            list_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(list_siswaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 254, Short.MAX_VALUE))
        );

        parent_siswa.add(list_siswa);
        list_siswa.setBounds(10, 11, 780, 600);

        add_siswa.setBackground(new java.awt.Color(255, 255, 255));
        add_siswa.setMinimumSize(new java.awt.Dimension(780, 600));
        add_siswa.setPreferredSize(new java.awt.Dimension(780, 600));

        inpAlamat.setColumns(20);
        inpAlamat.setRows(5);
        jScrollPane2.setViewportView(inpAlamat);

        jButton2.setText("Tambah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        inpNis.setLabelText("Nomor Induk Mahasiswa");

        inpAgama.setLabelText("Agama");
        inpAgama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpAgamaActionPerformed(evt);
            }
        });

        inpNama.setLabelText("Nama");

        inpTmpLahr.setLabelText("Tempat Lahir");

        InpJnsKelmn.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "   ", "Laki - Laki", "Perempuan" }));
        InpJnsKelmn.setLabeText("Jenis Kelamin");

        jLabel18.setText("Alamat");

        inpKelas.setLabeText("Kelas");
        inpKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpKelasActionPerformed(evt);
            }
        });

        inpTglLhr.setLabelText("Tanggal Lahir");
        inpTglLhr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpTglLhrActionPerformed(evt);
            }
        });

        jButton7.setText("Tambah");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout add_siswaLayout = new javax.swing.GroupLayout(add_siswa);
        add_siswa.setLayout(add_siswaLayout);
        add_siswaLayout.setHorizontalGroup(
            add_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_siswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(add_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(add_siswaLayout.createSequentialGroup()
                        .addComponent(inpKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, add_siswaLayout.createSequentialGroup()
                        .addGroup(add_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(add_siswaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(inpTglLhr, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(inpAgama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpTmpLahr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpNis, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(271, 271, 271))
                    .addGroup(add_siswaLayout.createSequentialGroup()
                        .addGroup(add_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(InpJnsKelmn, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(0, 271, Short.MAX_VALUE))))
        );
        add_siswaLayout.setVerticalGroup(
            add_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_siswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inpNis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inpNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inpTmpLahr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(add_siswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InpJnsKelmn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inpTglLhr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inpAgama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inpKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jButton7)
                .addGap(220, 220, 220)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        parent_siswa.add(add_siswa);
        add_siswa.setBounds(10, 11, 780, 500);

        panel_siswa.add(parent_siswa);
        parent_siswa.setBounds(160, 40, 800, 520);

        parent_panel.add(panel_siswa);
        panel_siswa.setBounds(30, 30, 970, 560);

        panel_pengembang.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("TUGAS PEMROGRAMAN 2 - KELOMPOK 1");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setText("1. 181011401874 - ALFIN DANU ARDIANTO");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel13.setText("2. 191011401561 - RIZEKI KARUNIA WATI");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel14.setText("3. 191011401689 - ROMI SANTOSO");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel15.setText("4. 191011402752 - ZAISUL MUTTABI");

        javax.swing.GroupLayout panel_pengembangLayout = new javax.swing.GroupLayout(panel_pengembang);
        panel_pengembang.setLayout(panel_pengembangLayout);
        panel_pengembangLayout.setHorizontalGroup(
            panel_pengembangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_pengembangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_pengembangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addContainerGap(603, Short.MAX_VALUE))
        );
        panel_pengembangLayout.setVerticalGroup(
            panel_pengembangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_pengembangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        parent_panel.add(panel_pengembang);
        panel_pengembang.setBounds(30, 30, 970, 440);

        panel_kelas.setBackground(new java.awt.Color(255, 255, 255));
        panel_kelas.setLayout(null);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_joyent_15px_1.png"))); // NOI18N
        jButton5.setText("Tambah Kelas");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton5MousePressed(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        panel_kelas.add(jButton5);
        jButton5.setBounds(10, 110, 144, 40);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_spotted_patterns_15px.png"))); // NOI18N
        jButton6.setText("Daftar Kelas");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton6MousePressed(evt);
            }
        });
        panel_kelas.add(jButton6);
        jButton6.setBounds(10, 60, 144, 40);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setText("DATA KELAS");
        panel_kelas.add(jLabel17);
        jLabel17.setBounds(10, 11, 144, 25);

        parent_kelas.setBackground(new java.awt.Color(255, 255, 255));
        parent_kelas.setLayout(null);

        list_kelas.setBackground(new java.awt.Color(255, 255, 255));

        tabel_kelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tabel_kelas);

        javax.swing.GroupLayout list_kelasLayout = new javax.swing.GroupLayout(list_kelas);
        list_kelas.setLayout(list_kelasLayout);
        list_kelasLayout.setHorizontalGroup(
            list_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(list_kelasLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 328, Short.MAX_VALUE))
        );
        list_kelasLayout.setVerticalGroup(
            list_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(list_kelasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        parent_kelas.add(list_kelas);
        list_kelas.setBounds(10, 11, 780, 387);

        add_kelas.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Nama Kelas");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Form Data Kelas");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout add_kelasLayout = new javax.swing.GroupLayout(add_kelas);
        add_kelas.setLayout(add_kelasLayout);
        add_kelasLayout.setHorizontalGroup(
            add_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, add_kelasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inpkls)
                .addGap(180, 180, 180))
            .addGroup(add_kelasLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(add_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(add_kelasLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(add_kelasLayout.createSequentialGroup()
                        .addGroup(add_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(add_kelasLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(add_kelasLayout.createSequentialGroup()
                                .addComponent(inputankelas, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 276, Short.MAX_VALUE)
                                .addComponent(jLabel19)))
                        .addGap(235, 235, 235))
                    .addGroup(add_kelasLayout.createSequentialGroup()
                        .addGroup(add_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        add_kelasLayout.setVerticalGroup(
            add_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_kelasLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGroup(add_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(add_kelasLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel19))
                    .addGroup(add_kelasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputankelas, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(52, 52, 52)
                .addComponent(inpkls)
                .addContainerGap(188, Short.MAX_VALUE))
        );

        parent_kelas.add(add_kelas);
        add_kelas.setBounds(10, 11, 780, 368);

        panel_kelas.add(parent_kelas);
        parent_kelas.setBounds(160, 40, 800, 390);

        parent_panel.add(panel_kelas);
        panel_kelas.setBounds(30, 30, 970, 440);

        bg.add(parent_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 1030, 600));

        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        setSize(new java.awt.Dimension(1280, 720));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKeluarMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnKeluarMouseClicked

    private void btn_siswaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_siswaMousePressed
        setColor(btn_siswa);
        resetColor(btn_kelas);
        resetColor(btn_pengembang);
        
        //buka menu
        parent_panel.removeAll();
        parent_panel.add(panel_siswa);
        parent_panel.repaint();
        parent_panel.revalidate();
    }//GEN-LAST:event_btn_siswaMousePressed

    private void btn_kelasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_kelasMousePressed
        setColor(btn_kelas);
        resetColor(btn_siswa);
        resetColor(btn_pengembang);
        
        //buka menu
        parent_panel.removeAll();
        parent_panel.add(panel_kelas);
        parent_panel.repaint();
        parent_panel.revalidate();
    }//GEN-LAST:event_btn_kelasMousePressed

    private void bgMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgMousePressed
        
    }//GEN-LAST:event_bgMousePressed

    private void btn_pengembangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_pengembangMousePressed
        setColor(btn_pengembang);
        resetColor(btn_siswa);
        resetColor(btn_kelas);
        
        //buka menu
        parent_panel.removeAll();
        parent_panel.add(panel_pengembang);
        parent_panel.repaint();
        parent_panel.revalidate();
    }//GEN-LAST:event_btn_pengembangMousePressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MousePressed
        //buka menu
        parent_kelas.removeAll();
        parent_kelas.add(list_kelas);
        parent_kelas.repaint();
        parent_kelas.revalidate();
    }//GEN-LAST:event_jButton6MousePressed

    private void jButton5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MousePressed
        //buka menu
        parent_kelas.removeAll();
        parent_kelas.add(add_kelas);
        parent_kelas.repaint();
        parent_kelas.revalidate();
    }//GEN-LAST:event_jButton5MousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        insertDataKelas();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MousePressed
        //buka menu
        parent_siswa.removeAll();
        parent_siswa.add(list_siswa);
        parent_siswa.repaint();
        parent_siswa.revalidate();
    }//GEN-LAST:event_jButton4MousePressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MousePressed
        //buka menu
        parent_siswa.removeAll();
        parent_siswa.add(add_siswa);
        parent_siswa.repaint();
        parent_siswa.revalidate();
    }//GEN-LAST:event_jButton3MousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        insertDataSiswa();
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void inpAgamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpAgamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpAgamaActionPerformed

    private void inpKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpKelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpKelasActionPerformed

    private void inpTglLhrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpTglLhrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpTglLhrActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        insertDataSiswa();
    }//GEN-LAST:event_jButton7ActionPerformed
    
    //effect bacgkground sidebar menu
    void setColor(JPanel panel){
        panel.setBackground(new Color(85, 65, 118));
    }
    
    void resetColor(JPanel panel){
        panel.setBackground(new Color(54, 33, 89));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tugaspem2.Combobox InpJnsKelmn;
    private javax.swing.JPanel add_kelas;
    private javax.swing.JPanel add_siswa;
    private javax.swing.JPanel bg;
    private javax.swing.JLabel btnKelas;
    private javax.swing.JLabel btnKelas1;
    private javax.swing.JLabel btnKeluar;
    private javax.swing.JLabel btnPengembang;
    private javax.swing.JPanel btn_kelas;
    private javax.swing.JPanel btn_keluar;
    private javax.swing.JPanel btn_pengembang;
    private javax.swing.JPanel btn_siswa;
    private textfield.TextField inpAgama;
    private javax.swing.JTextArea inpAlamat;
    private tugaspem2.Combobox inpKelas;
    private textfield.TextField inpNama;
    private textfield.TextField inpNis;
    private textfield.TextField inpTglLhr;
    private textfield.TextField inpTmpLahr;
    private javax.swing.JLabel inpkls;
    private javax.swing.JTextField inputankelas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel list_kelas;
    private javax.swing.JPanel list_siswa;
    private javax.swing.JPanel panel_kelas;
    private javax.swing.JPanel panel_pengembang;
    private javax.swing.JPanel panel_siswa;
    private javax.swing.JPanel parent_kelas;
    private javax.swing.JPanel parent_panel;
    private javax.swing.JPanel parent_siswa;
    private javax.swing.JPanel sidipane;
    private javax.swing.JTable tabel_kelas;
    private javax.swing.JTable tabel_siswa;
    // End of variables declaration//GEN-END:variables
}
