/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author @minhqn @lntr.tin
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import com.mysql.cj.jdbc.CallableStatement;
import java.util.Scanner;

public class MySQLConnect {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            String url = "jdbc:mysql://localhost/svcntt";
            String user = "root";
            String password = "anhbangocson";

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Nối kết thành công");
            while (true) {
                System.out.println("--------------------------------------------------------");
                System.out.println("Chương trình quản lý sinh viên khoa công nghệ thông tin.");
                System.out.println("--------------------------------------------------------");
                System.out.println("1. Xem danh sách tất cả sinh viên");
                System.out.println("2. Xem danh sách các sinh viên nữ của khoa");
                System.out.println("3. Xem danh sách các sinh viên nam của khoa");
                System.out.println("4. Tính điểm trung bình của sinh viên có mssv");
                System.out.println("5. Tìm sinh viên có tên");
                System.out.println("6. Thêm thông tin một sinh viên mới");
                System.out.println("7. Thêm điểm mới cho một sinh viên");
                System.out.println("8. Thêm một môn học mới");
                System.out.println("9. Thông tin của sinh viên nam có điểm cao nhất");
                System.out.println("10. Thông tin của sinh viên nữ có điểm cao nhất");
                System.out.println("11. Thông tin sinh viên điểm trung bình cao nhất");
                System.out.println("12. Thông tin sinh viên điểm trung bình thấp nhất");
                System.out.println("--------------------------------------------------------");
                int opt;
                System.out.print("Bạn muốn chọn thao tác nào (-1 để thoát): ");
                opt = sc.nextInt();
                if (opt == -1)
                    break;
                java.sql.CallableStatement cStmt;
                ResultSet rs;
                switch (opt) {
                    case 1:
                        cStmt = conn.prepareCall("{call dssv()}");
                        rs = cStmt.executeQuery();
                        System.out.println("Danh sách sinh viên khoa cntt&tt");
                        while (rs.next()) {
                            System.out.println("MSSV: " + rs.getString("mssv"));
                            System.out.println("Họ tên: " + rs.getString("hoten"));
                            System.out.println("Giới tính: " + rs.getString("gioitinh"));
                            System.out.println("------------------------------");
                        }
                        break;
                    case 2:
                        cStmt = conn.prepareCall("{call dssvnu()}");
                        rs = cStmt.executeQuery();
                        System.out.println("Danh sách sinh viên nữ của khoa cntt&tt");
                        while (rs.next()) {
                            System.out.println("MSSV: " + rs.getString("mssv"));
                            System.out.println("Họ tên: " + rs.getString("hoten"));
                            System.out.println("Giới tính: " + rs.getString("gioitinh"));
                            System.out.println("------------------------------");
                        }
                        break;
                    case 3:
                        cStmt = conn.prepareCall("{call dssvnam()}");
                        rs = cStmt.executeQuery();
                        System.out.println("Danh sách sinh viên nam của khoa cntt&tt");
                        while (rs.next()) {
                            System.out.println("MSSV: " + rs.getString("mssv"));
                            System.out.println("Họ tên: " + rs.getString("hoten"));
                            System.out.println("Giới tính: " + rs.getString("gioitinh"));
                            System.out.println("------------------------------");
                        }
                        break;
                    case 4:
                        sc.nextLine();
                        System.out.print("Tính điểm trung bình của sinh viên có mssv: ");
                        String mssv = sc.nextLine();
                        cStmt = conn.prepareCall("{call getAVG(?)}");
                        cStmt.setString(1, mssv);
                        rs = cStmt.executeQuery();
                        if (rs.next()) {
                            System.out.println("Kết quả học tập của sinh viên: ");
                            System.out.println("MSSV: " + rs.getString("mssv"));
                            System.out.println("Họ tên: " + rs.getString("hoten"));
                            System.out.println("Điểm trung bình: " + rs.getString("diemTB"));
                            System.out.println("------------------------------");
                        } else {
                            System.out.println("Không có sinh viên nào mang mssv này");
                        }
                        break;
                    case 5:
                        sc.nextLine();
                        System.out.print("Bạn muốn tìm sinh viên có tên: ");
                        String name = sc.nextLine();
                        cStmt = conn.prepareCall("{call getInfor(?)}");
                        cStmt.setString(1, name);
                        rs = cStmt.executeQuery();
                        System.out.println("Các sinh viên có tên khớp với kết quả tìm kiếm: ");
                        while (rs.next()) {
                            System.out.println("MSSV: " + rs.getString("mssv"));
                            System.out.println("Họ tên: " + rs.getString("hoten"));
                            System.out.println("Giới tính: " + rs.getString("gioitinh"));
                            System.out.println("Ngày sinh: " + rs.getString("ngaysinh"));
                            System.out.println("Quê quán: " + rs.getString("quequan"));
                            System.out.println("------------------------------");
                        }
                        break;
                    case 6:
                        sc.nextLine();
                        System.out.print("Thông tin sinh viên muốn thêm: ");
                        System.out.print("MSSV: ");
                        mssv = sc.nextLine();
                        System.out.print("Họ tên: ");
                        String hoten = sc.nextLine();
                        System.out.print("Giới tính: ");
                        String gioitinh = sc.nextLine();
                        System.out.print("Ngày sinh(YYYY-MM-D): ");
                        String dob = sc.nextLine();
                        System.out.print("Quê quán: ");
                        String que = sc.nextLine();
                        cStmt = conn.prepareCall("{call addSV(?, ?, ?, ?, ?)}");

                        cStmt.setString(1, mssv);
                        cStmt.setString(2, hoten);
                        cStmt.setString(3, gioitinh);
                        cStmt.setString(4, dob);
                        cStmt.setString(5, que);
                        rs = cStmt.executeQuery();
                        break;
                    case 7:
                        sc.nextLine();
                        System.out.println("Thông tin điểm cho sinh viên muốn thêm:");
                        System.out.print("MSSV: ");
                        mssv = sc.nextLine();
                        System.out.print("Mã HP: ");
                        String mahp = sc.nextLine();
                        System.out.print("Điểm: ");
                        String diem = sc.nextLine();
                        cStmt = conn.prepareCall("{call insertSVdiem(?, ?, ?)}");

                        cStmt.setString(1, mssv);
                        cStmt.setString(2, mahp);
                        cStmt.setString(3, diem);

                        rs = cStmt.executeQuery();
                        break;
                    case 8:
                        sc.nextLine();
                        System.out.println("Thông tin học phần cần thêm:");
                        System.out.print("Mã HP: ");
                        mahp = sc.nextLine();
                        System.out.print("Tên HP: ");
                        String tenhp = sc.nextLine();
                        System.out.print("Số tín chỉ: ");
                        String sotc = sc.nextLine();
                        cStmt = conn.prepareCall("{call themHP(?, ?, ?)}");

                        cStmt.setString(1, mahp);
                        cStmt.setString(2, tenhp);
                        cStmt.setString(3, sotc);

                        rs = cStmt.executeQuery();
                        break;
                    case 9:
                        cStmt = conn.prepareCall("{call diemtbM()}");
                        rs = cStmt.executeQuery();
                        System.out.println("Sinh viên nam có điểm trung bình lớn nhất là: ");
                        while (rs.next()) {
                            System.out.println("MSSV: " + rs.getString("mssv"));
                            System.out.println("Họ tên: " + rs.getString("hoten"));
                            System.out.println("Số HP: " + rs.getString("sohp"));
                            System.out.println("Điểm trung bình: " + rs.getString("diemtb"));
                        }
                        break;
                    case 10:
                        cStmt = conn.prepareCall("{call diemtbF()}");
                        rs = cStmt.executeQuery();
                        System.out.println("Sinh viên nữ có điểm trung bình lớn nhất là: ");
                        while (rs.next()) {
                            System.out.println("MSSV: " + rs.getString("mssv"));
                            System.out.println("Họ tên: " + rs.getString("hoten"));
                            System.out.println("Số HP: " + rs.getString("sohp"));
                            System.out.println("Điểm trung bình: " + rs.getString("diemtb"));
                        }
                        break;
                    case 11:
                        cStmt = conn.prepareCall("{call minDiemTB()}");
                        rs = cStmt.executeQuery();
                        System.out.println("Sinh viên  có điểm trung bình thấp nhất là: ");
                        while (rs.next()) {
                            System.out.println("MSSV: " + rs.getString("mssv"));
                            System.out.println("Họ tên: " + rs.getString("hoten"));
                            System.out.println("Số HP: " + rs.getString("sohp"));
                            System.out.println("Điểm trung bình: " + rs.getString("diemtb"));
                        }
                        break;
                    case 12:
                        cStmt = conn.prepareCall("{call maxDiemTB()}");
                        rs = cStmt.executeQuery();
                        System.out.println("Sinh viên có điểm trung bình cao nhất: ");
                        while (rs.next()) {
                            System.out.println("MSSV: " + rs.getString("mssv"));
                            System.out.println("Họ tên: " + rs.getString("hoten"));
                            System.out.println("Số HP: " + rs.getString("sohp"));
                            System.out.println("Điểm trung bình: " + rs.getString("diemtb"));
                        }
                        break;
                }
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
            }
            
            
        } catch (Exception e) {
            System.out.println("Đã có lỗi xảy ra: )" + e.getMessage());
        }
    }
}