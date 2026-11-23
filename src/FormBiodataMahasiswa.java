import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;


public class FormBiodataMahasiswa extends JFrame {   // JFrame = Top-level Container

    // ===== Atomic Components =====
    private final JTextField txtNim = new JTextField(20);
    private final JTextField txtNama = new JTextField(20);
    private final JComboBox<String> cmbJurusan = new JComboBox<>(new String[]{
            "Teknik Informatika", "Teknik Sipil", "Teknik Mesin", "Teknik Elektro"});
    private final JRadioButton rbLaki = new JRadioButton("Laki-laki", true);
    private final JRadioButton rbPerempuan = new JRadioButton("Perempuan");
    private final JCheckBox chkSetuju = new JCheckBox("Data yang saya isi sudah benar");
    private final JButton btnSimpan = new JButton("Simpan");
    private final JButton btnReset = new JButton("Reset");
    private final JButton btnKeluar = new JButton("Keluar");
    private final JTextArea areaHasil = new JTextArea(8, 30);
    private final JLabel lblStatus = new JLabel("Status: Siap");

    public FormBiodataMahasiswa() {
        super("Form Biodata Mahasiswa - Latihan GUI Java");
        initUI();
        initEvents();
    }
    // =====================================================================
    //  MEMBANGUN TAMPILAN
    // =====================================================================
    private void initUI() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // ditangani WindowAdapter
        setSize(520, 600);
        setLocationRelativeTo(null);


        setLayout(new BorderLayout(10, 10));

       
        JPanel panelJudul = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelJudul.setBackground(new Color(30, 90, 160));
        JLabel lblJudul = new JLabel("FORM BIODATA MAHASISWA");
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelJudul.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelJudul.add(lblJudul);
        add(panelJudul, BorderLayout.NORTH);

  
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(new TitledBorder("Data Mahasiswa"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tambahBaris(panelForm, gbc, 0, "NIM", txtNim);
        tambahBaris(panelForm, gbc, 1, "Nama", txtNama);
        tambahBaris(panelForm, gbc, 2, "Jurusan", cmbJurusan);

        
        ButtonGroup grupGender = new ButtonGroup();
        grupGender.add(rbLaki);
        grupGender.add(rbPerempuan);
        JPanel panelGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelGender.add(rbLaki);
        panelGender.add(rbPerempuan);
        tambahBaris(panelForm, gbc, 3, "Jenis Kelamin", panelGender);

     
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelForm.add(chkSetuju, gbc);


        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelTombol.add(btnSimpan);
        panelTombol.add(btnReset);
        panelTombol.add(btnKeluar);
        gbc.gridy = 5;
        panelForm.add(panelTombol, gbc);


        areaHasil.setEditable(false);
        areaHasil.setLineWrap(true);
        areaHasil.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(areaHasil);
        scroll.setBorder(new TitledBorder("Hasil"));

       
        panelTengah.setBorder(new EmptyBorder(0, 10, 0, 10));
        panelTengah.add(panelForm, BorderLayout.NORTH);
        panelTengah.add(scroll, BorderLayout.CENTER);
        add(panelTengah, BorderLayout.CENTER);

       
        JPanel panelStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelStatus.add(lblStatus);
        add(panelStatus, BorderLayout.SOUTH);
    }

    
    private void tambahBaris(JPanel panel, GridBagConstraints gbc, int baris,
                             String teks, JComponent komponen) {
        gbc.gridwidth = 1;
        gbc.gridy = baris;

        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(new JLabel(teks + " :"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(komponen, gbc);
    }

    // =====================================================================
    //  EVENT LISTENER
    // =====================================================================
    private void initEvents() {


        ActionListener aksiTombol = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object sumber = e.getSource();
                if (sumber == btnSimpan) {
                    simpanData();
                } else if (sumber == btnReset) {
                    resetForm();
                } else if (sumber == btnKeluar) {
                    konfirmasiKeluar();
                }
            }
        };
        btnSimpan.addActionListener(aksiTombol);
        btnReset.addActionListener(aksiTombol);
        btnKeluar.addActionListener(aksiTombol);

      
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                konfirmasiKeluar();
            }
        });

      
            @Override
            public void mouseEntered(MouseEvent e) {
                lblStatus.setText("Status: Klik untuk menyimpan data");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblStatus.setText("Status: Siap");
            }
        });

      
        txtNim.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume(); // batalkan karakter non-angka
                    lblStatus.setText("Status: NIM hanya boleh berisi angka!");
                }
            }
        });

      
        btnSimpan.setEnabled(false);
        chkSetuju.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                btnSimpan.setEnabled(chkSetuju.isSelected());
            }
        });
    }

    // =====================================================================
    //  LOGIKA APLIKASI
    // =====================================================================
    private void simpanData() {
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();

        if (nim.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "NIM dan Nama wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String gender = rbLaki.isSelected() ? "Laki-laki" : "Perempuan";
        areaHasil.append("NIM            : " + nim + "\n");
        areaHasil.append("Nama           : " + nama + "\n");
        areaHasil.append("Jurusan        : " + cmbJurusan.getSelectedItem() + "\n");
        areaHasil.append("Jenis Kelamin  : " + gender + "\n");
        areaHasil.append("-------------------------------------\n");
        lblStatus.setText("Status: Data " + nama + " berhasil disimpan");
    }

    private void resetForm() {
        txtNim.setText("");
        txtNama.setText("");
        cmbJurusan.setSelectedIndex(0);
        rbLaki.setSelected(true);
        chkSetuju.setSelected(false);
        areaHasil.setText("");
        lblStatus.setText("Status: Form direset");
    }

    private void konfirmasiKeluar() {
        int pilihan = JOptionPane.showConfirmDialog(this,
                "Yakin ingin keluar dari aplikasi?", "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    // =====================================================================
    //  MAIN
    // =====================================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormBiodataMahasiswa().setVisible(true));
    }
}
