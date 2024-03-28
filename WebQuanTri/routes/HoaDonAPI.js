var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/HoaDon')

const HoaDon = mongoose.model("hoaDon")


/* GET users listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});

/* POST Mau. */

router.post('/addHoaDon', function (req, res, next) {
  const hoaDon = new HoaDon({
    tongTien: req.body.tongTien,
    ngayTao: req.body.ngayTao,
    trangThaiNhanHang: req.body.trangThaiNhanHang,
    phuongThucThanhToan: req.body.phuongThucThanhToan,
    maDiaChiNhanHang: req.body.maDiaChiNhanHang,
    maKhachHang: req.body.maKhachHang,
    maCuaHang: req.body.maCuaHang,
  })
  hoaDon.save()
      .then(data => {
        // console.log(data)
        res.send(data)
      }).catch(err => {
    console.log
  })
});

/* GET loaidichvu listing. */
router.get('/getHoaDon', async (req, res) => {
  try {
    const hoaDon = await HoaDon.find();
    res.json(hoaDon);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
})

router.get('/getHoaDonTheoTrangThai/:trangThaiNhanHang', async (req, res) => {
  try {
    const trangThaiNhanHang = req.params.trangThaiNhanHang;
    // const hoaDon = await HoaDon.find({ trangThaiNhanHang });
    const hoaDon = await HoaDon.find({trangThaiNhanHang})
        .populate("maKhachHang")
        .populate({path: "maDiaChiNhanHang", populate: {path: "maKhachHang", model: "khachhang"}})
        .populate("maCuaHang")
    res.json(hoaDon);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});

//lấy hóa đơn theo mã cửa hàng
router.get('/getHoaDonTheoTrangThai/:trangThaiNhanHang/:maCuaHang', async (req, res) => {
  try {
    const trangThaiNhanHang = req.params.trangThaiNhanHang;
    const maCuaHang = req.params.maCuaHang;
    const hoaDon = await HoaDon.find({trangThaiNhanHang, maCuaHang})
        .populate("maKhachHang")
        .populate({path: "maDiaChiNhanHang", populate: {path: "maKhachHang", model: "khachhang"}})
        .populate("maCuaHang")
    res.json(hoaDon);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});
// lấy hóa đơn theo mã khách hàng
router.get('/getHoaDonTheoTrangThai-KH/:trangThaiNhanHang/:maKhachHang', async (req, res) => {
  try {
    const trangThaiNhanHang = req.params.trangThaiNhanHang;
    const maKhachHang = req.params.maKhachHang;
//    console.log(trangThaiNhanHang, " / ", maKhachHang)
    const hoaDon = await HoaDon.find({trangThaiNhanHang, maKhachHang})
        .populate("maKhachHang")
        .populate({path: "maDiaChiNhanHang", populate: {path: "maKhachHang", model: "khachhang"}})
        .populate("maCuaHang")
    res.json(hoaDon);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});
router.get('/getHoaDonTheoChiTiet', async (req, res) => {
  try {
    const hoaDon = await HoaDon.find()
        .populate("maKhachHang")
        .populate({path: "maDiaChiNhanHang", populate: {path: "maKhachHang", model: "khachhang"}})
        .populate("maCuaHang")
    res.json(hoaDon);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});

router.delete('/deleteHoaDon/:id', async (req, res) => {
  try {
    const data = await HoaDon.findByIdAndDelete(req.params.id)
    if (!data) {
      return res.status(404).json({message: "delete failed"})
    } else {
      return res.status(200).json({message: "delete successful"})
    }
  } catch (err) {
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateHoaDon/:id", async (req, res) => {
  try {
    const data = await HoaDon.findByIdAndUpdate(req.params.id, req.body, {new: true})
    if (!data) {
      return res.status(404).json({message: "update failed"})

    } else {
      return res.status(200).json({message: "update successful"})

    }
  } catch (err) {
    return res.status(500).json({message: err.message})
  }
})

router.get('/getHoaDonTheoCuaHang/:maCuaHang', async (req, res) => {
  try {
    const maCuaHang = req.params.maCuaHang;
    const hoaDon = await HoaDon.find({maCuaHang})
        .populate("maKhachHang")
        .populate({path: "maDiaChiNhanHang", populate: {path: "maKhachHang", model: "khachhang"}})
        .populate("maCuaHang")
    res.json(hoaDon);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});

router.get('/searchHoaDon/:maCuaHang', async (req, res) => {
  try {
    const maCuaHang = req.params.maCuaHang;
    const { tenNguoiNhan, tongTien, trangThaiNhanHang, ngayTao, username } = req.query;

    let hoaDon = await HoaDon.find({maCuaHang})
        .populate("maKhachHang")
        .populate({path: "maDiaChiNhanHang", populate: {path: "maKhachHang", model: "khachhang"}})
        .populate("maCuaHang")

    hoaDon = hoaDon.filter(hd => {
      return (!tenNguoiNhan || hd.maDiaChiNhanHang.tenNguoiNhan.toLowerCase().includes(tenNguoiNhan.toLowerCase())) &&
          (!tongTien || hd.tongTien.toString() === tongTien) &&
          (!trangThaiNhanHang || hd.trangThaiNhanHang === trangThaiNhanHang) &&
          (!ngayTao || hd.ngayTao === ngayTao) &&
          (!username || hd.maKhachHang.username.toLowerCase().includes(username.toLowerCase()));
    });

    res.json(hoaDon);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});

module.exports = router;
