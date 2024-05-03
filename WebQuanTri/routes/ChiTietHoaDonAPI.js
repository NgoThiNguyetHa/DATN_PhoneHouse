var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/HoaDon')
require('../models/ChiTietGioHang')
require("../models/ChiTietDienThoai");
require('../models/GioHang')
require('../models/ThongBao')

const HoaDon = mongoose.model("hoaDon")
require('../models/ChiTietHoaDon')

const ChiTietHoaDon = mongoose.model("chiTietHoaDon")
const ChiTietGioHang = mongoose.model("chiTietGioHang")
const ChiTietDienThoai = mongoose.model("chitietdienthoai");
const GioHang = mongoose.model("gioHang")
const ThongBao = mongoose.model("thongbao")

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});


router.post('/addChiTietHoaDon/:id', async function(req, res, next) {
  try {
    const chiTietHoaDonList = req.body;
    const gioHang = await GioHang.find({maKhachHang: req.params.id})

    if (!Array.isArray(chiTietHoaDonList)) {
      return res.status(400).json('Mảng chi tiết hóa đơn không hợp lệ' );
    }

    const savedChiTietHoaDonList = [];
    const invoicesAndStores = [];

    for (const item of chiTietHoaDonList) {
      const dienThoai = await ChiTietDienThoai.findById(item.maChiTietDienThoai)
          .populate({
            path: "maDienThoai",
            populate: [
              { path: "maCuaHang", model: "cuaHang" },
              { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
              { path: "maHangSX", model: "hangSanXuat" },
            ],
          })
          .populate("maMau")
          .populate("maDungLuong")
          .populate("maRam");

      if (dienThoai.soLuong < item.soLuong){
        await HoaDon.findByIdAndDelete(item.maHoaDon)
        return res.status(200).json('Số lượng trong kho không đủ' );
      }
      const chiTietHoaDon = new ChiTietHoaDon({
        soLuong: item.soLuong,
        giaTien: item.giaTien,
        maHoaDon: item.maHoaDon,
        maChiTietDienThoai: item.maChiTietDienThoai,
      });

      //update tổng tiền hóa đơn
      const tongTien = await HoaDon.findById(item.maHoaDon).select('tongTien');
      const giaTien = parseFloat(item.giaTien);
      await HoaDon.findByIdAndUpdate(item.maHoaDon, { tongTien: (parseFloat(tongTien.tongTien)+giaTien).toString() } );

      //update giỏ hàng
      await ChiTietGioHang.deleteMany({
        maChiTietDienThoai: item.maChiTietDienThoai,
        maGioHang: gioHang[0]._id
      });
      //update soLuong trong kho
      await ChiTietDienThoai.findByIdAndUpdate(
          item.maChiTietDienThoai,
          { $inc: { soLuong: -item.soLuong } },
          { new: true }
      )
      await chiTietHoaDon.save();
      savedChiTietHoaDonList.push(chiTietHoaDon);

      const existingPair = invoicesAndStores.find(pair => pair.maHoaDon === item.maHoaDon && pair.maCuaHang === dienThoai.maDienThoai.maCuaHang._id.toString());

      // add mcuawcuahang, hoadon vào mảng
      if (!existingPair) {
        invoicesAndStores.push({
          maHoaDon: item.maHoaDon,
          maCuaHang: dienThoai.maDienThoai.maCuaHang._id.toString()
        });
      }
    }

    // const populatedChiTietHoaDonList = await ChiTietHoaDon.populate(savedChiTietHoaDonList, [
    //   {
    //     path: "maHoaDon",
    //     populate: [
    //       { path: "maDiaChiNhanHang", model: "diaChiNhanHang", populate: { path: "maKhachHang", model: "khachhang" } },
    //       { path: "maKhachHang", model: "khachhang" },
    //       { path: "maCuaHang", model: "cuaHang" },
    //     ],
    //   },
    //   {
    //     path: "maChiTietDienThoai",
    //     populate: [
    //       {
    //         path: "maDienThoai",
    //         model: "dienthoai",
    //         populate: [
    //           { path: 'maCuaHang', model: 'cuaHang' },
    //           { path: 'maUuDai', model: 'uudai', populate: 'maCuaHang' },
    //           { path: 'maHangSX', model: 'hangSanXuat' }
    //         ],
    //       },
    //       { path: "maMau", model: "mau" },
    //       { path: "maDungLuong", model: "dungluong" },
    //       { path: "maRam", model: "ram" },
    //     ],
    //   },
    // ]);
    const now = new Date();
    const timeZoneOffset = 7; // Múi giờ của Việt Nam: UTC+7
    const thoiGianVietNam = new Date(now.getTime() + timeZoneOffset * 60 * 60 * 1000).toISOString();

    for (const pair of invoicesAndStores) {
      // Gửi thông báo cho cửa hàng
      const thongBaoCuaHang = new ThongBao({
        noiDung: 'Đang xử lý',
        thoiGian: thoiGianVietNam,
        trangThai: 0,
        maTaiKhoan: pair.maCuaHang,
        phanQuyen: 'cuahang',
        maHoaDon: pair.maHoaDon
      });
      await thongBaoCuaHang.save();

      // Gửi thông báo cho khách hàng
      const thongBaoKH = new ThongBao({
        noiDung: 'Đang xử lý',
        thoiGian: thoiGianVietNam,
        trangThai: 0,
        maTaiKhoan: req.params.id,
        phanQuyen: 'khachhang',
        maHoaDon: pair.maHoaDon
      });
      await thongBaoKH.save();
    }

    res.status(200).json('Đặt hàng thành công');
  } catch (err) {
    console.log(err);
    res.status(500).json('Đã xảy ra lỗi khi thêm chi tiết hóa đơn');
  }
});

/* GET loaidichvu listing. */
router.get('/getChiTietHoaDon', async (req,res) => {
  try {
    const chiTietHoaDon = await ChiTietHoaDon.find().populate("maHoaDon").populate("maChiTietDienThoai");
    res.json(chiTietHoaDon);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

router.delete('/deleteChiTietHoaDon/:id', async (req,res) => {
  try{
    const data =  await ChiTietHoaDon.findByIdAndDelete(req.params.id)
    if(!data){
      return res.status(404).json({message: "delete failed"})
    }else{
      return res.status(200).json({message: "delete successful"})
    }
  }catch(err){
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateChiTietHoaDon/:id", async (req, res ) => {
  try{
    const data = await ChiTietHoaDon.findByIdAndUpdate(req.params.id, req.body, {new: true})
    if(!data){
      return res.status(404).json({message: "update failed"})

    }else{
      return res.status(200).json({message: "update successful"})

    }
  }catch(err){
    return res.status(500).json({message: err.message})
  }
})

// get chi tiết hóa đơn theo id hóa đơn
router.get('/getChiTietHoaDonTheoHoaDon/:id', async (req,res) => {
  try {
    const chiTietHoaDon = await ChiTietHoaDon.find({maHoaDon: req.params.id})
        .populate({
          path: "maHoaDon",
          populate: [
            {
              path: "maDiaChiNhanHang",
              model: "diaChiNhanHang",
              populate: {path: "maKhachHang", model: "khachhang"}
            },
            {path: "maKhachHang", model: "khachhang"},
            {path: "maCuaHang", model: "cuaHang"},
          ]
        })
        .populate({
          path: "maChiTietDienThoai",
          populate: [
            {
              path: "maDienThoai",
              model:"dienthoai",
              populate: [
                {path: 'maCuaHang', model: 'cuaHang'},
                {path: 'maUuDai', model: 'uudai', populate: 'maCuaHang'},
                {path: 'maHangSX', model: 'hangSanXuat'}
              ]
            },
            {path: "maMau", model:"mau"},
            {path: "maDungLuong", model:"dungluong"},
            {path: "maRam", model:"ram"}
          ]
        });
    res.json(chiTietHoaDon);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})
router.get('/getChiTietHoaDonTheoLichSuMuaHang/:maKhachHang', async (req, res) => {
  try {
    const trangThaiNhanHang = "Đã giao";
    const maKhachHang = req.params.maKhachHang;
    const hoaDon = await HoaDon.find({trangThaiNhanHang, maKhachHang})
        .populate("maKhachHang")
        .populate({path: "maDiaChiNhanHang", populate: {path: "maKhachHang", model: "khachhang"}})
        .populate("maCuaHang")
    // console.log("hoaDon: ", hoaDon)
    const maHoaDonList = hoaDon.map(hoaDon => hoaDon._id);
    // console.log("mahoaDon: ", maHoaDonList)
    const chiTietHoaDonList = await ChiTietHoaDon.find({maHoaDon: {$in: maHoaDonList}})

        .populate({
          path: "maHoaDon",
          populate: [
            {
              path: "maDiaChiNhanHang",
              model: "diaChiNhanHang",
              populate: {path: "maKhachHang", model: "khachhang"}
            },
            {path: "maKhachHang", model: "khachhang"},
            {path: "maCuaHang", model: "cuaHang"},
          ]
        })
        .populate({
          path: "maChiTietDienThoai",
          populate: [
            {
              path: "maDienThoai",
              model: "dienthoai",
              populate: [
                {path: 'maCuaHang', model: 'cuaHang'},
                {path: 'maUuDai', model: 'uudai', populate: 'maCuaHang'},
                {path: 'maHangSX', model: 'hangSanXuat'}
              ]
            },
            {path: "maMau", model: "mau"},
            {path: "maDungLuong", model: "dungluong"},
            {path: "maRam", model: "ram"}
          ]
        });
        // console.log("zzzz" , chiTietHoaDonList);
    res.json(chiTietHoaDonList);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});
module.exports = router;
