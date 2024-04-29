var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/ThongBao')
const ThongBao = mongoose.model("thongbao")


router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});

router.get('/getThongBao/:phanQuyen/:id', async (req, res) => {
  try {
    const thongBao = await ThongBao.find({phanQuyen: req.params.phanQuyen, maTaiKhoan: req.params.id})
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
        }).sort({thoiGian: -1})
    res.json(thongBao);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});

router.put("/updateTrangThaiThongBao/:id", async (req, res) => {
  try {
    const { trangThai } = req.body;
    const updated = await ThongBao.findByIdAndUpdate(
        req.params.id,
        { trangThai },
        { new: true }
    );

    if (!updated) {
      return res.status(404).json("update failed")

    } else {
      return res.status(200).json("update successful")

    }
  } catch (err) {
    console.log("errr: ", err)
    return res.status(500).json({message: err.message})
  }
})

router.put('/updateAll/:phanQuyen/:id', async (req, res) => {
  try {

    await ThongBao.updateMany({ phanQuyen: req.params.phanQuyen, maTaiKhoan: req.params.id }, { trangThai: 1 });

    res.json('Cập nhật trạng thái thông báo thành công' );
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});
module.exports = router;
