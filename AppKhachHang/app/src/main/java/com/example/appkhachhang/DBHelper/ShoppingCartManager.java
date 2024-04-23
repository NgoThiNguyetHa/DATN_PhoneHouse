package com.example.appkhachhang.DBHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShoppingCartManager {
    private static final String SHARED_PREFERENCES_NAME = "GioHang";
    private static final String KEY_LIST_CHI_TIET_GIO_HANG = "ListChiTietGioHang";
    private static final String KEY_CART = "cart";

//    public static void saveChiTietGioHangForId(Context context, String idKhachHang, ChiTietGioHang chiTietGioHang) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        // Lấy danh sách hiện tại từ SharedPreferences
//        List<ChiTietGioHang> existingList = getChiTietGioHangForId(context, idKhachHang);
//
//        // Nếu danh sách không tồn tại, tạo mới
//        if (existingList == null) {
//            existingList = new ArrayList<>();
//        }
//
//        // Thêm đối tượng ChiTietGioHang mới vào danh sách hiện có
//        existingList.add(chiTietGioHang);
//
//        // Chuyển danh sách thành JSON
//        Gson gson = new Gson();
//        String json = gson.toJson(existingList);
//
//        // Lưu danh sách đã cập nhật vào SharedPreferences
//        editor.putString(idKhachHang, json);
//        editor.apply();
//    }

//    public static void saveChiTietGioHangForId(Context context, String idKhachHang, ChiTietGioHang chiTietGioHang) {
//            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//            // Lấy danh sách hiện tại từ SharedPreferences
//            List<ChiTietGioHang> existingList = getChiTietGioHangForId(context, idKhachHang);
//
//            // Nếu danh sách không tồn tại, tạo mới
//            if (existingList == null) {
//                existingList = new ArrayList<>();
//                existingList.add(chiTietGioHang);
//            } else {
//                // Kiểm tra và cộng dồn số lượng nếu trùng id
//                boolean found = false;
//                for (ChiTietGioHang item : existingList) {
//                    // Kiểm tra null trước khi gọi equals()
//                    if (item.getMaChiTietDienThoai().get_id() != null && item.getMaChiTietDienThoai().get_id().equals(chiTietGioHang.getMaChiTietDienThoai().get_id())) {
//                        // Nếu trùng id, cộng dồn số lượng
//                        item.setSoLuong(item.getSoLuong() + chiTietGioHang.getSoLuong());
//                        found = true;
//                        break;
//                    }
//                }
//                // Nếu không tìm thấy id trùng, thêm mới vào danh sách
//                if (!found) {
//                    existingList.add(chiTietGioHang);
//                }
//            }
//
//            // Chuyển danh sách thành JSON
//            Gson gson = new Gson();
//            String json = gson.toJson(existingList);
//
//            // Lưu danh sách đã cập nhật vào SharedPreferences
//            editor.putString(idKhachHang, json);
//            editor.apply();
//        }

    public static boolean saveChiTietGioHangForId(Context context, String idKhachHang, ChiTietGioHang chiTietGioHang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Lấy danh sách hiện tại từ SharedPreferences
        List<ChiTietGioHang> existingList = getChiTietGioHangForId(context, idKhachHang);

        // Nếu danh sách không tồn tại, tạo mới
        if (existingList == null) {
            existingList = new ArrayList<>();
            existingList.add(chiTietGioHang);
        } else {
            // Kiểm tra và cộng dồn số lượng nếu trùng id
            boolean found = false;
            for (ChiTietGioHang item : existingList) {
                // Kiểm tra null trước khi gọi equals()
                if (item.getMaChiTietDienThoai().get_id() != null && item.getMaChiTietDienThoai().get_id().equals(chiTietGioHang.getMaChiTietDienThoai().get_id())) {
                    // Nếu trùng id, cộng dồn số lượng
                    item.setSoLuong(item.getSoLuong() + chiTietGioHang.getSoLuong());
                    found = true;
                    break;
                }
            }
            // Nếu không tìm thấy id trùng, thêm mới vào danh sách
            if (!found) {
                existingList.add(chiTietGioHang);
            }
        }

        // Chuyển danh sách thành JSON
        Gson gson = new Gson();
        String json = gson.toJson(existingList);

        // Lưu danh sách đã cập nhật vào SharedPreferences
        editor.putString(idKhachHang, json);
        editor.apply();

        // Trả về kết quả thành công hoặc không thành công
        return existingList != null && !existingList.isEmpty();
    }

    public static List<ChiTietGioHang> getChiTietGioHangForId(Context context, String idKhachHang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(idKhachHang, null);
        Type type = new TypeToken<List<ChiTietGioHang>>(){}.getType();
        return gson.fromJson(json, type);
    }

//    public static List<ChiTietGioHang> getChiTietGioHangForId(Context context, String idKhachHang) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString(idKhachHang, null);
//        Type type = new TypeToken<List<ChiTietGioHang>>(){}.getType();
//        List<ChiTietGioHang> chiTietGioHangList = gson.fromJson(json, type);
//
//        // Kiểm tra và cộng dồn số lượng cho các đối tượng có cùng id
//        if (chiTietGioHangList != null) {
//            Map<String, Integer> idToSoLuongMap = new HashMap<>();
//            for (ChiTietGioHang chiTietGioHang : chiTietGioHangList) {
//                String id = chiTietGioHang.getMaChiTietDienThoai().get_id();
//                int soLuong = chiTietGioHang.getSoLuong();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    idToSoLuongMap.put(id, idToSoLuongMap.getOrDefault(id, 0) + soLuong);
//                }
//            }
//
//            // Tạo danh sách mới sau khi cộng dồn số lượng
//            List<ChiTietGioHang> updatedChiTietGioHangList = new ArrayList<>();
//            for (Map.Entry<String, Integer> entry : idToSoLuongMap.entrySet()) {
//                String id = entry.getKey();
//                int soLuong = entry.getValue();
//                ChiTietGioHang chiTietGioHang = new ChiTietGioHang(new ChiTietDienThoai(id), soLuong);
//                updatedChiTietGioHangList.add(chiTietGioHang);
//            }
//            return updatedChiTietGioHangList;
//        }
//        return null;
//    }

    public static void clearChiTietDienThoaiList(Context context, String idKhachHang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Xóa danh sách chi tiết điện thoại dựa trên idKhachHang
        editor.remove(idKhachHang);
        editor.apply();
    }


//    public static void updateQuantityForItem(Context context, String idKhachHang, String itemId, int quantityDelta) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        // Lấy danh sách hiện tại từ SharedPreferences
//        List<ChiTietGioHang> existingList = getChiTietGioHangForId(context, idKhachHang);
//
//        // Nếu danh sách không tồn tại, không cần thực hiện gì cả
//        if (existingList == null) {
//            return;
//        }
//
//        // Tìm chi tiết giỏ hàng có itemId tương ứng
//        for (int i = 0; i < existingList.size(); i++) {
//            ChiTietGioHang item = existingList.get(i);
//            if (item.getMaChiTietDienThoai().get_id() != null && item.getMaChiTietDienThoai().get_id().equals(itemId)) {
//                // Tăng hoặc giảm số lượng
//                int newQuantity = item.getSoLuong() + quantityDelta;
//
//                // Số lượng không thể nhỏ hơn 0
//                if (newQuantity < 0) {
//                    newQuantity = 0;
//                }
//
//                // Cập nhật số lượng mới
//                item.setSoLuong(newQuantity);
//
//                // Cập nhật danh sách đã cập nhật vào SharedPreferences
//                Gson gson = new Gson();
//                String json = gson.toJson(existingList);
//                editor.putString(idKhachHang, json);
//                editor.apply();
//
//                // Đã tìm thấy và cập nhật số lượng, không cần kiếm nữa
//                return;
//            }
//        }
//    }


    public static boolean updateQuantityForItem(Context context, String idKhachHang, String itemId, int quantityDelta) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Lấy danh sách hiện tại từ SharedPreferences
        List<ChiTietGioHang> existingList = getChiTietGioHangForId(context, idKhachHang);

        // Nếu danh sách không tồn tại, không cần thực hiện gì cả
        if (existingList == null) {
            return false;
        }

        // Tìm chi tiết giỏ hàng có itemId tương ứng
        for (int i = 0; i < existingList.size(); i++) {
            ChiTietGioHang item = existingList.get(i);
            if (item.getMaChiTietDienThoai().get_id() != null && item.getMaChiTietDienThoai().get_id().equals(itemId)) {
                // Tăng hoặc giảm số lượng
                int newQuantity = item.getSoLuong() + quantityDelta;

                // Số lượng không thể nhỏ hơn 0
                if (newQuantity < 0) {
                    newQuantity = 0;
                }

                // Cập nhật số lượng mới
                item.setSoLuong(newQuantity);

                // Cập nhật danh sách đã cập nhật vào SharedPreferences
                Gson gson = new Gson();
                String json = gson.toJson(existingList);
                editor.putString(idKhachHang, json);
                editor.apply();

                // Đã tìm thấy và cập nhật số lượng, trả về true
                return true;
            }
        }

        // Không tìm thấy itemId tương ứng trong danh sách, trả về false
        return false;
    }

