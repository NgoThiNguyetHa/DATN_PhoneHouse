var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/HoaDon')
require('../models/ChiTietHoaDon')
require('../models/DienThoai')
require('../models/HangSanXuat')


const HoaDon = mongoose.model("hoaDon")
const ChiTietHoaDon = mongoose.model("chiTietHoaDon")
const DienThoai = mongoose.model("dienthoai")
const HangSX = mongoose.model("hangSanXuat")


router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});

router.get('/getDienThoaiHotNhat', async (req, res) => {
  try {
    // Truy vấn dữ liệu từ bảng Hóa Đơn và nhóm theo mã điện thoại
    const dienThoaiDuocMuaNhieu = await ChiTietHoaDon.aggregate([
      {
        $group: {
          _id: '$maDienThoai',
          soLuong: { $sum: '$soLuong' }
        }
      },
      {
        $sort: { soLuong: -1 } // Sắp xếp theo số lượng mua giảm dần
      }
    ]).exec();
    console.log("31: ",dienThoaiDuocMuaNhieu)
    // Lấy thông tin chi tiết của các điện thoại từ bảng Điện Thoại
    const dienThoaiDetails = await DienThoai.populate(dienThoaiDuocMuaNhieu, { path: '_id', select: 'tenDienThoai giaTien' });

    res.status(200).json({ dienThoaiDuocMuaNhieu: dienThoaiDetails });
  } catch (error) {
    res.status(500).json({error: error.message});
  }
})

router.get('/getSoLuongSanPham-CuaHang/:id', async (req, res) => {
  try {
    const idCuaHang = req.params.id;
    const hangSX = await HangSX.find({maCuaHang: idCuaHang})
        .populate('maCuaHang', '_id')
        .populate('maCuaHang')
    const dienThoais = [];
    for (const hang of hangSX) {
      const dienThoai = await DienThoai.find({ maHangSX: hang._id })
          .populate('maHangSX', '_id')
          .populate('maHangSX');
      if (dienThoai){
        dienThoais.push(...dienThoai);
      }
    }
    res.status(200).json({soLuong: dienThoais.length});
  } catch (error) {
    res.status(500).json({error: error.message});
  }
})

router.get('/getSoLuongDonHangTheoTrangThai/:id/:trangThaiNhanHang', async (req, res) => {
  try {
    const trangThaiNhanHang = req.params.trangThaiNhanHang;
    const maCuaHang = req.params.id;

    const slHoaDon = await HoaDon.countDocuments({ trangThaiNhanHang, maCuaHang })
    res.json({soLuong: slHoaDon});
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/thongKeSoLuongKhachHang/:id/:ngay', async (req, res) => {
  try {
    const idCuaHang = req.params.id;
    const ngay = new Date(req.params.ngay);
    console.log(ngay)
    const thongKe = await HoaDon.aggregate([
      {
        $match: {
          maCuaHang: new mongoose.Types.ObjectId(idCuaHang),
          ngayTao: { $gte: ngay, $lt: new Date(ngay.getTime() + 86400000) }
        }
      },
      {
        $group: {
          _id: '$maKhachHang',
          soLuongHoaDon: { $sum: 1 }
        }
      },
      {
        $group: {
          _id: null,
          soLuongKhachHang: { $sum: 1 }
        }
      }
    ]);
    const soLuongKhachHang = thongKe.length > 0 ? thongKe[0].soLuongKhachHang : 0;

    res.json({ soLuongKhachHang });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});
module.exports = router;