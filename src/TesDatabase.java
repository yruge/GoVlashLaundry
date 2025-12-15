import java.sql.Connection;
import java.sql.ResultSet;
import database.Connect;

public class TesDatabase {
    public static void main(String[] args) {
        System.out.println("=== MULAI TES KONEKSI ===");
        
        // 1. Cek Koneksi
        Connection con = Connect.getInstance().getConnection();
        if (con == null) {
            System.err.println("❌ GAGAL: Koneksi database NULL. Cek Connect.java!");
            return;
        } else {
            System.out.println("✅ SUKSES: Terhubung ke Database.");
        }

        // 2. Cek Apakah Tabel 'users' Ada dan Berisi Data
        try {
            System.out.println("... Mengecek tabel 'users' ...");
            // Kita coba ambil semua data user
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM users");
            
            boolean adaData = false;
            while(rs.next()) {
                adaData = true;
                String email = rs.getString("user_email"); // Pastikan nama kolom benar
                String role = rs.getString("user_role");
                String pass = rs.getString("user_password");
                
                System.out.println("   -> Ditemukan User: " + email + " | Role: " + role + " | Pass: " + pass);
            }
            
            if (!adaData) {
                System.err.println("⚠️ WARNING: Koneksi sukses, tapi tabel 'users' KOSONG.");
                System.err.println("   Solusi: Jalankan INSERT SQL di phpMyAdmin.");
            } else {
                System.out.println("✅ DATA OKE: Tabel users ada isinya.");
            }
            
        } catch (Exception e) {
            System.err.println("❌ ERROR SQL: " + e.getMessage());
            System.err.println("   Kemungkinan nama kolom salah. Cek apakah 'user_email' atau 'userEmail'?");
        }
        
        System.out.println("=== TES SELESAI ===");
    }
}