//    public static void removeChiTietGioHang(Context context, String idKhachHang, String itemId) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        // Lấy danh sách hiện tại từ SharedPreferences
//        List<ChiTietGioHang> existingList = getChiTietGioHangForId(context, idKhachHang);
//
//        // Nếu danh sách không tồn tại hoặc rỗng, không cần thực hiện gì cả
//        if (existingList == null || existingList.isEmpty()) {
//            return;
//        }
//
//        // Tìm và loại bỏ ChiTietGioHang có itemId tương ứng
//        Iterator<ChiTietGioHang> iterator = existingList.iterator();
//        while (iterator.hasNext()) {
//            ChiTietGioHang item = iterator.next();
//            if (item.getMaChiTietDienThoai().get_id() != null && item.getMaChiTietDienThoai().get_id().equals(itemId)) {
//                iterator.remove();
//                break; // Nếu bạn muốn xóa chỉ một mục, bạn có thể loại bỏ dòng này để xóa tất cả các mục có itemId tương ứng.
//            }
//        }
//
//        // Cập nhật danh sách đã loại bỏ vào SharedPreferences
//        Gson gson = new Gson();
//        String json = gson.toJson(existingList);
//        editor.putString(idKhachHang, json);
//        editor.apply();
//    }


    public static List<ChiTietGioHang> removeChiTietGioHang(Context context, String idKhachHang, String itemId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Lấy danh sách hiện tại từ SharedPreferences
        List<ChiTietGioHang> existingList = getChiTietGioHangForId(context, idKhachHang);

        // Nếu danh sách không tồn tại hoặc rỗng, không cần thực hiện gì cả
        if (existingList == null || existingList.isEmpty()) {
            return existingList;
        }

        // Tạo một danh sách mới để lưu trữ kết quả sau khi loại bỏ phần tử
        List<ChiTietGioHang> updatedList = new ArrayList<>(existingList);

        // Tìm và loại bỏ ChiTietGioHang có itemId tương ứng
        Iterator<ChiTietGioHang> iterator = updatedList.iterator();
        while (iterator.hasNext()) {
            ChiTietGioHang item = iterator.next();
            if (item.getMaChiTietDienThoai().get_id() != null && item.getMaChiTietDienThoai().get_id().equals(itemId)) {
                iterator.remove();
                break; // Nếu bạn muốn xóa chỉ một mục, bạn có thể loại bỏ dòng này để xóa tất cả các mục có itemId tương ứng.
            }
        }

        // Cập nhật danh sách đã loại bỏ vào SharedPreferences
        Gson gson = new Gson();
        String json = gson.toJson(updatedList);
        editor.putString(idKhachHang, json);
        editor.apply();

        return updatedList; // Trả về danh sách đã cập nhật
    }

    public static void saveChiTietGioHangList(Context context, String idKhachHang, List<ChiTietGioHang> chiTietGioHangList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Chuyển danh sách ChiTietGioHang thành chuỗi JSON
        Gson gson = new Gson();
        String json = gson.toJson(chiTietGioHangList);

        // Lưu chuỗi JSON vào SharedPreferences
        editor.putString(idKhachHang, json);
        editor.apply();
    }

}
