package com.example.appkhachhang.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface.OnItemClickListenerNotification;
import com.example.appkhachhang.Model.Notification;
import com.example.appkhachhang.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
  Context mContext;
  List<Notification> list;
  OnItemClickListenerNotification listener;
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

  public NotificationAdapter(Context mContext, List<Notification> list, OnItemClickListenerNotification onItemClickListenerNotification) {
    this.mContext = mContext;
    this.list = list;
    this.listener = onItemClickListenerNotification;
  }

  @NonNull
  @Override
  public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_notification,parent,false));
  }

  @Override
  public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {
    Notification notification = list.get(position);
    int color = Color.parseColor("#D0dde4");
    if (notification.getTrangThai().equals("0")){
      holder.item_mLinearThongBao.setBackgroundColor(color);
    }
    holder.tvTieuDe.setText(notification.getNoiDung());

    try {
      Date date = sdf.parse(notification.getThoiGian());
      SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
      String formattedDate = outputFormat.format(date);
      holder.tvNgayGio.setText(formattedDate);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String orderCode = generateOrderCode(notification.getMaHoaDon().get_id());
    if (notification.getNoiDung().equals("Đặt hàng thành công")){
      String text = "Đơn hàng ";
      SpannableString spannableString = new SpannableString(text + "DH" + orderCode + " đã xác nhận thanh toán COD. Vui lòng giữ điện thoại để nhận cuộc gọi từ nhân viên giao hàng nhé.");

// Tạo Span để gạch chân và thay đổi màu sắc của "DH" và orderCode
      spannableString.setSpan(new UnderlineSpan(), text.length(), text.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Gạch chân "DH"
      spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), text.length(), text.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Màu xanh "DH"
      spannableString.setSpan(new UnderlineSpan(), text.length() + 2, text.length() + 2 + orderCode.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Gạch chân orderCode
      spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), text.length() + 2, text.length() + 2 + orderCode.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Màu xanh orderCode

      holder.tvMoTa.setText(spannableString);
    }

    holder.item_mLinearThongBao.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        listener.onItemClick(notification);
      }
    });
  };


  @Override
  public int getItemCount() {
    return list.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tvTieuDe, tvNgayGio, tvMoTa;
    LinearLayout item_mLinearThongBao;
    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tvTieuDe = itemView.findViewById(R.id.tvTieuDeNoiDung);
      tvNgayGio = itemView.findViewById(R.id.tvNgayGioThongBao);
      tvMoTa = itemView.findViewById(R.id.tvMoTaThongBao);
      item_mLinearThongBao = itemView.findViewById(R.id.item_mLinearThongBao);
    }
  }

  private static String generateOrderCode(String input) {
    try {
      // Tạo một đối tượng MessageDigest với thuật toán SHA-256
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(input.getBytes());

      // Chuyển đổi byte array thành chuỗi hex
      StringBuilder hexString = new StringBuilder();
      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }

      // Trả về mã đơn hàng (có thể cắt chuỗi để lấy độ dài mong muốn)
      return hexString.toString().substring(0, 10); // Ví dụ: Lấy 10 ký tự đầu
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }
}
